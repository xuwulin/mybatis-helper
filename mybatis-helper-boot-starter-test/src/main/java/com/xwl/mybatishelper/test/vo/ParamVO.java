package com.xwl.mybatishelper.test.vo;

import com.xwl.mybatishelper.annotation.CryptoField;
import com.xwl.mybatishelper.annotation.DesensitizedField;
import lombok.Data;

/**
 * @author xwl
 * @since 2022/12/19 14:46
 */
@Data
public class ParamVO {
    @DesensitizedField
    private String account;
    @CryptoField
    private String username;
    @CryptoField
    private String idNumber;
}
