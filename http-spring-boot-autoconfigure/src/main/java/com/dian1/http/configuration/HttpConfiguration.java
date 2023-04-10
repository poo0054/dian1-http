package com.dian1.http.configuration;

import com.dian1.http.utils.ApplicationContextUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author zhangzhi
 * @date 2023/3/28
 */
@Import({BuildConfiguration.class, HandleConfiguration.class})
public class HttpConfiguration {

    @Bean
    public static ApplicationContextUtils classUtils() {
        return new ApplicationContextUtils();
    }

}
