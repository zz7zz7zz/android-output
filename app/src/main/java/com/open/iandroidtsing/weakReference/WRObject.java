package com.open.iandroidtsing.weakReference;

import android.util.Log;

/**
 * Created by Administrator on 2016/8/22.
 */

public class WRObject {

    public WRObject() {
        Log.v("WRObject","new");
    }

    @Override
    protected void finalize() throws Throwable {
        Log.v("WRObject","delete");
        super.finalize();
    }
}
