package com.dian1.http.handle.parameter;

import com.dian1.http.annotate.parameter.Download;
import com.dian1.http.handle.MethodHandle;
import com.dian1.http.handle.ParameterHandle;
import com.dian1.http.properties.HttpProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhangzhi
 * @date 2023/3/29
 */
@Component
public class DownloadHandle implements ParameterHandle<Download>, MethodHandle<Download> {


    @Override
    public HttpProperties resolving(HttpProperties properties, Download download) {
        return properties;
    }

    @Override
    public HttpProperties resolving(HttpProperties properties, Object arg, Download download) {
        return properties;
    }
}
