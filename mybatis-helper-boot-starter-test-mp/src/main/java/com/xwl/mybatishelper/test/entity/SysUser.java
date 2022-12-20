package com.xwl.mybatishelper.test.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xwl.mybatishelper.annotation.CryptoField;
import com.xwl.mybatishelper.annotation.DesensitizedField;
import com.xwl.mybatishelper.enums.DesensitizedType;
import lombok.Data;

/**
 * @author xwl
 * @since 2022/12/6 15:40
 */
@Data
@TableName("sys_user")
public class SysUser {
    @TableId
    private Integer id;
    @DesensitizedField
    private String account;
    private String password;
    @CryptoField
    @DesensitizedField(replacement = "@", type = DesensitizedType.CHINESE_NAME)
    private String username;
    @CryptoField
    @DesensitizedField
    private String idNumber;
    private Integer deptId;
}
