/*
 * Copyright 2023 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dian1.http.properties;

import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpGlobalConfig;
import cn.hutool.http.Method;
import lombok.Data;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpCookie;
import java.net.Proxy;
import java.net.URLStreamHandler;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 配置对象,根据该对象构建请求对象
 *
 * @author zhangzhi
 * @date 2023/3/27
 */
@Data
public class HttpProperties implements Serializable {

    private static final long serialVersionUID = 4457928591528776972L;
    URLStreamHandler urlHandler;
    /**
     * 执行url
     */
    private String url;

    /**
     * relativePath – 相对URL
     */
    private String relativePath;
    /**
     * baseUrl – 基准URL 如:127.0.0.1:4523  其余都会删除
     */
    private String baseUrl;
    /**
     * 方式
     */
    private Method method;

    /**
     * 默认读取超时
     */
    private int timeout = HttpGlobalConfig.getTimeout();

    /**
     * 存储表单数据
     */
    private Map<String, Object> form = new HashMap<>();

    /**
     * body
     * 当使用body时，停止form的使用
     */
    private Map<String, Object> body = new HashMap<>();

    /**
     * 设置内容主体 请求体body参数支持两种类型：
     * 1. 标准参数，例如 a=1&b=2 这种格式
     * 2. Rest模式，此时body需要传入一个JSON或者XML字符串
     */
    private String bodyStr;

    /**
     * 优先级 bodyBytes>bodyStr>body
     * 设置主体字节码 需在此方法调用前使用charset方法设置编码，否则使用默认编码UTF-8
     */
    private byte[] bodyBytes;

    /**
     * 设置内容主体<br>
     * 请求体body参数支持两种类型：
     *
     * <pre>
     * 1. 标准参数，例如 a=1&amp;b=2 这种格式
     * 2. Rest模式，此时body需要传入一个JSON或者XML字符串，Hutool会自动绑定其对应的Content-Type
     * </pre>
     */
    private String contentType;

    /**
     * 请求头信息
     */
    private Map<String, String> headers = new HashMap<>();
    /**
     * Cookie
     */
    private Collection<HttpCookie> cookie;

    /**
     * 是否禁用缓存
     */
    private boolean disableCache;

    /**
     * 是否禁用cookie
     */
    private boolean disableCookie;

    /**
     * 是否是REST请求模式
     */
    private boolean isRest;

    /**
     * 代理
     */
    private Proxy proxy;

    /**
     * HostnameVerifier，用于HTTPS安全连接
     */
    private HostnameVerifier hostnameVerifier;
    /**
     * SSLSocketFactory，用于HTTPS安全连接
     */
    private SSLSocketFactory ssf;

    /**
     * 编码
     */
    private Charset charset = CharsetUtil.CHARSET_UTF_8;

    /**
     * 进度条
     */
    private StreamProgress streamProgress;

    /**
     * 比文件和流优先级低
     */
    private Consumer consumer;

    //下面二种只能存在一种,从上往下顺序优先级 返回值必须为long   字节长度

    private File file;

    private OutputStream outputStream;

    /**
     * 是否打开重定向,文件下载默认打开
     */
    private boolean isFollowRedirects;
    /**
     * 是否异步
     */
    private boolean isAsync;

    /**
     * 其余参数
     */
    private Map other;

    private java.lang.reflect.Method mostSpecificMethod;

    private Object[] args;
}
