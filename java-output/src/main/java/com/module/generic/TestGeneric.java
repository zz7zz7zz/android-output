package com.module.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TestGeneric {

    public static void main(String[] args) {

        testGeneric();
    }

    public static void testGeneric(){
        Son son = new Son();
        printlnSuperclass(son);

        SonImpl sonImpl = new SonImpl();
        printlnInterfaces(sonImpl);
    }

    public static void printlnSuperclass(Object o){
        System.out.println("##############################");
        System.out.println("printlnSuperclass o-> " + o);
        System.out.println("printlnSuperclass o.getClass()-> " + o.getClass());
        System.out.println("printlnSuperclass o.getClass().getGenericSuperclass()-> " + o.getClass().getGenericSuperclass());
        System.out.println("printlnSuperclass o.getClass().getGenericInterfaces()-> " + o.getClass().getGenericInterfaces());

        ParameterizedType type = (ParameterizedType)o.getClass().getGenericSuperclass();
        Class clz = (Class) type.getActualTypeArguments()[0];

        System.out.println("printlnSuperclass t-> " + type + " ParameterizedType " + (type instanceof ParameterizedType));
        System.out.println("printlnSuperclass o-> " + clz);

        Class DataClz = Data.class;
        System.out.println("printlnSuperclass DataClz-> " + clz);

        System.out.println("printlnSuperclass o==DataClz " + (DataClz == clz));
    }

    public static void printlnInterfaces(Object o){
        System.out.println("##############################");
        System.out.println("printlnInterfaces o-> " + o);
        System.out.println("printlnInterfaces o.getClass()-> " + o.getClass());
        System.out.println("printlnInterfaces o.getClass().getGenericSuperclass()-> " + o.getClass().getGenericSuperclass());
        System.out.println("printlnInterfaces o.getClass().getGenericInterfaces()-> " + o.getClass().getGenericInterfaces());

        Type[] types = o.getClass().getGenericInterfaces();
        for (Type type: types) {
            System.out.println("printlnInterfaces t-> " + type + " ParameterizedType " + (type instanceof ParameterizedType));
        }
        Class clz = (Class) ((ParameterizedType)types[0]).getActualTypeArguments()[0];
        System.out.println("printlnInterfaces o-> " + clz);

        Class DataClz = Data.class;
        System.out.println("printlnInterfaces DataClz-> " + clz);

        System.out.println("printlnInterfaces o==DataClz " + (DataClz == clz));
    }

}
