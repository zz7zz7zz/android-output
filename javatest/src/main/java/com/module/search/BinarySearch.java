package com.module.search;

public class BinarySearch {

    public static int binarySearch(int[] array,int key){
        int low = 0;
        int high = array.length -1;
        int middle = 0;

        while (low <= high){
//            middle = (low + high)/2;
            middle = (low + high)>>>1;

            if(array[middle]>key){
                high = middle-1;
            }else if(array[middle]<key){
                low = middle + 1;
            }else {
                return middle;
            }
        }
        return  -1;
    }

    // This is Arrays.binarySearch(), but doesn't do any argument validation.
    static int binarySearch(int[] array, int size, int value) {
        int lo = 0;
        int hi = size - 1;

        while (lo <= hi) {
            final int mid = (lo + hi) >>> 1;
            final int midVal = array[mid];

            if (midVal < value) {
                lo = mid + 1;
            } else if (midVal > value) {
                hi = mid - 1;
            } else {
                return mid;  // value found
            }
        }
        return ~lo;  // value not present
    }

    //------------------------------------------------
    public static void main(String[] args) {
        int[] array = {1,2,3,4,5,6,7,8,9,10};
        System.out.println("---- "+binarySearch(array,10));

    }
}
