package com.open.iandroidtsing.threadpool;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 有待优化
 * Created by long on 2017/2/20.
 */

public class IThreadPool {

    private Object lock = new Object();
    private ArrayList<ProxyRunnable> commandList = new ArrayList<>(6);
    private ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
    private static volatile IThreadPool INS;
    private IThreadPool(){}

    public static IThreadPool getInstance(){
        if(null == INS){
            synchronized(IThreadPool.class) {
                //when more than two threads run into the first null check same time, to avoid instanced more than one time, it needs to be checked again.
                if(INS == null)
                {
                    INS = new IThreadPool();
                }
            }
        }
        return INS;
    }

    public static void destory(){
        if(null != INS && null != INS.scheduledThreadPool){
            INS.scheduledThreadPool.shutdown();
        }
        INS = null;
    }

    public void execute(Runnable command){
        ProxyRunnable cmd = new ProxyRunnable(command);
        synchronized (lock){
            commandList.add(cmd);
        }
        INS.scheduledThreadPool.execute(cmd);
    }

    private void onFinish(ProxyRunnable cmd){
        synchronized (lock){
            commandList.remove(cmd);
        }
    }

    private int getCommandSize(){
        synchronized (lock) {
            return commandList.size();
        }
    }

    private final ProxyRunnable EXIT_COMMAND = new ProxyRunnable(new Runnable() {
        @Override
        public void run() {
            destory();
            System.out.println("EXIT_COMMAND");
        }
    });

    private class ProxyRunnable implements Runnable{
        Runnable command ;

        public ProxyRunnable(Runnable command) {
            this.command = command;
        }

        @Override
        public void run() {

            onFinish(this);

            if(this == EXIT_COMMAND){
                if(getCommandSize() == 0){
                    EXIT_COMMAND.run();
                }
            }
            else if(null != command){
                command.run();

                if(getCommandSize() == 0){
                    scheduledThreadPool.schedule(EXIT_COMMAND, 3, TimeUnit.SECONDS);
                }
            }
        }
    }

}
