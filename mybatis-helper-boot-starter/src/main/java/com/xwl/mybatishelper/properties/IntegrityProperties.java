package com.xwl.mybatishelper.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 完整性保护配置
 *
 * @author xwl
 * @since 2022/12/15 14:21
 */
@ConfigurationProperties(prefix = "mybatis-helper.integrity")
public class IntegrityProperties {
    /**
     * 是否开启完整性保护值计算日志（默认false），开启后info会记录完整性保护值计算日志
     */
    private boolean enableDetailLog = false;

    /**
     * 完整性保护算法（默认MD5）
     */
    private String mode = "MD5";

    /**
     * 脱敏类全类名
     */
    private String className = "com.xwl.mybatishelper.service.impl.DefaultIntegrityImpl";

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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
