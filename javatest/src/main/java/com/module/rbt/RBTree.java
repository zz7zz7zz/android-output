package com.module.rbt;

//动画演示
//https://www.cs.usfca.edu/~galles/visualization/AVLtree.html

//https://www.cnblogs.com/skywang12345/p/3624343.html

import static com.module.rbt.RBTNode.BLACK;

public class RBTree {

    public static final boolean isDebug = true;

    RBTNode mRoot = null;

    public RBTree() {

    }

    public RBTNode getParent(RBTNode node){
        return null != node ? node.parent : null;
    }

    public void setParent(RBTNode node,RBTNode parent){
        if(null != node){
            node.parent = parent;
        }
    }

    public boolean getColor(RBTNode node){
        return null != node ? node.color : BLACK;
    }

    public void setColor(RBTNode node,boolean color){
        if(null != node){
            node.color = color;
        }
    }

    public boolean isBlack(RBTNode node){
        return null != node &&  node.color == BLACK;
    }

    public boolean isRed(RBTNode node){
        return null != node && node.color == RBTNode.RED;
    }

    public void setRed(RBTNode node){
        if(null != node){
            node.color = RBTNode.RED;
        }
    }

    public void setBlack(RBTNode node){
        if(null != node){
            node.color = BLACK;
        }
    }


    //---------------------------------- 旋转 ----------------------------------
    /*
     * 对红黑树的节点(x)进行左旋转
     *
     * 左旋示意图(对节点x进行左旋)：
     *      px                              px
     *     /                               /
     *    x                               y
     *   /  \      --(左旋)-.            /  \                #
     *  lx   y                          x    ry
     *     /   \                       /  \
     *    ly   ry                     lx   ly
     *
     *
     */
    public void leftRotate(RBTNode x){

        if(isDebug){
            System.out.println("---左旋---");
        }

        // 设置x的右孩子为y
        RBTNode y = x.right;


        // 将 “y的左孩子” 设为 “x的右孩子”；
        // 如果y的左孩子非空，将 “x” 设为 “y的左孩子的父亲”
        x.right = y.left;
        if(null != y.left){
            y.left.parent = x;
        }

        // 将 “x的父亲” 设为 “y的父亲”
        y.parent = x.parent;

        if (x.parent == null) {
            this.mRoot = y;            // 如果 “x的父亲” 是空节点，则将y设为根节点
        } else {
            if (x.parent.left == x)
                x.parent.left = y;    // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
            else
                x.parent.right = y;    // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
        }

        // 将 “x” 设为 “y的左孩子”
        y.left = x;
        // 将 “x的父节点” 设为 “y”
        x.parent = y;

    }

    /*
     * 对红黑树的节点(y)进行右旋转
     *
     * 右旋示意图(对节点y进行左旋)：
     *            py                               py
     *           /                                /
     *          y                                x
     *         /  \      --(右旋)-.             /  \
     *        x   ry                          lx   y
     *       / \                                  / \
     *      lx  rx                               rx  ry
     *
     */
    public void rightRotate(RBTNode y){

        if(isDebug){
            System.out.println("---右旋---");
        }

        // 设置x是当前节点的左孩子。
        RBTNode x = y.left;

        // 将 “x的右孩子” 设为 “y的左孩子”；
        // 如果"x的右孩子"不为空的话，将 “y” 设为 “x的右孩子的父亲”
        y.left = x.right;
        if (x.right != null)
            x.right.parent = y;

        // 将 “y的父亲” 设为 “x的父亲”
        x.parent = y.parent;

        if (y.parent == null) {
            this.mRoot = x;            // 如果 “y的父亲” 是空节点，则将x设为根节点
        } else {
            if (y == y.parent.right)
                y.parent.right = x;    // 如果 y是它父节点的右孩子，则将x设为“y的父节点的右孩子”
            else
                y.parent.left = x;    // (y是它父节点的左孩子) 将x设为“x的父节点的左孩子”
        }

        // 将 “y” 设为 “x的右孩子”
        x.right = y;

        // 将 “y的父节点” 设为 “x”
        y.parent = x;


    }

    //---------------------------------- 插入 ----------------------------------
    public RBTNode insert(int key){
        if(isDebug){
            System.out.println("insert key " + key);
        }

        RBTNode insertNode = new RBTNode(key,RBTNode.RED,null,null,null);
        mRoot = insert(insertNode);
        return mRoot;
    }

    //将一个节点插入到红黑树中，需要执行哪些步骤呢？
    // 首先，将红黑树当作一颗二叉查找树，将节点插入；
    // 然后，将节点着色为红色；
    // 最后，通过"旋转和重新着色"等一系列操作来修正该树，使之重新成为一颗红黑树
    private RBTNode insert( RBTNode node){

        if (mRoot == null){
            if(null != node){
                mRoot = node;
                mRoot.color = BLACK;
            }
            return mRoot;
        }

        RBTNode y = null;//父节点
        RBTNode x = mRoot;

        // 1. 将红黑树当作一颗二叉查找树，将节点添加到二叉查找树中。
        while (x != null){
            y = x;
            if(node.key < x.key){
                x = x.left;
            }else if(node.key > x.key){
                x = x.right;
            }else{
                if(isDebug){
                    System.out.println("添加失败：不允许添加相同的节点！");
                }
                return mRoot;
            }
        }

        node.parent = y;
        if(null != y){
            if(node.key < y.key){
                y.left = node;
            }else if(node.key > y.key){
                y.right = node;
            }
        }else{
            //当然这里不可能执行的到，因为上面已经判断过根节点是否为空了
            mRoot = node;
            mRoot.color = BLACK;
            return mRoot;
        }

        // 2. 设置节点的颜色为红色
        node.color = RBTNode.RED;

        // 3. 将它重新修正为一颗二叉查找树
        insertFixUp(node);

        return mRoot;
    }

    private void insertFixUp(RBTNode node){

        RBTNode parent;

        // 若“父节点存在，并且父节点的颜色是红色”，
        while (null != (parent = node.getParent()) && isRed(parent)){

            RBTNode gparent = parent.getParent();//子，父结点都是红色，一定有更早的祖先结点

            //若“父节点”是“祖父节点的左孩子”
            if(gparent.left == parent){
                // Case 1条件：叔叔节点是红色
                RBTNode uncleNode = gparent.right;
                if(null != uncleNode && isRed(uncleNode)){
                    setBlack(uncleNode);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                // Case 2条件：叔叔是黑色，且当前节点是右孩子
                if(parent.right == node){
                    leftRotate(parent);

                    RBTNode tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3条件：叔叔是黑色，且当前节点是左孩子。
                setBlack(parent);
                setBlack(gparent);
                rightRotate(gparent);

            }else{//若“z的父节点”是“z的祖父节点的右孩子”
                // Case 1条件：叔叔节点是红色
                RBTNode uncleNode = gparent.left;
                if(null != uncleNode && isRed(uncleNode)){
                    setBlack(uncleNode);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                // Case 2条件：叔叔是黑色，且当前节点是右孩子
                if(parent.left == node){
                    rightRotate(parent);

                    RBTNode tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3条件：叔叔是黑色，且当前节点是左孩子。
                setBlack(parent);
                setBlack(gparent);
                leftRotate(gparent);
            }
        }

        // 将根节点设为黑色
        setBlack(mRoot);
    }



    //---------------------------------- 搜索 ----------------------------------
    public RBTNode search(int key){
        RBTNode ret =  search(mRoot,key);
        if(isDebug){
            System.out.println("search key "+key+" "+ (ret != null ? "成功":"失败"));
        }
        return ret;
    }

    private RBTNode search(RBTNode node, int key){
        if(node == null){
            return null;
        }

        if(key < node.key){
            return search(node.left,key);
        }else if(key > node.key){
            return search(node.right,key);
        }
        return node;
    }

    //---------------------------------- 删除 ----------------------------------

    public void remove(int key){

        RBTNode node;

        if ((node = search(mRoot, key)) != null)
            remove(node);
    }

    public void remove(RBTNode node){

        RBTNode child;
        RBTNode parent;
        boolean color;

        if(null != node.left && null != node.right){

            // 被删节点的后继节点。(称为"取代节点")
            // 用它来取代"被删节点"的位置，然后再将"被删节点"去掉。
            RBTNode replace = node;

            // 获取后继节点
            replace = replace.right;
            while (replace.left != null)
                replace = replace.left;

            // "node节点"不是根节点(只有根节点不存在父节点)
            if (getParent(node)!=null) {
                if (getParent(node).left == node)
                    getParent(node).left = replace;
                else
                    getParent(node).right = replace;
            } else {
                // "node节点"是根节点，更新根节点。
                this.mRoot = replace;
            }

            // child是"取代节点"的右孩子，也是需要"调整的节点"。
            // "取代节点"肯定不存在左孩子！因为它是一个后继节点。
            child = replace.right;
            parent = getParent(replace);
            // 保存"取代节点"的颜色
            color = getColor(replace);

            // "被删除节点"是"它的后继节点的父节点"
            if (parent == node) {
                parent = replace;
            } else {
                // child不为空
                if (child!=null)
                    setParent(child, parent);
                parent.left = child;

                replace.right = node.right;
                setParent(node.right, replace);
            }

            replace.parent = node.parent;
            replace.color = node.color;
            replace.left = node.left;
            node.left.parent = replace;

            if (color == BLACK)
                removeFixUp(child, parent);

            node = null;


            return;
        }

        child = null != node.left ? node.left : node.right;
        parent = node.parent;
        // 保存"取代节点"的颜色
        color = node.color;

        if(null != child){
            child.parent = parent;
        }

       // "node节点"不是根节点
        if(null != parent){
            if(parent.left == node){
                parent.left = child;
            }else{
                parent.right = child;
            }
        }else{
            mRoot = child;
        }

        if(color == BLACK){
            removeFixUp(child, parent);
        }
        node = null;
    }

    /*
     * 红黑树删除修正函数
     *
     * 在从红黑树中删除插入节点之后(红黑树失去平衡)，再调用该函数；
     * 目的是将它重新塑造成一颗红黑树。
     *
     * 参数说明：
     *     node 待修正的节点
     */
    private void removeFixUp(RBTNode node, RBTNode parent) {
        RBTNode other;

        while ((node==null || isBlack(node)) && (node != this.mRoot)) {
            if (parent.left == node) {
                other = parent.right;
                if (isRed(other)) {
                    // Case 1: x的兄弟w是红色的
                    setBlack(other);
                    setRed(parent);
                    leftRotate(parent);
                    other = parent.right;
                }

                if ((other.left==null || isBlack(other.left)) &&
                        (other.right==null || isBlack(other.right))) {
                    // Case 2: x的兄弟w是黑色，且w的俩个孩子也都是黑色的
                    setRed(other);
                    node = parent;
                    parent = getParent(node);
                } else {

                    if (other.right==null || isBlack(other.right)) {
                        // Case 3: x的兄弟w是黑色的，并且w的左孩子是红色，右孩子为黑色。
                        setBlack(other.left);
                        setRed(other);
                        rightRotate(other);
                        other = parent.right;
                    }
                    // Case 4: x的兄弟w是黑色的；并且w的右孩子是红色的，左孩子任意颜色。
                    setColor(other, getColor(parent));
                    setBlack(parent);
                    setBlack(other.right);
                    leftRotate(parent);
                    node = this.mRoot;
                    break;
                }
            } else {

                other = parent.left;
                if (isRed(other)) {
                    // Case 1: x的兄弟w是红色的
                    setBlack(other);
                    setRed(parent);
                    rightRotate(parent);
                    other = parent.left;
                }

                if ((other.left==null || isBlack(other.left)) &&
                        (other.right==null || isBlack(other.right))) {
                    // Case 2: x的兄弟w是黑色，且w的俩个孩子也都是黑色的
                    setRed(other);
                    node = parent;
                    parent = getParent(node);
                } else {

                    if (other.left==null || isBlack(other.left)) {
                        // Case 3: x的兄弟w是黑色的，并且w的左孩子是红色，右孩子为黑色。
                        setBlack(other.right);
                        setRed(other);
                        leftRotate(other);
                        other = parent.left;
                    }

                    // Case 4: x的兄弟w是黑色的；并且w的右孩子是红色的，左孩子任意颜色。
                    setColor(other, getColor(parent));
                    setBlack(parent);
                    setBlack(other.left);
                    rightRotate(parent);
                    node = this.mRoot;
                    break;
                }
            }
        }

        if (node!=null)
            setBlack(node);
    }

    //---------------------------------- 遍历 ----------------------------------
    public void preOrder(){
        if(isDebug){
            System.out.println("preOrder -----------start-----------");
        }
        preOrder(mRoot);
        if(isDebug){
            System.out.println("preOrder -----------end-----------");
        }
    }

    private void preOrder(RBTNode node){
        if(null != node){
            if(isDebug){
                System.out.println("preOrder Node " + node.key);
            }
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    public void inOrder(){
        if(isDebug){
            System.out.println("inOrder -----------start-----------");
        }
        inOrder(mRoot);
        if(isDebug){
            System.out.println("inOrder -----------end-----------");
        }
    }

    private void inOrder(RBTNode node){
        if(null != node){
            inOrder(node.left);
            if(isDebug){
                System.out.println("inOrder Node " + node.key);
            }
            inOrder(node.right);
        }
    }

    public void postOrder(){
        if(isDebug){
            System.out.println("postOrder -----------start-----------");
        }
        postOrder(mRoot);
        if(isDebug){
            System.out.println("postOrder -----------end-----------");
        }
    }

    private void postOrder(RBTNode node){
        if(null != node){
            postOrder(node.left);
            postOrder(node.right);
            if(isDebug) {
                System.out.println("postOrder Node " + node.key);
            }
        }
    }

    //---------------------------------- 最大值/最小值 ----------------------------------
    public RBTNode max(){

        RBTNode ret = max(mRoot);

        if(isDebug) {
            System.out.println("max Node " + ((null != ret) ? ret.key : "Null"));
        }
        return ret;
    }

    public RBTNode max(RBTNode node){
        if(null == node){
            return null;
        }
        while (null != node.right){
            node = node.right;
        }
        return node;
    }

    public RBTNode min(){

        RBTNode ret = min(mRoot);

        if(isDebug) {
            System.out.println("min Node " + ((null != ret) ? ret.key : "Null"));
        }
        return ret;
    }

    public RBTNode min(RBTNode node){
        if(null == node){
            return null;
        }
        while (null != node.left){
            node = node.left;
        }
        return node;
    }
}
