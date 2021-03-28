package com.module.aop.annotation.permission;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PermissionUtil {


    public static void invokeAnnotation(Object object,Class annotationClass, int requestCode){

        //获取切面上下文类型
        Class clazz = object.getClass();

        //获取类型中的方法
        Method[] methods = clazz.getDeclaredMethods();
        if(methods.length == 0){
            return;
        }

        for (Method method : methods){
            boolean isContainAnnotation = method.isAnnotationPresent(annotationClass);
            if(isContainAnnotation){
               Class[] parameterTypes  =  method.getParameterTypes();
                if(parameterTypes.length != 1){
                    throw new RuntimeException("有且仅有一个 int 参数");
                }

                method.setAccessible(true);
                try {
                    method.invoke(object,requestCode);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
