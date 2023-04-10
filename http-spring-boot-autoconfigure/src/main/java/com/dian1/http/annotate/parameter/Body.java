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
package com.dian1.http.annotate.parameter;

import java.lang.annotation.*;

/**
 * 值放在请求的Body中
 *
 * @author zhangzhi
 * @date 2023/3/27
 * @see com.dian1.http.handle.parameter.BodyHandle
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Body {
    /**
     * body的参数.
     * key为空必须为map
     *
     * @return form的key
     */
    String value() default "";
}
