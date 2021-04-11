package com.module.other;

public class TestClass {

    public static void main(String[] args) throws Exception{
        Class clazz = String.class;
        Class clazz2 = Class.forName("java.lang.String");
        System.out.println("class == "+(clazz == clazz2) + " equals " + clazz.equals(clazz2));
        System.out.println("getName == "+(clazz.getName() == clazz2.getName()) + " equals " + clazz.getName().equals(clazz2.getName()));
        System.out.println(clazz.getName());
    }
}
