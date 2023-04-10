/*
 * Copyright 2023 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dian1.http.configuration;

import com.dian1.http.annotate.EnableHttp;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author zhangzhi
 * @date 2023/3/28
 */
public class HttpRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(EnableHttp.class.getName()));
        if (null != mapperScanAttrs) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(HttpBeanDefinitionRegistry.class);
            Class<?> httpFactoryBean = mapperScanAttrs.getClass("httpFactoryBean");
            builder.addPropertyValue("httpFactoryBean", httpFactoryBean);
            builder.addPropertyValue("scan", mapperScanAttrs.getStringArray("scan"));
            builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            registry.registerBeanDefinition(HttpBeanDefinitionRegistry.class.getName(), builder.getBeanDefinition());

//            BeanDefinitionBuilder httpConfigurationBuilder = BeanDefinitionBuilder.genericBeanDefinition(HttpConfiguration.class);
//            httpConfigurationBuilder.setLazyInit(true);
//            registry.registerBeanDefinition(HttpConfiguration.class.getName(), httpConfigurationBuilder.getBeanDefinition());
        }
    }


}
