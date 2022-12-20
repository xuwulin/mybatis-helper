package com.xwl.mybatishelper.service.impl;

import com.xwl.mybatishelper.enums.DesensitizedType;
import com.xwl.mybatishelper.service.IDesensitized;

/**
 * 空实现，用于@DesensitizedField注解属性iDesensitized 默认值
 *
 * @author xwl
 * @since 2022/12/15 13:17
 */
public class NoneDesensitizedImpl implements IDesensitized {

    @Override
    public String execute(String value, String replacement, DesensitizedType type) {
        return value;
    }
}
