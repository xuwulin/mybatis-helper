package com.xwl.mybatishelper.enums;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

/**
 * @author xwl
 * @since 2022/12/14 10:56
 */
public enum ConditionEnum {
    GREATER(">", "大于"),
    LESS("<", "小于"),
    EQUAL("=", "等于"),
    NOT_EQUAL("<>", "不等于"),
    GREATER_EQUAL(">=", "大于等于"),
    LESS_EQUAL("<=", "小于等于"),
    BETWEEN_START("between start", "范围：开始区间"),
    BETWEEN_END("between end", "范围：结束区间"),
    LIKE("like", "模糊查询"),
    NOT_IN("not in", "不在指定范围值查询"),
    IN("in", "指定范围值查询");

    private final String code;

    private final String name;

    ConditionEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 通过code获取枚举
     *
     * @param code 编码
     * @return
     */
    public static ConditionEnum getConditionEnumByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (ConditionEnum conditionEnum : values()) {
            if (conditionEnum.getCode().equals(code)) {
                return conditionEnum;
            }
        }
        return null;
    }
}
