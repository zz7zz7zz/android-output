package com.open.test.com.open.frame;

import android.content.Context;
import android.os.Environment;
import android.support.v4.content.ContextCompat;

import java.io.File;

/**
 * Created by Administrator on 2017/1/16.
 */

public class SDCardUtil {

    public static String getDiskCacheDir(Context mContext) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File[] externalFilesDirs = ContextCompat.getExternalFilesDirs(mContext, null);
            if (externalFilesDirs != null && externalFilesDirs.length > 0 && externalFilesDirs[0] != null) {
                cachePath = externalFilesDirs[0].getAbsolutePath();
            } else {
                cachePath = mContext.getCacheDir().getPath();
            }
        } else {
            cachePath = mContext.getCacheDir().getPath();
        }
        return cachePath + File.separator;
    }

    public static String getDiskFilePath(Context mContext , String fileName) {
        return getDiskCacheDir(mContext) + fileName;
    }
}
