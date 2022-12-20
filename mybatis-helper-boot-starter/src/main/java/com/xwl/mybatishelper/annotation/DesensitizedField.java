package com.xwl.mybatishelper.annotation;

import com.xwl.mybatishelper.service.IDesensitized;
import com.xwl.mybatishelper.service.impl.NoneDesensitizedImpl;
import com.xwl.mybatishelper.enums.DesensitizedType;

import java.lang.annotation.*;

/**
 * 字段脱敏注解
 *
 * @author xwl
 * @since 2022/12/15 13:15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface DesensitizedField {
    /**
     * 填充值
     * 注解默认值为""，使用全局配置文件中指定的值（配置文件默认*），方便全局替换使用自定义填充值
     * 当注解值不为""时，使用注解值
     *
     * @return
     */
    String replacement() default "";

    /**
     * 脱敏类型
     * 注解默认值为DesensitizedType.DEFAULT，使用正则表达式自动匹配（目前只能匹配：手机号、邮箱、银行卡号、身份证号）
     * 当注解值不为DesensitizedType.DEFAULT时，使用注解值
     *
     * @return
     */
    DesensitizedType type() default DesensitizedType.DEFAULT;

    /**
     * 脱敏实现
     * 注解默认值为NoneDesensitizedImpl.class，使用全局配置文件中指定的值（配置文件默认DefaultDesensitizedImpl.class），方便全局替换使用自定义脱敏实现
     * 当注解值不为NoneDesensitizedImpl.class时，使用使用注解值
     * 注：NoneDesensitizedImpl.class可以理解为null，因为注解属性的默认值不能指定为null，所以使用NoneDesensitizedImpl替换
     *
     * @return
     */
    Class<? extends IDesensitized> iDesensitized() default NoneDesensitizedImpl.class;
}
