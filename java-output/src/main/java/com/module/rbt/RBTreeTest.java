package com.module.rbt;

import com.module.rbt.RBTree;

public class RBTreeTest {

    public static void main(String[] argv){

        RBTree rb = new RBTree();
//        int arr[]= {3,2,1,4,5,6,7,16,15,14,13,12,11,10,8,9};

//        int arr[]= {3,3,2,1};
//        int arr[]= {3,4,5};
        int arr[]= {10, 40, 30, 60, 90, 70, 20, 50, 80};

        insert(rb,arr);

        order(rb);

        search(rb,5);
        search(rb,6);
        max(rb);
        min(rb);


//        remove(rb,8);
//        order(rb);

    }

    public static void insert(RBTree rbTree,int arr[]){
        for (int i = 0;i<arr.length;i++){
            rbTree.insert(arr[i]);
        }
    }

    public static void order(RBTree rbTree){

        rbTree.preOrder();

        rbTree.inOrder();

        rbTree.postOrder();
    }

    public static void search(RBTree rbTree,int key){
        rbTree.search(key);
    }

    public static void max(RBTree rbTree){
        rbTree.max();
    }

    public static void min(RBTree rbTree){
        rbTree.min();
    }

    public static void remove(RBTree rbTree,int data){
        rbTree.remove(data);
    }


}
