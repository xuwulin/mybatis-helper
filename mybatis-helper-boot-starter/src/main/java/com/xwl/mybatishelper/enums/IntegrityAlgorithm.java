package com.xwl.mybatishelper.enums;

/**
 * 完整性算法
 *
 * @author xwl
 * @since 2022/12/7 10:56
 */
public enum IntegrityAlgorithm {
    /**
     * NONE 不指定，通过全局配置文件指定加密方式
     */
    NONE,

    /**
     * MD5
     */
    MD5,

    /**
     * SHA1
     */
    SHA1,

    /**
     * SHA256
     */
    SHA256
}
