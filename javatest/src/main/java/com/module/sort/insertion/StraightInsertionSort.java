package com.module.sort.insertion;

public class StraightInsertionSort {

    public static void straightInsertionSort(int [] array){
        for(int i=1;i<array.length;i++)
        {
            if(array[i]<array[i-1])
            {
                int tmp=array[i];
                int j;
                for (j =i-1; j >=0; --j)
                {
                    if(array[j]>tmp)//向后移动
                    {
                        array[j+1] = array[j];
                    }
                    else
                    {
                        break;
                    }
                }
                array[j+1]=tmp;
            }
            else
            {
                //插入即可
            }
        }
    }

    //------------------------------------------------
    public static void main(String[] args) {
        int[] array = {36,25,48,12,65,43,20,58};

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
