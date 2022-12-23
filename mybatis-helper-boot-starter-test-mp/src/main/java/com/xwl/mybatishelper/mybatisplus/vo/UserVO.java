package com.xwl.mybatishelper.mybatisplus.vo;

import com.xwl.mybatishelper.annotation.CryptoField;
import lombok.Data;

/**
 * @author xwl
 * @since 2022/12/8 21:50
 */
@Data
public class UserVO {
    private String account;

    @CryptoField
    private String username;

    @CryptoField
    private String idNumber;
}
