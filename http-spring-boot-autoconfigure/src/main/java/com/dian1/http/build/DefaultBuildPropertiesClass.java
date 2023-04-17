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
package com.dian1.http.build;

import cn.hutool.core.util.ClassUtil;
import com.dian1.http.handle.ClassHandle;
import com.dian1.http.handle.HttpHandleCompose;
import com.dian1.http.properties.HttpProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author zhangzhi
 */
@Data
@Slf4j
public class DefaultBuildPropertiesClass implements BuildPropertiesClass {

    @Autowired
    private HttpHandleCompose httpHandleCompose;

    @Override
    public <V extends Annotation> void buildClassHandle(Class httpInterfaces, HttpProperties httpProperties) {
        if (null == httpProperties) {
            httpProperties = new HttpProperties();
        }
        List<ClassHandle> allClassHandle = httpHandleCompose.getAllClassHandle(httpInterfaces);
        for (ClassHandle<V> typeHandle : allClassHandle) {
            Class<V> typeArgument = (Class<V>) ClassUtil.getTypeArgument(typeHandle.getClass());
            V annotation = AnnotationUtils.findAnnotation(httpInterfaces, typeArgument);
            typeHandle.resolving(httpProperties, annotation);
        }
    }
}
