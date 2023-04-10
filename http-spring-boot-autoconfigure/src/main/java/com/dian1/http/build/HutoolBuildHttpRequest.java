package com.dian1.http.build;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.dian1.http.handle.HttpHandleCompose;
import com.dian1.http.properties.HttpProperties;
import com.dian1.http.proxy.HttpProxy;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author zhangzhi
 * @date 2023/4/7
 */
@Data
@Slf4j
public class HutoolBuildHttpRequest implements BuildHttpRequest<HttpRequest> {

    @Autowired
    private HttpHandleCompose httpHandleCompose;

    @Override
    public HttpRequest request(HttpProperties properties) {
        String baseUrl = properties.getBaseUrl();
        String url = null;
        if (StrUtil.isNotBlank(baseUrl)) {
            url = URLUtil.completeUrl(UrlBuilder.ofHttp(baseUrl, properties.getCharset()).build(), properties.getRelativePath());
        }
        url = (null == url) ? properties.getRelativePath() : url;
        properties.setUrl(url);
        HttpRequest httpRequest = new HttpRequest(url);
        httpRequest.setMethod(properties.getMethod());
        httpRequest.setRest(properties.isRest());
        if (ObjectUtil.isNotEmpty(properties.getForm())) {
            httpRequest.form(properties.getForm());
        }
        if (ObjectUtil.isNotEmpty(properties.getBody())) {
            httpRequest.body(JSON.toJSONString(properties.getBody()));
        }
        httpRequest.timeout(properties.getTimeout());
        httpRequest.setUrlHandler(properties.getUrlHandler());
        httpRequest.cookie(properties.getCookie());
        if (properties.isDisableCache()) {
            httpRequest.disableCache();
        }
        if (properties.isDisableCookie()) {
            httpRequest.disableCookie();
        }
        httpRequest.setProxy(properties.getProxy());
        httpRequest.setHostnameVerifier(properties.getHostnameVerifier());
        httpRequest.setSSLSocketFactory(properties.getSsf());
        Charset charset = properties.getCharset();
        httpRequest.charset(null == charset ? CharsetUtil.CHARSET_UTF_8 : charset);
        Map<String, String> headers = properties.getHeaders();
        if (ObjectUtil.isNotEmpty(headers)) {
            headers.forEach(httpRequest::header);
        }
        httpRequest.contentType(properties.getContentType());
        httpRequest.setFollowRedirects(properties.isFollowRedirects());
        return httpRequest;
    }

    @Override
    public <V extends Annotation> Object response(HttpRequest httpRequest, Method method, HttpProperties httpProperties) {
        log.info("httpRequest:{} \n httpRequest-form:{} \n 参数:{}", httpRequest, httpRequest.form(), httpProperties);
        return resolving(httpRequest, httpProperties);
    }

    /**
     * 处理接口返回
     *
     * @param httpRequest httpRequest
     * @param properties  properties配置文件
     * @return 接口返回值
     */
    public <V> Object resolving(HttpRequest httpRequest, HttpProperties properties) {
        //流 文件 HttpResponse处理
        Class<?> returnType = properties.getMostSpecificMethod().getReturnType();
        if (ObjectUtil.isNotEmpty(properties.getFile())) {
            HttpResponse response = httpRequest.setFollowRedirects(true).executeAsync();
            long l = response.writeBody(properties.getFile(), properties.getStreamProgress());
            return returnDownload(returnType, response, l);
        } else if (ObjectUtil.isNotEmpty(properties.getOutputStream())) {
            HttpResponse response = httpRequest.setFollowRedirects(true).executeAsync();
            //TODO  可以把是否关闭流抽象出来
            long l = response.writeBody(properties.getOutputStream(), true, properties.getStreamProgress());
            return returnDownload(returnType, response, l);
        } else if (ObjectUtil.isNotEmpty(properties.getConsumer())) {
            // 兼容后面版本
            try (HttpResponse response = httpRequest.execute(true)) {
                properties.getConsumer().accept(response);
            }
            return null;
        } else if (HttpProxy.linkedList.get(4).isAssignableFrom(returnType)) {
            HttpResponse execute = httpRequest.execute(properties.isAsync());
            return execute;
        }
        //默认
        HttpResponse response = httpRequest.execute(properties.isAsync());
        log.info("response :{}", response.toString());
        String body = response.body();
        if (response.isOk()) {
            return returnType(body, properties.getMostSpecificMethod());
        }
        throw new HttpException("请求:" + httpRequest + "\n返回:" + response);
    }


    public <T> Object returnType(String body, Method method) {
        if (method.getReturnType().isAssignableFrom(void.class)) {
            return null;
        }
        return JSON.parseObject(body, method.getGenericReturnType());
    }

    public Object returnDownload(Class<?> returnType, HttpResponse response, long l) {
        if (returnType.isAssignableFrom(HttpResponse.class)) {
            return response;
        } else if (returnType.isAssignableFrom(long.class) || returnType.isAssignableFrom(Long.class)) {
            return l;
        }
        return null;
    }
}
