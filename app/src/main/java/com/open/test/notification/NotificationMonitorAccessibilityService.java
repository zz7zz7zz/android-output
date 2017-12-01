package com.open.test.notification;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import java.util.List;

/**
 * Created by Administrator on 2017/1/12.
 */

public class NotificationMonitorAccessibilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:

                Parcelable data = event.getParcelableData();
                if (data instanceof Notification) {
                    Notification mNotification = (Notification) data;

                    Bundle mBundle = new Bundle();
                    mBundle.putInt(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_CMD,NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_CMD_ACCESSIBILITYSERVICE_BACK);
                    mBundle.putString(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_PKG, event.getPackageName().toString());
                    mBundle.putParcelable(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION_KEY_DATA_2,mNotification);

                    Intent intent = new Intent(NotificationMonitorActivity.NOTIFICATION_MONITOR_ACTION);
                    intent.putExtras(mBundle);
                    sendBroadcast(intent);
                }

                List<CharSequence> texts = event.getText();
                if (!texts.isEmpty()) {
                    for (CharSequence text : texts) {
                        String content = text.toString();
                        Log.v("NFAccessibilityService", "text:"+content);
                    }
                }
                break;
        }
    }

    @Override
    public void onInterrupt() {

    }
}
