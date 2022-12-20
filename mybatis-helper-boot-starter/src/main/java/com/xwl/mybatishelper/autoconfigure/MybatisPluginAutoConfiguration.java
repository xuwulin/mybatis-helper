package com.xwl.mybatishelper.autoconfigure;

import com.xwl.mybatishelper.inteceptor.CryptoInterceptor;
import com.xwl.mybatishelper.inteceptor.DesensitizeInterceptor;
import com.xwl.mybatishelper.properties.DesensitizedProperties;
import com.xwl.mybatishelper.properties.CryptoProperties;
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
@EnableConfigurationProperties(value = {CryptoProperties.class, DesensitizedProperties.class})
public class MybatisPluginAutoConfiguration {

    @Bean
    public CryptoInterceptor cryptoInterceptor() {
        return new CryptoInterceptor();
    }

    @Bean
    public DesensitizeInterceptor desensitizeInterceptor() {
        return new DesensitizeInterceptor();
    }
}
