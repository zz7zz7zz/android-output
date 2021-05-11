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
        testShared2();
        testMMKV();
//        testMigrate();
        testMigrate2();//合并文件
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

        SharedPreUtil.putBoolean(this,"file100","bKey",true);
        SharedPreUtil.putInt(this,"file100","iKey",8888);
        SharedPreUtil.putString(this,"file100","iString","I am from file100");

        bKey = SharedPreUtil.getBoolean(this,"file100","bKey");
        iKey = SharedPreUtil.getInt(this,"file100","iKey");
        iString = SharedPreUtil.getString(this,"file100","iString");
        System.out.println(String.format("mmkv doTest: bKey %b ikey %d iString %s " ,bKey,iKey,iString));

    }

    private void testShared2(){
        boolean bKey1 = SharedPreUtil.getBoolean(this,"file101","bKey1");
        int iKey1 = SharedPreUtil.getInt(this,"file101","iKey1");
        String iString1 = SharedPreUtil.getString(this,"file101","iString1");
        System.out.println(String.format("mmkv doTest: bKey1 %b ikey1 %d iString1 %s " ,bKey1,iKey1,iString1));

        SharedPreUtil.putBoolean(this,"file101","bKey1",false);
        SharedPreUtil.putInt(this,"file101","iKey1",9999);
        SharedPreUtil.putString(this,"file101","iString1","I am from file101");

        bKey1 = SharedPreUtil.getBoolean(this,"file101","bKey1");
        iKey1 = SharedPreUtil.getInt(this,"file101","iKey1");
        iString1 = SharedPreUtil.getString(this,"file101","iString1");
        System.out.println(String.format("mmkv doTest: bKey1 %b ikey1 %d iString1 %s " ,bKey1,iKey1,iString1));

    }

    private void testMigrate(){

        //https://github.com/Tencent/MMKV/wiki/android_tutorial_cn

//        //SharedPreferences preferences = getSharedPreferences("myData", MODE_PRIVATE);
//        MMKV preferences = MMKV.mmkvWithID("myData");
//        // 迁移旧数据
//        {
//            SharedPreferences old_man = getSharedPreferences("myData", MODE_PRIVATE);
//            preferences.importFromSharedPreferences(old_man);
//            old_man.edit().clear().commit();
//        }
//        // 跟以前用法一样
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putBoolean("bool", true);
//        editor.putInt("int", Integer.MIN_VALUE);
//        editor.putLong("long", Long.MAX_VALUE);
//        editor.putFloat("float", -3.14f);
//        editor.putString("string", "hello, imported");
//        HashSet<String> set = new HashSet<String>();
//        set.add("W"); set.add("e"); set.add("C"); set.add("h"); set.add("a"); set.add("t");
//        editor.putStringSet("string-set", set);
//        // 无需调用 commit()
//        //editor.commit();

        int mode = Context.MODE_MULTI_PROCESS;
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
        System.out.println(String.format("mmkv testMigrate: bKey %b iKey %d iString %s " ,bKey,iKey,iString));


        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("bKey", true);
        editor.putInt("iKey", 88880001);
        editor.putString("iString", "Success !!! ");
    }

    private void testMigrate2(){
        int mode = Context.MODE_MULTI_PROCESS;
//        MMKV preferences = MMKV.mmkvWithID("file100");
//        MMKV preferences = MMKV.mmkvWithID("file100",(mode & Context.MODE_MULTI_PROCESS) == Context.MODE_MULTI_PROCESS ? MMKV.MULTI_PROCESS_MODE : MMKV.SINGLE_PROCESS_MODE);
        MMKV preferences = MMKV.defaultMMKV();


        boolean isMigrateSuccess = preferences.decodeBool("isMigrateSuccess");
        System.out.println("mmkv isMigrateSuccess" + isMigrateSuccess);
        if(!isMigrateSuccess){
            long start = System.currentTimeMillis();
            //迁移旧数据
            {
                SharedPreferences old_man = getSharedPreferences("file100", Context.MODE_PRIVATE);
                preferences.importFromSharedPreferences(old_man);
                old_man.edit().clear().commit();

                old_man = getSharedPreferences("file101", Context.MODE_PRIVATE);
                preferences.importFromSharedPreferences(old_man);
                old_man.edit().clear().commit();
            }

            System.out.println("mmkv migrate cost" + (System.currentTimeMillis() - start) + "ms");
            preferences.encode("isMigrateSuccess",true);
        }

        boolean bKey = preferences.getBoolean("bKey",false);
        int iKey = preferences.getInt("iKey",-1);
        String iString = preferences.getString("iString",null);

        boolean bKey1 = preferences.getBoolean("bKey1",false);
        int iKey1 = preferences.getInt("iKey1",-1);
        String iString1 = preferences.getString("iString1",null);


        System.out.println(String.format("mmkv testMigrate2: bKey %b iKey %d iString %s " ,bKey,iKey,iString));
        System.out.println(String.format("mmkv testMigrate2: bKey1 %b iKey1 %d iString1 %s " ,bKey1,iKey1,iString1));
    }
}
