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
package com.dian1.http.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * @author zhangzhi
 * @date 2023/4/4
 */
public class ClassUtils {


    public static <T> T getBean(String name, Class<T> tClass) {
        return getBean(name, ApplicationContextUtils.getApplicationContext(), tClass);
    }

    public static <T> T getBean(String name, ApplicationContext applicationContext, Class<T> tClass) {
        T bean;
        try {
            bean = applicationContext.getBean(name, tClass);
        } catch (BeansException e) {
            try {
                Class<T> aClass = (Class<T>) Class.forName(name);
                bean = org.springframework.objenesis.instantiator.util.ClassUtils.newInstance(aClass);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
        return bean;
    }


}
