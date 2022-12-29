package com.xwl.mybatishelper.mybatisplus.vo;

import com.xwl.mybatishelper.annotation.CryptoField;
import com.xwl.mybatishelper.annotation.DesensitizedField;
import lombok.Data;

/**
 * @author xwl
 * @since 2022/12/28 14:35
 */
@Data
public class OutExtendsVO extends BaseVO{
    @CryptoField
    @DesensitizedField
    private String idNumber;

    private String deptId;
}
