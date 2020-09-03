package introduction.to.algorithms.chapter1;

import util.testcase.sort.SortCase;

public class MergeSortTopDown {

    public static void sort(int[] array) {
        sort(array, 0, array.length - 1);
    }

    private static void sort(int[] array, int low, int high) {
        if(low < high) {
            sort(array, low, (low + high) / 2);
            sort(array,  (low + high) / 2 + 1, high);
            merge(array, low, (low + high) / 2, high);
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
        SortCase.test(MergeSortTopDown.class, 10, 0, 10);
    }
}
