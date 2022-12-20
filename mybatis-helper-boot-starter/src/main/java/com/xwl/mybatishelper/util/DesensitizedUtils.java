package com.xwl.mybatishelper.util;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.xwl.mybatishelper.enums.DesensitizedType;

import java.util.regex.Pattern;

/**
 * 脱敏工具类
 *
 * @author xwl
 * @since 2022/12/15 13:19
 */
public class DesensitizedUtils {
    /**
     * 手机号码匹配
     */
    public static final String PHONE_REG = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";

    /**
     * 邮箱email
     */
    public static final String EMAIL_REG = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     * 银行卡卡号位数
     */
    public final static String BANK_CARD_NUMBER = "^\\d{16}|\\d{19}$";

    /**
     * 身份证号码位数限制
     */
    public final static String ID_CARD_NUMBER = "^\\d{15}|(\\d{17}[0-9,x,X])$";

    /**
     * 脱敏处理。使用正则表达式判断使用哪种规则
     *
     * @param sourceStr   需要处理的敏感信息
     * @param replacement 替换值
     * @return String
     */
    public static String desensitized(String sourceStr, String replacement) {
        if (StrUtil.isBlank(sourceStr)) {
            return "";
        }
        if (Pattern.matches(PHONE_REG, sourceStr)) {
            return mobilePhoneNum(sourceStr, replacement);
        } else if (Pattern.matches(EMAIL_REG, sourceStr)) {
            return emailNum(sourceStr, replacement);
        } else if (Pattern.matches(BANK_CARD_NUMBER, sourceStr)) {
            return bankCardNum(sourceStr, replacement);
        } else if (Pattern.matches(ID_CARD_NUMBER, sourceStr)) {
            return idCard(sourceStr, replacement);
        } else {
            return replaceSecretInfo(sourceStr, replacement);
        }
    }

    /**
     * 移动电话：显示前三后四，范例：189****3684
     *
     * @param phoneNum
     * @param replacement
     * @return
     */
    public static String mobilePhoneNum(String phoneNum, String replacement) {
        if (StrUtil.isBlank(phoneNum)) {
            return "";
        }
        return replaceBetween(phoneNum, 3, phoneNum.length() - 4, replacement);
    }

    /**
     * email：显示前二后四，范例：abxx@xxx.com
     *
     * @param email
     * @param replacement
     * @return
     */
    public static String emailNum(String email, String replacement) {
        // 判断是否为邮箱地址
        if (StrUtil.isBlank(email) || !Pattern.matches(EMAIL_REG, email)) {
            return "";
        }

        StringBuilder sb = new StringBuilder(email);
        // 邮箱账号名只显示前两位
        int at_position = sb.indexOf("@");
        if (at_position > 2) {
            sb.replace(2, at_position, repeat(replacement, at_position - 2));
        }
        // 邮箱域名隐藏
        int period_position = sb.lastIndexOf(".");
        sb.replace(at_position + 1, period_position, repeat(replacement, period_position - at_position - 1));
        return sb.toString();
    }

    /**
     * 银行卡号：显示前六后四，范例：622848******4568
     *
     * @param bankCardNum
     * @param replacement
     * @return
     */
    public static String bankCardNum(String bankCardNum, String replacement) {
        if (StrUtil.isBlank(bankCardNum)) {
            return "";
        }
        return replaceBetween(bankCardNum, 6, bankCardNum.length() - 4, replacement);
    }

    /**
     * 身份证号码：显示前六后四，范例：110601********2015
     *
     * @param idCardNum
     * @param replacement
     * @return
     */
    public static String idCard(String idCardNum, String replacement) {
        if (StrUtil.isBlank(idCardNum)) {
            return "";
        }
        return replaceBetween(idCardNum, 6, idCardNum.length() - 4, replacement);
    }

    /**
     * 将字符串开始位置到结束位置之间的字符用指定字符替换
     *
     * @param sourceStr   待处理字符串
     * @param begin       开始位置
     * @param end         结束位置
     * @param replacement 替换字符
     * @return
     */
    private static String replaceBetween(String sourceStr, int begin, int end, String replacement) {
        if (StrUtil.isBlank(sourceStr)) {
            return "";
        }
        if (StrUtil.isBlank(replacement)) {
            replacement = "*";
        }
        int replaceLength = end - begin;
        if (replacement.length() > 0 && replaceLength > 0) {
            StringBuilder sb = new StringBuilder(sourceStr);
            sb.replace(begin, end, repeat(replacement, replaceLength));
            return sb.toString();
        } else {
            return sourceStr;
        }
    }

    /**
     * 构建重复字符
     *
     * @param value
     * @param count
     * @return
     */
    private static String repeat(String value, int count) {
        String result = "";
        if (count > 0 && value != null && !"".equals(value)) {
            for (int i = 0; i < count; i++) {
                result = result.concat(value);
            }
        }
        return result;
    }

    /**
     * 敏感数据处理
     *
     * @param info 要加密的字符串
     */
    public static String replaceSecretInfo(String info, String fillValue) {
        if (StrUtil.isBlank(info)) {
            return "";
        }
        String result;
        int infoLength = info.length();
        if (infoLength == 1) {
            result = fillValue;
        } else if (infoLength == 2) {
            result = info.substring(0, 1) + fillValue;
        } else {
            double tempNum = (double) infoLength / 3;
            int num1 = (int) Math.floor(tempNum);
            int num2 = (int) Math.ceil(tempNum);
            int num3 = infoLength - num1 - num2;
            String star = "";
            for (int i = 0; i < num2; i++) {
                star = star.concat(fillValue);
            }

            String regex = "(.{" + num1 + "})(.{" + num2 + "})(.{" + num3 + "})";
            String replacement = "$1" + star + "$3";
            result = info.replaceAll(regex, replacement);
        }
        return result;
    }

    /**
     * 脱敏处理，根据类型判断使用哪种规则
     *
     * @param value       需要处理的敏感信息
     * @param replacement 替换值
     * @param type        类型
     * @return
     */
    public static String desensitized(String value, String replacement, DesensitizedType type) {
        String newStr = value;
        switch (type) {
            case USER_ID:
                newStr = String.valueOf(0);
                break;
            case CHINESE_NAME:
                newStr = chineseName(value, replacement);
                break;
            case ID_CARD:
                newStr = idCardNum(value, 1, 2, replacement);
                break;
            case FIXED_PHONE:
                newStr = fixedPhone(value, replacement);
                break;
            case MOBILE_PHONE:
                newStr = mobilePhone(value, replacement);
                break;
            case ADDRESS:
                newStr = address(value, 8, replacement);
                break;
            case EMAIL:
                newStr = email(value, replacement);
                break;
            case PASSWORD:
                newStr = password(value, replacement);
                break;
            case CAR_LICENSE:
                newStr = carLicense(value, replacement);
                break;
            case BANK_CARD:
                newStr = bankCard(value, replacement);
                break;
            default:
        }
        return newStr;
    }

    /**
     * 【中文姓名】只显示第一个汉字，其他隐藏为2个星号，比如：李**
     *
     * @param fullName    姓名
     * @param replacement 替换值
     * @return 脱敏后的姓名
     */
    public static String chineseName(String fullName, String replacement) {
        if (StrUtil.isBlank(fullName)) {
            return StrUtil.EMPTY;
        }
        replacement = StrUtil.isNotBlank(replacement) ? replacement : "*";
        return StrUtil.replace(fullName, 1, fullName.length(), replacement);
    }

    /**
     * 【身份证号】前1位 和后2位
     *
     * @param idCardNum   身份证
     * @param front       保留：前面的front位数；从1开始
     * @param end         保留：后面的end位数；从1开始
     * @param replacement 替换值
     * @return 脱敏后的身份证
     */
    public static String idCardNum(String idCardNum, int front, int end, String replacement) {
        // 身份证不能为空
        if (StrUtil.isBlank(idCardNum)) {
            return StrUtil.EMPTY;
        }
        // 需要截取的长度不能大于身份证号长度
        if ((front + end) > idCardNum.length()) {
            return StrUtil.EMPTY;
        }
        // 需要截取的不能小于0
        if (front < 0 || end < 0) {
            return StrUtil.EMPTY;
        }
        replacement = StrUtil.isNotBlank(replacement) ? replacement : "*";
        return StrUtil.replace(idCardNum, front, idCardNum.length() - end, replacement);
    }

    /**
     * 【固定电话 前四位，后两位
     *
     * @param num         固定电话
     * @param replacement 替换值
     * @return 脱敏后的固定电话；
     */
    public static String fixedPhone(String num, String replacement) {
        if (StrUtil.isBlank(num)) {
            return StrUtil.EMPTY;
        }
        replacement = StrUtil.isNotBlank(replacement) ? replacement : "*";
        return StrUtil.replace(num, 4, num.length() - 2, replacement);
    }

    /**
     * 【手机号码】前三位，后4位，其他隐藏，比如135****2210
     *
     * @param num         移动电话；
     * @param replacement 替换值
     * @return 脱敏后的移动电话；
     */
    public static String mobilePhone(String num, String replacement) {
        if (StrUtil.isBlank(num)) {
            return StrUtil.EMPTY;
        }
        replacement = StrUtil.isNotBlank(replacement) ? replacement : "*";
        return StrUtil.replace(num, 3, num.length() - 4, replacement);
    }

    /**
     * 【地址】只显示到地区，不显示详细地址，比如：北京市海淀区****
     *
     * @param address       家庭住址
     * @param sensitiveSize 敏感信息长度
     * @param replacement   替换值
     * @return 脱敏后的家庭地址
     */
    public static String address(String address, int sensitiveSize, String replacement) {
        if (StrUtil.isBlank(address)) {
            return StrUtil.EMPTY;
        }
        int length = address.length();
        replacement = StrUtil.isNotBlank(replacement) ? replacement : "*";
        return StrUtil.replace(address, length - sensitiveSize, length, replacement);
    }

    /**
     * 【电子邮箱】邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示，比如：d**@126.com
     *
     * @param email       邮箱
     * @param replacement 替换值
     * @return 脱敏后的邮箱
     */
    public static String email(String email, String replacement) {
        if (StrUtil.isBlank(email)) {
            return StrUtil.EMPTY;
        }
        int index = StrUtil.indexOf(email, '@');
        if (index <= 1) {
            return email;
        }
        replacement = StrUtil.isNotBlank(replacement) ? replacement : "*";
        return StrUtil.replace(email, 1, index, replacement);
    }

    /**
     * 【密码】密码的全部字符都用*代替，比如：******
     *
     * @param password    密码
     * @param replacement 替换值
     * @return 脱敏后的密码
     */
    public static String password(String password, String replacement) {
        if (StrUtil.isBlank(password)) {
            return StrUtil.EMPTY;
        }
        replacement = StrUtil.isNotBlank(replacement) ? replacement : "*";
        return StrUtil.repeat(replacement, password.length());
    }

    /**
     * 【中国车牌】车牌中间用*代替
     * eg1：null       -》 ""
     * eg1：""         -》 ""
     * eg3：苏D40000   -》 苏D4***0
     * eg4：陕A12345D  -》 陕A1****D
     * eg5：京A123     -》 京A123     如果是错误的车牌，不处理
     *
     * @param carLicense  完整的车牌号
     * @param replacement 替换值
     * @return 脱敏后的车牌
     */
    public static String carLicense(String carLicense, String replacement) {
        if (StrUtil.isBlank(carLicense)) {
            return StrUtil.EMPTY;
        }
        replacement = StrUtil.isNotBlank(replacement) ? replacement : "*";
        // 普通车牌
        if (carLicense.length() == 7) {
            carLicense = StrUtil.replace(carLicense, 3, 6, replacement);
        } else if (carLicense.length() == 8) {
            // 新能源车牌
            carLicense = StrUtil.replace(carLicense, 3, 7, replacement);
        }
        return carLicense;
    }

    /**
     * 银行卡号脱敏
     * eg: 1101 **** **** **** 3256
     *
     * @param bankCardNo  银行卡号
     * @param replacement 替换值
     * @return 脱敏之后的银行卡号
     */
    public static String bankCard(String bankCardNo, String replacement) {
        if (StrUtil.isBlank(bankCardNo)) {
            return bankCardNo;
        }
        bankCardNo = StrUtil.trim(bankCardNo);
        if (bankCardNo.length() < 9) {
            return bankCardNo;
        }

        final int length = bankCardNo.length();
        final int midLength = length - 8;
        final StringBuilder buf = new StringBuilder();

        buf.append(bankCardNo, 0, 4);
        for (int i = 0; i < midLength; ++i) {
            if (i % 4 == 0) {
                buf.append(CharUtil.SPACE);
            }
            buf.append(replacement);
        }
        buf.append(CharUtil.SPACE).append(bankCardNo, length - 4, length);
        return buf.toString();
    }
}
