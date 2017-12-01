package com.open.test.javabase;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/10/10.
 */

public class javaArrayList {

    public static void main(String args[]){

//        ArrayListEquals();
//        systemPrintBoolean();

//        System.out.println(String.format(" -%b-%b",false,true));

//        longint();
//        arraySub();
        arrayDelete();
//        arrayDelete2();
//        arrayDelete3();
//        arrayDelete4();
    }


    public static void ArrayListEquals(){
        ArrayList<String> a=new ArrayList<>();
        ArrayList<String> b=new ArrayList<>();

        System.out.println(a == b);
    }

    public static void longint(){

        System.out.println(4>>1);

        long i = System.currentTimeMillis();
        System.out.println("long 0x"+Long.toHexString(i));

        long i2 = i&0x0FFFFFFF;
        int i3 = (int)i2;
        System.out.println("long i2 0x"+Long.toHexString(i&0x0FFFFFFF));
        System.out.println("long i2 0x"+Long.toHexString(i2));
        System.out.println("long 0x"+Long.toHexString(i3));
        System.out.println("int  0x"+Long.toHexString(i3));

        int j = (int)(i>>8);
        System.out.println("int  0x"+Integer.toHexString(j));

        System.out.println(i);
        System.out.println(i2);
        System.out.println(j);
    }

    public static void systemPrintBoolean(){
        boolean ret= false;
        System.out.println("A:"+ret);
    }

    public static void arraySub(){
        ArrayList<String> al=new ArrayList<>(5);
        al.add("b");
        al.add("a");
        al.add("c");
        al.add("d");
        al.add("e");


        al = new ArrayList<>(al.subList(0,100));
        System.out.println("-------------------");

        for (String s : al){
            System.out.println(s);
        }
    }

    public static void arrayDelete(){
        ArrayList<String> al=new ArrayList<>(5);
        al.add("a");
        al.add("b");
        al.add("c");
        al.add("d");
        al.add("e");

//        System.out.println("----------Testing 1---------");
//        List<String> sub = al.subList(0,2);
//        ArrayList<String> subCopy = new ArrayList<>(sub);
////        ArrayList<String> subCopy = new ArrayList<>();
////        subCopy.addAll(sub);
//        for (String s : sub){
//            System.out.println(s);
//        }
//
//        al.removeAll(sub);
//        al.removeAll(subCopy);


//        System.out.println("----------Testing 2---------");
        ArrayList<String> subCopy = new ArrayList<>(2);
        for (int i = 0 ; i< 2 ; i++){
            subCopy.add(al.get(i));
        }
        for (String s : subCopy){
            System.out.println(s);
        }

        al.removeAll(subCopy);

        System.out.println("-------------------al.size() " + al.size()  );
//        System.out.println("-------------------sub.size() " + sub.size()  );
        System.out.println("-------------------subCopy.size() " + subCopy.size()  );


        for (String s : al){
            System.out.println(s);
        }

        System.out.println("-------------------");
        for (String s : subCopy){
            System.out.println(s);
        }
    }

    public static void arrayDelete2(){
        ArrayList<String> al=new ArrayList<>(5);
        al.add("b");
        al.add("a");
        al.add("c");
        al.add("d");
        al.add("e");


        al.removeAll(al.subList(5,al.size()));
        System.out.println("-------------------");

        for (String s : al){
            System.out.println(s);
        }
    }

    public static void arrayDelete3(){

        ArrayList<String> al=new ArrayList<>(5);
        al.add("b");
        al.add("a");
        al.add("c");
        al.add("d");
        al.add("e");

        ArrayList<String> a2=new ArrayList<>(5);
        a2.addAll(al.subList(0,2));

        System.out.println("-------a1------------");
        for (String s : al){
            System.out.println(s);
        }

        System.out.println("-------a2------------");
        for (String s : a2){
            System.out.println(s);
        }

        Iterator<String> it = a2.iterator();//删除空数据
        int count = 0;
        while (it.hasNext() && count< 1) {
            it.next();
            it.remove();
            count++;
        }

        System.out.println("-------a1------------");
        for (String s : al){
            System.out.println(s);
        }


        System.out.println("-------a2------------");
        for (String s : a2){
            System.out.println(s);
        }
    }

    public static void arrayDelete4(){

        ArrayList<A> al=new ArrayList<>(5);
        al.add(new A("b"));
        al.add(new A("a"));
        al.add(new A("c"));
        al.add(new A("d"));
        al.add(new A("e"));

        ArrayList<A> a2=new ArrayList<>(5);
        a2.addAll(al.subList(0,2));

        System.out.println("-------a1------------");
        for (A s : al){
            System.out.println(s);
        }

        System.out.println("-------a2------------");
        for (A s : a2){
            System.out.println(s);
        }

        Iterator<A> it = a2.iterator();//删除空数据
        int count = 0;
        while (it.hasNext() && count< 1) {
            it.next();
            it.remove();
            count++;
        }

        System.out.println("-------a1------------");
        for (A s : al){
            System.out.println(s);
        }


        System.out.println("-------a2------------");
        for (A s : a2){
            System.out.println(s);
        }
    }

    static class A{
        public String a;

        public A(String a) {
            this.a = a;
        }
    }
}
