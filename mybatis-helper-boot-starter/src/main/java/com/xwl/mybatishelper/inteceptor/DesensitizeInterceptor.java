package com.xwl.mybatishelper.inteceptor;

import cn.hutool.core.util.StrUtil;
import com.xwl.mybatishelper.properties.DesensitizedProperties;
import com.xwl.mybatishelper.service.IDesensitized;
import com.xwl.mybatishelper.service.impl.NoneDesensitizedImpl;
import com.xwl.mybatishelper.annotation.DesensitizedField;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.chrono.ChronoLocalDate;
import java.util.*;

/**
 * mybatis脱敏拦截器
 *
 * @author xwl
 * @since 2022/12/15 13:08
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
public class DesensitizeInterceptor implements Interceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DesensitizeInterceptor.class);

    @Resource
    private DesensitizedProperties desensitizedProperties;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return selectHandle(invocation);
    }

    /**
     * 查询操作处理
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    private Object selectHandle(Invocation invocation) throws Exception {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler<Object> resultHandler = (ResultHandler) args[3];
        Executor executor = (Executor) invocation.getTarget();

        CacheKey cacheKey;
        BoundSql boundSql;

        if (args.length == 4) {
            boundSql = ms.getBoundSql(parameter);
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else {
            cacheKey = (CacheKey) args[4];
            boundSql = (BoundSql) args[5];
        }

        List<Object> resultList = executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
        for (Object object : resultList) {
            HashMap<Field, Object> fieldObjectHashMap = new HashMap<>();
            if (object != null) {
                handleObject(object, object.getClass(), fieldObjectHashMap);
            }
            // 对查询结果进行脱敏处理
            fieldObjectHashMap.keySet().forEach(key -> {
                try {
                    desensitizeHandle(key, fieldObjectHashMap.get(key));
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            });
        }
        return resultList;
    }

    private boolean filter(Object object) {
        return object == null
                || object instanceof CharSequence
                || object instanceof Number
                || object instanceof Collection
                || object instanceof Date
                || object instanceof ChronoLocalDate;
    }

    /**
     * 聚合父类属性
     *
     * @param oClass
     * @param fields
     * @return
     */
    private List<Field> mergeField(Class<?> oClass, List<Field> fields) {
        if (fields == null) {
            fields = new ArrayList<>();
        }
        Class<?> superclass = oClass.getSuperclass();
        if (superclass != null && !superclass.equals(Object.class) && superclass.getDeclaredFields().length > 0) {
            mergeField(superclass, fields);
        }
        for (Field declaredField : oClass.getDeclaredFields()) {
            int modifiers = declaredField.getModifiers();
            if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers) || Modifier.isVolatile(modifiers) || Modifier.isSynchronized(modifiers)) {
                continue;
            }
            fields.add(declaredField);
        }
        return fields;
    }

    /**
     * 处理Object
     *
     * @param obj
     * @param oClass
     * @throws IllegalAccessException
     */
    private void handleObject(Object obj, Class<?> oClass, HashMap<Field, Object> fieldObjectHashMap) throws Exception {
        // 过滤
        if (filter(obj)) {
            return;
        }
        List<Field> fields = mergeField(oClass, null);
        for (Field declaredField : fields) {
            // 静态属性直接跳过
            if (Modifier.isStatic(declaredField.getModifiers())) {
                continue;
            }
            boolean accessible = declaredField.isAccessible();
            declaredField.setAccessible(true);
            Object value = declaredField.get(obj);
            declaredField.setAccessible(accessible);
            if (value == null) {
                continue;
            } else if (value instanceof Number) {
                continue;
            } else if (value instanceof String) {
                DesensitizedField annotation = declaredField.getAnnotation(DesensitizedField.class);
                if (annotation != null) {
                    fieldObjectHashMap.put(declaredField, obj);
                }
            } else if (value instanceof Collection) {
                Collection coll = (Collection) value;
                for (Object o : coll) {
                    if (filter(o)) {
                        // 默认集合内类型一致
                        break;
                    }
                    handleObject(o, o.getClass(), fieldObjectHashMap);
                }
            } else {
                handleObject(value, value.getClass(), fieldObjectHashMap);
            }
        }
    }

    /**
     * 脱敏处理
     *
     * @param field
     * @param object
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void desensitizeHandle(Field field, Object object) throws Exception {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        Object value = field.get(object);
        DesensitizedField annotation = field.getAnnotation(DesensitizedField.class);
        if (annotation != null) {
            String replacement = annotation.replacement();
            if (StrUtil.isBlank(replacement)) {
                replacement = desensitizedProperties.getReplacement();
            }
            IDesensitized iDesensitized;
            Class<? extends IDesensitized> desensitizedImpl = annotation.iDesensitized();
            if (desensitizedImpl == null || desensitizedImpl == NoneDesensitizedImpl.class) {
                // 使用配置文件中配置的脱敏器
                Class desensitizedClazz = Class.forName(desensitizedProperties.getClassName());
                iDesensitized = (IDesensitized) desensitizedClazz.newInstance();
            } else {
                // 使用注解上的脱敏器
                iDesensitized = desensitizedImpl.newInstance();
            }
            String desensitizeValue = iDesensitized.execute(String.valueOf(value), replacement, annotation.type());
            LOGGER.debug("脱敏前：{}，脱敏后：{}", value, desensitizeValue);
            field.set(object, String.valueOf(desensitizeValue));
            field.setAccessible(accessible);
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
