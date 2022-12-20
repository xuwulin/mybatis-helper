package com.xwl.mybatishelper.annotation;

import com.xwl.mybatishelper.enums.CryptoAlgorithm;
import com.xwl.mybatishelper.service.ICrypto;
import com.xwl.mybatishelper.service.impl.NoneCryptoImpl;

import java.lang.annotation.*;

/**
 * 字段加解密注解
 *
 * @author xwl
 * @since 2022/12/7 11:25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface CryptoField {
    /**
     * 对称加密秘钥（对称加密用，必须是16位字符串）
     * 注解默认值为""，使用全局配置文件中指定的值，方便全局替换使用自定义填充值
     * 当注解值不为""时，使用注解值
     *
     * @return
     */
    String key() default "";

    /**
     * 加密解密算法
     * 注解默认值为CryptoAlgorithm.NONE，使用全局配置文件中指定的加密算法，方便全局替换使用自定义填充值
     * 当注解值不为CryptoAlgorithm.NONE时，使用注解值
     *
     * @return
     */
    CryptoAlgorithm algorithm() default CryptoAlgorithm.NONE;

    /**
     * 加密解密器
     * 注解默认值为NoneCryptoImpl.class，使用全局配置文件中指定的值（配置文件默认DefaultCryptoImpl.class），方便全局替换使用自定义脱敏实现
     * 当注解值不为NoneCryptoImpl.class时，使用使用注解值
     * 注：NoneCryptoImpl.class可以理解为null，因为注解属性的默认值不能指定为null，所以使用NoneCryptoImpl替换
     *
     * @return
     */
    Class<? extends ICrypto> iCrypto() default NoneCryptoImpl.class;
}
