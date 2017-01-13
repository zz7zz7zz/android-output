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
            Bundle extras = nf.extras;
            if(null != extras){
                NotificationMonitorResultBeaen resultBeaen = new NotificationMonitorResultBeaen();
                resultBeaen.id = sbn.getId();
                resultBeaen.pkg = sbn.getPackageName();
                resultBeaen.title = extras.getString(Notification.EXTRA_TITLE);
                resultBeaen.content = extras.getString(Notification.EXTRA_TEXT);
                resultBeaen.subText = extras.getString(Notification.EXTRA_SUB_TEXT);
                resultBeaen.showWhen = nf.when;

                broadcast(resultBeaen , NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_CMD_ADD);
                Log.v(TAG, "onNotificationPosted : "+resultBeaen.toString());
            }
        }

        //----------------方法二-----------------------
        if(null != nf){
            Notification mNotification = nf;

            Bundle mBundle = new Bundle();
            mBundle.putInt(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_CMD,NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_CMD_ACCESSIBILITYSERVICE_BACK);
            mBundle.putParcelable(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_DATA,mNotification);
            mBundle.putString(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_PKG, sbn.getPackageName());

            Intent intent = new Intent(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION);
            intent.putExtras(mBundle);
            sendBroadcast(intent);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);

        Log.v(TAG, "onNotificationRemoved : "+sbn.toString());

        Notification nf = sbn.getNotification();
        if(null != nf){
            Bundle extras = nf.extras;
            if(null != extras){
                NotificationMonitorResultBeaen resultBeaen = new NotificationMonitorResultBeaen();
                resultBeaen.id = sbn.getId();
                resultBeaen.pkg = sbn.getPackageName();
                resultBeaen.title = extras.getString(Notification.EXTRA_TITLE);
                resultBeaen.content = extras.getString(Notification.EXTRA_TEXT);
                resultBeaen.subText = extras.getString(Notification.EXTRA_SUB_TEXT);
                resultBeaen.showWhen = nf.when;

                broadcast(resultBeaen , NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_CMD_REMOVE);
                Log.v(TAG, "onNotificationRemoved : "+resultBeaen.toString());
            }
        }

    }

    public void broadcast(NotificationMonitorResultBeaen resultBeaen , int type){

        if(resultBeaen.pkg.contains("android") ){

        }

        Bundle mBundle = new Bundle();
        mBundle.putInt(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_CMD,type);
        mBundle.putParcelable(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_DATA,resultBeaen);
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
