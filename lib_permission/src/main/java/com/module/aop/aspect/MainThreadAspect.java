package com.module.aop.aspect;

import android.os.Handler;
import android.os.Looper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


//切面类
@Aspect
public class MainThreadAspect {

    @Around("execution(@com.module.aop.annotation.MainThread void *(..))")
    public void doMain(ProceedingJoinPoint joinPoint){
        if(Looper.myLooper() == Looper.getMainLooper()){
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }else{
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        joinPoint.proceed();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            });
        }
    }

}
