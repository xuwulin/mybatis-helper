package com.xwl.mybatishelper.service.impl;

import com.xwl.mybatishelper.enums.IntegrityAlgorithm;
import com.xwl.mybatishelper.service.IIntegrity;

/**
 * 空实现，用于@IntegrityField注解属性iDesensitized 默认值
 * @author xwl
 * @since 2023/6/5 12:09
 */
public class NoneIntegrityImpl implements IIntegrity {
    @Override
    public String calc(IntegrityAlgorithm integrityAlgorithm, String... fields) {
        return null;
    }
}
