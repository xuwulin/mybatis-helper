package com.xwl.mybatishelper.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.symmetric.SM4;

import java.security.KeyPair;

/**
 * 加解密工具类，基于hutool
 *
 * @author xwl
 * @since 2022/12/7 11:10
 */
public class CryptoUtils {
    /**
     * 默认密钥
     */
    private final static String KEY = "a3deb145671dd04e";

    /**
     * 默认私钥
     */
    private final static String PRIVATE_KEY = "308193020100301306072a8648ce3d020106082a811ccf5501822d047930770201010420169d98f96e7a3deb145671dd04eefdf54b3c2196ca5569b5fb96a9ff5abe957da00a06082a811ccf5501822da14403420004e6d02503196bda21356390f43e324aa9cb42dbe64204ac4d9e1652fe055e4636702f84f376a54af81ca165d355f3e68d137702b9c715b4ecf70d50b65d9d6e78";

    /**
     * 默认公钥
     */
    private final static String PUBLIC_KEY = "3059301306072a8648ce3d020106082a811ccf5501822d03420004e6d02503196bda21356390f43e324aa9cb42dbe64204ac4d9e1652fe055e4636702f84f376a54af81ca165d355f3e68d137702b9c715b4ecf70d50b65d9d6e78";

    /**
     * SM2算法加密
     *
     * @param plaintext  明文
     * @param publicKey  公钥（加密）
     * @param privateKey 私钥（解密）
     * @return
     */
    public static String sm2Encrypt(String plaintext, String publicKey, String privateKey) {
        if (isNull(publicKey, privateKey)) {
            publicKey = PUBLIC_KEY;
            privateKey = PRIVATE_KEY;
        }
        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);
        return sm2.encryptBcd(plaintext, KeyType.PublicKey);
    }

    /**
     * SM2算法解密
     *
     * @param ciphertext 密文
     * @param publicKey  公钥（加密）
     * @param privateKey 私钥（解密）
     * @return
     */
    public static String sm2Decrypt(String ciphertext, String publicKey, String privateKey) {
        if (isNull(publicKey, privateKey)) {
            publicKey = PUBLIC_KEY;
            privateKey = PRIVATE_KEY;
        }
        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);
        return StrUtil.utf8Str(sm2.decryptFromBcd(ciphertext, KeyType.PrivateKey));
    }

    /**
     * SM3算法加密
     *
     * @param plaintext 明文
     * @return
     */
    public static String sm3Encrypt(String plaintext) {
        return SmUtil.sm3(plaintext);
    }

    /**
     * SM3算法解密
     *
     * @param ciphertext 密文
     * @return SM3算法不可解密，类似MD5，直接返回密文
     */
    public static String sm3Decrypt(String ciphertext) {
        return ciphertext;
    }

    /**
     * SM4算法解密
     *
     * @param plaintext 明文
     * @param key       密钥
     * @return
     */
    public static String sm4Encrypt(String plaintext, String key) {
        if (isNull(key)) {
            key = KEY;
        }
        SM4 sm4 = SmUtil.sm4(key.getBytes());
        return sm4.encryptHex(plaintext);
    }

    /**
     * SM4算法解密
     *
     * @param ciphertext 密文
     * @param key        密钥
     * @return
     */
    public static String sm4Decrypt(String ciphertext, String key) {
        if (isNull(key)) {
            key = KEY;
        }
        SM4 sm4 = SmUtil.sm4(key.getBytes());
        return sm4.decryptStr(ciphertext, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 判断是否为空
     *
     * @param key 密钥
     * @return
     */
    private static Boolean isNull(String key) {
        return key == null || "".equals(key);
    }

    /**
     * 判断是否为空
     *
     * @param privateKey 私钥
     * @param publicKey  公钥
     * @return
     */
    private static Boolean isNull(String publicKey, String privateKey) {
        return (publicKey == null || "".equals(publicKey)) || (privateKey == null || "".equals(privateKey));
    }

    public static void main(String[] args) {
        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();
        String privateKeyStr = HexUtil.encodeHexStr(privateKey);
        String publicKeyStr = HexUtil.encodeHexStr(publicKey);
        System.out.println("privateKey: " + privateKeyStr);
        System.out.println("publicKey: " + publicKeyStr);
    }
}
