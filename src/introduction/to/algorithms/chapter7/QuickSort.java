package introduction.to.algorithms.chapter7;

import util.Test;
import util.UnitTest;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

/**
 * general implementation of quick sort
 */
public class QuickSort {

    public static void sort(int[] array) {
        Objects.requireNonNull(array);
        shuffle(array);
        sort(array, 0, array.length - 1);
    }

    /**
     * partition array[start...end] by a selected key in array[start...end]
     * so that
     * array[start...index - 1] <= key <= array[index + 1...end]
     * @param array array to be partied
     * @param start start offset
     * @param end end offset
     * @return index of key
     */
    private static int partition(int[] array, int start, int end) {
        int key = array[end];
        int temp = Integer.MAX_VALUE;
        int index = start -1;

        for(int j = start; // index of elements less than key
            j < end; j++) {
            if(array[j] < key) { // found an element less than key
                index++; // increase index
                // swap that element
                temp = array[index];
                array[index] = array[j];
                array[j] = temp;
            }
        }

        // index is the bound of elements less than key
        // which indicates index + 1 is where key belongs in partitioned array
        array[end] = array[index + 1];
        array[index + 1] = key;
        return index + 1;
    }

    private static void sort(int[] array, int start, int end) {
        if(start < end) {
            int index = partition(array, start, end);
            sort(array, start, index - 1);
            sort(array, index + 1, end);
        }
    }

    /**
     * random partition may cause unstable rank
     * @param array array to partition
     * @param start start
     * @param end end
     * @return index
     */
    private static int randomPartition(int[] array, int start, int end) {
        int index = (int)(Math.random() * (start - end + 1)) + start;
        int temp = array[index];
        array[index] = array[end];
        array[end] = temp;
        return partition(array, start, end);
    }

    public static void shuffle(int[] array) {
        Random random = new Random();
        for(int i = array.length - 1; i > -1; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }
    }

    public static class Driver {

        public static void main(String[] args) {
            UnitTest.run(new Driver());
        }

        @Test(iteration = 100)
        public void sortTest() {
            Random random = new Random();
            int[] array = random.ints(20, 0, 10).toArray();
            int[] copy = Arrays.copyOf(array, array.length);
            QuickSort.sort(array);
            boolean result = isOrdered(array);

            if(!result) {
                System.err.println("Quick Sort Test Failed.");
            }
            System.out.println("Original Array: " + Arrays.toString(copy));
            System.out.println("Sorted Result: " + Arrays.toString(array));
        }

        public boolean isOrdered(int[] array) {
            for (int i = 1; i < array.length; i++) {
                if(array[i - 1] > array[i]) {
                    return false;
                }
            }
            return true;
        }

        @Test(iteration = 100)
        public void partitionTest() {
            Random random = new Random();
            int[] array = random.ints(20, 0, 10).toArray();

            int index = partition(array, 0, array.length - 1);
            boolean result = verifyPartition(array, 0, array.length - 1, index);
            if(!result) {
                System.err.println("Partition Test Failed.");
            }
            System.out.println("Partition Result: " + Arrays.toString(array));
            System.out.println("Index: " + index + ", Key: " + array[index]);
        }

        public boolean verifyPartition(int[] array, int start, int end, int index) {
            for (int i = start; i < end; i++) {
                if(i < index) {
                    if(array[i] > array[index]) {
                        return false;
                    }
                }

                if(i > index) {
                    if(array[i] < array[index]) {
                        return false;
                    }
                }
            }

            return true;
        }
    }
}
