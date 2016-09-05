package com.open.iandroidtsing.sharedprefs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.open.iandroidtsing.R;
import com.open.iandroidtsing.com.open.frame.FileUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/5.
 */

public class SharedPrefsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharedprefs);

        TextView sharedprefs_1 = (TextView)findViewById(R.id.sharedprefs_1);
        TextView sharedprefs_2 = (TextView)findViewById(R.id.sharedprefs_2);

        byte [] b = FileUtil.readFile("/data/data/"+this.getPackageName()+"/shared_prefs/sdk_push.xml");
        sharedprefs_1.setText("old : \n"+(null !=b ? new String(b) : "null"));

        doTest();

        b = FileUtil.readFile("/data/data/"+this.getPackageName()+"/shared_prefs/sdk_push.xml");
        sharedprefs_2.setText("\n\nnew : \n"+(null !=b ? new String(b) : "null"));
    }

    private void doTest(){
        //如果有相同的push，则只显示一次
        //先判断是不是同一天，如果不是同一天，则将所有记录都删除

        String content_id = "1234567989";
        SharedPreferences shared = getSharedPreferences("sdk_push", Context.MODE_PRIVATE);
        String oldToday = shared.getString("today", "");;
        String newToday = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        SharedPreferences.Editor editor = shared.edit();
        if(!newToday.equalsIgnoreCase(oldToday)){
            editor.clear();
            editor.putString("today", newToday);
        }
        int count = shared.getInt(content_id,0);
        editor.putInt(content_id,++count);
        editor.commit();
    }

}
