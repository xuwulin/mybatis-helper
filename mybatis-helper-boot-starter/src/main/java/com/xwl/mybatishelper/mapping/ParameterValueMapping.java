package com.xwl.mybatishelper.mapping;

import com.xwl.mybatishelper.enums.ConditionEnum;

/**
 * 参数名、列名映射对象
 *
 * @author xwl
 * @since 2022/12/14 10:56
 */
public class ParameterValueMapping extends ParameterColumnMapping {

    /**
     * 参数值
     */
    private Object paramValue;

    public ParameterValueMapping() {
    }

    public ParameterValueMapping(String paramName, Object paramValue) {
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

    public ParameterValueMapping(String paramName, String columnName, ConditionEnum conditionEnum) {
        this.paramName = paramName;
        this.columnName = columnName;
        this.conditionEnum = conditionEnum;
    }

    public ParameterValueMapping(String paramName, String columnName, Object paramValue, ConditionEnum conditionEnum) {
        this.paramName = paramName;
        this.columnName = columnName;
        this.paramValue = paramValue;
        this.conditionEnum = conditionEnum;
    }

    public Object getParamValue() {
        return paramValue;
    }

    public void setParamValue(Object paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public String toString() {
        return "ParameterValueMapping{" +
                "paramName='" + paramName + '\'' +
                ", columnName='" + columnName + '\'' +
                ", conditionEnum='" + conditionEnum + '\'' +
                ", paramValue=" + paramValue +
                '}';
    }
}
