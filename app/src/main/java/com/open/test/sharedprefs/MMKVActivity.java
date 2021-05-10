package com.open.test.sharedprefs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.open.test.R;
import com.open.test.utils.FileUtil;
import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVContentProvider;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/5.
 */

public class MMKVActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharedprefs);

        String rootDir = MMKV.initialize(this);
        System.out.println("mmkv root: " + rootDir);



        MMKV kv = MMKV.defaultMMKV();
        System.out.println(String.format("mmkv before: bValue %b iValue %d str %s " ,kv.decodeBool("bool"),kv.decodeInt("int"),kv.decodeString("string")));


        kv.encode("bool", true);
        boolean bValue = kv.decodeBool("bool");

        kv.encode("int", Integer.MIN_VALUE);
        int iValue = kv.decodeInt("int");

        kv.encode("string", "Hello from mmkv");
        String str = kv.decodeString("string");

        System.out.println(String.format("mmkv after: bValue %b iValue %d str %s " ,kv.decodeBool("bool"),kv.decodeInt("int"),kv.decodeString("string")));

    }

    private void doTest(){

    }

}
