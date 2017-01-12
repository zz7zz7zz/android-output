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
import android.util.Log;
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

    private static final String TAG = "NFMonitorActivity";

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

        boolean isNotificationListenerServiceOn = isNotificationListenerServiceOn();
        ((Button)(findViewById(R.id.notification_monitor_authorization))).setTextColor(isNotificationListenerServiceOn ? Color.BLACK: Color.RED);

        boolean isNotificationAccessibilitySettingsOn = isNotificationAccessibilitySettingsOn(getApplicationContext());
        ((Button)(findViewById(R.id.notification_monitor_authorization_accessibility_service))).setTextColor(isNotificationAccessibilitySettingsOn ? Color.BLACK: Color.RED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(this.mReceiver);
    }

    private void initView(){

        notification_monitor_logcat_set = (LinearLayout) findViewById(R.id.notification_monitor_logcat_set);

        findViewById(R.id.notification_monitor_authorization).setOnClickListener(clickListener);
        findViewById(R.id.notification_monitor_authorization_accessibility_service).setOnClickListener(clickListener);
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
                    startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                    break;

                case R.id.notification_monitor_authorization_accessibility_service:
                    startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
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
    private boolean isNotificationListenerServiceOn() {
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


    public boolean isNotificationAccessibilitySettingsOn(Context mContext) {

        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(mContext.getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            Log.v(TAG, e.getMessage());
        }

        if (accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                return services.toLowerCase().contains(mContext.getPackageName().toLowerCase());
            }
        }
        return false;

        /*
            int accessibilityEnabled = 0;
            try {
                    accessibilityEnabled = Settings.Secure.getInt(mContext.getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            } catch (Settings.SettingNotFoundException e) {
                Log.e(TAG, e.getMessage());
            }
            TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

            if (accessibilityEnabled == 1) {
                String settingValue = Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
                String service = getPackageName() + "/" + NotificationMonitorAccessibilityService.class.getCanonicalName();
                if (settingValue != null) {
                    mStringColonSplitter.setString(settingValue);
                    while (mStringColonSplitter.hasNext()) {
                        String accessibilityService = mStringColonSplitter.next();
                        if (accessibilityService.equalsIgnoreCase(service)) {
                            return true;
                        }
                    }
                }
            }
            return false;
            */
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

                        String txt = "add \n"+ resultBeaen.toString2();
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

                        String txt = "remove \n"+ resultBeaen.toString2();
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

                                String txt = resultBeaen.toString2();
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
                        lp.leftMargin = 20;
                        notification_monitor_logcat_set.addView(addTextView,0,lp);
                    }
                }
            }
        }
    }

}
