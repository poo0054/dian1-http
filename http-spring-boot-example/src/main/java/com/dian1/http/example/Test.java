package com.dian1.http.example;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.ssl.DefaultSSLInfo;
import com.dian1.http.net.HttpSSLFactory;

import java.io.File;

/**
 * @author zhangzhi

 */
public class Test {
    public static void main(String[] args) {
        String url = "https://oms.test.1-dian.cn/oms/gen/download/1637748183482265601";
        HttpRequest post = HttpRequest.post(url);
        post.setSSLSocketFactory(HttpSSLFactory.SSL_FACTORY);
        post.setHostnameVerifier(DefaultSSLInfo.TRUST_ANY_HOSTNAME_VERIFIER);
        HttpResponse response = post.timeout(-1).executeAsync();
        response.writeBody(new File("d://poo0054.txt"));
    }
}
