package com.open.iandroidtsing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.open.iandroidtsing.fragment.FragmentPagerActivity;
import com.open.iandroidtsing.fragment.FragmentStatePagerActivity;
import com.open.iandroidtsing.notification.NotificationActivity;
import com.open.iandroidtsing.sharedprefs.SharedPrefsActivity;
import com.open.iandroidtsing.weakReference.WRA_Activity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
    }

    private void initView(){

        findViewById(R.id.notification).setOnClickListener(clickListener);
        findViewById(R.id.notification_monitor).setOnClickListener(clickListener);

        findViewById(R.id.fragment_pager).setOnClickListener(clickListener);
        findViewById(R.id.fragment_statepager).setOnClickListener(clickListener);

        findViewById(R.id.weakreference).setOnClickListener(clickListener);

        findViewById(R.id.shared_prefs).setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){

                case R.id.notification:
                    startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
                    break;
                case R.id.notification_monitor:
                    startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
                    break;


                case R.id.fragment_pager:
                    startActivity(new Intent(getApplicationContext(),FragmentPagerActivity.class));
                    break;
                case R.id.fragment_statepager:
                    startActivity(new Intent(getApplicationContext(),FragmentStatePagerActivity.class));
                    break;

                case R.id.weakreference:
                    startActivity(new Intent(getApplicationContext(),WRA_Activity.class));
                    break;

                case R.id.shared_prefs:
                    startActivity(new Intent(getApplicationContext(),SharedPrefsActivity.class));
                    break;
            }
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            android.os.Process.killProcess(android.os.Process.myPid());
//			System.exit(0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
