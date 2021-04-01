package com.module.producterconsumer;

import com.module.producterconsumer.bean.Node;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 单个Condition
 */
public class PCReenterLock2 {

    private static final String TAG = "PCReenterLock2";

    private Node head;
    private Node tail;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void produce(int count){

        try{
//            System.out.println(TAG + " produce start ");
            lock.lock();
//            System.out.println(TAG + " produce start 2 ");
            Node node = new Node(count);
            if(null == head){
                head = tail = node;
            }else{
                tail.next = node;
                tail = node;
            }
            System.out.println(TAG + " produce "+ node.value);

//            Thread.sleep(10);
            condition.signal();

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
//            System.out.println(TAG + " produce end " + count);
        }
    }


    public void consume(){
        try{
//            System.out.println(TAG + " consume start ");
            lock.lock();
//            System.out.println(TAG + " consume start 2 ");
            condition.await();
//            System.out.println(TAG + " consume start 3 ");
            Node nodes = head;
            head = tail = null;
            while (null != nodes){
                System.out.println(TAG + " consume "+ nodes.value);
                nodes = nodes.next;
            }

//            Thread.sleep(10);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
//            System.out.println(TAG + " consume end ");
        }

    }




    public static void main(String[]  argc) throws InterruptedException {

        final PCReenterLock2 mProCon = new PCReenterLock2();

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

        consumerThread.start();//注意要让消费者先获取锁，所以需要先执行
        Thread.sleep(100);
        producerThread.start();

        consumerThread.join();
        producerThread.join();

    }
}
