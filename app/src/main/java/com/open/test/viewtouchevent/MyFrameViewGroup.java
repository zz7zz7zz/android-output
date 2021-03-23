package com.open.test.viewtouchevent;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Stack;

public class
MyFrameViewGroup extends FrameLayout implements View.OnTouchListener {

    public MyFrameViewGroup(Context context) {
        this(context,null);
    }

    public MyFrameViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyFrameViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        setOnTouchListener(this);
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
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    //-------------------------------------------------------------
    private String TAG = "Event: ViewGroup";

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.v(TAG,"Flow  onInterceptTouchEvent" );

        boolean ret  = super.onInterceptTouchEvent(ev);
//        if(Constants.DEBUG){
//            ret = Constants.viewGroup_return_onInterceptTouchEvent;
//        }
        Log.v(TAG,"onInterceptTouchEvent    ret: " + ret + "   ev: " + Constants.getActionString(ev));
        return ret;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.v(TAG,"Flow  onTouch" );

        boolean ret = false;

//        if(Constants.DEBUG){
//            ret = Constants.viewGroup_return_onTouch;
//        }
        Log.v(TAG,"onTouch  ret: "+ret+" ev: " + Constants.getActionString(event));
        return ret;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v(TAG,"Flow  onTouchEvent" );

        boolean ret  = super.onTouchEvent(event);
//        if(Constants.DEBUG){
//            ret = Constants.viewGroup_return_onTouchEvent;
//        }
        Log.v(TAG,"onTouchEvent             ret: " + ret + "   ev: " + Constants.getActionString(event));
        return ret;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.v(TAG,"Flow  dispatchTouchEvent" );

        boolean ret  = super.dispatchTouchEvent(ev);
//        if(Constants.DEBUG){
//            ret = Constants.viewGroup_return_dispatchTouchEvent;
//        }
        Log.v(TAG,"dispatchTouchEvent       ret: " + ret + "   ev: " + Constants.getActionString(ev));
        return ret;
    }

}
