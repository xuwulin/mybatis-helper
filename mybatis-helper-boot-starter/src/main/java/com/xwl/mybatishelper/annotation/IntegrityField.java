package com.xwl.mybatishelper.annotation;

import com.xwl.mybatishelper.enums.IntegrityAlgorithm;
import com.xwl.mybatishelper.service.IIntegrity;
import com.xwl.mybatishelper.service.impl.NoneIntegrityImpl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据完整性注解
 *
 * @author xwl
 * @since 2022/12/7 11:25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface IntegrityField {

    /**
     * 完整性保护算法
     * 注解默认值为IntegrityAlgorithm.NONE，使用全局配置文件中指定的完整性保护算法，方便全局替换使用自定义填充值
     * 当注解值不为IntegrityAlgorithm.NONE时，使用注解值
     *
     * @return
     */
    IntegrityAlgorithm algorithm() default IntegrityAlgorithm.NONE;

    /**
     * 完整性保护算法器
     * 注解默认值为NoneIntegrity.class，使用全局配置文件中指定的值（配置文件默认DefaultIntegrity.class），方便全局替换使用自定义完整性保护实现
     * 当注解值不为NoneIntegrity.class时，使用使用注解值
     * 注：NoneIntegrity.class可以理解为null，因为注解属性的默认值不能指定为null，所以使用NoneIntegrityImpl替换
     *
     * @return
     */
    Class<? extends IIntegrity> iIntegrity() default NoneIntegrityImpl.class;
}
