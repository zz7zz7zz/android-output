package com.open.test.exceptionfix;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.open.test.R;

public class ClassAddCodeActivity extends Activity {

    private String TAG = "ClassAddCodeActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.net_okhttp);

        doSomething();
    }

    public void doSomething(){

        Log.v(TAG,"doSomething , I am lonely code");
        System.out.println("doSomething , I am lonely code more ");

    }

}
