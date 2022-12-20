package com.xwl.mybatishelper.test.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * SM4 加解密工具类
 * @author XUNING
 */
@Slf4j
public class SM4Utils {

    /**
     * 密钥
     */
    private static final String KEY = "JEF8U9WHKWEFS5KD";

    /**
     * 加密
     * @param plainText
     * @return
     */
    public static String encryptData_ECB(String plainText) {
        return encryptData_ECB(plainText, KEY);
    }

    /**
     * 解密
     * @param plainText
     * @return
     */
    public static String decryptData_ECB(String plainText) {
        if (StringUtils.isBlank(plainText)) {
            return "";
        }
        return decryptData_ECB(plainText, KEY);
    }

    /**
     * 使用指定的 密钥key 加密
     * @param plainText 需要加密的字符串
     * @param key 密钥key
     * @return
     */
    public static String encryptData_ECB(String plainText, String key) {
        try {
            SM4Context ctx = new SM4Context();
            ctx.isPadding = true;
            ctx.mode = 1;
            byte[] keyBytes = key.getBytes("UTF-8");
            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_ecb(ctx, plainText.getBytes("UTF-8"));
            return Base64Utils.encode(encrypted);
        } catch (Exception var6) {
            log.error("数据加密失败，直接返回原始数据");
            return plainText;
        }
    }

    /**
     * 使用指定的 密钥key 解密
     * @param cipherText
     * @param key 密钥key
     * @return
     */
    public static String decryptData_ECB(String cipherText, String key) {
        try {
            if (!isBase64(cipherText)) {
                return cipherText;
            } else {
                SM4Context ctx = new SM4Context();
                ctx.isPadding = true;
                ctx.mode = 0;
                byte[] keyBytes = key.getBytes("UTF-8");
                SM4 sm4 = new SM4();
                sm4.sm4_setkey_dec(ctx, keyBytes);
                byte[] decrypted = sm4.sm4_crypt_ecb(ctx, Base64Utils.decode(cipherText));
                return new String(decrypted, "UTF-8");
            }
        } catch (Exception e) {
            log.error("数据解密失败，直接返回原始内容");
            return cipherText;
        }
    }

    private static boolean isBase64(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else if (str.length() % 4 != 0) {
            return false;
        } else {
            char[] strChars = str.toCharArray();
            char[] var2 = strChars;
            int var3 = strChars.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                char c = var2[var4];
                if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c < '0' || c > '9') && c != '+' && c != '/' && c != '=') {
                    return false;
                }
            }
            return true;
        }
    }

    public static void main(String[] args) {
        String enc = encryptData_ECB("510623199310124817");
        System.out.println(enc);
        System.out.println(decryptData_ECB(enc));
    }

}
