package com.module.other;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.util.ArrayList;
import java.util.Queue;

public class JOLTest {

    public static class A{

        public String a_name;
        public int a_age;

        byte _byte;
//        boolean _boolean;
//        char _char;
//        short _short;
//        int _int;
//        float _float;
//        double _double;
//        long _long;
    }

    public static class B extends A{

        public String b_name;
        public int b_age;
    }

    public static void main(String[] argv){

        System.out.println(VM.current().details());

//        objectTest();
//        other();
        objectArrayList();

    }

    private static void objectArrayList(){

        ArrayList o = new ArrayList();


        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        System.out.println("hashCode "+o.hashCode());

        System.out.println("---------------------");
        synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
            System.out.println("hashCode "+o.hashCode());
        }

    }

    private static void objectTest(){

        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        System.out.println("hashCode "+o.hashCode());

        System.out.println("---------------------");
        synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
            System.out.println("hashCode "+o.hashCode());
        }

    }


    public static void other(){

        System.out.println("----------A-----------");
        System.out.println(ClassLayout.parseClass(A.class).toPrintable());

        System.out.println("----------B-----------");
        A a = new A();
        System.out.println(ClassLayout.parseInstance(a).toPrintable());



        System.out.println("----------C-----------");
        System.out.println(ClassLayout.parseClass(B.class).toPrintable());

        System.out.println("----------D-----------");
        B b = new B();
        System.out.println(ClassLayout.parseInstance(b).toPrintable());
    }
}
