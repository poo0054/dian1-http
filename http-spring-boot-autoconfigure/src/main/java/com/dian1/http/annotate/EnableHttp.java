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
package com.dian1.http.annotate;

import com.dian1.http.configuration.HttpSelector;
import com.dian1.http.proxy.HttpFactoryBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启动类
 *
 * @author zhangzhi
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(HttpSelector.class)
public @interface EnableHttp {

    /**
     * 扫描路径
     *
     * @return 扫描路径
     */
    String[] scan();

    /**
     * 构建的 {@link FactoryBean}
     *
     * @return 实现类
     */
    Class<? extends FactoryBean> httpFactoryBean() default HttpFactoryBean.class;
}
