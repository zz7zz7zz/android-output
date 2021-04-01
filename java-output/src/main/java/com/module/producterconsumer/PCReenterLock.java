package com.module.producterconsumer;

import com.module.producterconsumer.bean.Node;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 无Condition
 */
public class PCReenterLock {

    private static final String TAG = "PCReenterLock";

    private Node head;
    private Node tail;
    private ReentrantLock lock = new ReentrantLock();

    public void produce(int count){

        try{
            lock.lock();

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
        }finally {
            lock.unlock();
        }
    }


    public void consume(){
        try{
            lock.lock();

            Node nodes = head;
            head = tail = null;
            while (null != nodes){
                System.out.println(TAG + " consume "+ nodes.value);
                nodes = nodes.next;
            }

            Thread.sleep(10);

        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }




    public static void main(String[]  argc) throws InterruptedException {

        final PCReenterLock mProCon = new PCReenterLock();

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

        producerThread.start();
        consumerThread.start();

        producerThread.join();
        consumerThread.join();

    }
}
