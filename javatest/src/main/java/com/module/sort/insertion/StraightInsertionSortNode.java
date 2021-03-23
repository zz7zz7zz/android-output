package com.module.sort.insertion;

import com.module.sort.Node;

public class StraightInsertionSortNode {

    public static void straightInsertionSort(Node [] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i].key < array[i - 1].key) {
                Node tmp = array[i];
                int j;
                for (j = i - 1; j >= 0; --j) {
                    if (array[j].key > tmp.key)//向后移动
                    {
                        array[j + 1] = array[j];
                    } else {
                        break;
                    }
                }
                array[j + 1] = tmp;
            } else {
                //插入即可
            }
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
        straightInsertionSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + (i == array.length - 1 ? " \r\n" : " "));
        }
    }
}
