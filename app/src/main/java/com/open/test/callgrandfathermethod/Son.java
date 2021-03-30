package com.open.test.callgrandfathermethod;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class Son extends Father {

    public void do1(){
        System.out.println("Son do1");

//        MethodType methodType = MethodType.methodType(void.class);
//        try {
//            MethodHandle methodHandle = MethodHandles.lookup().findSpecial(Grandfather.class,"do1",methodType,getClass());
//            methodHandle.invoke(this);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }

    }

    protected void do2(){
        System.out.println("Son do2");
        super.do2();
    }

    private void do3(){
        System.out.println("Son do3");
    }

    private void do4(){
        System.out.println("Son do4");
    }

    private void do5(){
        System.out.println("Son do5");
    }
}