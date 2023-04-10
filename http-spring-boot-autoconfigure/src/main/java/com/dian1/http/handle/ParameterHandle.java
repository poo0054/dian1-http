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
 * 参数处理对象 一般添加在方法上面 如{@link com.dian1.http.annotate.parameter.Form}
 *
 * @author zhangzhi
 * @date 2023/3/27
 */
public interface ParameterHandle<T extends Annotation> extends HttpHandle {


    HttpProperties resolving(HttpProperties properties, Object arg, T t);


}
