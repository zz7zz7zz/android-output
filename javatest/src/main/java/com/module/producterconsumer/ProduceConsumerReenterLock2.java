package com.module.producterconsumer;

public class ProduceConsumerReenterLock2 {


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

//    public void produce(int count){
//        synchronized (lock){
//            Node node = new Node(count);
//            if(null == head){
//                head = tail = node;
//            }else{
//                tail.next = node;
//                tail = node;
//            }
//            System.out.println("produce "+ node.value);
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    public void consume(){
//        synchronized (lock){
//            Node nodes = head;
//            head = tail = null;
//            while (null != nodes){
//                System.out.println("consume "+ nodes.value);
//                nodes = nodes.next;
//            }
//            try {
//                Thread.sleep(20);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    ReentrantLock lock2 = new ReentrantLock();
//    public void produce(int count){
//
//        try{
////            System.out.println("produce start-1");
//            lock2.lock();
////            System.out.println("produce start-2");
//
//            Node node = new Node(count);
//            if(null == head){
//                head = tail = node;
//            }else{
//                tail.next = node;
//                tail = node;
//            }
////            System.out.println("produce "+ node.value);
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }finally {
////            System.out.println("produce end-1");
//            lock2.unlock();
////            System.out.println("produce end-2");
//        }
//    }



//    public static void main(String[]  argc){
//
//        final ProduceConsumerReenterLock2 mProConTest = new ProduceConsumerReenterLock2();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int count = 100;
//                while(count-- > 0) {
//                    mProConTest.produce(count);
//                }
//
//            }
//        }).start();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//                    mProConTest.consume();
//                }
//            }
//        }).start();
//
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }
}
