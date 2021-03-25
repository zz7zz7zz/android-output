package com.module.call;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class CallTest {


    public static class Grandfather{

        public void do1(){
            System.out.println("Grandfather do1");
        }

        protected void do2(){
            System.out.println("Grandfather do2");
        }

        private void do3(){
            System.out.println("Grandfather do3");
        }
    }

    public static class Father extends Grandfather{

        public void do1(){
            System.out.println("Father do1");
        }

        protected void do2(){
            System.out.println("Father do2");
        }

        private void do3(){
            System.out.println("Father do3");
        }
    }

    public static class Son extends Father{

        public void do1(){
            System.out.println("Son do1");

            MethodType methodType = MethodType.methodType(void.class);
            try {
                MethodHandle methodHandle = MethodHandles.lookup().findSpecial(Grandfather.class,"do1",methodType,getClass());
                methodHandle.invoke(this);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

        }

        protected void do2(){
            System.out.println("Son do2");
        }

        private void do3(){
            System.out.println("Son do3");
        }
    }


    public static void main(String[] args) {

        Son son = new Son();
        son.do1();

    }
}
