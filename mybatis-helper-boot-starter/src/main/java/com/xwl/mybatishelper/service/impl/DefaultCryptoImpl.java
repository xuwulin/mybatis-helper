package com.xwl.mybatishelper.service.impl;

import com.xwl.mybatishelper.enums.CryptoAlgorithm;
import com.xwl.mybatishelper.service.ICrypto;
import com.xwl.mybatishelper.util.CryptoUtils;

/**
 * 加解密实现
 *
 * @author xwl
 * @since 2022/12/7 11:07
 */
public class DefaultCryptoImpl implements ICrypto {
    @Override
    public String encrypt(CryptoAlgorithm cryptoAlgorithm,
                          String plaintext,
                          String key,
                          String publicKey,
                          String privateKey) throws Exception {
        switch (cryptoAlgorithm) {
            case SM2:
                return CryptoUtils.sm2Encrypt(plaintext, publicKey, privateKey);
            case SM3:
                return CryptoUtils.sm3Encrypt(plaintext);
            case SM4:
                return CryptoUtils.sm4Encrypt(plaintext, key);
            default:
                return plaintext;
        }
    }

    @Override
    public String decrypt(CryptoAlgorithm cryptoAlgorithm,
                          String ciphertext,
                          String key,
                          String publicKey,
                          String privateKey) throws Exception {
        switch (cryptoAlgorithm) {
            case SM2:
                return CryptoUtils.sm2Decrypt(ciphertext, publicKey, privateKey);
            case SM3:
                return CryptoUtils.sm3Decrypt(ciphertext);
            case SM4:
                return CryptoUtils.sm4Decrypt(ciphertext, key);
            default:
                return ciphertext;
        }
    }
}
