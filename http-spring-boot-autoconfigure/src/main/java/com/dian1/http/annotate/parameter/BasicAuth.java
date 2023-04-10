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
 * username    password
 * 构建简单的账号秘密验证信息，使用Base64.构建后类似于：
 * Basic YWxhZGRpbjpvcGVuc2VzYW1l
 * ------------------------------
 * 在方法上username和password必填
 * 在参数上参数为map,key为username和password的值 默认为username和password
 *
 * @author zhangzhi
 * @date 2023/3/27
 * @see com.dian1.http.handle.parameter.BasicAuthHandle
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BasicAuth {

    String username() default "username";

    String password() default "password";

    String charset() default "UTF-8";
}
