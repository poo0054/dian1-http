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
package com.dian1.http.handle.method;


import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Method;
import com.dian1.http.annotate.type.Delete;
import com.dian1.http.handle.MethodHandle;
import com.dian1.http.properties.HttpProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhangzhi
 * @date 2023/3/27
 */
@Component
public class DeleteHandle implements MethodHandle<Delete> {

    @Override
    public HttpProperties resolving(HttpProperties properties, Delete delete) {
        properties.setMethod(Method.DELETE);
        properties.setRelativePath(delete.value());
        if (-1 != delete.timeout()) {
            properties.setTimeout(delete.timeout());
        }
        if (StrUtil.isNotBlank(delete.charSet())) {
            properties.setCharset(CharsetUtil.charset(delete.charSet()));
        }
        return properties;
    }
}
