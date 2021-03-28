package com.module.aop.aspect;

import android.content.Context;

import com.module.aop.annotation.permission.IPermissionResult;
import com.module.aop.annotation.permission.PermissionCancel;
import com.module.aop.annotation.permission.PermissionDenied;
import com.module.aop.annotation.permission.PermissionGetActivity;
import com.module.aop.annotation.permission.PermissionNeed;
import com.module.aop.annotation.permission.PermissionUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


//切面类
@Aspect
public class PermissionAspect {

    @Pointcut("execution(@com.module.aop.annotation.permission.PermissionNeed * *(..)) && @annotation(permissionNeed)")
    public void doRequestPermission(PermissionNeed permissionNeed){

    }

    @Around("doRequestPermission(permissionNeed)")
    public void doRequestPermissionMain(ProceedingJoinPoint joinPoint, PermissionNeed permissionNeed){
        Context context = (Context) joinPoint.getThis();
        PermissionGetActivity.requestPermission(context, permissionNeed.permissions(), permissionNeed.requestCode(), new IPermissionResult() {
            @Override
            public void onPermissionGranted() {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onPermissionDenied(int requestCode) {
                PermissionUtil.invokeAnnotation(context, PermissionDenied.class,requestCode);
            }

            @Override
            public void onPermissionCanceled(int requestCode) {
                PermissionUtil.invokeAnnotation(context, PermissionCancel.class,requestCode);
            }
        });
    }
}
