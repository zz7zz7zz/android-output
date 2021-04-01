package com.module.producterconsumer;

import com.module.producterconsumer.bean.Node;

public class PCSynchronized {

    private static final String TAG = "Synchronized";

    private Node head;
    private Node tail;
    private Object lock = new Object();

    public synchronized void produce(int count){
            Node node = new Node(count);
            if(null == head){
                head = tail = node;
            }else{
                tail.next = node;
                tail = node;
            }
            System.out.println(TAG + " produce "+ node.value);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }


    public synchronized void consume(){
            Node nodes = head;
            head = tail = null;
            while (null != nodes){
                System.out.println(TAG + " consume "+ nodes.value);
                nodes = nodes.next;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

//    public  void produce(int count){
//        synchronized (lock){
//            Node node = new Node(count);
//            if(null == head){
//                head = tail = node;
//            }else{
//                tail.next = node;
//                tail = node;
//            }
//            System.out.println(TAG + " produce "+ node.value);
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//
//    public void consume(){
//        synchronized (lock){
//            Node nodes = head;
//            head = tail = null;
//            while (null != nodes){
//                System.out.println(TAG + " consume "+ nodes.value);
//                nodes = nodes.next;
//            }
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public static void main(String[]  argc) throws InterruptedException {

        final PCSynchronized mProCon = new PCSynchronized();

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
