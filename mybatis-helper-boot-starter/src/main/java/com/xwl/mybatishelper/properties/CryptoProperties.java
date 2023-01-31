package com.xwl.mybatishelper.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 加解密配置
 *
 * @author xwl
 * @since 2022/12/7 11:22
 */
@ConfigurationProperties(prefix = "mybatis-helper.crypto")
public class CryptoProperties {

    /**
     * 是否开启加解密日志（默认false），开启后info会记录加解密详情
     */
    private boolean enableDetailLog = false;

    /**
     * 加解密方式（默认SM4）
     */
    private String mode = "SM4";

    /**
     * 参数加密前缀（默认enc_，即@Param注解value前缀，如@Param("enc_username") String username，表示username需要加密）
     */
    private String paramPrefix = "enc_";

    /**
     * 加解密类全类名（默认com.xwl.mybatishelper.service.impl.DefaultCryptoImpl）
     */
    private String className = "com.xwl.mybatishelper.service.impl.DefaultCryptoImpl";

    /**
     * 对称加密密钥（SM4），不配置使用默认密钥
     */
    private String key = "a3deb145671dd04e";

    /**
     * 非对称加密公钥（SM2），不配置使用默认公钥
     */
    private String publicKey = "3059301306072a8648ce3d020106082a811ccf5501822d03420004e6d02503196bda21356390f43e324aa9cb42dbe64204ac4d9e1652fe055e4636702f84f376a54af81ca165d355f3e68d137702b9c715b4ecf70d50b65d9d6e78";

    /**
     * 非对称加密私钥（SM2），不配置使用默认私钥
     */
    private String privateKey = "308193020100301306072a8648ce3d020106082a811ccf5501822d047930770201010420169d98f96e7a3deb145671dd04eefdf54b3c2196ca5569b5fb96a9ff5abe957da00a06082a811ccf5501822da14403420004e6d02503196bda21356390f43e324aa9cb42dbe64204ac4d9e1652fe055e4636702f84f376a54af81ca165d355f3e68d137702b9c715b4ecf70d50b65d9d6e78";

    public boolean isEnableDetailLog() {
        return enableDetailLog;
    }

    public void setEnableDetailLog(boolean enableDetailLog) {
        this.enableDetailLog = enableDetailLog;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getParamPrefix() {
        return paramPrefix;
    }

    public void setParamPrefix(String paramPrefix) {
        this.paramPrefix = paramPrefix;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
