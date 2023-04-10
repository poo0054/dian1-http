package com.dian1.http.utils;

/**
 * @author zhangzhi
 * @date 2023/4/10
 */
@FunctionalInterface
public interface ExceptionConsumer {
    void accept(Throwable e, String modeName);
}
