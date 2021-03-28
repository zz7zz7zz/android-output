package com.open.test.service;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import android.util.Log;

public class ImplQueenService extends AbsQueenService {

    private static final String TAG = "ImplQueenService";

    public ImplQueenService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        int cmd = (null != intent) ? intent.getIntExtra(CMD, 0xFFFFFFFF) : 0xFFFFFFFF;

        Log.v(TAG, "onHandleIntent CMD "+ cmd + " This " +this);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.v(TAG, "onDestroy This " + this);
    }

    //---------------------------------------------------------------------------------------
    public static final String CMD                         = "cmd";
    public static final int    CMD_TURN_ON_PUSH            = 0x0001;
    public static final int    CMD_UPDATE_ADCONFIG         = 0x0002;

    public static void turnOnPush(Context mContext) {
        Intent mIntent = new Intent(mContext, ImplQueenService.class);
        mIntent.putExtra(ImplQueenService.CMD, ImplQueenService.CMD_TURN_ON_PUSH);
        mContext.startService(mIntent);
    }

    public static void loadConfig(Context mContext) {
        Intent mIntent = new Intent(mContext, ImplQueenService.class);
        mIntent.putExtra(ImplQueenService.CMD, ImplQueenService.CMD_UPDATE_ADCONFIG);
        mContext.startService(mIntent);
    }
}
