package com.module.sort;

public class Node<T>{
    public int key;
    public T value;

    public Node(int key, T value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}