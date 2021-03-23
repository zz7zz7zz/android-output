package com.module.other;


import java.util.ArrayList;
import java.util.LinkedList;

public class Bit {


    public static void main(String[] argv){

       oneBitCount();
    }

    //求一个整型数字中1的个数，1的位置
    public static void oneBitCount(){
        int n = 15;

        double size = (int) (Math.log(n+1) / Math.log(2));
        int maxOneBitCountSize = (int) size;
        System.out.println("size " + size + " maxOneBitCountSize " + maxOneBitCountSize);
        int lastN = n;

        int count = 0;//1的个数
        ArrayList<Integer> indexList = new ArrayList<>(maxOneBitCountSize);//1所在的位置


        while(n!=0){
            n&=(n-1);
            count++;
            //在java中求log2N，首先要弄明白一个初中学到的公式log2N=logeN/loge2,logeN代表以e为底的N的对数,loge2代表以e为底的2的对数.
            //在java.lang.math类中的log(double a)代表以e为底的a的对数,因此log2N在Java中的表示为:
            //log((double)N)/log((double)2)

            double v = Math.log(lastN - n) / Math.log(2);
            System.out.println("n "+n+" index "+(int)v);
            indexList.add(((int) v + 1));
            lastN = n;
        }

        System.out.println("count "+count);
        for (int i =0;i<indexList.size();i++){
            System.out.println("index "+indexList.get(i));
        }
    }

    public static void exp(){
        int a = 1;
        int b = 2;
        System.out.printf("before a %d b %d\n",a,b);
        a ^= b ^= a ^= b;
        System.out.printf("affter a %d b %d\n",a,b);

        int arr[] = {100,200};
        System.out.printf("before arr[0] %d arr[1] %d\n",arr[0],arr[1]);
        arr[0] ^= arr[1] ^= arr[0] ^= arr[1];
        System.out.printf("affter arr[0] %d arr[1] %d\n",arr[0],arr[1]);


    }

}
