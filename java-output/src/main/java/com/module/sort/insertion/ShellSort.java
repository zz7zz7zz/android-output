package com.module.sort.insertion;

public class ShellSort {

    public static void shellSort(int []array )
    {
        int d=array.length;
        while(true)
        {
            d=d/2;

            for (int x = 0; x < d; x++)
            {
                for (int i = x+d; i < array.length; i=i+d)
                {

                    if(array[i]<array[i-d])
                    {
                        int tmp=array[i];
                        int j;
                        for ( j = i-d; j >=0; j=j-d)
                        {
                            if(array[j]>tmp)//向后移动
                            {
                                array[j+d] = array[j];
                            }
                            else
                            {
                                break;
                            }
                        }
                        array[j+d]=tmp;
                    }
                }
            }

            if(d==1)
            {
                break;
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
        shellSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + (i == array.length - 1 ? " \r\n" : " "));
        }
    }
}
