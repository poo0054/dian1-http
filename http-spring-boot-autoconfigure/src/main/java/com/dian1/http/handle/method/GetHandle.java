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
import com.dian1.http.annotate.method.Get;
import com.dian1.http.handle.MethodHandle;
import com.dian1.http.properties.HttpProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhangzhi
 */
@Component
public class GetHandle implements MethodHandle<Get> {
    @Override
    public HttpProperties resolving(HttpProperties properties, Get get) {
        properties.setMethod(Method.GET);
        properties.setRelativePath(get.value());
        if (-1 != get.timeout()) {
            properties.setTimeout(get.timeout());
        }
        if (StrUtil.isNotBlank(get.charSet())) {
            properties.setCharset(CharsetUtil.charset(get.charSet()));
        }
        return properties;
    }

}
