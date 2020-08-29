package sort;

import container.impl.Array;
import util.Test;
import util.UnitTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class Driver {

    public static void main(String[] args) {
        System.out.println(Driver.class.getResource("/"));
                UnitTest.run(new Driver());
    }

    @Test
    public void testInsertionSort() {
        int[] array = generateArray();
        int[] clone = Arrays.copyOf(array, array.length);
        Arrays.sort(clone);

        InsertionSort sort = new InsertionSort() {};
        sort.sort(array);

        boolean pass = Arrays.equals(array, clone);
        System.out.println("Insertion Sort Test Result: " + pass);
        if(!pass) {
            throw new RuntimeException("Test Failed!");
        }
    }

    @Test
    public void testSelectionSort() {
        int[] array = generateArray();
        int[] clone = Arrays.copyOf(array, array.length);
        Arrays.sort(clone);

        SelectionSort sort = new SelectionSort() {};
        sort.sort(array);

        boolean pass = Arrays.equals(array, clone);
        System.out.println("Selection Sort Test Result: " + pass);
        if(!pass) {
            throw new RuntimeException("Test Failed!");
        }
    }

    @Test
    public void testBubbleSort() {
        int[] array = generateArray();
        int[] clone = Arrays.copyOf(array, array.length);
        Arrays.sort(clone);

        BubbleSort sort = new BubbleSort() {};
        sort.sort(array);

        boolean pass = Arrays.equals(array, clone);
        System.out.println("Bubble Sort Test Result: " + pass);
        if(!pass) {
            throw new RuntimeException("Test Failed!");
        }
    }

    @Test
    public void testShellSort() {
        int[] array = generateArray();
        int[] clone = Arrays.copyOf(array, array.length);
        Arrays.sort(clone);

        ShellSort sort = new ShellSort() {};
        sort.sort(array);

        boolean pass = Arrays.equals(array, clone);
        System.out.println("Shell Sort Test Result: " + pass);
        if(!pass) {
            throw new RuntimeException("Test Failed!");
        }
    }

    @Test
    public void testMergeSort() {
        int[] array = generateArray();
        int[] clone = Arrays.copyOf(array, array.length);
        Arrays.sort(clone);

        MergeSort sort = new MergeSort() {};
        sort.sort(array);

        boolean pass = Arrays.equals(array, clone);
        System.out.println("Merge Sort Test Result: " + pass);
        if(!pass) {
            throw new RuntimeException("Test Failed!");
        }
    }

    @Test
    public void testQuickSort() {
        int[] array = generateArray();
        int[] clone = Arrays.copyOf(array, array.length);
        Arrays.sort(clone);

        QuickSort sort = new QuickSort() {};
        sort.sort(array);

        boolean pass = Arrays.equals(array, clone);
        System.out.println("Quick Sort Test Result: " + pass);
        if(!pass) {
            throw new RuntimeException("Test Failed!");
        }
    }

    private int[] generateArray() {
        Random random = new Random();
        return random.ints(10, 0, 10).toArray();
    }
}
