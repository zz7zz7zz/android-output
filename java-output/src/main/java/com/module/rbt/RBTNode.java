package com.module.rbt;

public class RBTNode {


    public static final boolean RED   = false;
    public static final boolean BLACK = true;

    public int key;//关键字

    public boolean color;//颜色，新增结点默认为红色

    public RBTNode left;//左孩子
    public RBTNode right;//右孩子

    public RBTNode parent;//双亲结点

    public RBTNode(int key, boolean color, RBTNode left, RBTNode right, RBTNode parent) {
        this.key = key;
        this.color = color;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }


    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public RBTNode getParent() {
        return parent;
    }

    public void setParent(RBTNode parent) {
        this.parent = parent;
    }
}


