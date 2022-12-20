package com.xwl.mybatishelper.enums;

/**
 * 脱敏类型
 *
 * @author xwl
 * @since 2022/12/16 20:43
 */
public enum DesensitizedType {
    /**
     * 默认
     */
    DEFAULT,
    /**
     * 用户id
     */
    USER_ID,
    /**
     * 中文名
     */
    CHINESE_NAME,
    /**
     * 身份证号
     */
    ID_CARD,
    /**
     * 座机号
     */
    FIXED_PHONE,
    /**
     * 手机号
     */
    MOBILE_PHONE,
    /**
     * 地址
     */
    ADDRESS,
    /**
     * 电子邮件
     */
    EMAIL,
    /**
     * 密码
     */
    PASSWORD,
    /**
     * 中国大陆车牌，包含普通车辆、新能源车辆
     */
    CAR_LICENSE,
    /**
     * 银行卡
     */
    BANK_CARD
}
