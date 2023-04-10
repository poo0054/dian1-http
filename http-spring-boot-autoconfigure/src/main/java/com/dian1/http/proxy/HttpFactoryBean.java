package com.dian1.http.proxy;

import com.dian1.http.build.BuildHttpRequest;
import com.dian1.http.build.BuildPropertiesClass;
import com.dian1.http.build.BuildPropertiesMethod;
import com.dian1.http.build.BuildPropertiesParameter;
import com.dian1.http.handle.HttpHandleCompose;
import lombok.Getter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * 当前默认的FactoryBean.把 {@link com.dian1.http.annotate.OpenHttp}注解的类创建代理对象
 *
 * @author zhangzhi
 * @date 2023/3/27
 */
@Getter
public class HttpFactoryBean<T> implements FactoryBean<T>, BeanFactoryAware {

    BeanFactory beanFactory;
    /**
     * 当前接口类------被OpenHttp注释的接口
     */
    private Class<T> httpInterfaces;
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
        if (!init) {
            this.httpHandleCompose = beanFactory.getBean(HttpHandleCompose.class);
            this.buildPropertiesClass = beanFactory.getBean(BuildPropertiesClass.class);
            this.buildPropertiesMethod = beanFactory.getBean(BuildPropertiesMethod.class);
            this.buildPropertiesParameter = beanFactory.getBean(BuildPropertiesParameter.class);
            this.buildHttpRequest = beanFactory.getBean(BuildHttpRequest.class);
            init = true;
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

}
