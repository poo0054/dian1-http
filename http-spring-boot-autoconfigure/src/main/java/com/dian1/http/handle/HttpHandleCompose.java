package com.dian1.http.handle;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ClassUtil;
import com.dian1.http.handle.parameter.FormHandle;
import com.dian1.http.proxy.HttpProxy;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 处理器的Compose
 *
 * @author zhangzhi
 * @date 2023/3/27
 */
@Data
public class HttpHandleCompose implements ApplicationContextAware, InitializingBean {

    /**
     * 用来存储spring中所有的HttpHandle
     */
    private static Map<Class, HttpHandle> handleMap = new HashMap<>();
    private ApplicationContext applicationContext;
    /**
     * 适配自动注入form
     */
    @Autowired
    private FormHandle formHandle;

    /**
     * 对应的ClassHandle的实现
     */
    private Map<Class, List<ClassHandle>> classHandle = new ConcurrentHashMap<>();
    /**
     * 对应的methodHandle的实现
     */
    private Map<Method, List<MethodHandle>> typeHandle = new ConcurrentHashMap<>();
    /**
     * 对应ParameterHandle的实现
     */
    private Map<Method, List<ParameterHandle>> parameterHandle = new ConcurrentHashMap<>();

    public static List<ClassHandle> classHandle(Class httpinterfaces) {
        List<ClassHandle> typeHandles = new LinkedList<>();
        Annotation[] annotations = AnnotationUtil.getAnnotations(httpinterfaces, true);
        for (Annotation annotation : annotations) {
            for (Class key : handleMap.keySet()) {
                HttpHandle httpHandle = handleMap.get(key);
                if (httpHandle instanceof ClassHandle && annotation.annotationType().isAssignableFrom(key)) {
                    typeHandles.add((ClassHandle) httpHandle);
                }
            }
        }
        typeHandles.sort(Comparator.comparingInt(sort::order));
        return typeHandles;
    }

    public static List<MethodHandle> methodHandle(Method method) {
        List<MethodHandle> methodHandles = new LinkedList<>();
        Annotation[] annotations = AnnotationUtil.getAnnotations(method, true);
        for (Annotation annotation : annotations) {
            for (Class key : handleMap.keySet()) {
                HttpHandle httpHandle = handleMap.get(key);
                if (httpHandle instanceof MethodHandle && annotation.annotationType().isAssignableFrom(key)) {
                    methodHandles.add((MethodHandle) httpHandle);
                }
            }
        }
        methodHandles.sort(Comparator.comparingInt(sort::order));
        return methodHandles;
    }

    public static Set<Class> allHandleKey() {
        return handleMap.keySet();
    }

    public static boolean allHandleKeyExist(Parameter parameterType) {
        //TODO 添加缓存
        for (Class aClass : allHandleKey()) {
            if (AnnotationUtil.hasAnnotation(parameterType, aClass)) {
                return true;
            }
        }
        return false;
    }

    public List<ClassHandle> getAllClassHandle(Class aClass) {
        return classHandle.computeIfAbsent(aClass, HttpHandleCompose::classHandle);
    }

    public <T> List<MethodHandle> getAllMethodHandle(Method method) {
        return typeHandle.computeIfAbsent(method, HttpHandleCompose::methodHandle);
    }

    public <T> List<ParameterHandle> getAllParameterHandle(Method method) {
        return parameterHandle.computeIfAbsent(method, (key) -> parameterHandle(method));
    }

    public List<ParameterHandle> parameterHandle(Method method) {
        List<ParameterHandle> typeHandles = new LinkedList<>();
        for (Parameter parameter : method.getParameters()) {
            Annotation[] annotations = AnnotationUtil.getAnnotations(parameter, true);
            if (null == annotations) {
                break;
            }
            //处理自动添加form属性
            if (!allHandleKeyExist(parameter) && !HttpProxy.linkedList.contains(parameter.getType())) {
                typeHandles.add(formHandle);
            }
            for (Annotation annotation : annotations) {
                for (Class key : handleMap.keySet()) {
                    HttpHandle httpHandle = handleMap.get(key);
                    if (httpHandle instanceof ParameterHandle && annotation.annotationType().isAssignableFrom(key)) {
                        typeHandles.add((ParameterHandle) httpHandle);
                    }
                }
            }
        }
        typeHandles.sort(Comparator.comparingInt(sort::order));
        return typeHandles;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private void init() {
        ObjectProvider<HttpHandle> httpHandles = applicationContext.getBeanProvider(HttpHandle.class);
        for (HttpHandle handle : httpHandles) {
            Class typeArgument = ClassUtil.getTypeArgument(handle.getClass());
            if (null != typeArgument) {
                handleMap.put(typeArgument, handle);
            }
        }
    }
}
