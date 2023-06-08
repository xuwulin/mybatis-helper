package com.xwl.mybatishelper.inteceptor;

import cn.hutool.json.JSONUtil;
import com.xwl.mybatishelper.annotation.IntegrityField;
import com.xwl.mybatishelper.enums.IntegrityAlgorithm;
import com.xwl.mybatishelper.properties.IntegrityProperties;
import com.xwl.mybatishelper.service.IIntegrity;
import com.xwl.mybatishelper.service.impl.NoneIntegrityImpl;
import com.xwl.mybatishelper.util.WrapperParamUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.chrono.ChronoLocalDate;
import java.util.*;

/**
 * mybatis数据完整性保护拦截器
 * 注解@Intercepts 标识该类是一个拦截器类
 * 注解@Signature 指明自定义拦截器要拦截哪一个类型的哪一个具体方法
 * type指明拦截对象的类型，method是拦截的方法，args是method执行的参数
 * 通过拦截Executor的query和update方法实现对sql的监控
 *
 * @author xwl
 * @since 2022/12/7 11:30
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class IntegrityInterceptor implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntegrityInterceptor.class);

    @Resource
    private IntegrityProperties integrityProperties;

    @Override
    public Object intercept(Invocation invocation) throws Exception {
        Method method = invocation.getMethod();
        LOGGER.info("==>com.xwl.plugin.inteceptor.IntegrityInterceptor 拦截方法：" + method);
        return updateHandle(invocation);
    }

    /**
     * 修改操作处理
     *
     * @param invocation
     * @return
     * @throws Exception
     */
    private Object updateHandle(Invocation invocation) throws Exception {
        // 拦截方法参数
        Object[] args = invocation.getArgs();
        // MappedStatement维护了一条<select|update|delete|insert>节点的封装
        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object paramObj = args[1];
        // 处理参数，更新参数加密
        handleParam(mappedStatement, paramObj);
        Object proceed = invocation.proceed();
        return proceed;
    }

    /**
     * 处理参数，完整性计算
     *
     * @param mappedStatement mappedStatement
     * @param obj             参数信息
     * @throws Exception
     */
    private void handleParam(MappedStatement mappedStatement, Object obj) throws Exception {
        HashMap<Field, Object> fieldMap = new HashMap<>();
        // 判断参数类型，如果是mybatis-plus的xxxService.getById(id)/xxxMapper.selectById(id)这种查询，参数类型就不是MapperMethod.ParamMap
        if (obj instanceof HashMap) {
            // pagehelper分页插件，分页查询，会拦截两次，第一次查总数，不包含分页参数：参数类型是MapperMethod.ParamMap；第二次查询结果集，包含分页参数：参数类型会转成HashMap
            // mybatis-plus分页插件，分页查询，只会拦截一次（包含查询参数和分页参数，查询总数和结果集），参数类型是MapperMethod.ParamMap
            HashMap paramMap = (HashMap) obj;
            Set keySet = paramMap.keySet();
            for (Object key : keySet) {
                String keyStr = (String) key;
                Object value = paramMap.get(key);
                if (value != null) {
                    if (filter(value)) {

                    } else if (value instanceof Collection) {

                    } else if (WrapperParamUtils.isWrapper(value)) {

                    } else {
                        // 参数是实体或者VO类型
                        getIntegrityField(value, value.getClass(), fieldMap);
                    }
                }
            }
        } else {
            if (obj != null) {
                getIntegrityField(obj, obj.getClass(), fieldMap);
            }
        }

        // 完整性值计算
        fieldMap.keySet().forEach(key -> {
            try {
                IntegrityHandle(key, fieldMap.get(key));
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        });
    }

    /**
     * 获取对象的完整性保护属性
     *
     * @param obj   对象
     * @param clazz 对象类型
     * @throws IllegalAccessException
     */
    private void getIntegrityField(Object obj, Class<?> clazz, HashMap<Field, Object> fieldMap) throws IllegalAccessException {
        // 过滤
        if (filter(obj)) {
            return;
        }

        // 获取对象属性（包含父类属性）
        List<Field> fields = getField(clazz, null);
        for (Field declaredField : fields) {
            // 静态属性直接跳过
            if (Modifier.isStatic(declaredField.getModifiers())) {
                continue;
            }
            boolean accessible = declaredField.isAccessible();
            declaredField.setAccessible(true);
            Object value = declaredField.get(obj);
            declaredField.setAccessible(accessible);
            IntegrityField annotation = declaredField.getAnnotation(IntegrityField.class);
            if (annotation != null) {
                fieldMap.put(declaredField, obj);
            }
        }
    }

    /**
     * 获取对象属性（包含父类属性）
     *
     * @param clazz  对象类型
     * @param fields 属性集合
     * @return
     */
    private List<Field> getField(Class<?> clazz, List<Field> fields) {
        if (fields == null) {
            fields = new ArrayList<>();
        }
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null && !superclass.equals(Object.class) && superclass.getDeclaredFields().length > 0) {
            getField(superclass, fields);
        }
        for (Field declaredField : clazz.getDeclaredFields()) {
            int modifiers = declaredField.getModifiers();
            if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers) || Modifier.isVolatile(modifiers) || Modifier.isSynchronized(modifiers)) {
                continue;
            }
            fields.add(declaredField);
        }
        return fields;
    }

    /**
     * 完整性值计算
     *
     * @param field  字段
     * @param object 对象
     * @throws Exception
     */
    private void IntegrityHandle(Field field, Object object) throws Exception {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        Object value = field.get(object);
        IntegrityField annotation = field.getAnnotation(IntegrityField.class);
        if (annotation != null) {
            // 完整性保护算法（优先使用注解上的配置）
            IntegrityAlgorithm integrityAlgorithm = annotation.algorithm();
            if (integrityAlgorithm == null || integrityAlgorithm == IntegrityAlgorithm.NONE) {
                integrityAlgorithm = IntegrityAlgorithm.valueOf(integrityProperties.getMode());
            }

            IIntegrity iIntegrity;
            Class<? extends IIntegrity> integrityImpl = annotation.iIntegrity();
            if (integrityImpl == null || integrityImpl == NoneIntegrityImpl.class) {
                // 使用配置文件中配置的脱敏器
                Class desensitizedClazz = Class.forName(integrityProperties.getClassName());
                iIntegrity = (IIntegrity) desensitizedClazz.newInstance();
            } else {
                // 使用注解上的完整性保护算法
                iIntegrity = integrityImpl.newInstance();
            }
            String result = iIntegrity.calc(integrityAlgorithm, JSONUtil.toJsonStr(object));
            if (integrityProperties.isEnableDetailLog()) {
                LOGGER.info("完整性保护计算，参数：{}，结果：{}", JSONUtil.toJsonStr(object), result);
            }
            field.set(object, String.valueOf(result));
            field.setAccessible(accessible);
        }
    }

    /**
     * 过滤对象类型
     *
     * @param object 对象
     * @return
     */
    private boolean filter(Object object) {
        return object == null
                || object instanceof CharSequence
                || object instanceof Number
                || object instanceof Date
                || object instanceof ChronoLocalDate;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
