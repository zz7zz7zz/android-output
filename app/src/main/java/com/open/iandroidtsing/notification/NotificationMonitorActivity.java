package com.open.iandroidtsing.notification;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.open.iandroidtsing.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/18.
 */

public class NotificationMonitorActivity extends Activity {

    public static final String NOTIFICATION_MONITOR_ACTION = "com.open.iandroidtsing.notification.monitor";
    public static final String NOTIFICATION_MONITOR_ACTION_KEY_TYPE = "type";
    public static final String NOTIFICATION_MONITOR_ACTION_KEY_DATA = "data";
    public static final int NOTIFICATION_MONITOR_ACTION_ADD = 1;
    public static final int NOTIFICATION_MONITOR_ACTION_REMOVE = 2;

    private NotificationMonitorBroadcastReceiver mReceiver = new NotificationMonitorBroadcastReceiver();


    private LinearLayout notification_monitor_logcat_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_monitor);
        initView();

        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction(NOTIFICATION_MONITOR_ACTION);
        registerReceiver(this.mReceiver, localIntentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isEnable = isEnabled();
        ((Button)(findViewById(R.id.notification_monitor_authorization))).setTextColor(isEnable ? Color.BLACK: Color.RED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(this.mReceiver);
    }

    private void initView(){

        notification_monitor_logcat_set = (LinearLayout) findViewById(R.id.notification_monitor_logcat_set);

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


    public class NotificationMonitorBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            if ((intent != null) && (intent.getAction() != null) && (intent.getAction().equals(NOTIFICATION_MONITOR_ACTION)))
            {
                Bundle extras = intent.getExtras();
                if(null != extras){

                    int type = extras.getInt(NOTIFICATION_MONITOR_ACTION_KEY_TYPE);
                    NotificationMonitorResultBeaen resultBeaen = extras.getParcelable(NOTIFICATION_MONITOR_ACTION_KEY_DATA);

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                    String date = df.format(new Date());

                    if(type == NOTIFICATION_MONITOR_ACTION_ADD){

                        String txt = "add \n\ndate " + date+ resultBeaen.toString2();
                        TextView addTextView = new TextView(getApplicationContext());
                        addTextView.setText(txt);
                        addTextView.setTextColor(Color.BLUE);
                        addTextView.setBackgroundResource(R.drawable.nf_item_bg);

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp.topMargin = 20;
                        lp.bottomMargin = 20;
                        lp.leftMargin = 30;
                        lp.rightMargin = 30;
                        notification_monitor_logcat_set.addView(addTextView,0,lp);

                    }else if(type == NOTIFICATION_MONITOR_ACTION_REMOVE){

                        String txt = "remove \n\ndate " + date+ resultBeaen.toString2();
                        TextView removeTextView = new TextView(getApplicationContext());
                        removeTextView.setText(txt);
                        removeTextView.setTextColor(Color.RED);
                        removeTextView.setBackgroundResource(R.drawable.nf_item_bg);

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp.topMargin = 20;
                        lp.bottomMargin = 20;
                        lp.leftMargin = 30;
                        lp.rightMargin = 30;

                        notification_monitor_logcat_set.addView(removeTextView,0,lp);

                    }
                }
            }
        }
    }

}
