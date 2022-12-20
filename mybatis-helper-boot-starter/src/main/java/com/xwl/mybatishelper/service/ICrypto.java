package com.xwl.mybatishelper.service;

import com.xwl.mybatishelper.enums.CryptoAlgorithm;

/**
 * 加解密接口
 *
 * @author xwl
 * @since 2022/12/7 10:54
 */
public interface ICrypto {
    /**
     * 加密
     *
     * @param cryptoAlgorithm 加密算法
     * @param plaintext       明文
     * @param key             对称加密密钥（SM4）
     * @param publicKey       非对称加密公钥（SM2）
     * @param privateKey      非对称加密私钥（SM2）
     * @return 密文
     * @throws Exception
     */
    String encrypt(CryptoAlgorithm cryptoAlgorithm,
                   String plaintext,
                   String key,
                   String publicKey,
                   String privateKey) throws Exception;

    /**
     * 解密
     *
     * @param cryptoAlgorithm 解密算法
     * @param ciphertext      密文
     * @param key             对称加密密钥（SM4）
     * @param publicKey       非对称加密公钥（SM2）
     * @param privateKey      非对称加密私钥（SM2）
     * @return 明文
     * @throws Exception
     */
    String decrypt(CryptoAlgorithm cryptoAlgorithm,
                   String ciphertext,
                   String key,
                   String publicKey,
                   String privateKey) throws Exception;
}
