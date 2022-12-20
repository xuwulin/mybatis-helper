package com.xwl.mybatishelper.enums;

/**
 * @author xwl
 * @since 2022/12/7 10:56
 */
public enum CryptoAlgorithm {
    /**
     * NONE 不指定，通过全局配置文件指定加密方式
     */
    NONE,

    /**
     * 国密 SM2 非对称加密算法，基于 ECC
     */
    SM2,

    /**
     * 国密 SM3 消息摘要算法，可以用 MD5 作为对比理解
     */
    SM3,

    /**
     * 国密 SM4 对称加密算法，无线局域网标准的分组数据算法
     */
    SM4,

    CryptoAlgorithm() {
    }
}
