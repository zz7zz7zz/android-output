package com.open.test.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 有待优化
 * Created by long on 2017/2/20.
 */

public class IThreadPool2 {

    BlockingQueue queue = new LinkedBlockingQueue();
    ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 6, 10, TimeUnit.SECONDS, queue);
    private static volatile IThreadPool2 INS;
    private IThreadPool2(){}

    public static IThreadPool2 getInstance(){
        if(null == INS){
            synchronized(IThreadPool2.class) {
                //when more than two threads run into the first null check same time, to avoid instanced more than one time, it needs to be checked again.
                if(INS == null)
                {
                    INS = new IThreadPool2();
                }
            }
        }
        return INS;
    }

    public static void destory(){
        if(null != INS && null != INS.executor){
            INS.executor.shutdown();
        }
        INS = null;
    }

    public void execute(Runnable command){
        INS.executor.execute(command);
    }
}
