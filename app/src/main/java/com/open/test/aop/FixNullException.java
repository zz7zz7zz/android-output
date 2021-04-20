package com.open.test.aop;

import android.content.Context;

public class FixNullException {


    public static boolean isSuccess(Object o){
        return o.equals("yes");

    }

    public static boolean isSuccessNew(Object o){
        if(o == null){
            return false;
        }
        return o.equals("yes");
    }
}
