package com.dian1.http.build;

import com.dian1.http.properties.HttpProperties;

import java.lang.annotation.Annotation;

/**
 * @author zhangzhi
 * @date 2023/4/7
 */
public interface BuildPropertiesParameter {

    /**
     * 构建参数的处理器
     *
     * @param httpProperties 参数
     */
    <V extends Annotation> void buildParameterHandle(HttpProperties httpProperties);
}
