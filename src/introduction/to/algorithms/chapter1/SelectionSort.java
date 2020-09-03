package introduction.to.algorithms.chapter1;

import util.Test;
import util.UnitTest;
import util.testcase.sort.SortCase;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Random;

public class SelectionSort {

    public static void sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[i]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        SortCase.test(SelectionSort.class, 10, 0, 10);
    }
}
