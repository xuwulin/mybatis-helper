package com.xwl.mybatishelper.mybatis.entity;

import com.xwl.mybatishelper.annotation.CryptoField;

/**
 * @author xwl
 * @since 2022/12/12 10:14
 */
public class SysDept {
    private Integer id;
    private String deptName;
    @CryptoField
    private String deptPhone;
    private String deptAddress;
}
