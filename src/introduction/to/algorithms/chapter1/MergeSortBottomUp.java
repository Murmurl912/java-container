package introduction.to.algorithms.chapter1;

import util.testcase.sort.SortCase;

public class MergeSortBottomUp {

    public static void sort(int[] array) {

        for(int size = 1; size < array.length; size = size + size) {
            // sub problem size

            for(int low = 0; low < array.length - size; low += size + size) {
                // merge sub problem
                merge(array, low,
                        low + size - 1,
                        Math.min(low + size + size - 1, array.length - 1));
            }
        }
    }

    private static void merge(int[] array, int low, int middle, int high) {
        int[] a = new int[middle - low + 2];
        int[] b = new int[high - middle + 1];

        if (a.length - 1 >= 0)
            System.arraycopy(array, low, a, 0, a.length - 1);

        if(b.length - 1 >= 0)
            System.arraycopy(array, middle + 1, b, 0, b.length - 1);


        a[a.length - 1] = b[b.length - 1] = Integer.MAX_VALUE;

        for (int i = low, j = 0, k = 0; i <= high; i++) {
            if(a[j] <= b[k]) {
                array[i] = a[j++];
            } else {
                array[i] = b[k++];
            }
        }
    }


    public static void main(String[] args) {
        SortCase.test(MergeSortBottomUp.class, 10, 0, 10);
    }

}
