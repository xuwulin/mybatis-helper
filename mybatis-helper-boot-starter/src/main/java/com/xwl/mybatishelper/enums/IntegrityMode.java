package com.xwl.mybatishelper.enums;

/**
 * 完整性计算模式
 *
 * @author xwl
 * @since 2022/12/7 10:56
 */
public enum IntegrityMode {
    /**
     * ALL 针对整条记录值（不包含原mac值，在计算新mac值时会自动将原记录中的mac值置空）进行计算
     */
    ALL,

    /**
     * CRYPTO_FIELD 针对加密字段进行完整性计算
     */
    CRYPTO_FIELD;
}
