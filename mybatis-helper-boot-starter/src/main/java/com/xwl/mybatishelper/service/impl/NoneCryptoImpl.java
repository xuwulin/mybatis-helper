package com.xwl.mybatishelper.service.impl;

import com.xwl.mybatishelper.enums.CryptoAlgorithm;
import com.xwl.mybatishelper.service.ICrypto;

/**
 * 空实现，用于@CryptoField注解属性iCrypto 默认值
 *
 * @author xwl
 * @since 2022/12/15 21:39
 */
public class NoneCryptoImpl implements ICrypto {
    @Override
    public String encrypt(CryptoAlgorithm cryptoAlgorithm, String plaintext, String key, String publicKey, String privateKey) throws Exception {
        return plaintext;
    }

    @Override
    public String decrypt(CryptoAlgorithm cryptoAlgorithm, String ciphertext, String key, String publicKey, String privateKey) throws Exception {
        return ciphertext;
    }
}
