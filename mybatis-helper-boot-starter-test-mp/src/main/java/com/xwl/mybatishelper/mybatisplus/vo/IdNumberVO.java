package com.xwl.mybatishelper.mybatisplus.vo;

import com.xwl.mybatishelper.annotation.CryptoField;
import lombok.Data;

import java.util.List;

/**
 * @author xwl
 * @since 2022/12/26 9:50
 */
@Data
public class IdNumberVO {
    /**
     * 使用 @CryptoField 无效，必须使用@Param("enc_vo") 添加enc_标识
     */
    @CryptoField
    private List<String> idNumbers;
}
