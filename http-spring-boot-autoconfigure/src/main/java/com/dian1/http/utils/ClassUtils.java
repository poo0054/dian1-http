package com.dian1.http.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * @author zhangzhi
 * @date 2023/4/4
 */
public class ClassUtils {


    public static <T> T getBean(String name, Class<T> tClass) {
        return getBean(name, ApplicationContextUtils.getApplicationContext(), tClass);
    }

    public static <T> T getBean(String name, ApplicationContext applicationContext, Class<T> tClass) {
        T bean;
        try {
            bean = applicationContext.getBean(name, tClass);
        } catch (BeansException e) {
            try {
                Class<T> aClass = (Class<T>) Class.forName(name);
                bean = org.springframework.objenesis.instantiator.util.ClassUtils.newInstance(aClass);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
        return bean;
    }


}
