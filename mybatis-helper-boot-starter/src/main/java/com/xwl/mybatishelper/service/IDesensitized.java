package com.xwl.mybatishelper.service;

import com.xwl.mybatishelper.enums.DesensitizedType;

/**
 * 脱敏器
 *
 * @author xwl
 * @since 2022/12/15 13:16
 */
public interface IDesensitized {
    /**
     * 脱敏处理
     *
     * @param value       要脱敏的值
     * @param replacement 填充的符号
     * @param type        脱敏类型
     * @return
     */
    String execute(String value, String replacement, DesensitizedType type);
}
