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
package com.dian1.http.handle;


import com.dian1.http.properties.HttpProperties;

import java.lang.annotation.Annotation;

/**
 * 类别处理器比如 {@link com.dian1.http.annotate.method.Get} {@link com.dian1.http.annotate.method.Post}等
 *
 * @author zhangzhi
 */
public interface MethodHandle<T extends Annotation> extends HttpHandle {

    /**
     * 处理方法
     *
     * @param properties 配置类对象
     * @param t          当前注解
     * @return properties
     */
    HttpProperties resolving(HttpProperties properties, T t);

}
