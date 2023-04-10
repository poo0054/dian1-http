package com.dian1.http.build;

import cn.hutool.core.util.ClassUtil;
import com.dian1.http.handle.ClassHandle;
import com.dian1.http.handle.HttpHandleCompose;
import com.dian1.http.properties.HttpProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author zhangzhi
 * @date 2023/4/7
 */
@Data
@Slf4j
public class DefaultBuildPropertiesClass implements BuildPropertiesClass {

    @Autowired
    private HttpHandleCompose httpHandleCompose;

    @Override
    public <V extends Annotation> void buildClassHandle(Class httpInterfaces, HttpProperties httpProperties) {
        if (null == httpProperties) {
            httpProperties = new HttpProperties();
        }
        List<ClassHandle> allClassHandle = httpHandleCompose.getAllClassHandle(httpInterfaces);
        for (ClassHandle<V> typeHandle : allClassHandle) {
            Class<V> typeArgument = (Class<V>) ClassUtil.getTypeArgument(typeHandle.getClass());
            V annotation = AnnotationUtils.findAnnotation(httpInterfaces, typeArgument);
            typeHandle.resolving(httpProperties, annotation);
        }
    }
}
