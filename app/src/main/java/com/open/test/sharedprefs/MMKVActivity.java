package com.open.test.sharedprefs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.open.test.R;
import com.open.test.utils.SharedPreUtil;
import com.tencent.mmkv.MMKV;

/**
 * Created by Administrator on 2016/9/5.
 */

public class MMKVActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharedprefs);

        testShared();
        testMMKV();
        testMigrate();
    }

    private void testMMKV(){


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

    private void testShared(){
        boolean bKey = SharedPreUtil.getBoolean(this,"file100","bKey");
        int iKey = SharedPreUtil.getInt(this,"file100","iKey");
        String iString = SharedPreUtil.getString(this,"file100","iString");
        System.out.println(String.format("mmkv doTest: bKey %b ikey %d iString %s " ,bKey,iKey,iString));

//        SharedPreUtil.putBoolean(this,"file100","bKey",true);
//        SharedPreUtil.putInt(this,"file100","iKey",99999);
//        SharedPreUtil.putString(this,"file100","iString","I am from SharedPre 20210510");
//
//        bKey = SharedPreUtil.getBoolean(this,"file100","bKey");
//        iKey = SharedPreUtil.getInt(this,"file100","iKey");
//        iString = SharedPreUtil.getString(this,"file100","iString");
//        System.out.println(String.format("mmkv doTest: bKey %b ikey %d iString %s " ,bKey,iKey,iString));

    }

    private void testMigrate(){
//        MMKV preferences = MMKV.mmkvWithID("file100");
        MMKV preferences = MMKV.mmkvWithID("file100",(mode & Context.MODE_MULTI_PROCESS) == Context.MODE_MULTI_PROCESS ? MMKV.MULTI_PROCESS_MODE : MMKV.SINGLE_PROCESS_MODE);

        //迁移旧数据
        {
            SharedPreferences old_man = getSharedPreferences("file100", Context.MODE_PRIVATE);
            preferences.importFromSharedPreferences(old_man);
            old_man.edit().clear().commit();
        }

        boolean bKey = preferences.getBoolean("bKey",false);
        int iKey = preferences.getInt("iKey",-1);
        String iString = preferences.getString("iString",null);
        System.out.println(String.format("mmkv testMigrate: bKey %b ikey %d iString %s " ,bKey,iKey,iString));

    }

}
