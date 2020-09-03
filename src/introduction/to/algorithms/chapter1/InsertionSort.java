package introduction.to.algorithms.chapter1;

import introduction.to.algorithms.chapter7.QuickSort;
import sort.Sort;
import util.Test;
import util.UnitTest;
import util.testcase.sort.SortCase;


import  java.util.Random;
import java.util.Arrays;

public class InsertionSort {

    public static void sort(int[] array) {

        for(int i = 1, j; i < array.length; i++) {
            // insert array[i] into sorted array[1...i-1]
            int key = array[i];
            for(j = i - 1; j > -1 ; j--) {
                if(key < array[j]) {
                    array[j + 1] = array[j];
                    // write traffic can be reduced
                    // array[j] = key;
                } else {
                    // insertion point has been found
                    break;
                }
            }
            array[j + 1] = key;

        }

    }

    public static void sortSimplify(int[] array) {
        for(int i = 1, j; i < array.length; i++) {
            // insert array[i] into sorted array[1...i-1]
            int key = array[i];
            for(j = i - 1; j > -1 && key < array[j]; j--) {
                array[j + 1] = array[j];
            }
            array[j + 1] = key;
        }
    }

    public static void main(String[] args) {
        SortCase.test(InsertionSort.class, 10, 0, 10);
    }
}
