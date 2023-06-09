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
import com.dian1.http.annotate.parameter.Form;
import com.dian1.http.handle.ParameterHandle;
import com.dian1.http.properties.HttpProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangzhi
 */
@Component
public class FormHandle implements ParameterHandle<Form> {


    @Override
    public HttpProperties resolving(HttpProperties properties, Object arg, Form restful) {
        Map<String, Object> form = properties.getForm();
        if (null == form) {
            form = new HashMap<>();
        }
        if (StrUtil.isNotBlank(restful.value())) {
            form.put(restful.value(), arg);
        } else {
            if (Map.class.isAssignableFrom(arg.getClass())) {
                form.putAll((Map) arg);
            }
        }
        properties.setForm(form);
        return properties;
    }

    /*
       适配不填写参数的方式添加form
       必须添加启动参数 -parameters
       <p>
       maven项目可以添加
       --------------------------
       <plugin>
       <groupId>org.apache.maven.plugins</groupId>
       <artifactId>maven-compiler-plugin</artifactId>
       <configuration>
       <source>${java.version}</source>
       <target>${java.version}</target>
       <encoding>${project.build.sourceEncoding}</encoding>
       <compilerArgs>
       <arg>-parameters</arg>
       </compilerArgs>
       </configuration>
       </plugin>
     */
    public HttpProperties autoResolving(HttpProperties properties, Object arg, String name) {
        Map<String, Object> form = properties.getForm();
        if (null == form) {
            form = new HashMap<>();
        }
        if (Map.class.isAssignableFrom(arg.getClass())) {
            form.putAll((Map) arg);
        } else {
            form.put(name, arg);
        }
        properties.setForm(form);
        return properties;
    }

}
