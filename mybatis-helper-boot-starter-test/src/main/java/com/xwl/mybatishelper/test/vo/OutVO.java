package com.xwl.mybatishelper.test.vo;

import com.xwl.mybatishelper.annotation.DesensitizedField;
import lombok.Data;

/**
 * @author xwl
 * @since 2022/12/19 14:47
 */
@Data
public class OutVO extends ParamVO {
    @DesensitizedField
    private String password;
    private String deptName;
}
