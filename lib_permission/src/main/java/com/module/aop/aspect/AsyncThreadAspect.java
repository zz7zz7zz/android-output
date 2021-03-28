package com.module.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

//切面类
@Aspect
public class AsyncThreadAspect {

    @Around("execution(@com.module.aop.annotation.AsyncThread void *(..))")
    public void doAsync(ProceedingJoinPoint joinPoint){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }).start();
    }

}
