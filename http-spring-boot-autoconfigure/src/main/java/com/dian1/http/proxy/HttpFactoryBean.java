package com.dian1.http.proxy;

import com.dian1.http.build.BuildHttpRequest;
import com.dian1.http.build.BuildPropertiesClass;
import com.dian1.http.build.BuildPropertiesMethod;
import com.dian1.http.build.BuildPropertiesParameter;
import com.dian1.http.handle.HttpHandleCompose;
import com.dian1.http.utils.ApplicationContextUtils;
import lombok.Data;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Proxy;

/**
 * 当前默认的FactoryBean.把 {@link com.dian1.http.annotate.OpenHttp}注解的类创建代理对象
 *
 * @author zhangzhi
 * @date 2023/3/27
 */
@Data
public class HttpFactoryBean<T> implements FactoryBean<T> {

    BeanFactory beanFactory;
    /**
     * 当前接口类------被OpenHttp注释的接口
     */
    private Class<T> httpInterfaces;
    private ApplicationContext applicationContext;
    private HttpHandleCompose httpHandleCompose;
    private BuildPropertiesClass buildPropertiesClass;
    private BuildPropertiesMethod buildPropertiesMethod;
    private BuildPropertiesParameter buildPropertiesParameter;
    private BuildHttpRequest buildHttpRequest;

    private boolean init = false;

    public HttpFactoryBean(Class<T> httpInterfaces) {
        this.httpInterfaces = httpInterfaces;
    }

    @Override
    public T getObject() {
//        初始化
        init();
        HttpProxy<T> httpProxy = new HttpProxy<>(httpInterfaces);
        httpProxy.setHttpHandleCompose(httpHandleCompose);
        httpProxy.setBuildPropertiesClass(buildPropertiesClass);
        httpProxy.setBuildPropertiesMethod(buildPropertiesMethod);
        httpProxy.setBuildPropertiesParameter(buildPropertiesParameter);
        httpProxy.setBuildHttpRequest(buildHttpRequest);
        return (T) Proxy.newProxyInstance(httpInterfaces.getClassLoader(), new Class[]{httpInterfaces}, httpProxy);
    }


    @Override
    public Class<T> getObjectType() {
        return httpInterfaces;
    }

    public void init() {
        if (init) {
            applicationContext = ApplicationContextUtils.getApplicationContext();
            this.httpHandleCompose = applicationContext.getBean(HttpHandleCompose.class);
            this.buildPropertiesClass = applicationContext.getBean(BuildPropertiesClass.class);
            this.buildPropertiesMethod = applicationContext.getBean(BuildPropertiesMethod.class);
            this.buildPropertiesParameter = applicationContext.getBean(BuildPropertiesParameter.class);
            this.buildHttpRequest = applicationContext.getBean(BuildHttpRequest.class);
            init = true;
        }
    }


}
