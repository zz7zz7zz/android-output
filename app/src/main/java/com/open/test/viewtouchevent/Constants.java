package com.open.test.viewtouchevent;

import android.view.MotionEvent;

public final class Constants {

    public final static boolean DEBUG = true;

    public final static boolean view_return_onTouch = false;
    public final static boolean view_return_onTouchEvent = true;
    public final static boolean view_return_dispatchTouchEvent = true;

    public final static boolean viewGroup_return_onTouch = true;
    public final static boolean viewGroup_return_onTouchEvent = true;
    public final static boolean viewGroup_return_dispatchTouchEvent = true;
    public final static boolean viewGroup_return_onInterceptTouchEvent = true;

    public final static boolean activity_return_onTouch = true;
    public final static boolean activity_return_onTouchEvent = true;
    public final static boolean activity_return_dispatchTouchEvent = true;


    public static String getActionString(MotionEvent ev){
        String ret ;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                ret = "ACTION_DOWN";
                break;

            case MotionEvent.ACTION_UP:
                ret = "ACTION_UP";
                break;

            case MotionEvent.ACTION_MOVE:
                ret = "ACTION_MOVE";
                break;

            case MotionEvent.ACTION_CANCEL:
                ret = "ACTION_CANCEL";
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                ret = "ACTION_POINTER_DOWN";
                break;

            case MotionEvent.ACTION_POINTER_UP:
                ret = "ACTION_POINTER_UP";
                break;

            case MotionEvent.ACTION_HOVER_MOVE:
                ret = "ACTION_HOVER_MOVE";
                break;

            case MotionEvent.ACTION_SCROLL:
                ret = "ACTION_SCROLL";
                break;

            case MotionEvent.ACTION_HOVER_ENTER:
                ret = "ACTION_HOVER_ENTER";
                break;

            case MotionEvent.ACTION_HOVER_EXIT:
                ret = "ACTION_HOVER_EXIT";
                break;

            case MotionEvent.ACTION_BUTTON_PRESS:
                ret = "ACTION_BUTTON_PRESS";
                break;

            case MotionEvent.ACTION_BUTTON_RELEASE:
                ret = "ACTION_BUTTON_RELEASE";
                break;

            default:
                ret = "unKnown action "+ev.getAction();

        }
        return ret;
    }
}
