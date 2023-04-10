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
package com.dian1.http.handle.type;

import com.dian1.http.annotate.OpenHttp;
import com.dian1.http.handle.ClassHandle;
import com.dian1.http.properties.HttpProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author zhangzhi
 * @date 2023/3/28
 */
@Component
@Data
public class OpenHttpHandle implements ClassHandle<OpenHttp> {


    @Override
    public HttpProperties resolving(HttpProperties httpProperties, OpenHttp openHttp) {
        httpProperties.setBaseUrl(openHttp.value());
        httpProperties.setTimeout(openHttp.timeout());
        return httpProperties;
    }
}
