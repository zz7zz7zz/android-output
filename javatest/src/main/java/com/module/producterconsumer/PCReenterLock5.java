package com.module.producterconsumer;

import com.module.producterconsumer.bean.Node;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 无Condition
 */
public class PCReenterLock5 {

    private static final String TAG = "PCReenterLock";

    private Node head;
    private Node tail;
    private ReentrantLock lock = new ReentrantLock();

    private Node his_head;
    private Node his_tail;

    public boolean produce(int count){
        boolean acquiredLock = lock.tryLock();
        if(acquiredLock){
            try{
                Node node = new Node(count);
                System.out.println(TAG + " produce "+ node.value);
                if(null != his_head){
                    his_tail.next = node;
                    head = his_head;
                    his_head = his_tail = null;
                }else{
                    if(null == head){
                        head = tail = node;
                    }else{
                        tail.next = node;
                        tail = node;
                    }
                }
                Thread.sleep(10);
                return true;
            }catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }else{
            Node node = new Node(count);
            if(null == his_head){
                his_head = his_tail = node;
            }else{
                his_tail.next = node;
                his_tail = node;
            }
            System.out.println(TAG + " produce acquiredLock false "+ node.value);
        }
        return false;
    }

    public void flush(){
        if(null == his_head){
            return;
        }
        try{
            lock.lock();
            if(null != his_head){
                head = his_head;
                tail = his_tail;
                his_head = his_tail = null;
            }
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

        final PCReenterLock5 mProCon = new PCReenterLock5();

        Thread producerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 1000;
                while(count-- > 0) {
                    mProCon.produce(count);
                    mProCon.flush();
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
