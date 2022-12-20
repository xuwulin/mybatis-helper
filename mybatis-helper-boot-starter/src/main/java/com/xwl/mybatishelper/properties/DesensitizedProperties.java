package com.xwl.mybatishelper.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 脱敏处理配置
 *
 * @author xwl
 * @since 2022/12/15 14:21
 */
@ConfigurationProperties(prefix = "mybatis-helper.desensitized")
public class DesensitizedProperties {
    /**
     * 填充符号
     */
    private String replacement = "*";

    /**
     * 脱敏类全类名
     */
    private String className = "com.xwl.mybatishelper.service.impl.DefaultDesensitizedImpl";

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
