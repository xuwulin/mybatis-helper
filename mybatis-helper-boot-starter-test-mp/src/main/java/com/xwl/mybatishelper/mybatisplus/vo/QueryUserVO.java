package com.xwl.mybatishelper.mybatisplus.vo;

import com.xwl.mybatishelper.annotation.CryptoField;
import lombok.Data;

/**
 * @author xwl
 * @since 2022/12/7 22:02
 */
@Data
public class QueryUserVO {
    @CryptoField
    private String idNumber;
}
