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

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/18.
 */

public class NotificationMonitorActivity extends Activity {

    public static final String NOTIFICATION_MONITOR_ACTION = "com.open.iandroidtsing.notification.monitor.response";
    public static final String NOTIFICATION_MONITOR_ACTION_KEY_CMD = "cmd";
    public static final String NOTIFICATION_MONITOR_ACTION_KEY_DATA = "data";
    public static final int NOTIFICATION_MONITOR_ACTION_CMD_ADD     = 1;
    public static final int NOTIFICATION_MONITOR_ACTION_CMD_REMOVE  = 2;
    public static final int NOTIFICATION_MONITOR_ACTION_CMD_ALLINFO = 3;

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
        findViewById(R.id.notification_monitor_allinfo).setOnClickListener(clickListener);
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

                case R.id.notification_monitor_allinfo:
                    {
                        Bundle mBundle = new Bundle();
                        mBundle.putInt(NotificationMonitorService.NOTIFICATION_MONITOR_ACTION_CANCEL_CMD, NotificationMonitorService.NOTIFICATION_MONITOR_ACTION_CANCEL_CMD_GETALLNFS);
                        Intent intent = new Intent(NotificationMonitorService.NOTIFICATION_MONITOR_ACTION_CANCEL);
                        intent.putExtras(mBundle);
                        sendBroadcast(intent);
                    }
                    break;

                case R.id.notification_monitor_create:
                    startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
                    break;

                case R.id.notification_monitor_clear:
                    {
                        Bundle mBundle = new Bundle();
                        mBundle.putInt(NotificationMonitorService.NOTIFICATION_MONITOR_ACTION_CANCEL_CMD,NotificationMonitorService.NOTIFICATION_MONITOR_ACTION_CANCEL_CMD_ALL);
                        Intent intent = new Intent(NotificationMonitorService.NOTIFICATION_MONITOR_ACTION_CANCEL);
                        intent.putExtras(mBundle);
                        sendBroadcast(intent);
                    }
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

                    int cmd = extras.getInt(NOTIFICATION_MONITOR_ACTION_KEY_CMD);
                    if(cmd == NOTIFICATION_MONITOR_ACTION_CMD_ADD){

                        NotificationMonitorResultBeaen resultBeaen = extras.getParcelable(NOTIFICATION_MONITOR_ACTION_KEY_DATA);

                        String date = resultBeaen.notificationShowWhen;

                        String txt = "add \n\ndate " + date+ resultBeaen.toString2();
                        TextView addTextView = new TextView(getApplicationContext());
                        addTextView.setText(txt);
                        addTextView.setTextColor(Color.BLUE);
                        addTextView.setBackgroundResource(R.drawable.nf_item_bg);

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp.topMargin = 20;
                        lp.bottomMargin = 20;
                        lp.leftMargin = 20;
                        lp.rightMargin = 20;
                        notification_monitor_logcat_set.addView(addTextView,0,lp);

                    }else if(cmd == NOTIFICATION_MONITOR_ACTION_CMD_REMOVE){

                        NotificationMonitorResultBeaen resultBeaen = extras.getParcelable(NOTIFICATION_MONITOR_ACTION_KEY_DATA);

                        String date = resultBeaen.notificationShowWhen;

                        String txt = "remove \n\ndate " + date+ resultBeaen.toString2();
                        TextView removeTextView = new TextView(getApplicationContext());
                        removeTextView.setText(txt);
                        removeTextView.setTextColor(Color.RED);
                        removeTextView.setBackgroundResource(R.drawable.nf_item_bg);

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp.topMargin = 20;
                        lp.bottomMargin = 20;
                        lp.leftMargin = 20;
                        lp.rightMargin = 20;

                        notification_monitor_logcat_set.addView(removeTextView,0,lp);

                    }else if(cmd == NOTIFICATION_MONITOR_ACTION_CMD_ALLINFO){

                        ArrayList<NotificationMonitorResultBeaen> resultBeaenList = extras.getParcelableArrayList(NOTIFICATION_MONITOR_ACTION_KEY_DATA);
                        int size = (null != resultBeaenList && resultBeaenList.size() > 0) ? resultBeaenList.size() : 0 ;

                        if(size > 0){
                            for (int i = size-1; i >=0 ; --i) {
                                NotificationMonitorResultBeaen resultBeaen = resultBeaenList.get(i);

                                String date = resultBeaen.notificationShowWhen;

                                String txt = "date " + date+ resultBeaen.toString2();
                                TextView addTextView = new TextView(getApplicationContext());
                                addTextView.setText(txt);
                                addTextView.setTextColor(Color.GRAY);
                                addTextView.setBackgroundResource(R.drawable.nf_item_bg);

                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                lp.topMargin = 20;
                                lp.bottomMargin = 20;
                                lp.leftMargin = 20;
                                lp.rightMargin = 20;
                                notification_monitor_logcat_set.addView(addTextView,0,lp);
                            }
                        }

                        TextView addTextView = new TextView(getApplicationContext());
                        addTextView.setText(String.format(size > 0 ? "There are %d activie notifications " : "There are %d activie notification", size));
                        addTextView.setTextColor(Color.GRAY);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        notification_monitor_logcat_set.addView(addTextView,0,lp);
                    }
                }
            }
        }
    }

}
