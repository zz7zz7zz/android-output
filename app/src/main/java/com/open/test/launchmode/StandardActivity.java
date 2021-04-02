package com.open.test.launchmode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.annotation.Nullable;
import com.open.test.R;
import com.open.test.arch.mvp.view.MvpActivity;

public class StandardActivity extends Activity implements OnClickListener{

    private static final String TAG = "StandardActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launchmode);


        ((TextView)(findViewById(R.id.launchmode))).setText("I am "+ TAG);

        findViewById(R.id.launchmode_standard).setOnClickListener(this);
        findViewById(R.id.launchmode_singleTop).setOnClickListener(this);
        findViewById(R.id.launchmode_singleTask).setOnClickListener(this);
        findViewById(R.id.launchmode_singleInstance).setOnClickListener(this);

        Log.v("LaunchMode","onCreate "+TAG+" " + this.hashCode());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.launchmode_standard:
                startActivity(new Intent(getApplicationContext(), StandardActivity.class));
                break;

            case R.id.launchmode_singleTop:
                startActivity(new Intent(getApplicationContext(), SingleTopActivity.class));
                break;

            case R.id.launchmode_singleTask:
                startActivity(new Intent(getApplicationContext(), SingleTaskActivity.class));
                break;

            case R.id.launchmode_singleInstance:
                startActivity(new Intent(getApplicationContext(), SingleInstanceActivity.class));
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.v("LaunchMode","onNewIntent "+TAG+" " + this.hashCode());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("LaunchMode","onDestroy "+TAG+" " + this.hashCode());
    }
}
