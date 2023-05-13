package com.open.test.paint;

import android.app.Activity;
import android.os.Bundle;


public class PaintDipperActivity extends Activity {

    private final String TAG = "PaintActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DipperView(getApplicationContext()));
    }

}
