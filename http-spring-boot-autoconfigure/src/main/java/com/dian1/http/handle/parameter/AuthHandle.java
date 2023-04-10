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

import cn.hutool.http.Header;
import com.dian1.http.annotate.parameter.Auth;
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
public class AuthHandle implements ParameterHandle<Auth>, MethodHandle<Auth> {

    @Override
    public HttpProperties resolving(HttpProperties properties, Object arg, Auth basicAuth) {
        if (Map.class.isAssignableFrom(arg.getClass())) {
            properties.getHeaders().computeIfAbsent(Header.AUTHORIZATION.getValue(), s -> {
                Map arg1 = (Map) arg;
                Object username = arg1.get(basicAuth.value());
                return username.toString();
            });
        }
        return properties;
    }

    @Override
    public HttpProperties resolving(HttpProperties properties, Auth auth) {
        properties.getHeaders().computeIfAbsent(Header.AUTHORIZATION.getValue(), s -> auth.value());
        return properties;
    }

    @Override
    public int order() {
        return 1;
    }
}
