package com.open.iandroidtsing.weakReference;

import android.app.Activity;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/8/22.
 */

public class WRTaskMgr {

    public static void doWork(Activity activity){
        new Thread(new WRTask(activity)).start();
    }

    public static class WRTask implements Runnable{

        public WeakReference<Activity> ref;

        public WRTask(Activity act) {
            this.ref = new WeakReference<>(act);
        }

        @Override
        public void run() {
            int count = 0;
            while (count<500){
                try {

                    new WRObject();

                    ++count;
                    Thread.sleep(2000);

                    System.gc();
//                    System.runFinalizersOnExit(true);

                    Activity act = ref.get();
                    if(null == act){
                        Log.v("WRTask","count "+count+" code = 1");
                    }else if(act.isFinishing()){
                        Log.v("WRTask","count "+count+" code = 2");
                        ref.clear();
                    }else{
                        Log.v("WRTask","count "+count+" code = 3");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
