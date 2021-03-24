package com.open.test.exceptionfix;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.open.test.R;

public class ExceptionFixActivity extends Activity {

    private String TAG = "ExceptionFixActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.net_okhttp);

        Toast.makeText(this,"我是错误的代码",Toast.LENGTH_LONG).show();
    }


}
