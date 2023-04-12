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
package com.dian1.http.example.http;

import cn.hutool.core.io.StreamProgress;
import cn.hutool.http.HttpResponse;
import com.dian1.http.annotate.OpenHttp;
import com.dian1.http.annotate.method.Get;
import com.dian1.http.annotate.method.Post;
import com.dian1.http.annotate.parameter.*;
import com.dian1.http.example.entity.OmsContractHead;
import com.dian1.http.example.entity.Request;

import java.io.File;
import java.io.OutputStream;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author zhangzhi
 * @date 2023/3/27
 */
@OpenHttp("127.0.0.1:4523")
public interface HttpTest {

    /**
     * get方法
     * <p>
     * auth: 请求头 Authorization 的值  类似 Authorization: 123456789
     */
    @Get("/m1/2406035-0-default/omsProductDetail/get")
    @Auth("123456789")
    String get();

    /**
     * 普通授权,根据username:password 进行base64编译,构建后类似   Authorization: Basic YWxhZGRpbjpvcGVuc2VzYW1l
     */
    @Post("/m1/2406035-0-default/omsContractHead/list")
    @BasicAuth(username = "abc", password = "asd")
    OmsContractHead basicAuth(@Form Map map);

    /**
     * restfull风格替换
     * BasicAuth:参数上,值为map. 会根据注解的username找到map的key.注解的password找到value再进行base64编译
     */
    @Get("m1/2406035-0-default/omsProductDetail/getProductCode/{code}")
    String restful(@BasicAuth Map nu, @Restful("code") String code);

    /**
     * 参数的方式添加form 必须添加启动参数 -parameters
     *
     * @Auth :header对应map=key:value
     */
    @Get("m1/2406035-0-default/omsProductHeader/get")
    void form(String id, Consumer consumer, @Auth("key") Map key);

    /**
     * Header: 请求头信息
     */
    @Post("/m1/2406035-0-default/omsContractHead/list")
    @Header({"Authorization: 123456789", "Accept-Encoding: gzip"})
    OmsContractHead header();

    /**
     * 返回值默认fastjson格式化 .适配复杂的结构
     */
    @Post("/m1/2406035-0-default/omsContractHead/list")
    OmsContractHead headerPar(String str);

    /**
     * Header:对应map的key:value
     */
    @Post("/m1/2406035-0-default/omsContractHead/list")
    OmsContractHead headerMap(@Header Map str);

    /**
     * 文件下载
     * Header: 使用:分割
     */
    @Post("https://oms.test.1-dian.cn/oms/gen/download/1637748183482265601")
    @Header("Authorization: Bearer 318e7574-8e33-4a00-8e64-beeb15eb1ce3")
    HttpResponse dowFile(File file);

    /**
     * 文件下载
     */
    @Post("https://oms.test.1-dian.cn/oms/gen/download/1637748183482265601")
    @Header("Authorization: Bearer 318e7574-8e33-4a00-8e64-beeb15eb1ce3")
    HttpResponse dowOutputStream(OutputStream outputStream, StreamProgress streamProgress);

    /**
     * 文件上传:  会自动构建请求头.value为file即可
     *
     * @param map
     */
    @Post("http://127.0.0.1:4523/m1/2406035-0-default/common/templateUploadFile")
    void uploadMap(@Form Map<String, Object> map);

    /**
     * 文件上传:  会自动构建请求头.
     * key:key  value :file
     */
    @Post("test")
    void upload(@Form("key") File file);

    /**
     * Header: key为apifoxToken , value为header
     */
    @Post("https://mock.apifox.cn/m1/2406035-0-default/omsProductDetail/add?apifoxToken=byFyzN6aEZfSNoiLWuoaBc7dvPtTlWo8")
    void validate(@HttpValidated Request request, @Header("apifoxToken") String header);
}
