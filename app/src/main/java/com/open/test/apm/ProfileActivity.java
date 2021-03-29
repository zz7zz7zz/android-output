package com.open.test.apm;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.open.test.R;

public class ProfileActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = "ProfileActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        findViewById(R.id.apm).setOnClickListener(this);
        BlockTrackChoreographer.getInstance().start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BlockTrackChoreographer.getInstance().stop();
    }

    @Override
    public void onClick(View v) {
//        ThreadLocal<String > threadLocal = new ThreadLocal<>();
//        threadLocal.set("WWW");
        Log.v(TAG,Log.getStackTraceString(new Throwable()));
        try {
            Thread.sleep(2000);
            Log.v("ProfileActivity","onClick");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
