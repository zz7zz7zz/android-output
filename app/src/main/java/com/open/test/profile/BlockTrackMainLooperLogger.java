package com.open.test.profile;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.util.Printer;

/**
 * author       :   Administrator
 * created on   :   2018/1/24
 * description  :
 */

public class BlockTrackMainLooperLogger {

    private static final long TIME_BLOCK = 20L;
    private static BlockTrackMainLooperLogger INS = new BlockTrackMainLooperLogger();

    private HandlerThread mLogThread = new HandlerThread("log");
    private Handler mHandler;
    private Runnable mLogRunnable = new Runnable() {
        @Override
        public void run() {
            StringBuilder sb = new StringBuilder();
            StackTraceElement[] stackTrace = Looper.getMainLooper().getThread().getStackTrace();
            for (StackTraceElement s : stackTrace) {
                sb.append(s.toString() + "\n");
            }
            Log.v("SnippetTrack", sb.toString());
        }
    };

    private BlockTrackMainLooperLogger() {
        mLogThread.start();
        mHandler = new Handler(mLogThread.getLooper());
    }


    public static BlockTrackMainLooperLogger getInstance() {
        return INS;
    }

    public void startMonitor() {
        mHandler.removeCallbacks(mLogRunnable);
        mHandler.postDelayed(mLogRunnable, TIME_BLOCK);
    }

    public void removeMonitor() {
        mHandler.removeCallbacks(mLogRunnable);
    }

    public void start() {

        Looper.getMainLooper().setMessageLogging(new Printer() {

            final String START = ">>>>> Dispatching";
            final String END = "<<<<< Finished";

            @Override
            public void println(String log) {
                if (log.startsWith(START)) {
                    startMonitor();
                }
                if (log.startsWith(END)) {
                    removeMonitor();
                }
            }
        });

    }
}
