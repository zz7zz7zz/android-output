package com.module.producterconsumer;

import java.util.concurrent.locks.ReentrantLock;

public class ProduceConsumerReenterLock1 {


    public static class Node{

        public int value;

        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    private Object lock = new Object();
    private Node head;
    private Node tail;

    ReentrantLock lock2 = new ReentrantLock();
    public void produce(int count){

        try{
//            System.out.println("produce start-1");
            lock2.lock();
//            System.out.println("produce start-2");

            Node node = new Node(count);
            if(null == head){
                head = tail = node;
            }else{
                tail.next = node;
                tail = node;
            }
//            System.out.println("produce "+ node.value);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
//            System.out.println("produce end-1");
            lock2.unlock();
//            System.out.println("produce end-2");
        }
    }


    public void consume(){
        try{
//            System.out.println("consume start-1");
            lock2.lock();
//            System.out.println("consume start-2");

            Node nodes = head;
            head = tail = null;
            while (null != nodes){
                System.out.println("consume "+ nodes.value);
                nodes = nodes.next;
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
//            System.out.println("consume end-1");
            lock2.unlock();
//            System.out.println("consume end-2");
        }
    }




    public static void main(String[]  argc){

        final ProduceConsumerReenterLock1 mProConTest = new ProduceConsumerReenterLock1();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 100;
                while(count-- > 0) {
                    mProConTest.produce(count);
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    mProConTest.consume();
                }
            }
        }).start();

        try {

            final  Object o = new Object();
            new Thread(new Runnable() {
                @Override
                public void run() {


                    synchronized (o){
                        o.notify();
                    }
                }
            }).start();
            synchronized (o){
                o.wait();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
