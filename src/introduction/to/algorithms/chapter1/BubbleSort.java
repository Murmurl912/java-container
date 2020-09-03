package introduction.to.algorithms.chapter1;

import util.testcase.sort.SortCase;

public class BubbleSort {

    public static void sort(int[] array) {
        for(int i = 0; i < array.length; i++) {
            for(int j = 1; j < array.length; j++) {
                if(array[j - 1] > array[j]) {
                    int temp = array[j];
                    array[j] = array[j-1];
                    array[j - 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        SortCase.test(BubbleSort.class, 10, 0, 10);
    }
}
