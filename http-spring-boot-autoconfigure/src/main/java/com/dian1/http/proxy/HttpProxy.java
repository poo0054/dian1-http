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
package com.dian1.http.proxy;

import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.ssl.DefaultSSLInfo;
import com.dian1.http.build.BuildHttpRequest;
import com.dian1.http.build.BuildPropertiesClass;
import com.dian1.http.build.BuildPropertiesMethod;
import com.dian1.http.build.BuildPropertiesParameter;
import com.dian1.http.exception.HttpException;
import com.dian1.http.handle.HttpHandleCompose;
import com.dian1.http.net.HttpSSLFactory;
import com.dian1.http.properties.HttpProperties;
import com.dian1.http.utils.ExceptionConsumer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.cache.annotation.EnableCaching;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Proxy;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 接口的代理对象
 *
 * @author zhangzhi

 */
@Data
@Slf4j
@EnableCaching
public class HttpProxy<T> implements InvocationHandler {

    public static List<Class<?>> linkedList = new LinkedList<>();

    static {
        linkedList.add(Consumer.class);
        linkedList.add(File.class);
        linkedList.add(OutputStream.class);
        //进度条
        linkedList.add(StreamProgress.class);
        //返回对象
        linkedList.add(HttpResponse.class);
        //ssl
        linkedList.add(HostnameVerifier.class);
        linkedList.add(SSLSocketFactory.class);
        //代理
        linkedList.add(Proxy.class);
    }

    /**
     * mapper接口
     */
    private final Class<T> httpInterfaces;

    private HttpHandleCompose httpHandleCompose;

    private BuildPropertiesClass buildPropertiesClass;

    private BuildPropertiesMethod buildPropertiesMethod;

    private BuildPropertiesParameter buildPropertiesParameter;

    private BuildHttpRequest buildHttpRequest;

    public HttpProxy(Class<T> httpInterfaces) {
        this.httpInterfaces = httpInterfaces;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Method mostSpecificMethod = AopUtils.getMostSpecificMethod(method, httpInterfaces);
        //调用object方法
        if (AopUtils.isEqualsMethod(method)) {
            // The target does not implement the equals(Object) method itself.
            return equals(args[0]);
        } else if (AopUtils.isHashCodeMethod(method)) {
            // The target does not implement the hashCode() method itself.
            return hashCode();
        } else if (AopUtils.isToStringMethod(mostSpecificMethod)) {
            return toString();
        }

        HttpProperties properties = new HttpProperties();
        properties.setMostSpecificMethod(mostSpecificMethod);
        properties.setArgs(args);
        return exec(properties, (e, name) -> {
            log.error("{}构建失败   properties:{}", name, properties);
            e.printStackTrace();
        });
    }

    private Object exec(HttpProperties properties, ExceptionConsumer exceptionConsumer) {
        //ssl默认信任所有
        if (properties.isTrustSsl()) {
            properties.setSsf(HttpSSLFactory.SSL_FACTORY);
            properties.setHostnameVerifier(DefaultSSLInfo.TRUST_ANY_HOSTNAME_VERIFIER);
        }

        String error;
        //首先处理一些特殊的参数,  Consumer<HttpResponse> file  OutputStream
        try {
            handleSpecialArgs(properties);
        } catch (Exception e) {
            error = "handleSpecialArgs";
            exceptionConsumer.accept(e, error);
        }
        //类
        try {
            buildPropertiesClass.buildClassHandle(httpInterfaces, properties);
        } catch (Exception e) {
            error = "buildClassHandle";
            exceptionConsumer.accept(e, error);
        }
        //方法
        try {
            buildPropertiesMethod.buildMethodHandles(properties);
        } catch (Exception e) {
            error = "buildMethodHandles";
            exceptionConsumer.accept(e, error);
        }
        //参数
        try {
            buildPropertiesParameter.buildParameterHandle(properties);
        } catch (Exception e) {
            error = "buildParameterHandle";
            exceptionConsumer.accept(e, error);
        }
        //构建请求并构建返回
        try {
            return buildHttpRequest.response(buildHttpRequest.request(properties), properties);
        } catch (Exception e) {
            error = "BuildHttpRequest";
            exceptionConsumer.accept(e, error);
        }
        if (properties.isError()) {
            throw new HttpException(error);
        }
        return null;
    }

    private void handleSpecialArgs(HttpProperties properties) {
        Object[] args = properties.getArgs();
        //首先处理一些特殊的参数, Consumer < HttpResponse > file OutputStream
        if (ObjectUtil.isEmpty(args)) {
            return;
        }
        Method method = properties.getMostSpecificMethod();
        for (int i = 0; i < method.getParameterCount(); i++) {
            Class<?> parameterType = method.getParameterTypes()[i];
            //存在当前存在的注解的直接排除
            if (HttpHandleCompose.allHandleKeyExist(method.getParameters()[i])) {
                return;
            }
            if (linkedList.get(0).isAssignableFrom(parameterType)) {
                properties.setConsumer((Consumer) args[i]);
            } else if (linkedList.get(1).isAssignableFrom(parameterType)) {
                properties.setFile((File) args[i]);
            } else if (linkedList.get(2).isAssignableFrom(parameterType)) {
                properties.setOutputStream((OutputStream) args[i]);
            } else if (linkedList.get(3).isAssignableFrom(parameterType)) {
                properties.setStreamProgress((StreamProgress) args[i]);
            }
        }
    }

}
