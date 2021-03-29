package com.module.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

//切面类
@Aspect
public class TestAspect {

    @Before("execution(@com.module.aop.annotation.TestAspectJ void testBefore(..))")
    public void doBefore(JoinPoint joinPoint){
        System.out.println("------doBefore------");
    }

    @After("execution(@com.module.aop.annotation.TestAspectJ void testAfter(..))")
    public void doAfter(JoinPoint joinPoint){
        System.out.println("------doAfter------");
    }

    @Around("execution(@com.module.aop.annotation.TestAspectJ void testReplace(..))")
    public void doAroundExecution(ProceedingJoinPoint joinPoint){
        System.out.println("------doAroundExecution start ------");
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("------doAroundExecution  end ------");
    }

    @Around("call(@com.module.aop.annotation.TestAspectJ void testInner(..))")
    public void doAroundCall(ProceedingJoinPoint joinPoint){
        System.out.println("------doAroundCall  start ------");
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("------doAroundCall  end ------");
    }
}
