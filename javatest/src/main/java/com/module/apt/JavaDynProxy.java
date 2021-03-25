package com.module.apt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import sun.misc.ProxyGenerator;

public class JavaDynProxy {


    public interface IDo {
        void onDo();
    }

    public static class A implements IDo{

        public void do1(){
            System.out.println("do1");
        }

        protected void do2(){
            System.out.println("do2");
        }

        private void do3(){
            System.out.println("do3");
        }

        @Override
        public void onDo() {
            System.out.println("onDo");
        }
    }

    public static final class B implements IDo{

        public void do1(){
            System.out.println("do1");
        }

        protected void do2(){
            System.out.println("do2");
        }

        private void do3(){
            System.out.println("do3");
        }

        @Override
        public void onDo() {
            System.out.println("onDo");
        }
    }


//    public static class InvocationHandlerImpl implements InvocationHandler {
//
//        private A target;
//
//        public InvocationHandlerImpl(A target)
//        {
//            this.target =target;
//        }
//
//
//        @Override
//        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
//            System.out.println("--------do before--------");
//            method.invoke(target,objects);
//            System.out.println("--------do after--------");
//            return null;
//        }
//
//        // 生成代理类
//        public Object create() {
//            return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
//        }
//    }

    public static class InvocationHandlerImp2 implements InvocationHandler {

        private Object target;

        public InvocationHandlerImp2(Object target)
        {
            this.target =target;
        }


        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            System.out.println("--------do before--------");
            method.invoke(target,objects);
            System.out.println("--------do after--------");
            return null;
        }

        // 生成代理类
        public Object create() {
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
        }
    }

    public static void main(String[] args) {

        testA();

        System.out.println("###############################");

//        testB();
    }

    public static void testA(){
        A a = new A();
        // 1.获取对应的ClassLoader
        ClassLoader classLoader = a.getClass().getClassLoader();

        // 2.获取A 所实现的所有接口
        Class[] interfaces = a.getClass().getInterfaces();

        // 3.设置一个来自代理传过来的方法调用请求处理器，处理所有的代理对象上的方法调用
        InvocationHandler handler = new InvocationHandlerImp2(a);
		/*
		  4.根据上面提供的信息，创建代理对象 在这个过程中，
                         a.JDK会通过根据传入的参数信息动态地在内存中创建和.class 文件等同的字节码
		         b.然后根据相应的字节码转换成对应的class，
                         c.然后调用newInstance()创建实例
		 */
        Object o = Proxy.newProxyInstance(classLoader, interfaces, handler);

        IDo a1 = (IDo) o;
        a1.onDo();


        generateClassFile(A.class,"AProxy");

        A a2 = (A) o;
        a2.do1();
        a2.do2();
        a2.do3();
    }

    public static void testB(){
//        B a = new B();
//        // 1.获取对应的ClassLoader
//        ClassLoader classLoader = a.getClass().getClassLoader();
//
//        // 2.获取A 所实现的所有接口
//        Class[] interfaces = a.getClass().getInterfaces();
//
//        // 3.设置一个来自代理传过来的方法调用请求处理器，处理所有的代理对象上的方法调用
//        InvocationHandler handler = new InvocationHandlerImpl(a);
//		/*
//		  4.根据上面提供的信息，创建代理对象 在这个过程中，
//                         a.JDK会通过根据传入的参数信息动态地在内存中创建和.class 文件等同的字节码
//		         b.然后根据相应的字节码转换成对应的class，
//                         c.然后调用newInstance()创建实例
//		 */
//        Object o = Proxy.newProxyInstance(classLoader, interfaces, handler);
//        B vehicle = (B) o;
//        vehicle.do1();
//        vehicle.do2();
//        vehicle.do3();
    }


    /*
	 * 将根据类信息 动态生成的二进制字节码保存到硬盘中，
	 * 默认的是clazz目录下
         * params :clazz 需要生成动态代理类的类
         * proxyName : 为动态生成的代理类的名称
         */
    public static void generateClassFile(Class clazz,String proxyName)
    {
        //根据类信息和提供的代理类名称，生成字节码
        byte[] classFile = ProxyGenerator.generateProxyClass(proxyName, clazz.getInterfaces());
        String paths = clazz.getResource(".").getPath();
        System.out.println(paths);
        FileOutputStream out = null;

        try {
            //保留到硬盘中
            out = new FileOutputStream(paths+proxyName+".class");
            out.write(classFile);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
