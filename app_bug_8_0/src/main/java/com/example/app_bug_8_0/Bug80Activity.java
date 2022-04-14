package com.example.app_bug_8_0;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Bug80Activity extends Activity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bug80);
    }

    public static void start(Context context){
        Intent mIntent = new Intent(context,Bug80Activity.class);
        if (!(context instanceof Activity)) {    // Non activity, need less one flag.
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(mIntent);
    }

}
