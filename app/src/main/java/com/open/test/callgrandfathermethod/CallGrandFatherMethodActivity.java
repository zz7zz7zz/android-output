package com.open.test.callgrandfathermethod;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.open.test.R;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class CallGrandFatherMethodActivity extends Activity {

    private String TAG = "CallGrandFatherMethodActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.net_okhttp);


        Son son = new Son();
//        son.do1();
        son.do2();
//        son.do3();

    }
}