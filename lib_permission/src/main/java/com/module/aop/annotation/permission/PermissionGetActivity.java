package com.module.aop.annotation.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

public class PermissionGetActivity extends Activity {

    private static final String PERMISSION = "permission";
    private static final String REQUESTCODE = "requestCode";

    private static IPermissionResult permissionRequestListener;

    public static void requestPermission(Context context, String[] permissions, int requestCode,IPermissionResult permissionRequestListener){
        PermissionGetActivity.permissionRequestListener = permissionRequestListener;

        Intent intent = new Intent(context,PermissionGetActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putStringArray(PERMISSION,permissions);
        bundle.putInt(REQUESTCODE,requestCode);
        intent.putExtras(bundle);
        context.startActivity(intent);

        ((Activity)context).overridePendingTransition(0,0);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        String[] permissions = bundle.getStringArray(PERMISSION);
        int requestCode = bundle.getInt(REQUESTCODE);
        requestPermission(this,permissions,requestCode);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        permissionRequestListener = null;
    }

    public void requestPermission(Activity activity ,String[] permissions,int requestCode){

        if(hasPermissions(activity,permissions)){
            permissionRequestListener.onPermissionGranted();
            finish();
        }else{
            ActivityCompat.requestPermissions(activity,permissions,requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(verifyPermissions(grantResults)){
            permissionRequestListener.onPermissionGranted();
        }else{
            if(shouldShowRequestPermissionRationale(this,permissions)){
                permissionRequestListener.onPermissionCanceled(requestCode);
            }else{
                permissionRequestListener.onPermissionDenied(requestCode);
            }
        }
        finish();
        overridePendingTransition(0,0);
    }

    //------------------------------------------------------
    public boolean hasPermissions(Context context, String[] permissions){
        for (String permission : permissions){
            if(isPermissionExist(permission) && !hasPermissions(context,permission)){
                return false;
            }
        }
        return true;
    }

    public boolean isPermissionExist(String permission){
        //不同版本添加的权限不一样，后面版本的权限会增加
        return true;
    }

    public boolean hasPermissions(Context context, String permission){
        return ActivityCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED;
    }


    public boolean shouldShowRequestPermissionRationale(Context context, String[] permissions){
        for (String permission : permissions){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,permission)){
                return true;
            }
        }
        return false;
    }

    public boolean verifyPermissions(int ... results){
        if(results.length == 0){
            return false;
        }

        for (int result : results){
            if(result != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }
    //------------------------------------------------------
//    public void requestPermission(Context context ,String permission,int requestCode){
//
//        if(ActivityCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED){
//            //未授予权限
//            if(ActivityCompat.shouldShowRequestPermissionRationale(context,permission)){
//                //取消权限.异步向用户显示，用户看到解释后，再次尝试获取权限
//                ActivityCompat.requestPermissions(context,new String[]{permission},requestCode);
//            }else{
                  //拒绝权限.
//                ActivityCompat.requestPermissions(context,new String[]{permission},requestCode);
//            }
//
//        }else{
//            //授予权限.
//        }
//    }




}
