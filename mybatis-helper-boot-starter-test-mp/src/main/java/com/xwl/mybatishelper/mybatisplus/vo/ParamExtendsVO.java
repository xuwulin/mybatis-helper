package com.xwl.mybatishelper.mybatisplus.vo;

import com.xwl.mybatishelper.annotation.CryptoField;
import lombok.Data;

/**
 * @author xwl
 * @since 2022/12/28 14:33
 */
@Data
public class ParamExtendsVO extends BaseVO{

    @CryptoField
    private String idNumber;
}
