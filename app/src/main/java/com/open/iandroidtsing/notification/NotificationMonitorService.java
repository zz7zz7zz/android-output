package com.open.iandroidtsing.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/11.
 */
@SuppressLint("NewApi")
public class NotificationMonitorService extends NotificationListenerService {

    public static final String TAG = "NotificationMonitor";

    public static final String NOTIFICATION_MONITOR_ACTION_CANCEL = "com.open.iandroidtsing.notification.monitor.cancel";
    public static final String NOTIFICATION_MONITOR_ACTION_CANCEL_CMD = "cmd";
    public static final int NOTIFICATION_MONITOR_ACTION_CANCEL_CMD_ALL          = 1;
    public static final int NOTIFICATION_MONITOR_ACTION_CANCEL_CMD_LATEST       = 2;
    public static final int NOTIFICATION_MONITOR_ACTION_CANCEL_CMD_GETALLNFS    = 3;

    //需要过滤的包名，完全匹配
    private final String [] exactStringMatchingPkgArray = new String[]{
            "android"
    };

    //需要过滤的包名，模糊匹配
    private final String [] likeStringMatchingPkgArray = new String[]{
            "com.android","com.google.android"
    };

    private CancelNotificationBroadcastReceiver mReceiver = new CancelNotificationBroadcastReceiver();

    public void onCreate()
    {
        super.onCreate();
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction(NOTIFICATION_MONITOR_ACTION_CANCEL);
        registerReceiver(this.mReceiver, localIntentFilter);
    }

    public void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(this.mReceiver);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);

        Log.v(TAG, "onNotificationPosted : "+sbn.toString());

        //----------------方法一-----------------------
        Notification nf = sbn.getNotification();
        if(null != nf){
            NotificationMonitorResultBeaen resultBeaen = new NotificationMonitorResultBeaen();
            resultBeaen.id = sbn.getId();
            resultBeaen.pkg = sbn.getPackageName();

            Bundle extras = nf.extras;
            if(null != extras){
                resultBeaen.title = extras.getString(Notification.EXTRA_TITLE);
                resultBeaen.content = extras.getString(Notification.EXTRA_TEXT);
                resultBeaen.subText = extras.getString(Notification.EXTRA_SUB_TEXT);
                resultBeaen.showWhen = nf.when;
            }

            Log.v(TAG, "onNotificationPosted : "+resultBeaen.toString());
            broadcast(resultBeaen , NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_CMD_ADD);
        }

        //----------------方法二-----------------------
        if(null != nf){
            NotificationMonitorResultBeaen resultBeaen = new NotificationMonitorResultBeaen();
            resultBeaen.id = sbn.getId();
            resultBeaen.pkg = sbn.getPackageName();

            Bundle extras = nf.extras;
            if(null != extras){
                resultBeaen.title = extras.getString(Notification.EXTRA_TITLE);
                resultBeaen.content = extras.getString(Notification.EXTRA_TEXT);
                resultBeaen.subText = extras.getString(Notification.EXTRA_SUB_TEXT);
                resultBeaen.showWhen = nf.when;
            }

            Log.v(TAG, "onNotificationPosted 2 : "+resultBeaen.toString());
            broadcast2(resultBeaen , NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_CMD_ADD_2,nf);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);

        Log.v(TAG, "onNotificationRemoved : "+sbn.toString());

        Notification nf = sbn.getNotification();
        if(null != nf){

            NotificationMonitorResultBeaen resultBeaen = new NotificationMonitorResultBeaen();
            resultBeaen.id = sbn.getId();
            resultBeaen.pkg = sbn.getPackageName();

            Bundle extras = nf.extras;
            if(null != extras){
                resultBeaen.title = extras.getString(Notification.EXTRA_TITLE);
                resultBeaen.content = extras.getString(Notification.EXTRA_TEXT);
                resultBeaen.subText = extras.getString(Notification.EXTRA_SUB_TEXT);
                resultBeaen.showWhen = nf.when;
            }

            Log.v(TAG, "onNotificationRemoved : "+resultBeaen.toString());
            broadcast(resultBeaen , NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_CMD_REMOVE);
        }

    }

    private boolean exactStringMatching(String pkg){

        if(!TextUtils.isEmpty(pkg)){
            for (String item: exactStringMatchingPkgArray) {
                if(pkg.equals(item)){
                    return true;
                }
            }
        }

        return false;
    }

    private boolean likeStringMatching(String pkg){

        if(!TextUtils.isEmpty(pkg)){
            for (String item: likeStringMatchingPkgArray) {
                if(pkg.contains(item)){
                    return true;
                }
            }
        }

        return false;
    }

    public void broadcast(NotificationMonitorResultBeaen resultBeaen , int type){

        boolean filterPkg = exactStringMatching(resultBeaen.pkg) || likeStringMatching(resultBeaen.pkg);
        Log.v(TAG, "filterPkg : " + filterPkg);
        if(filterPkg){
            return;
        }

        Bundle mBundle = new Bundle();
        mBundle.putInt(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_CMD,type);
        mBundle.putParcelable(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_DATA,resultBeaen);
        Intent intent = new Intent(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION);
        intent.putExtras(mBundle);
        sendBroadcast(intent);
    }

    public void broadcast2(NotificationMonitorResultBeaen resultBeaen , int type , Notification nf){

        boolean filterPkg = exactStringMatching(resultBeaen.pkg) || likeStringMatching(resultBeaen.pkg);
        Log.v(TAG, "filterPkg : " + filterPkg);
        if(filterPkg){
            return;
        }

        Bundle mBundle = new Bundle();
        mBundle.putInt(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_CMD,NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_CMD_ACCESSIBILITYSERVICE_BACK);
        mBundle.putString(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_PKG, resultBeaen.pkg);
        mBundle.putParcelable(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_DATA,resultBeaen);
        mBundle.putParcelable(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_DATA_2,nf);
        Intent intent = new Intent(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION);
        intent.putExtras(mBundle);
        sendBroadcast(intent);
    }

    public void broadcast(ArrayList<NotificationMonitorResultBeaen> resultBeaenList , int type){

        Bundle mBundle = new Bundle();
        mBundle.putInt(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_CMD,type);
        mBundle.putParcelableArrayList(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_DATA,resultBeaenList);
        Intent intent = new Intent(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION);
        intent.putExtras(mBundle);
        sendBroadcast(intent);
    }


    class CancelNotificationBroadcastReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent)
        {
            if ((intent != null) && (intent.getAction() != null) && (intent.getAction().equals(NOTIFICATION_MONITOR_ACTION_CANCEL))) {
                Bundle extras = intent.getExtras();
                if (null != extras) {
                    int cmd = extras.getInt(NOTIFICATION_MONITOR_ACTION_CANCEL_CMD);
                    if(cmd == NOTIFICATION_MONITOR_ACTION_CANCEL_CMD_ALL){
                        NotificationMonitorService.this.cancelAllNotifications();
                    }else if(cmd == NOTIFICATION_MONITOR_ACTION_CANCEL_CMD_LATEST){

                    }else if(cmd == NOTIFICATION_MONITOR_ACTION_CANCEL_CMD_GETALLNFS){

                        ArrayList<NotificationMonitorResultBeaen> resultBeaenList = null;
                        StatusBarNotification[] arrayOfStatusBarNotification = getActiveNotifications();
                        if(null != arrayOfStatusBarNotification && arrayOfStatusBarNotification.length > 0){
                            resultBeaenList = new ArrayList<>(arrayOfStatusBarNotification.length);
                            for (int i = 0 ;i < arrayOfStatusBarNotification.length;i++){
                                StatusBarNotification sbn = arrayOfStatusBarNotification[i];
                                Notification nf = sbn.getNotification();
                                if(null != nf){
                                    Bundle _extras = nf.extras;
                                    if(null != _extras){
                                        NotificationMonitorResultBeaen resultBeaen = new NotificationMonitorResultBeaen();
                                        resultBeaen.id = sbn.getId();
                                        resultBeaen.pkg = sbn.getPackageName();
                                        resultBeaen.title = extras.getString(Notification.EXTRA_TITLE);
                                        resultBeaen.content = extras.getString(Notification.EXTRA_TEXT);
                                        resultBeaen.subText = extras.getString(Notification.EXTRA_SUB_TEXT);
                                        resultBeaen.showWhen = nf.when;

                                        resultBeaenList.add(resultBeaen);
                                    }
                                }
                            }
                        }

                        broadcast(resultBeaenList , NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_CMD_ALLINFO);
                    }
                }
            }
        }
    }

}
