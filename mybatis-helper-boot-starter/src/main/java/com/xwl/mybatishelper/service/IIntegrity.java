package com.xwl.mybatishelper.service;

import com.xwl.mybatishelper.enums.IntegrityAlgorithm;

/**
 * @author xwl
 * @since 2023/6/5 12:06
 */
public interface IIntegrity {
    /**
     * 数据完整性保护
     *
     * @param integrityAlgorithm 完整性算法
     * @param fields             字段数组
     * @return
     */
    String calc(IntegrityAlgorithm integrityAlgorithm, String... fields);
}
