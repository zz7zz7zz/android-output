package com.open.test.paint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.open.test.R;
import com.open.test.weakReference.WRB_Activity;
import com.open.test.weakReference.WRTaskMgr;


public class PaintActivity extends Activity {

    private final String TAG = "PaintActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PentacleView(getApplicationContext()));
    }

}
