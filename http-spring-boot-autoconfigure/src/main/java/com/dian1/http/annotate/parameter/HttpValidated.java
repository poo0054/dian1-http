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

import com.dian1.http.handle.ParameterHandle;

import javax.validation.groups.Default;
import java.lang.annotation.*;

/**
 * 在参数上,校验当前参数
 *
 * @author zhangzhi
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpValidated {
    /**
     * @return 分组
     */
    Class[] groups() default Default.class;

    /**
     * @return 指定处理类, 必须为{@link ParameterHandle}的子类全类名 或者注入spring的name
     */
    String handleClassName() default "";
}
