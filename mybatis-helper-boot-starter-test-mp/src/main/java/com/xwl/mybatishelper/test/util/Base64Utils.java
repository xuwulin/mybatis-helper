package com.xwl.mybatishelper.test.util;

import org.apache.commons.codec.binary.Base64;

public class Base64Utils {
    public Base64Utils() {
    }

    public static byte[] encodeByte(byte[] binaryData) {
        return Base64.encodeBase64(binaryData, false);
    }

    public static String encode(byte[] binaryData) {
        return Base64.encodeBase64String(binaryData);
    }

    public static byte[] decode(String base64String) {
        return (new Base64()).decode(base64String);
    }

    public static byte[] decode(byte[] base64Data) {
        return (new Base64()).decode(base64Data);
    }

    public static String decodeStr(String base64String) {
        return new String(decode(base64String));
    }
}