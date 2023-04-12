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
import com.dian1.http.annotate.parameter.*;
import com.dian1.http.annotate.type.Get;
import com.dian1.http.annotate.type.Post;
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
     *
     * @return 返回strng
     */
    @Get("/m1/2406035-0-default/omsProductDetail/get")
    @Auth("123456789")
    String get();

    @Get("m1/2406035-0-default/omsProductDetail/getProductCode/{code}")
    String restful(@BasicAuth Map nu, @Restful("code") String code);

    @Get("m1/2406035-0-default/omsProductHeader/get")
    void form(String id, Consumer consumer, @Auth("key") Map key);

    @Post("/m1/2406035-0-default/omsContractHead/list")
    @BasicAuth(username = "abc", password = "asd")
    OmsContractHead basicAuth(Map map);

    @Post("/m1/2406035-0-default/omsContractHead/list")
    @Header({"Authorization: 123456789", "Accept-Encoding: gzip"})
    OmsContractHead header();

    @Post("/m1/2406035-0-default/omsContractHead/list")
    OmsContractHead headerPar(String str);

    @Post("/m1/2406035-0-default/omsContractHead/list")
    OmsContractHead headerMap(@Header Map str);

    @Post("https://oms.test.1-dian.cn/oms/gen/download/1637748183482265601")
    @Header("Authorization: Bearer 318e7574-8e33-4a00-8e64-beeb15eb1ce3")
    HttpResponse dowFile(File file);

    @Post("https://oms.test.1-dian.cn/oms/gen/download/1637748183482265601")
    @Header("Authorization: Bearer 318e7574-8e33-4a00-8e64-beeb15eb1ce3")
    HttpResponse dowOutputStream(@Download OutputStream outputStream, StreamProgress streamProgress);

    @Post("http://127.0.0.1:4523/m1/2406035-0-default/common/templateUploadFile")
    void uploadMap(Map<String, Object> map);

    @Post("test")
    void upload(@Form("file") File file);

    @Post("https://mock.apifox.cn/m1/2406035-0-default/omsProductDetail/add?apifoxToken=byFyzN6aEZfSNoiLWuoaBc7dvPtTlWo8")
    void validate(@HttpValidated Request request, @Header("apifoxToken") String hean);
}
