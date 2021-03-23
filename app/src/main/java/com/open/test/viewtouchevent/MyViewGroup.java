package com.open.test.viewtouchevent;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class MyViewGroup extends ViewGroup implements View.OnTouchListener {

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    //-------------------------------------------------------------
    private String TAG = "Event: MyViewGroup: ViewGroup";
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.v(TAG,"Flow  onInterceptTouchEvent" );

        boolean ret  = super.onInterceptTouchEvent(ev);
        Log.v(TAG,"onInterceptTouchEvent    ret: " + ret + "   ev: " + Constants.getActionString(ev));
        return ret;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v(TAG,"Flow  onTouchEvent" );

        boolean ret  = super.onTouchEvent(event);
        Log.v(TAG,"onTouchEvent             ret: " + ret + "   ev: " + Constants.getActionString(event));
        return ret;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.v(TAG,"Flow  dispatchTouchEvent" );

        boolean ret  = super.dispatchTouchEvent(ev);
        Log.v(TAG,"dispatchTouchEvent       ret: " + ret + "   ev: " + Constants.getActionString(ev));
        return ret;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.v(TAG,"Flow  onTouch" );

        boolean ret = false;
        Log.v(TAG,"onTouch                  ret: " + ret +"     ev: " + Constants.getActionString(event));
        return ret;
    }
}
