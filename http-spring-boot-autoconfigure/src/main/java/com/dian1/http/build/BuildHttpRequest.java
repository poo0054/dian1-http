package com.dian1.http.build;

import com.dian1.http.properties.HttpProperties;

import java.lang.annotation.Annotation;

/**
 * @author zhangzhi
 * @date 2023/4/7
 */
public interface BuildHttpRequest<T> {
    /**
     * 构建请求
     *
     * @param properties 参数
     * @return 请求
     */
    T request(HttpProperties properties);


    <V extends Annotation> Object response(T httpRequest, HttpProperties httpProperties);
}
