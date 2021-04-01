package com.module.sort.selectsort;

import com.module.sort.Node;

public class StraightSelectSortNode {

    /*在要排序的一组数中，选出最小（或者最大）的一个数与第1个位置的数交换；然后在剩下的数当中再找最小（或者最大）的与第2个位置的数交换，依次类推，直到第n-1个元素（倒数第二个数）和第n个元素（最后一个数）比较为止。

	 简单选择排序是不稳定的排序。
　   时间复杂度：T(n)=O(n2)。
    */
    public static void straightSelectSort(Node[] array){
        for (int i = 0; i < array.length; i++)
        {
            int minIndex=i;
            for (int j = i+1; j < array.length; j++)
            {
                if(array[j].key<array[minIndex].key)
                {
                    minIndex=j;
                }
            }

            if(minIndex != i){
                Node minValue=array[minIndex];
                array[minIndex]=array[i];
                array[i]=minValue;
            }
        }
    }

    //优化后的简单排序
    /*
     * 简单选择排序，每趟循环只能确定一个元素排序后的定位。我们可以考虑改进为每趟循环确定两个元素（当前趟最大和最小记录）的位置,从而减少排序所需的循环次数。
     * 改进后对n个数据进行排序，最多只需进行[n/2]趟循环即可。具体实现如下：
     */
    public static void straightSelectSort2(Node []array)
    {
        for (int i = 0; i < array.length/2; i++)
        {
            int minIndex=i;
            int maxIndex=i;

            for (int j = i+1; j < array.length-i; j++)
            {
                if(array[j].key<array[minIndex].key)
                {
                    minIndex=j;
                    continue;
                }

                if(array[j].key >array[maxIndex].key)
                {
                    maxIndex=j;
                }
            }


            if(minIndex != i){
                Node minValue=array[minIndex];
                array[minIndex]=array[i];
                array[i]=minValue;
            }

            if(maxIndex != i){
                Node maxValue=array[maxIndex];
                array[maxIndex]=array[array.length-1-i];
                array[array.length-1-i]=maxValue;
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
        straightSelectSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + (i == array.length - 1 ? " \r\n" : " "));
        }

        System.out.println("#####################################");
        straightSelectSort2(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + (i == array.length - 1 ? " \r\n" : " "));
        }
    }

}
