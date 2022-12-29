package com.xwl.mybatishelper.mybatisplus.vo;

import com.xwl.mybatishelper.annotation.CryptoField;
import com.xwl.mybatishelper.annotation.DesensitizedField;
import lombok.Data;

/**
 * @author xwl
 * @since 2022/12/28 14:33
 */
@Data
public class BaseVO {

    private String account;

    private String username;

    @CryptoField
    @DesensitizedField
    private String password;
}
