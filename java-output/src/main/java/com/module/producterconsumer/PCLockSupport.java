package com.module.producterconsumer;

import com.module.producterconsumer.bean.Node;

import java.util.concurrent.locks.LockSupport;

public class PCLockSupport {

    private static final String TAG = "PCLockSupport";

    private Node head;
    private Node tail;

    Thread producerThread;
    Thread consumerThread;

    public boolean produce(int count){

        try{
            Node node = new Node(count);
            if(null == head){
                head = tail = node;
            }else{
                tail.next = node;
                tail = node;
            }
            System.out.println(TAG + " produce "+ node.value);
            Thread.sleep(10);

        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        LockSupport.unpark(consumerThread);
        LockSupport.park();
        return false;
    }

    public void consume(){
        LockSupport.park();

        try{
            Node nodes = head;
            head = tail = null;
            while (null != nodes){
                System.out.println(TAG + " consume "+ nodes.value);
                nodes = nodes.next;
            }

            Thread.sleep(10);

        }catch (InterruptedException e){
            e.printStackTrace();
        }

        LockSupport.unpark(producerThread);
    }


    public static void main(String[]  argc) throws InterruptedException {

        final PCLockSupport mProCon = new PCLockSupport();

        Thread producerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 100;
                while(count-- > 0) {
                    mProCon.produce(count);
                }

            }
        },"Producer");

        Thread consumerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    mProCon.consume();
                }
            }
        },"Consumer");

        mProCon.producerThread = producerThread;
        mProCon.consumerThread = consumerThread;

        producerThread.start();
        consumerThread.start();

        producerThread.join();
        consumerThread.join();

    }
}
