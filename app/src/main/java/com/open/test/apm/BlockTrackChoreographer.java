package com.open.test.apm;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.Choreographer;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class BlockTrackChoreographer {

    private static final String TAG = "BlockTrackWithChoreographer";

    private static int doFrameNum = 0;
    private long startTime = 0;
    private long last_frameTimeNanos = 0;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            doFrameNum = doFrameNum > 60 ? 60 : doFrameNum;
            Log.d(TAG,"Choreographer FPS " + doFrameNum + " skipFrames " + (60- doFrameNum));

            reset();
            handler.postDelayed(this, 1000);
        }
    };

    private Choreographer.FrameCallback callback = new Choreographer.FrameCallback() {

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void doFrame(long frameTimeNanos) {

//            if(last_frameTimeNanos != 0){
//                Log.d(TAG,"Choreographer doFrame cost "  + (frameTimeNanos -last_frameTimeNanos)/1000000L+ "ms");
//                last_frameTimeNanos = frameTimeNanos;
//            }
//            last_frameTimeNanos = frameTimeNanos;

            doFrameNum++;
            Choreographer.getInstance().postFrameCallback(callback);
        }
    };

    private static BlockTrackChoreographer instance;

    public static BlockTrackChoreographer getInstance(){
        if(instance == null){
            instance = new BlockTrackChoreographer();
        }
        return instance;
    }


    public void start() {
        startTime = System.currentTimeMillis();
        handler.postDelayed(runnable, 1000);
        Choreographer.getInstance().postFrameCallback(callback);

    }


    public void stop() {
        Choreographer.getInstance().removeFrameCallback(callback);
        handler.removeCallbacks(runnable);
        reset();
    }

    private void reset() {
        startTime = System.currentTimeMillis();
        doFrameNum = 0;
    }

}