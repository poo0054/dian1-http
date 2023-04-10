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
package com.dian1.http.build;

import com.dian1.http.properties.HttpProperties;

import java.lang.annotation.Annotation;

/**
 * @author zhangzhi
 * @date 2023/4/7
 */
public interface BuildPropertiesClass {

    /**
     * 构建类属性
     *
     * @param httpInterfaces 当前接口
     * @param httpProperties 当前参数
     */
    <V extends Annotation> void buildClassHandle(Class httpInterfaces, HttpProperties httpProperties);
}
