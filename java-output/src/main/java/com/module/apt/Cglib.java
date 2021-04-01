package com.module.apt;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Cglib {
    public static class A{

        public void do1(){
            System.out.println("do1");
        }

        protected void do2(){
            System.out.println("do2");
        }

        private void do3(){
            System.out.println("do3");
        }
    }

    public static final class B{

        public void do1(){
            System.out.println("do1");
        }

        protected void do2(){
            System.out.println("do2");
        }

        private void do3(){
            System.out.println("do3");
        }
    }


    public static class MethodInterceptorImpl implements MethodInterceptor{

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("--------do before--------");
            proxy.invokeSuper(obj, args);
            System.out.println("--------do after--------");
            return null;
        }
    }

    public static void main(String[] args) {

        testA();

        System.out.println("###############################");

//        testB();
    }

    public static void testA(){

        // 将class 文件保存
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/long/Desktop");

        A a = new A();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(a.getClass());
        enhancer.setCallback(new MethodInterceptorImpl());


        A a2 = (A) enhancer.create();
        a2.do1();
        a2.do2();
        a2.do3();
    }

    public static void testB(){
        B b = new B();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(b.getClass());
        enhancer.setCallback(new MethodInterceptorImpl());


        B b2 = (B) enhancer.create();
        b2.do1();
        b2.do2();
        b2.do3();
    }
}
