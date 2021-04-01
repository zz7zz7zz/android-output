package com.module.other;

import java.util.HashMap;

public class TestFind {

    public static void main(String[] args) {
        int[] array={1,2,3,2,2,2,5,4,2};
        System.out.println("------"+MoreThanHalfNum_Solution(array));
    }
    public static int MoreThanHalfNum_Solution(int [] array) {
        int halfLength = array.length/2;
        HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
        for(int i = 0;i<array.length;i++){
            if(map.get(array[i]) == null){
                map.put(array[i],1);
            }else{
                int oldSize = map.get(array[i]);
                if((array.length-i) + oldSize > halfLength){
                    int newSize = oldSize + 1;
                    if(newSize > halfLength){
                        return array[i];
                    }
                    map.put(array[i],newSize);
                }
            }
        }
        return 0;
    }
}
