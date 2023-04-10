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

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.dian1.http.annotate.parameter.Body;
import com.dian1.http.handle.ParameterHandle;
import com.dian1.http.properties.HttpProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhangzhi
 * @date 2023/3/28
 */
@Component
public class BodyHandle implements ParameterHandle<Body> {


    @Override
    public HttpProperties resolving(HttpProperties properties, Object arg, Body body) {
        //map类型
        if (Map.class.isAssignableFrom(arg.getClass())) {
            properties.getBody().putAll((Map) arg);
            return properties;
        }
        //有参数的
        if (StrUtil.isNotBlank(body.value())) {
            properties.getBody().put(body.value(), arg);
        } else {
            //无参数的
            if (String.class.isAssignableFrom(arg.getClass())) {
                properties.setBodyStr((String) arg);
                return properties;
            }
            //其余类型默认为转换string
            properties.setBodyStr(JSON.toJSONString(arg));
        }
        return properties;
    }

}
