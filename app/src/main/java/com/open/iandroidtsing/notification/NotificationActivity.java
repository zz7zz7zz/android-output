package com.open.iandroidtsing.notification;

import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.view.View;

import com.open.iandroidtsing.R;

/**
 * Created by Administrator on 2016/7/18.
 */

public class NotificationActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        initView();
    }

    private void initView(){
        findViewById(R.id.notification_priority_max).setOnClickListener(clickListener);
        findViewById(R.id.notification_priority_high).setOnClickListener(clickListener);
        findViewById(R.id.notification_priority_default).setOnClickListener(clickListener);
        findViewById(R.id.notification_priority_low).setOnClickListener(clickListener);
        findViewById(R.id.notification_priority_min).setOnClickListener(clickListener);

        findViewById(R.id.user_define_notification).setOnClickListener(clickListener);

    }

    static int count = 0;
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            count++;

            switch(v.getId()){
                case R.id.notification_priority_max:
                    NotificationUtil.notifyPriority(getApplicationContext(),count, " A "+count + " priority_max ", Notification.PRIORITY_MAX);
                    break;

                case R.id.notification_priority_high:
                    NotificationUtil.notifyPriority(getApplicationContext(),count, "B "+count + " priority_high ", Notification.PRIORITY_HIGH);
                    break;

                case R.id.notification_priority_default:
                    NotificationUtil.notifyPriority(getApplicationContext(),count, "C "+count + " priority_default ", Notification.PRIORITY_DEFAULT);
                    break;

                case R.id.notification_priority_low:
                    NotificationUtil.notifyPriority(getApplicationContext(),count, "D "+count + " priority_low ", Notification.PRIORITY_LOW);
                    break;

                case R.id.notification_priority_min:
                    NotificationUtil.notifyPriority(getApplicationContext(),count, "E "+count + " priority_min ", Notification.PRIORITY_MIN);
                    break;

                case R.id.user_define_notification:
                    NotificationUtil.user_define_notification(getApplicationContext() , count);
                    break;
            }
        }
    };

}
