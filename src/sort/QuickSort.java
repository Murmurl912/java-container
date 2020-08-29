package sort;

import java.util.Arrays;

public interface QuickSort extends Sort {

    @Override
    default public void sort(int[] array) {
        sort(array, 0, array.length - 1);
    }

    private static void sort(int[] array, int low, int high) {
        if(high <= low) {
            return;
        }

        int middle = partition(array, low, high);
        sort(array, low, middle - 1);
        sort(array,  middle + 1, high);
    }

    private static int partition(int[] array, int low, int high) {
        int key = array[low];

        for(int i = low, j = high + 1;;) {
            // find element larger than key
            while (array[++i] < key) {
                if(i == high) {
                    break;
                }
            }
            // array[i] either larger than key
            // or i == high
            // if i == high then j = high


            // find element less than key
            while (key < array[--j]) {
                if(j == low) {
                    break;
                }
            }
            // array[j] either less than key
            // or j == low
            // if j == low than i == low

            if(i >= j) {
                array[low] = array[j];
                array[j] = key;
                return j;
            }

            // swap
            array[low] = array[i];
            array[i] = array[j];
            array[j] = array[low];
        }
    }

    private static int anotherPartition(int[] array, int low, int high) {
        int key = array[low];
        int less = low + 1;
        int lager = high;
        while (less < lager) {

        }
        return -1;
    }
}
