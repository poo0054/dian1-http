package com.dian1.http.handle.parameter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.dian1.http.annotate.parameter.Body;
import com.dian1.http.handle.ParameterHandle;
import com.dian1.http.properties.HttpProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhangzhi
 * @date 2023/3/28
 */
@Component
public class BodyHandle implements ParameterHandle<Body> {


    @Override
    public HttpProperties resolving(HttpProperties properties, Object arg, Body body) {
        //map类型
        if (Map.class.isAssignableFrom(arg.getClass())) {
            properties.getBody().putAll((Map) arg);
            return properties;
        }
        //有参数的
        if (StrUtil.isNotBlank(body.value())) {
            properties.getBody().put(body.value(), arg);
        } else {
            //无参数的
            if (String.class.isAssignableFrom(arg.getClass())) {
                properties.setBodyStr((String) arg);
                return properties;
            }
            //其余类型默认为转换string
            properties.setBodyStr(JSON.toJSONString(arg));
        }
        return properties;
    }

}
