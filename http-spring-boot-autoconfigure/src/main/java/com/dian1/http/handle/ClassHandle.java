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
 * 类接口,当前支持OpenHttp.
 *
 * @author zhangzhi
 */
public interface ClassHandle<T extends Annotation> extends HttpHandle {
    /**
     * 解析当前接口,
     *
     * @param httpProperties HttpProperties
     * @param annotation     注解
     * @return HttpProperties
     */
    HttpProperties resolving(HttpProperties httpProperties, T annotation);
}
