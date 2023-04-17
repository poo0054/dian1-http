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
 * 当前值会放入head Authorization 的 value
 * ------------------------------
 * 在方法上注解上必填,
 * <p>
 * 在参数上参数必须为map.取其中value的值放入 Authorization
 *
 * @author zhangzhi
 * @see com.dian1.http.handle.parameter.HeadHandle
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Header {

    /**
     * @return head属性
     */
    String[] value() default {"Accept: text/html,application/json,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"};

}
