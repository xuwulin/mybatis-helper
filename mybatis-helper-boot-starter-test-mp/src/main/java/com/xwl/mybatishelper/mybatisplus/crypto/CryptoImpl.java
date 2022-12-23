package com.xwl.mybatishelper.mybatisplus.crypto;

import com.xwl.mybatishelper.enums.CryptoAlgorithm;
import com.xwl.mybatishelper.service.ICrypto;
import com.xwl.mybatishelper.mybatisplus.util.SM4Utils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xwl
 * @since 2022/12/8 22:53
 */
@Slf4j
public class CryptoImpl implements ICrypto {
    @Override
    public String encrypt(CryptoAlgorithm cryptoAlgorithm, String plaintext, String key, String publicKey, String privateKey) throws Exception {
        log.info("自定义加密方法，明文：{}，加密结果：{}", plaintext, SM4Utils.encryptData_ECB(plaintext, key));
        return SM4Utils.encryptData_ECB(plaintext, key);
    }

    @Override
    public String decrypt(CryptoAlgorithm cryptoAlgorithm, String ciphertext, String key, String publicKey, String privateKey) throws Exception {
        log.info("自定义解密方法，密文：{}, 解密结果：{}", ciphertext, SM4Utils.decryptData_ECB(ciphertext, key));
        return SM4Utils.decryptData_ECB(ciphertext, key);
    }
}
