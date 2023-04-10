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
package com.dian1.http.handle.parameter;

import com.dian1.http.annotate.parameter.Header;
import com.dian1.http.handle.MethodHandle;
import com.dian1.http.handle.ParameterHandle;
import com.dian1.http.properties.HttpProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhangzhi
 * @date 2023/3/29
 */
@Component
public class HeadHandle implements ParameterHandle<Header>, MethodHandle<Header> {

    @Override
    public HttpProperties resolving(HttpProperties properties, Object arg, Header basicAuth) {
        if (Map.class.isAssignableFrom(arg.getClass())) {
            Map map = (Map) arg;
            for (Object key : map.keySet()) {
                properties.getHeaders().computeIfAbsent(key.toString(), s -> map.get(key).toString());
            }
        } else {
            String key = basicAuth.value()[0];
            properties.getHeaders().computeIfAbsent(key, s -> arg.toString());
        }
        return properties;
    }

    @Override
    public HttpProperties resolving(HttpProperties properties, Header header) {
        for (String headStr : header.value()) {
            String[] split = headStr.split(":");
            properties.getHeaders().computeIfAbsent(split[0], key -> split[1]);
        }
        return properties;
    }

    @Override
    public int order() {
        return 1;
    }
}
