package com.dian1.http.annotate.parameter;

import java.lang.annotation.*;

/**
 * 值放在请求的Body中
 *
 * @author zhangzhi
 * @date 2023/3/27
 * @see com.dian1.http.handle.parameter.BodyHandle
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Body {
    /**
     * body的参数.
     * key为空必须为map
     *
     * @return form的key
     */
    String value() default "";
}
