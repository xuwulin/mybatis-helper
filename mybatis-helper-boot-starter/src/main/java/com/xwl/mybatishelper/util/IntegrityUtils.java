package com.xwl.mybatishelper.util;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * @author xwl
 * @since 2023/6/5 12:13
 */
public class IntegrityUtils {
    public static String md5(String value) {
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        return md5.digestHex(value);
    }

    public static String sha1(String value) {
        Digester sha1 = new Digester(DigestAlgorithm.SHA1);
        return sha1.digestHex(value);
    }

    public static String sha256(String value) {
        Digester sha256 = new Digester(DigestAlgorithm.SHA1);
        return sha256.digestHex(value);
    }
}
