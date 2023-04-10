package com.dian1.http.proxy;

import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpResponse;
import com.dian1.http.build.BuildHttpRequest;
import com.dian1.http.build.BuildPropertiesClass;
import com.dian1.http.build.BuildPropertiesMethod;
import com.dian1.http.build.BuildPropertiesParameter;
import com.dian1.http.handle.HttpHandleCompose;
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
 * @date 2023/3/27
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
        HttpProperties properties = new HttpProperties();
        Method mostSpecificMethod = AopUtils.getMostSpecificMethod(method, httpInterfaces);
        properties.setMostSpecificMethod(mostSpecificMethod);
        properties.setArgs(args);
        return exec(properties, (e, name) -> {
            log.error("{}构建失败   properties:{}", name, properties);
            e.printStackTrace();
        });
    }

    private Object exec(HttpProperties properties, ExceptionConsumer exceptionConsumer) {
        //首先处理一些特殊的参数,  Consumer<HttpResponse> file  OutputStream
        try {
            handleSpecialArgs(properties);
        } catch (Exception e) {
            exceptionConsumer.accept(e, "handleSpecialArgs");
        }
        //类
        try {
            buildPropertiesClass.buildClassHandle(httpInterfaces, properties);
        } catch (Exception e) {
            exceptionConsumer.accept(e, "buildClassHandle");
        }
        //方法
        try {
            buildPropertiesMethod.buildMethodHandles(properties);
        } catch (Exception e) {
            exceptionConsumer.accept(e, "buildMethodHandles");
        }
        //参数
        try {
            buildPropertiesParameter.buildParameterHandle(properties);
        } catch (Exception e) {
            exceptionConsumer.accept(e, "buildParameterHandle");
        }
        //构建请求并构建返回
        try {
            return buildHttpRequest.response(buildHttpRequest.request(properties), properties);
        } catch (Exception e) {
            exceptionConsumer.accept(e, "BuildHttpRequest");
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
