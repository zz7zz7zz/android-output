package com.module.aop.annotation.permission;

public interface IPermissionResult {

    void onPermissionGranted();

    void onPermissionDenied(int requestCode);

    void onPermissionCanceled(int requestCode);

}
