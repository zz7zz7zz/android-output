package com.module.sort.exchange;

import com.module.sort.Node;

public class QuickSortNode {

    public static void quickSort(Node[] array, int l, int h){
        if(l<h){
            int i = partition(array,l,h);
            quickSort(array,l,i-1);
            quickSort(array,i+1,h);
        }
    }

    public static int partition(Node [] array,int l,int h){
        int i = l;
        int j = h;
        Node tmp = array[i];
        do{
            while(array[j].key>=tmp.key && i<j){
                j--;
            }
            if( i<j)
                array[i++] = array[j];

            while(array[i].key<=tmp.key && i<j){
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
        quickSort(array,0,array.length-1);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + (i == array.length - 1 ? " \r\n" : " "));
        }
    }
}
