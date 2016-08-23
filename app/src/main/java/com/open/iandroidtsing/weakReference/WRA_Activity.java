package com.open.iandroidtsing.weakReference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.open.iandroidtsing.R;

/**
 * Created by Administrator on 2016/8/22.
 */

public class WRA_Activity extends Activity {

    private final String TAG = "WRA_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weakreference_a);

        initView();
        WRTaskMgr.doWork(this);


        if(null != savedInstanceState)
        {
            Log.v(TAG, "onCreate()-savedInstanceState-----------");
        }
        else
        {
            Log.v(TAG, "onCreate()");
        }
    }

    private void initView(){
        findViewById(R.id.weakreference_to_wrb_activity).setOnClickListener(clickListener);

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch(v.getId()){
                case R.id.weakreference_to_wrb_activity:
//                    finish();
                    startActivity(new Intent(getApplicationContext(),WRB_Activity.class));
                    break;

            }
        }
    };

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.v(TAG, "onRestoreInstanceState()");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.v(TAG, "onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        Log.v(TAG, "onStart()");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.v(TAG, "onRestart()");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.v(TAG, "onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.v(TAG, "onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.v(TAG, "onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        Log.v(TAG, "onLowMemory");
        super.onLowMemory();
    }

    @Override
    protected void finalize() throws Throwable {
        Log.v(TAG,"delete");
        super.finalize();
    }
}
