package com.xwl.mybatishelper.mybatisplus.vo;

import com.xwl.mybatishelper.annotation.CryptoField;
import com.xwl.mybatishelper.annotation.DesensitizedField;
import com.xwl.mybatishelper.enums.DesensitizedType;
import lombok.Data;

/**
 * @author xwl
 * @since 2022/12/19 14:47
 */
@Data
public class OutVO {
    @DesensitizedField
    private String account;
    @DesensitizedField(type = DesensitizedType.CHINESE_NAME)
    private String username;
    @CryptoField
    @DesensitizedField
    private String idNumber;
    @CryptoField
    @DesensitizedField(type = DesensitizedType.PASSWORD)
    private String password;
    private String deptName;
}
