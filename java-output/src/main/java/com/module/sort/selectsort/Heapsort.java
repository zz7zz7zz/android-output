package com.module.sort.selectsort;

public class Heapsort {


    private static void shift(int array[] , int start , int end){
        int j = 2* start;
        int temp = array[start];
        while(j< end){
            if((j+1<end) && array[j] < array[j+1]){
                j++;
            }
            if(temp < array[j]){
                array[start] = array[j];
                start = j;
                j = 2 * start;
            }else {
                break;
            }
        }
        array[start] = temp;
    }

    public static void heapSort(int array[]){
        for(int i = array.length/2-1;i>=0;i--){
            shift(array,i,array.length);
        }

        for (int i=array.length-1;i>=1;i--){
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            shift(array,0,i-1);
        }
    }


    //------------------------------------------------
    public static void main(String[] args) {
        int[] array = {36,25,48,12,65,43,20,58};

        System.out.println("#####################################");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + (i == array.length - 1 ? " \r\n" : " "));
        }

        System.out.println("#####################################");
        heapSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + (i == array.length - 1 ? " \r\n" : " "));
        }
    }

}
