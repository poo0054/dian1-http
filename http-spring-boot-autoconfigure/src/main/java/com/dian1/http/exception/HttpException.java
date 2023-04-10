package com.dian1.http.exception;

import cn.hutool.core.util.StrUtil;

/**
 * @author zhangzhi
 * @date 2023/4/10
 */
public class HttpException extends RuntimeException {
    private static final long serialVersionUID = 4443507961031283271L;

    public HttpException(Throwable e) {
        super(e.getMessage(), e);
    }

    public HttpException(String message) {
        super(message);
    }

    public HttpException(String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params));
    }

    public HttpException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public HttpException(Throwable throwable, String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params), throwable);
    }
}
