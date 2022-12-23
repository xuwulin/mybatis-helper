package com.xwl.mybatishelper.mybatis.entity;

import com.xwl.mybatishelper.annotation.CryptoField;
import com.xwl.mybatishelper.annotation.DesensitizedField;
import com.xwl.mybatishelper.enums.DesensitizedType;
import lombok.Data;

/**
 * @author xwl
 * @since 2022/12/6 15:40
 */
@Data
public class SysUser {
    private Integer id;

    @DesensitizedField
    private String account;

    @CryptoField
    @DesensitizedField(type = DesensitizedType.PASSWORD)
    private String password;

    @DesensitizedField(type = DesensitizedType.CHINESE_NAME)
    private String username;

    @CryptoField
    @DesensitizedField
    private String idNumber;

    private Integer deptId;
}
