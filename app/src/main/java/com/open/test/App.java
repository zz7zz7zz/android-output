package com.open.test;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        BlockCanary.install(this, new BlockCanaryContext(){
//            @Override
//            public int provideBlockThreshold() {
//                return 50;
//            }
//        }).start();
    }
}
