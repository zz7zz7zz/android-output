package com.open.test;

import android.app.Application;
import android.util.Log;

import com.getkeepsafe.relinker.ReLinker;
import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVContentChangeNotification;
import com.tencent.mmkv.MMKVHandler;
import com.tencent.mmkv.MMKVLogLevel;
import com.tencent.mmkv.MMKVRecoverStrategic;

public class App extends Application implements MMKVHandler, MMKVContentChangeNotification {

    @Override
    public void onCreate() {
        super.onCreate();
        initMMKV();
//        BlockCanary.install(this, new BlockCanaryContext(){
//            @Override
//            public int provideBlockThreshold() {
//                return 50;
//            }
//        }).start();
    }


    @Override
    public void onTerminate() {
        MMKV.onExit();
        super.onTerminate();
    }

    private void initMMKV(){
        String dir = getFilesDir().getAbsolutePath() + "/mmkv";
        String rootDir;
        if (android.os.Build.VERSION.SDK_INT == 19) {
            rootDir = MMKV.initialize(this, dir, new MMKV.LibLoader() {
                @Override
                public void loadLibrary(String libName) {
                    ReLinker.loadLibrary(App.this, libName);
                }
            }, MMKVLogLevel.LevelInfo);
        }else {
            rootDir = MMKV.initialize(App.this);
        }

        Log.i("MMKV", "mmkv root: " + rootDir);
        Log.i("MMKV", "mmkv version: " + MMKV.version());

        // set log level
        MMKV.setLogLevel(MMKVLogLevel.LevelInfo);

        // you can turn off logging
        //MMKV.setLogLevel(MMKVLogLevel.LevelNone);

        // log redirecting & recover logic
        MMKV.registerHandler(this);

        // content change notification
        MMKV.registerContentChangeNotify(this);
    }

    @Override
    public MMKVRecoverStrategic onMMKVCRCCheckFail(String mmapID) {
        return MMKVRecoverStrategic.OnErrorRecover;
    }

    @Override
    public MMKVRecoverStrategic onMMKVFileLengthError(String mmapID) {
        return MMKVRecoverStrategic.OnErrorRecover;
    }

    @Override
    public boolean wantLogRedirecting() {
        return true;
    }

    @Override
    public void mmkvLog(MMKVLogLevel level, String file, int line, String func, String message) {
        String log = "<" + file + ":" + line + "::" + func + "> " + message;
        switch (level) {
            case LevelDebug:
                Log.d("redirect logging MMKV", log);
                break;
            case LevelNone:
            case LevelInfo:
                Log.i("redirect logging MMKV", log);
                break;
            case LevelWarning:
                Log.w("redirect logging MMKV", log);
                break;
            case LevelError:
                Log.e("redirect logging MMKV", log);
                break;
        }
    }

    @Override
    public void onContentChangedByOuterProcess(String mmapID) {
        Log.i("MMKV", "other process has changed content of : " + mmapID);
    }

}
