package com.open.test.permission;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.module.aop.annotation.AsyncThread;
import com.module.aop.annotation.MainThread;
import com.module.aop.annotation.TimeLogger;
import com.module.aop.annotation.permission.PermissionCancel;
import com.module.aop.annotation.permission.PermissionDenied;
import com.module.aop.annotation.permission.PermissionNeed;
import com.open.test.R;

public class PermissionActivity extends Activity {

    private String TAG = "PermissionActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission);

        findViewById(R.id.permission_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPermissionGrant();
            }
        });

        findViewById(R.id.backgroudThread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBackgroudThread();
            }
        });

        findViewById(R.id.uiThread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUiThread();
            }
        });
        findViewById(R.id.timeconsumption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeconsumption();
            }
        });

    }

//    @PermissionNeed(permissions={Manifest.permission.ACCESS_FINE_LOCATION},requestCode = 100)
    @PermissionNeed(permissions = {Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA}, requestCode = 10)
    private void onPermissionGrant(){
        Log.v(TAG,"onPermissionGrant");
    }

    @PermissionCancel
    private void onPermissionCancel(int requestCode){
        Log.v(TAG,"onPermissionCancel " + requestCode);
    }

    @PermissionDenied
    private void onPermissionDenied(int requestCode){
        Log.v(TAG,"onPermissionDenied "+ requestCode);
    }

    @AsyncThread
    private void doBackgroudThread(){
        Log.v(TAG,"doBackgroudThread "+Thread.currentThread().getName());
        doUiThread();
    }

    @MainThread
    private void doUiThread(){
        Log.v(TAG,"doUiThread "+Thread.currentThread().getName());
    }

    @TimeLogger(level = Log.ERROR)
    private void timeconsumption() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
