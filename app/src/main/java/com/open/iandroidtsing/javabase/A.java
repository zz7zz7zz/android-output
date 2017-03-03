package com.open.iandroidtsing.javabase;

/**
 * Created by Administrator on 2016/9/20.
 */

public class A {

    public String a;
    public int b;
    public B c;

    public A(String a, int b, B c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public String toString() {
        return "A{" +
                "b='" + a + '\'' +
                ", a=" + b +
                ", c=" + c +
                '}';
    }

    public static class B{
        public String d;
        public int    e;
        public double f;

        public B(String d, int e, double f) {
            this.d = d;
            this.e = e;
            this.f = f;
        }

        @Override
        public String toString() {
            return "B{" +
                    "d='" + d + '\'' +
                    ", e=" + e +
                    ", f=" + f +
                    '}';
        }
    }


    public static void main(String args[]){
        A a =new A("class A1", 1 ,null);
        System.out.println(a);

        a =new A("class A2", 2 ,new A.B("class B",22,33));
        System.out.println(a);
    }
}
