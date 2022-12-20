package com.xwl.mybatishelper.test.entity;

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
    private String password;
    @CryptoField
    private String username;
    @CryptoField
    @DesensitizedField(type = DesensitizedType.ID_CARD)
    private String idNumber;
    private Integer deptId;
}
