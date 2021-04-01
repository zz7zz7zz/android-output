package com.module.avl;

//动画演示
//https://www.cs.usfca.edu/~galles/visualization/AVLtree.html

//https://www.cnblogs.com/skywang12345/p/3577479.html
//https://www.jianshu.com/p/7d318791bf25
//https://www.jianshu.com/p/2621b04db718
//https://www.zhihu.com/question/31032841/answer/668144642

public class AVLTree {

    public static final boolean isDebug = true;

    AVLNode mRoot = null;

    public AVLTree() {

    }

    public int height(AVLNode node){
        return null == node ? 0 : node.height;
    }

    public int height(){
        int ret= height(mRoot);
        if(isDebug){
            System.out.println("height " +ret);
        }
        return ret;
    }

    //---------------------------------- 旋转 ----------------------------------
    //LL 左-左型：做右旋。

    /*
            k2
           / \
          /   \
         k1    Z
        / \
       /   \
      X     Y

           ---->

            k1
           / \
          /   \
         X    k2
             /  \
            /    \
           Y      Z
 */
    //1.k1变成根节点
    //2.因为k1的右子数目右旋后会与k2进行碰撞，所以将k1的右子树变成k2的左子数
    //3.k2变成k1的右子树
    //4.重新调整当前节点的高度

    public AVLNode LL_RightRotate(AVLNode k2){

        if(isDebug){
            System.out.println("LL 左-左型：做右旋");
        }


        AVLNode k1 = k2.left;

        k2.left = k1.right;
        k1.right = k2;

        k2.height = Math.max(height(k2.left),height(k2.right)) + 1;
//        k1.height = Math.max(height(k1.left),height(k1.right)) + 1;
        k1.height = Math.max(height(k1.left),k2.height)+1;

        if(isDebug){
            System.out.println(String.format("height k1 %d, k2 %d" ,k1.height,k2.height));
        }

        return k1;
    }

    //RR 右-右型：做左旋。
            /*
                 k1
                / \
               /   \
              X     k2
                   / \
                  /   \
                 Y    Z

            ---->

                   k2
                   / \
                  /   \
                 k1    Z
                / \
               /   \
              X     Y
         */
    //1.k2变成根节点
    //2.因为k2的左子数目左旋后会与k1进行碰撞，所以将k2的左子树变成k1的右子树
    //3.k1变成k2的左子树
    //4.重新调整当前节点的高度
    public AVLNode RR_LeftRotate(AVLNode k1){

        if(isDebug){
            System.out.println("RR 右-右型：做左旋。");
        }

        AVLNode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;

        k1.height = Math.max(height(k1.left),height(k1.right))+1;
//        k2.height = Math.max(height(k2.left),height(k2.right))+1;
        k2.height = Math.max(height(k2.left),k1.height)+1;
        return k2;
    }

    //LR 左-右型：先做左旋，后做右旋。
    public AVLNode LR_LeftRightRotate(AVLNode k3){

        if(isDebug){
            System.out.println("LR 左-右型：先做左旋，后做右旋。");
        }

        k3.left = RR_LeftRotate(k3.left);
        return LL_RightRotate(k3);
    }

    //RL 右-左型：先做右旋，再做左旋。
    public AVLNode RL_RightLeftRotate(AVLNode k1){

        if(isDebug){
            System.out.println("RL 右-左型：先做右旋，再做左旋。");
        }

        k1.right = LL_RightRotate(k1.right);
        return RR_LeftRotate(k1);
    }

    //---------------------------------- 插入 ----------------------------------
    public AVLNode insert(int key){
        if(isDebug){
            System.out.println("insert key " + key);
        }

        mRoot = insert(mRoot,key);
        return mRoot;
    }

    private AVLNode insert(AVLNode node,int key){
        if(node == null){//作为根节点

            node = new AVLNode();
            node.key = key;
            node.left = node.right = null;

        }else{

            if(key < node.key){//左子树中进行插入

                node.left = insert(node.left,key);

                //如果左孩子的高度比右孩子大2
                if(height(node.left) - height(node.right) >=2){
                    if(key < node.left.key){//LL
                        node = LL_RightRotate(node);
                    }else {//LR
                        node = LR_LeftRightRotate(node);
                    }
                }

            }else if(key > node.key){//右子树中进行插入

                node.right = insert(node.right,key);

                //如果右孩子的高度比左孩子大2
                if(height(node.right) - height(node.left) >=2){
                    if(key > node.right.key){//RR
                        node = RR_LeftRotate(node);
                    }else {//RL
                        node = RL_RightLeftRotate(node);
                    }
                }

            }else{
                if(isDebug){
                    System.out.println("添加失败：不允许添加相同的节点！");
                }
            }
        }

        node.height = Math.max(height(node.left),height(node.right)) + 1;
        return node;
    }

    //---------------------------------- 搜索 ----------------------------------
    public AVLNode search(int key){
        AVLNode ret =  search(mRoot,key);
        if(isDebug){
            System.out.println("search key "+key+" "+ (ret != null ? "成功":"失败"));
        }
        return ret;
    }

    private AVLNode search(AVLNode node,int key){
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

    public AVLNode remove(int key){
        return mRoot = remove(mRoot,key);
    }

    //返回根节点
    public AVLNode remove(AVLNode node,int key){
        if(node == null){
            return null;
        }

        if(key < node.key){
            node.left =  remove(node.left,key);

            // 删除节点后，若AVL树失去平衡，则进行相应的调节。
            if(height(node.right) - height(node.left)>=2){
                AVLNode r = node.right;
                if(height(r.left) > height(r.right)){
                    node = RL_RightLeftRotate(node);
                }else{
                    node = RR_LeftRotate(node);
                }
            }


        }else if(key > node.key){
            node.right = remove(node.right,key);

            // 删除节点后，若AVL树失去平衡，则进行相应的调节。
            if(height(node.left) - height(node.right)>=2){
                AVLNode l = node.left;
                if(height(l.right) > height(l.left)){
                    node = LR_LeftRightRotate(node);
                }else{
                    node = LL_RightRotate(node);
                }
            }
        }else{

            /*
            对于二叉查找树，我们都知道它的删除要分三种情况：
            - 删除的节点为叶子节点
            - 删除的节点左子树或右子树有一个为空
            - 删除的节点有两个子树
             */

            if(null == node.left || null == node.right){
                node = null != node.left ? node.left : node.right;
            }else{
                if(height(node.left)>height(node.right)){

                    // 如果tree的左子树比右子树高；
                    // 则(01)找出tree的左子树中的最大节点
                    //   (02)将该最大节点的值赋值给tree。
                    //   (03)删除该最大节点。
                    // 这类似于用"tree的左子树中最大节点"做"tree"的替身；
                    // 采用这种方式的好处是：删除"tree的左子树中最大节点"之后，AVL树仍然是平衡的。

                    AVLNode max = max(node.left);
                    node.key = max.key;
                    node.left = remove(node.left,max.key);
                }else{

                    // 如果tree的左子树不比右子树高(即它们相等，或右子树比左子树高1)
                    // 则(01)找出tree的右子树中的最小节点
                    //   (02)将该最小节点的值赋值给tree。
                    //   (03)删除该最小节点。
                    // 这类似于用"tree的右子树中最小节点"做"tree"的替身；
                    // 采用这种方式的好处是：删除"tree的右子树中最小节点"之后，AVL树仍然是平衡的。
                    AVLNode min = min(node.right);
                    node.key = min.key;
                    node.right = remove(node.right, min.key);
                }
            }
        }
        return node;
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

    private void preOrder(AVLNode node){
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

    private void inOrder(AVLNode node){
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

    private void postOrder(AVLNode node){
        if(null != node){
            postOrder(node.left);
            postOrder(node.right);
            if(isDebug) {
                System.out.println("postOrder Node " + node.key);
            }
        }
    }

    //---------------------------------- 最大值/最小值 ----------------------------------
    public AVLNode max(){

        AVLNode ret = max(mRoot);

        if(isDebug) {
            System.out.println("max Node " + ((null != ret) ? ret.key : "Null"));
        }
        return ret;
    }

    public AVLNode max(AVLNode node){
        if(null == node){
            return null;
        }
        while (null != node.right){
            node = node.right;
        }
        return node;
    }

    public AVLNode min(){

        AVLNode ret = min(mRoot);

        if(isDebug) {
            System.out.println("min Node " + ((null != ret) ? ret.key : "Null"));
        }
        return ret;
    }

    public AVLNode min(AVLNode node){
        if(null == node){
            return null;
        }
        while (null != node.left){
            node = node.left;
        }
        return node;
    }
}
