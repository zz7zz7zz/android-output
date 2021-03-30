package com.module.other;

public class TestTryFinally {

    public static void main(String[] args) {
        System.out.println(test());
    }
    public static int test() {
        int i = 1;
        try {
            return i;
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }finally {
            i++;
            System.out.println("i = " + i);

            i=99;
            System.out.println("i = " + i);

//            return i;

            return 88;
        }
    }
}
