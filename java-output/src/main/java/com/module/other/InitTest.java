package com.module.other;

class InitTest {

    public static void main(String[] args) {
        A a = new A();
        System.out.println("----value ----" + a.a);
    }

    public static class A{

        public int a = 1;

        {
            a = 2;
            System.out.println("----step 2 ----");
        }

        public A() {
            a = 3;
            System.out.println("----step 3 ----");
        }
    }
}
