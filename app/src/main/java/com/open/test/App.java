package com.open.test;

import android.app.Application;

import com.didichuxing.doraemonkit.DoraemonKit;
import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DoraemonKit.install(this,"88d7426976c87339f80cef833dd4561c");


//        BlockCanary.install(this, new BlockCanaryContext(){
//            @Override
//            public int provideBlockThreshold() {
//                return 50;
//            }
//        }).start();
    }
}
