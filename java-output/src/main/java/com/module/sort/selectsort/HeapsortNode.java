package com.module.sort.selectsort;

import com.module.sort.Node;

public class HeapsortNode {

    private static void shift(Node array[] , int start , int end){
        int j = 2* start;
        Node temp = array[start];
        while(j< end){
            if((j+1<end) && array[j].key < array[j+1].key){
                j++;
            }
            if(temp.key < array[j].key){
                array[start] = array[j];
                start = j;
                j = 2 * start;
            }else {
                break;
            }
        }
        array[start] = temp;
    }

    public static void heapSort(Node array[]){
        for(int i = array.length/2-1;i>=0;i--){
            shift(array,i,array.length);
        }

        for (int i=array.length-1;i>=1;i--){
            Node temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            shift(array,0,i-1);
        }
    }


    //------------------------------------------------
    public static void main(String[] args) {
        int [] key = {36,25,48,12,65,43,20,58};
        Node[] array = new Node[key.length];
        for (int i = 0 ;i<key.length;i++){
            array[i] = new Node(key[i],"value "+key[i]);
        }

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
