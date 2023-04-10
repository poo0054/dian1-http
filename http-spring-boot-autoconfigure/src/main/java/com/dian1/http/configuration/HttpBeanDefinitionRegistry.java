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

import com.dian1.http.proxy.HttpFactoryBean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * @author zhangzhi
 * @date 2023/3/27
 */
@Slf4j
@Data
public class HttpBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {

    private Class<? extends FactoryBean> httpFactoryBean = HttpFactoryBean.class;
    private String[] scan;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        HttpClassPathBeanDefinitionScanner scanner = new HttpClassPathBeanDefinitionScanner(registry);
        scanner.setHttpFactoryBean(httpFactoryBean);
        scanner.initIncludeFilter();
        scanner.scan(scan);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //ignore
    }
}
