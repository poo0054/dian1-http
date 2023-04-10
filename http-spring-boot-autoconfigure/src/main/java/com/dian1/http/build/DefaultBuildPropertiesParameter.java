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

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import com.dian1.http.handle.HttpHandleCompose;
import com.dian1.http.handle.ParameterHandle;
import com.dian1.http.handle.parameter.FormHandle;
import com.dian1.http.properties.HttpProperties;
import com.dian1.http.proxy.HttpProxy;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * @author zhangzhi
 * @date 2023/4/7
 */
@Data
@Slf4j
public class DefaultBuildPropertiesParameter implements BuildPropertiesParameter {

    @Autowired
    private HttpHandleCompose httpHandleCompose;


    @Override
    public <V extends Annotation> void buildParameterHandle(HttpProperties httpProperties) {
        Method method = httpProperties.getMostSpecificMethod();
        Object[] args = httpProperties.getArgs();
        List<ParameterHandle> allParameterHandle = httpHandleCompose.getAllParameterHandle(method);
        if (ObjectUtil.isEmpty(allParameterHandle) || ObjectUtil.isEmpty(args)) {
            return;
        }
        for (ParameterHandle<V> parameterHandle : allParameterHandle) {
            Class<V> typeArgument = (Class<V>) ClassUtil.getTypeArgument(parameterHandle.getClass());
            for (int i = 0; i < method.getParameterCount(); i++) {
                Parameter parameter = method.getParameters()[i];
                V annotation = AnnotationUtils.findAnnotation(parameter, typeArgument);
                //处理自动添加form属性
                if (ObjectUtil.isNotEmpty(annotation)) {
                    parameterHandle.resolving(httpProperties, args[i], annotation);
                } else {
                    //处理自动添加form属性
                    if (ObjectUtil.isEmpty(AnnotationUtil.getAnnotations(parameter, true))
                            && FormHandle.class.isAssignableFrom(parameterHandle.getClass())
                            && !HttpProxy.linkedList.contains(parameter.getType())) {
                        String[] parameterNames = new DefaultParameterNameDiscoverer().getParameterNames(method);
                        FormHandle httpHandle1 = (FormHandle) parameterHandle;
                        httpHandle1.autoResolving(httpProperties, args[i], parameterNames[i]);
                    }
                }
            }
        }
    }
}
