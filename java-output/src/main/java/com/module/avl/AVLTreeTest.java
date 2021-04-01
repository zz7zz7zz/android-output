package com.module.avl;

public class AVLTreeTest {

    public static void main(String[] argv){

        AVLTree avlTree = new AVLTree();
//        int arr[]= {3,2,1,4,5,6,7,16,15,14,13,12,11,10,8,9};

//        int arr[]= {3,3,2,1};
//        int arr[]= {3,4,5};
        int arr[]= {3,2,1,4,5,6,7,16,15,14,13,12,11,10,8,9};

        insert(avlTree,arr);

        order(avlTree);

        search(avlTree,5);
        search(avlTree,6);
        max(avlTree);
        min(avlTree);
        height(avlTree);


        remove(avlTree,8);
        order(avlTree);

    }

    public static void insert(AVLTree avlTree,int arr[]){
        for (int i = 0;i<arr.length;i++){
            avlTree.insert(arr[i]);
        }
    }

    public static void order(AVLTree avlTree){

        avlTree.preOrder();

        avlTree.inOrder();

        avlTree.postOrder();
    }

    public static void search(AVLTree avlTree,int key){
        avlTree.search(key);
    }

    public static void max(AVLTree avlTree){
        avlTree.max();
    }

    public static void min(AVLTree avlTree){
        avlTree.min();
    }

    public static void remove(AVLTree avlTree,int data){
        avlTree.remove(data);
    }


    public static void height(AVLTree avlTree){
        avlTree.height();
    }

}
