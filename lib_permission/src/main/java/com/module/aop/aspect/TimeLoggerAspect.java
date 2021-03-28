package com.module.aop.aspect;

import android.util.Log;

import com.module.aop.annotation.TimeLogger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

//切面类
@Aspect
public class TimeLoggerAspect {

    @Around("execution(@com.module.aop.annotation.TimeLogger * *(..))")
    public void doLog(ProceedingJoinPoint joinPoint)  {

        long start = System.currentTimeMillis();
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long end   = System.currentTimeMillis();


        //获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        //获取注解的参数
        Method method = methodSignature.getMethod();
        TimeLogger timeLogger = method.getAnnotation(TimeLogger.class);

        //根据方法签名，获取参数
        Class[] parameterTypes = methodSignature.getParameterTypes();
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();

        if(parameterTypes.length>0){
            sb.append(parameterTypes[0].getSimpleName());
            sb.append(" ");
            sb.append(parameterNames[0]);
            sb.append(" = ");
            sb.append(args[0]);
            for (int i = 1;i<parameterTypes.length;i++){
                sb.append(",");
                sb.append(parameterTypes[i].getSimpleName());
                sb.append(" ");
                sb.append(parameterNames[i]);
                sb.append(" = ");
                sb.append(args[i]);
            }
        }

        //获取返回类型
        String returnType = methodSignature.getReturnType().getSimpleName();

        //打印日志
        Log.println(timeLogger.level(),method.getDeclaringClass().getSimpleName(),
                method.getName()+"() 耗时："+(end-start)+"ms");

    }

}
