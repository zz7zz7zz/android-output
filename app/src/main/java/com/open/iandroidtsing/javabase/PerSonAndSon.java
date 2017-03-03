package com.open.iandroidtsing.javabase;

/**
 * Created by Administrator on 2016/9/20.
 */

public class PerSonAndSon {

    public static class PerSon{
        public final void p(){
            System.out.println("---PerSon---");
        }
    }

    public static class Son extends PerSon{
//        public void p(){
//            System.out.println("---Son---");
//        }
    }


    public static void main(String args[]){
//        PerSon o1 = new PerSon();
//        Son    o2 = new Son();
//
//        o1.p();
//        o2.p();


        int[] array= {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        int findValue = 1;
        int startIndex = -5;
        int left = Math.max(Math.min(startIndex,array.length-1),0);
        int right= left+1;
        int findIndex = -1;
        while(!(left<0  && right>=array.length)){

            System.out.println("left " + left + " right " + right);

            if(left>=0 && left<array.length && array[left] == findValue){
                findIndex = left;
                break;
            }else if(right>=0 && right < array.length && array[right] == findValue){
                findIndex = right;
                break;
            }

            if(left>=0){
                left -- ;
            }

            if(right<array.length){
                right ++ ;
            }
        }

        System.out.println("findIndex " + findIndex);
    }
}
