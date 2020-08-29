package sort;

import java.util.Arrays;

public interface MergeSort extends Sort {

    @Override
    default void sort(int[] array) {
        int[] aux = Arrays.copyOf(array, array.length);

        for(int size = 1; size < array.length; size = size + size) {
            for(int low = 0; low < array.length - size; low += size + size) {
                merge(array, aux, low,
                        low + size - 1,
                        Math.min(low + size + size - 1, array.length - 1));
            }
        }

    }

    private static void merge(int[] array,
                              int[] aux,
                              int low,
                              int middle,
                              int high) {
        if (high + 1 - low >= 0)
            System.arraycopy(array, low, aux, low, high + 1 - low);

        // merge array[low...middle] and array[middle+1...high]
        for (int i = low, j = middle + 1, k = low;
             k <= high;
             k++) {
            if(i > middle) {
                // left array has been exhausted
                array[k] =  aux[j++];
            } else if(j > high) {
                // right array has been exhausted
                array[k] = aux[i++];
            } else if(aux[j] < aux[i]) {
                array[k] = aux[j++];
            } else {
                array[k] = aux[i++];
            }
        }
    }

}