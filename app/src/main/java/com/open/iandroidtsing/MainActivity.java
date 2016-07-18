package com.open.iandroidtsing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.open.iandroidtsing.notification.NotificationActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
    }

    private void initView(){
        findViewById(R.id.notification).setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.notification:
                    startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
                    break;
            }
        }
    };
}
