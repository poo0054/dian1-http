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

import java.lang.annotation.*;

/**
 * @author zhangzhi
 * @date 2023/3/27
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpenHttp {

    /**
     * 当前类下所有前缀  例如 http://www.baodu.com
     * 可以为空
     *
     * @return 请求前缀
     */
    String value() default "";

    /**
     * 超时时间
     *
     * @return 毫秒
     */
    int timeout() default -1;
}
