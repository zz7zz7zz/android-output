package com.open.test.aop;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;

import com.open.test.R;

public class JavassistAddCodeActivity extends Activity {

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

    static IDo DEFAULT = new IDo() {
        @Override
        public void do1() {

        }

        @Override
        public void do2() {

        }
    };

    interface IDo {

        void do1();

        void do2();
    }


}
