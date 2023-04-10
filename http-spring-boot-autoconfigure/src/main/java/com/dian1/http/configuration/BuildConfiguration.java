package com.dian1.http.configuration;

import cn.hutool.http.HttpRequest;
import com.dian1.http.build.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangzhi
 * @date 2023/4/7
 */
@Configuration(proxyBeanMethods = false)
@ComponentScan("com.dian1.http.build")
public class BuildConfiguration {

    @Bean
    @ConditionalOnMissingBean(BuildPropertiesClass.class)
    public BuildPropertiesClass buildPropertiesClass() {
        return new DefaultBuildPropertiesClass();
    }

    @Bean
    @ConditionalOnMissingBean(BuildPropertiesMethod.class)
    public BuildPropertiesMethod buildPropertiesMethod() {
        return new DefaultBuildPropertiesMethod();
    }

    @Bean
    @ConditionalOnMissingBean(BuildPropertiesParameter.class)
    public BuildPropertiesParameter buildPropertiesParameter() {
        return new DefaultBuildPropertiesParameter();
    }

    @Bean
    @ConditionalOnMissingBean(BuildHttpRequest.class)
    @ConditionalOnClass(HttpRequest.class)
    public BuildHttpRequest buildHttpRequest() {
        return new HutoolBuildHttpRequest();
    }
}
