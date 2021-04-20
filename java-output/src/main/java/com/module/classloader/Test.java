package com.module.classloader;

public class Test {

    public static void main(String[] args) {
        try{
            Class clz = Class.forName("com.module.other.Bit");
            Class clz2 = Class.forName("com.module.other.Bit");
            System.out.println("clz "+clz);
            System.out.println("clz2 "+clz2);
            System.out.println(" == "+(clz == clz2));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
