package com.xwl.mybatishelper.mapping;

import com.xwl.mybatishelper.enums.ConditionEnum;

import java.io.Serializable;

/**
 * 参数名、列名映射对象
 *
 * @author xwl
 * @since 2022/12/14 10:56
 */
public class ParameterColumnMapping implements Serializable {

    /**
     * 参数名 例：paramNameValuePairs.MPGENVAL1
     */
    protected String paramName;

    /***
     * 列名
     */
    protected String columnName;

    /**
     * sql条件表达式枚举，主要用于区分多个不同的paramName对应一个columnName时的条件逻辑
     * 例：  age BETWEEN paramNameValuePairs.MPGENVAL1 AND paramNameValuePairs.MPGENVAL2
     * paramName = paramNameValuePairs.MPGENVAL1  columnName = age  expression = betweenStart
     * paramName = paramNameValuePairs.MPGENVAL2  columnName = age  expression = betweenStart
     */
    protected ConditionEnum conditionEnum;

    ParameterColumnMapping() {
    }

    public ParameterColumnMapping(String paramName) {
        this.paramName = paramName;
    }

    public ParameterColumnMapping(String paramName, String columnName) {
        this.paramName = paramName;
        this.columnName = columnName;
    }

    public ParameterColumnMapping(String paramName, String columnName, ConditionEnum conditionEnum) {
        this.paramName = paramName;
        this.columnName = columnName;
        this.conditionEnum = conditionEnum;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public ConditionEnum getConditionEnum() {
        return conditionEnum;
    }

    public void setConditionEnum(ConditionEnum conditionEnum) {
        this.conditionEnum = conditionEnum;
    }
}
