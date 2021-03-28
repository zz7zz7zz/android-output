package com.open.test.viewtouchevent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.open.test.R;

public class ViewTouchEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_touch_event);getWindow().getDecorView();
        Log.v("ViewTouchEventActivity","onCreate");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("ViewTouchEventActivity","onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("ViewTouchEventActivity","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("ViewTouchEventActivity","onResume");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("ViewTouchEventActivity","onSaveInstanceState");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("ViewTouchEventActivity","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("ViewTouchEventActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("ViewTouchEventActivity","onDestroy");
    }
    //-------------------------------------------------------------
    private String TAG = "Event: Activity";


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.v(TAG,"Flow  onKeyDown" );

//        throw new NullPointerException("Exception");
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v(TAG,"Flow  onTouchEvent" );

        boolean ret  = super.onTouchEvent(event);
//        if(Constants.DEBUG){
//            ret = Constants.activity_return_onTouchEvent;
//        }
        Log.v(TAG,"onTouchEvent         ret: " + ret + "    ev: " + Constants.getActionString(event));
        return ret;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.v(TAG,"Flow  dispatchTouchEvent" );
//        throw new NullPointerException("Activity");

        boolean ret  = super.dispatchTouchEvent(ev);
        if(Constants.DEBUG){
            ret = Constants.activity_return_dispatchTouchEvent;
        }
        Log.v(TAG,"dispatchTouchEvent   ret: " + ret + "    ev: " + Constants.getActionString(ev));

//        throw new NullPointerException("Exception");
        return ret;
    }
}
