package com.dian1.http.annotate.type;

import com.dian1.http.handle.method.DeleteHandle;

import java.lang.annotation.*;

/**
 * Delete请求
 *
 * @author zhangzhi
 * @date 2023/3/27
 * @see DeleteHandle
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Delete {
    /**
     * 路径,可以为空.自行在处理器里面处理
     *
     * @return 路径
     */
    String value();

    /**
     * 超时时间
     *
     * @return 毫秒
     */
    int timeout() default -1;

    /**
     * 编码
     *
     * @return 编码格式
     */
    String charSet() default "UTF-8";
}
