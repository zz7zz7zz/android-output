package com.open.iandroidtsing.notification;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/1/17.
 */

public class IScrollView extends ScrollView {

    public IScrollView(Context context) {
        super(context);
    }

    public IScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {

        Log.v("IScrollView","getScrollY() "+ getScrollY() +" getHeight "+getHeight() + " getScrollY "+computeVerticalScrollRange());

        if(null !=sc && getScrollY() + getHeight() >=  computeVerticalScrollRange())
        {
            sc.ScrollBottom();
        }

        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    protected OnScrollStateChanged sc=null;

    public void setOnScrollStateChanged(OnScrollStateChanged _sc)
    {
        sc=_sc;
    }

    public interface OnScrollStateChanged
    {
        void ScrollTop();
        void ScrollBottom();
    }
}
