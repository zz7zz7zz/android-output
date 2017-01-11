package com.open.iandroidtsing.notification;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.open.iandroidtsing.R;

/**
 * Created by Administrator on 2016/7/18.
 */

public class NotificationMonitorActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_monitor);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isEnable = isEnabled();
        ((Button)(findViewById(R.id.notification_monitor_authorization))).setTextColor(isEnable ? Color.BLACK: Color.RED);
    }

    private void initView(){
        findViewById(R.id.notification_monitor_authorization).setOnClickListener(clickListener);
        findViewById(R.id.notification_monitor_create).setOnClickListener(clickListener);
        findViewById(R.id.notification_monitor_clear).setOnClickListener(clickListener);
        findViewById(R.id.notification_monitor_api_setting).setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch(v.getId()){
                case R.id.notification_monitor_authorization:
                    openNotificationAuthorization();
                    break;

                case R.id.notification_monitor_create:
                    startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
                    break;

                case R.id.notification_monitor_clear:
                    break;

                case R.id.notification_monitor_api_setting:
                    break;

            }
        }
    };

    //判断是否打开了通知监听权限
    private boolean isEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //去设置权限
    private void openNotificationAuthorization() {
        startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
    }

}
