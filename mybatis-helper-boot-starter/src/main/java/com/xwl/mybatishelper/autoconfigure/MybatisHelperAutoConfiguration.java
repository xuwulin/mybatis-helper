package com.xwl.mybatishelper.autoconfigure;

import com.xwl.mybatishelper.inteceptor.CryptoInterceptor;
import com.xwl.mybatishelper.inteceptor.DesensitizeInterceptor;
import com.xwl.mybatishelper.inteceptor.IntegrityInterceptor;
import com.xwl.mybatishelper.properties.CryptoProperties;
import com.xwl.mybatishelper.properties.DesensitizedProperties;
import com.xwl.mybatishelper.properties.IntegrityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 同一个类中@configuration + @bean是按照定义的顺序依次加载的
 *
 * @author xwl
 * @since 2022/12/7 11:28
 */
@Configuration
@EnableConfigurationProperties(value = {CryptoProperties.class, IntegrityProperties.class, DesensitizedProperties.class})
public class MybatisHelperAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisHelperAutoConfiguration.class);

    @Bean
    public CryptoInterceptor cryptoInterceptor() {
        LOGGER.info("==>com.xwl.mybatishelper.autoconfigure.MybatisHelperAutoConfiguration：MybatisHelper自动配置，注册CryptoInterceptor");
        return new CryptoInterceptor();
    }

    @Bean
    public IntegrityInterceptor integrityInterceptor() {
        LOGGER.info("==>com.xwl.mybatishelper.autoconfigure.MybatisHelperAutoConfiguration：MybatisHelper自动配置，注册IntegrityInterceptor");
        return new IntegrityInterceptor();
    }

    @Bean
    public DesensitizeInterceptor desensitizeInterceptor() {
        LOGGER.info("==>com.xwl.mybatishelper.autoconfigure.MybatisHelperAutoConfiguration：MybatisHelper自动配置，注册DesensitizeInterceptor");
        return new DesensitizeInterceptor();
    }
}
