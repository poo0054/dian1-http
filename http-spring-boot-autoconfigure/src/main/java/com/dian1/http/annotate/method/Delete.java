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
package com.dian1.http.annotate.method;

import com.dian1.http.handle.method.DeleteHandle;

import java.lang.annotation.*;

/**
 * Delete请求
 *
 * @author zhangzhi
 * @date 2023/3/27
 * @see DeleteHandle
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Delete {
    /**
     * 路径,可以为空.自行在处理器里面处理
     *
     * @return 路径
     */
    String value();

    /**
     * 超时时间
     *
     * @return 毫秒
     */
    int timeout() default -1;

    /**
     * 编码
     *
     * @return 编码格式
     */
    String charSet() default "UTF-8";
}
