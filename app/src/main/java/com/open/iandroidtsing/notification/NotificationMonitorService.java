package com.open.iandroidtsing.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

/**
 * Created by Administrator on 2017/1/11.
 */
@SuppressLint("NewApi")
public class NotificationMonitorService extends NotificationListenerService {

    public static final String TAG = "NotificationMonitor";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);

        Log.v(TAG, "onNotificationPosted : "+sbn.toString());

        Notification nf = sbn.getNotification();
        if(null != nf){
            Bundle extras = nf.extras;
            if(null != extras){
                NotificationMonitorResultBeaen resultBeaen = new NotificationMonitorResultBeaen();
                resultBeaen.notificationId  = sbn.getId();
                resultBeaen.notificationPkg = sbn.getPackageName();
                resultBeaen.notificationTitle = extras.getString(Notification.EXTRA_TITLE);
                resultBeaen.notificationText = extras.getString(Notification.EXTRA_TEXT);
                resultBeaen.notificationSubText = extras.getString(Notification.EXTRA_SUB_TEXT);

                broadcast(resultBeaen , NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_ADD);
                Log.v(TAG, "onNotificationPosted : "+resultBeaen.toString());
            }
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
                resultBeaen.notificationId  = sbn.getId();
                resultBeaen.notificationPkg = sbn.getPackageName();
                resultBeaen.notificationTitle = extras.getString(Notification.EXTRA_TITLE);
                resultBeaen.notificationText = extras.getString(Notification.EXTRA_TEXT);
                resultBeaen.notificationSubText = extras.getString(Notification.EXTRA_SUB_TEXT);

                broadcast(resultBeaen , NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_REMOVE);
                Log.v(TAG, "onNotificationRemoved : "+resultBeaen.toString());
            }
        }

    }

    public void broadcast(NotificationMonitorResultBeaen resultBeaen , int type){
        Bundle mBundle = new Bundle();
        mBundle.putInt(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_TYPE,type);
        mBundle.putParcelable(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_DATA,resultBeaen);
        Intent intent = new Intent(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION);
        intent.putExtras(mBundle);
        sendBroadcast(intent);
    }

}
