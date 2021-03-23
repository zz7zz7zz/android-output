package com.module.parameterization;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] argc) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List mList = new ArrayList();
//        List<Object> mList = new ArrayList();
        mList .add("Yang");
        mList .add("Long");
        mList .add("Hui");
        mList .add(100);


        Class cls = mList.getClass();
        Method method = cls.getMethod("add",Object.class);
        method.invoke(mList,1);
        method.invoke(mList,2);
        method.invoke(mList,3);


        System.out.println(mList);
//        int i =(int)mList.get(3);
//        System.out.println(i);
        for (int i = 0; i < mList.size(); i++) {
            System.out.println(mList.get(i));
        }

    }
}
