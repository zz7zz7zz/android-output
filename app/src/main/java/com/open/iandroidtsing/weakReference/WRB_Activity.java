package com.open.iandroidtsing.weakReference;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.open.iandroidtsing.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/22.
 */

public class WRB_Activity extends Activity {

    private final String TAG = "WRB_Activity";

    Handler mHandler = new Handler();
    ArrayList<byte[]> list= new ArrayList<byte[]>(10);//模拟资源枯竭

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weakreference_b);

        initView();

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
        findViewById(R.id.new_memory).setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch(v.getId()){
                case R.id.new_memory:
                    mHandler.post(allocMemoryRunnable);

                    break;
            }
        }
    };

    private Runnable allocMemoryRunnable =new Runnable() {

        @Override
        public void run() {

            list.add(new byte[1<<22]);//每次分配4M
            Log.v(TAG, "allocMemoryRunnable():size:"+list.size()*4+"M");
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
