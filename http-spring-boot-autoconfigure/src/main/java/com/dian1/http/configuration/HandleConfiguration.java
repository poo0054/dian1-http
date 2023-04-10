package com.dian1.http.configuration;

import com.dian1.http.handle.HttpHandleCompose;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @author zhangzhi
 * @date 2023/4/7
 */
@Configuration(proxyBeanMethods = false)
@ComponentScan(value = "com.dian1.http.handle")
public class HandleConfiguration {
    @Bean
    @Lazy
    public HttpHandleCompose httpHandleCompose() {
        return new HttpHandleCompose();
    }

}
