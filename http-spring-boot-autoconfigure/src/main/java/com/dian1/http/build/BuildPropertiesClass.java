package com.dian1.http.build;

import com.dian1.http.properties.HttpProperties;

import java.lang.annotation.Annotation;

/**
 * @author zhangzhi
 * @date 2023/4/7
 */
public interface BuildPropertiesClass {

    /**
     * 构建类属性
     *
     * @param httpInterfaces 当前接口
     * @param httpProperties 当前参数
     */
    <V extends Annotation> void buildClassHandle(Class httpInterfaces, HttpProperties httpProperties);
}
