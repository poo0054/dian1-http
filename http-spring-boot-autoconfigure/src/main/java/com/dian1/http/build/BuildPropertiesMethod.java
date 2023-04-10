package com.dian1.http.build;

import com.dian1.http.properties.HttpProperties;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author zhangzhi
 * @date 2023/4/7
 */
public interface BuildPropertiesMethod {

    /**
     * 构建方法属性
     *
     * @param method         当前调用方法
     * @param httpProperties 当前参数
     */
    <V extends Annotation> void buildMethodHandles(Method method, HttpProperties httpProperties);
}
