package com.xwl.mybatishelper.inteceptor;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.xwl.mybatishelper.enums.CryptoAlgorithm;
import com.xwl.mybatishelper.enums.CryptoType;
import com.xwl.mybatishelper.service.ICrypto;
import com.xwl.mybatishelper.service.impl.NoneCryptoImpl;
import com.xwl.mybatishelper.util.ParameterUtils;
import com.xwl.mybatishelper.annotation.CryptoField;
import com.xwl.mybatishelper.properties.CryptoProperties;
import org.apache.ibatis.binding.MapperMethod;
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
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.time.chrono.ChronoLocalDate;
import java.util.*;

/**
 * mybatis加解密拦截器
 * 注解@Intercepts 标识该类是一个拦截器类
 * 注解@Signature 指明自定义拦截器要拦截哪一个类型的哪一个具体方法
 * type指明拦截对象的类型，method是拦截的方法，args是method执行的参数
 * 通过拦截Executor的query和update方法实现对sql的监控
 *
 * @author xwl
 * @since 2022/12/7 11:30
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
})
public class CryptoInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(CryptoInterceptor.class);

    @Resource
    private CryptoProperties cryptoProperties;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        logger.info("==>com.xwl.plugin.inteceptor.CryptoInterceptor 拦截方法：" + method);
        switch (method.getName()) {
            case "update":
                return updateHandle(invocation);
            case "query":
                return selectHandle(invocation);
            default:
                return invocation.proceed();
        }
    }

    /**
     * 查询操作处理
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    private Object selectHandle(Invocation invocation) throws Throwable {
        // 拦截方法参数
        Object[] args = invocation.getArgs();
        // MappedStatement维护了一条<select|update|delete|insert>节点的封装
        MappedStatement mappedStatement = (MappedStatement) args[0];
        // 查询参数
        /*
        args[1]是一个Object类型，实际类型会根据Mapper方法的参数数量和类型发生变化。
        因此需要分多种情况考虑。

        1）无参数
        Mapper执行的是一个无参方法时，args[1]为null。
        需要创建一个MapperMethod.ParamMap对象，然后将自定义的参数添加进去，再赋值给arg[1]就可以了。
        MapperMethod.ParamMap是一个继承于Map的类。把他当作一个Map<String,Object>就可以了。

        2）一个参数
        Mapper执行的是一个单一参数方法时，args[1]是Object类型。
        需要创建一个MapperMethod.ParamMap对象，将已经有的参数放进去，然后将自定义参数放进去。
        如果args[1]是一个基本数据类型，就存在一个问题，Map是key-value结构，args[1]只有值，没有参数名。我们知道value是不够的，sql解析时根据参数名去找对应的value。
        所以我们需要去反射实际执行的mapper，拿到对应的参数名。
        如果args[1]是一个引用类型，我们就需要解析他所有的字段，将字段名和其值组成key-value键值对，存储到Map中。
        因为单一参数是引用时，mybatis会直接忽略参数名，直接匹配参数中的字段名。

        3）多个参数
        Mapper执行的是一个多参数方法时，args[1]已经是MapperMethod.ParamMap类型。
        所以我们无需修改args[1]的类型，直接将自定义的参数添加进去就可以了。
        */
        Object paramObj = args[1];
        // 分页对象
        RowBounds rowBounds = (RowBounds) args[2];
        // 处理Statement执行完成后返回结果集的接口对象，mybatis通过它把ResultSet集合映射成实体对象
        ResultHandler<Object> resultHandler = (ResultHandler) args[3];
        // @Signature 指定了 type= Executor 后，这里的 invocation.getTarget() 便是Executor
        Executor executor = (Executor) invocation.getTarget();

        CacheKey cacheKey;
        BoundSql boundSql;

        // 处理参数作为条件查询需要加密
        handleParameterOrResult(paramObj, CryptoType.ENCRYPT);

        // 由于逻辑关系，只会进入一次
        if (args.length == 4) {
            // 4 个参数
            // 获取SQL：表示动态生成的SQL语句以及相应的参数信息
            boundSql = mappedStatement.getBoundSql(paramObj);
            cacheKey = executor.createCacheKey(mappedStatement, paramObj, rowBounds, boundSql);
        } else {
            // 6 个参数（分页查询）
            cacheKey = (CacheKey) args[4];
            boundSql = (BoundSql) args[5];
        }

        // 执行查询mappedStatement = {MappedStatement@6629}
        List<Object> resultList = executor.query(mappedStatement, paramObj, rowBounds, resultHandler, cacheKey, boundSql);
        for (Object result : resultList) {
            // 处理查询结果，如果有解密字段，则执行解密
            handleParameterOrResult(result, CryptoType.DECRYPT);
        }

        return resultList;
    }

    /**
     * 新增修改操作处理
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    private Object updateHandle(Invocation invocation) throws Throwable {
        // 处理参数
        handleParameterOrResult(invocation.getArgs()[1], CryptoType.ENCRYPT);
        return invocation.proceed();
    }

    /**
     * 处理参数或结果
     *
     * @param obj        参数或结果
     * @param cryptoType 加解密类型
     * @throws IllegalAccessException
     */
    private void handleParameterOrResult(Object obj, CryptoType cryptoType) throws Exception {
        HashMap<Field, Object> fieldObjectHashMap = new HashMap<>();
        // 多个参数
        if (obj instanceof MapperMethod.ParamMap) {
            // pagehelper分页插件，分页查询，会拦截两次，第一次查总数，不包含分页参数：参数类型是MapperMethod.ParamMap；第二次查询结果集，包含分页参数：参数类型会转成HashMap
            // mybatis-plus分页插件，分页查询，只会拦截一次（包含查询参数和分页参数，查询总数和结果集），参数类型是MapperMethod.ParamMap
            MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) obj;
            Set keySet = paramMap.keySet();
            for (Object key : keySet) {
                String keyStr = (String) key;
                Object value = paramMap.get(key);
                if (value != null) {
                    if (filter(value)) {
                        if (keyStr.startsWith(cryptoProperties.getParamPrefix())) {
                            String ciphertext = getCiphertext(value);
                            paramMap.put(key, ciphertext);
                        }
                    } else if (value instanceof Collection) {
                        if (keyStr.startsWith(cryptoProperties.getParamPrefix())) {
                            Collection values = (Collection) value;
                            if (CollectionUtil.isNotEmpty(values)) {
                                List<Object> ciphertextList = new ArrayList<>();
                                for (Object o : values) {
                                    ciphertextList.add(getCiphertext(o));
                                }
                                paramMap.put(key, ciphertextList);
                            }
                        }
                    } else if (ParameterUtils.isWrapper(value)) {
                        if (!keyStr.startsWith("param")) {
                            ParameterUtils.handleWrapper(value, cryptoProperties);
                        }
                    } else {
                        handleObject(value, value.getClass(), fieldObjectHashMap);
                    }
                }
            }
        } else {
            if (obj != null) {
                handleObject(obj, obj.getClass(), fieldObjectHashMap);
            }
        }

        // 加解密
        fieldObjectHashMap.keySet().forEach(key -> {
            try {
                handleString(key, fieldObjectHashMap.get(key), cryptoType);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
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
    private void handleObject(Object obj, Class<?> oClass, HashMap<Field, Object> fieldObjectHashMap) throws IllegalAccessException {
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
                CryptoField annotation = declaredField.getAnnotation(CryptoField.class);
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
     * 处理字符，加解密
     *
     * @param field      字段
     * @param object     对象
     * @param cryptoType 加解密类型
     * @throws Exception
     */
    private void handleString(Field field, Object object, CryptoType cryptoType) throws Exception {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        Object value = field.get(object);

        CryptoField annotation = field.getAnnotation(CryptoField.class);
        if (annotation != null) {
            // 对称加密密钥
            String propertiesKey = cryptoProperties.getKey();
            // 非对称加密私钥
            String privateKey = cryptoProperties.getPrivateKey();
            // 非对称加密公钥
            String publicKey = cryptoProperties.getPublicKey();
            // 对称加密密钥（优先使用注解上的配置）
            String key = annotation.key();
            if (StrUtil.isBlank(key)) {
                key = propertiesKey;
            }
            // 加密算法（优先使用注解上的配置）
            CryptoAlgorithm cryptoAlgorithm = annotation.algorithm();
            if (cryptoAlgorithm == null || cryptoAlgorithm == CryptoAlgorithm.NONE) {
                cryptoAlgorithm = CryptoAlgorithm.valueOf(cryptoProperties.getMode());
            }
            // 加密实现（优先使用注解上的配置）
            ICrypto iCrypto;
            Class<? extends ICrypto> iCryptoImpl = annotation.iCrypto();
            if (iCryptoImpl == null || iCryptoImpl == NoneCryptoImpl.class) {
                Class cryptClazz = Class.forName(cryptoProperties.getClassName());
                iCrypto = (ICrypto) cryptClazz.newInstance();
            } else {
                iCrypto = iCryptoImpl.newInstance();
            }

            String valueResult;
            if (cryptoType.equals(CryptoType.DECRYPT)) {
                valueResult = iCrypto.decrypt(cryptoAlgorithm, String.valueOf(value), key, publicKey, privateKey);
            } else {
                valueResult = iCrypto.encrypt(cryptoAlgorithm, String.valueOf(value), key, publicKey, privateKey);
            }
            field.set(object, String.valueOf(valueResult));
            field.setAccessible(accessible);
        }
    }

    /**
     * 获取密文（对Mapper的@Param参数进行加密），使用全局配置文件中的加密参数
     *
     * @param value 明文
     * @return 密文
     * @throws Exception
     */
    private String getCiphertext(Object value) throws Exception {
        // 加密方式
        String mode = cryptoProperties.getMode();
        // 加密类全类名
        String cryptoClassName = cryptoProperties.getClassName();
        // 对称加密密钥
        String propertiesKey = cryptoProperties.getKey();
        // 非对称加密私钥
        String privateKey = cryptoProperties.getPrivateKey();
        // 非对称加密公钥
        String publicKey = cryptoProperties.getPublicKey();

        CryptoAlgorithm cryptoAlgorithm = CryptoAlgorithm.valueOf(mode);
        Class cryptClazz = Class.forName(cryptoClassName);
        ICrypto iCrypto = (ICrypto) cryptClazz.newInstance();

        String ciphertext = iCrypto.encrypt(cryptoAlgorithm, String.valueOf(value), propertiesKey, publicKey, privateKey);
        return ciphertext;
    }

    private boolean isBase(Type type) {
        return boolean.class.equals(type)
                || char.class.equals(type)
                || long.class.equals(type)
                || int.class.equals(type)
                || byte.class.equals(type)
                || short.class.equals(type)
                || double.class.equals(type)
                || float.class.equals(type);
    }

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
