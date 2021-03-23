package com.module.sort.exchange;

public class QuickSort {

    public static void quickSort(int[] array,int l,int h){
        if(l<h){
            int i = partition(array,l,h);
            quickSort(array,l,i-1);
            quickSort(array,i+1,h);
        }
    }

    public static int partition(int [] array,int l,int h){
        int i = l;
        int j = h;
        int tmp = array[i];
        do{
            while(array[j]>=tmp && i<j){
                j--;
            }
            if( i<j)
                array[i++] = array[j];

            while(array[i]<=tmp && i<j){
                i++;
            }
            if( i<j)
                array[j--] = array[i];

        }while(i!=j);
        array[i] = tmp;
        return i;
    }

    //------------------------------------------------
    public static void main(String[] args) {
        int[] array = {36,25,48,12,65,43,20,58};

        System.out.println("#####################################");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + (i == array.length - 1 ? " \r\n" : " "));
        }

        System.out.println("#####################################");
        quickSort(array,0,array.length-1);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + (i == array.length - 1 ? " \r\n" : " "));
        }
    }
}
