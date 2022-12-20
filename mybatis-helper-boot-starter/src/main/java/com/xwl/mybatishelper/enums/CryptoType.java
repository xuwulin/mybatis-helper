package com.xwl.mybatishelper.enums;

/**
 * @author xwl
 * @since 2022/12/7 11:00
 */
public enum CryptoType {
    /**
     * ENCRYPT 加密
     */
    ENCRYPT("encrypt"),

    /**
     * DECRYPT 解密
     */
    DECRYPT("decrypt");

    /**
     * 对应加密器方法名称
     */
    private String method;

    CryptoType() {
    }

    CryptoType(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
