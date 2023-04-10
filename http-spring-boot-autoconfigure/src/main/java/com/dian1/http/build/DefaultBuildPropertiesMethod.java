package com.dian1.http.build;

import cn.hutool.core.util.ClassUtil;
import com.dian1.http.handle.HttpHandleCompose;
import com.dian1.http.handle.MethodHandle;
import com.dian1.http.properties.HttpProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author zhangzhi
 * @date 2023/4/7
 */
@Data
@Slf4j
public class DefaultBuildPropertiesMethod implements BuildPropertiesMethod {

    @Autowired
    private HttpHandleCompose httpHandleCompose;

    @Override
    public <V extends Annotation> void buildMethodHandles(HttpProperties httpProperties) {
        Method method = httpProperties.getMostSpecificMethod();
        List<MethodHandle> allMethodHandle = httpHandleCompose.getAllMethodHandle(method);
        for (MethodHandle<V> methodHandle : allMethodHandle) {
            Class<V> typeArgument = (Class<V>) ClassUtil.getTypeArgument(methodHandle.getClass());
            V annotation = AnnotationUtils.findAnnotation(method, typeArgument);
            methodHandle.resolving(httpProperties, annotation);
        }
    }
}
