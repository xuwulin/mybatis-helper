package com.xwl.mybatishelper.service.impl;

import com.xwl.mybatishelper.service.IDesensitized;
import com.xwl.mybatishelper.util.DesensitizedUtils;
import com.xwl.mybatishelper.enums.DesensitizedType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 脱敏实现
 *
 * @author xwl
 * @since 2022/12/15 13:17
 */
public class DefaultDesensitizedImpl implements IDesensitized {

    @Override
    public String execute(String value, String replacement, DesensitizedType type) {
        if (value == null || value.length() == 0 || replacement == null || replacement.length() == 0) {
            return "";
        }
        String sensitiveInfo;
        if (type != null && type != DesensitizedType.DEFAULT) {
            sensitiveInfo = DesensitizedUtils.desensitized(value, replacement, type);
        } else {
            sensitiveInfo = DesensitizedUtils.desensitized(value, replacement);
        }
        return sensitiveInfo;
    }
}
