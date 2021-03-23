package com.open.test.viewtouchevent;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View implements View.OnTouchListener {

    public MyView(Context context) {
        this(context,null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        if(count == 5)
        throw new NullPointerException("MyView");
        count++;
        Log.v(TAG,"count "+count);
    }

    static int count = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    //-------------------------------------------------------------
    private String TAG = "Event: View   ";

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.v(TAG,"Flow  onTouch" );

        boolean ret = false;
//        if(Constants.DEBUG){
//            ret = Constants.view_return_onTouch;
//        }
        Log.v(TAG,"onTouch              ret: "+ ret + " ev: " + Constants.getActionString(event));

        return ret;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v(TAG,"Flow  onTouchEvent" );

        boolean ret  = super.onTouchEvent(event);
//        if(Constants.DEBUG){
//            ret = Constants.view_return_onTouchEvent;
//        }

//        getParent().requestDisallowInterceptTouchEvent(true);
        Log.v(TAG,"onTouchEvent         ret: " + ret + " ev: " + Constants.getActionString(event));
        return ret;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.v(TAG,"Flow  dispatchTouchEvent" );

        boolean ret  = super.dispatchTouchEvent(event);
//        if(Constants.DEBUG){
//            ret = Constants.view_return_dispatchTouchEvent;
//        }
        Log.v(TAG,"dispatchTouchEvent   ret: " + ret + " ev: " + Constants.getActionString(event));
        return ret;
    }




}
