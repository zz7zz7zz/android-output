package com.open.iandroidtsing.notification;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

/**
 * Created by Administrator on 2017/1/11.
 */

public class NotificationMonitorService extends NotificationListenerService {

    public static final String TAG = "NotificationMonitor";

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);

        Log.v(TAG, "onNotificationRemoved : "+sbn.toString());
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);

        Log.v(TAG, "onNotificationPosted : "+sbn.toString());
    }
}
