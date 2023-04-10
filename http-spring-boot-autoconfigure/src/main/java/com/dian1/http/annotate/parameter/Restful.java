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
 * 处理{@link com.dian1.http.properties.HttpProperties#getRelativePath()}的一种方法.
 * <p>
 * 例如 /getProductCode/{code}  {@link  #value()}填写code就可以自动替换
 *
 * @author zhangzhi
 * @date 2023/3/28
 * @see com.dian1.http.handle.parameter.RestfulHandle
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Restful {

    /**
     * 当前参数的值
     *
     * @return {}表示
     */
    String value();
}
