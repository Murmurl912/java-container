package introduction.to.algorithms.chapter9;

import introduction.to.algorithms.chapter7.QuickSort;
import util.Test;
import util.UnitTest;

import java.util.Arrays;
import java.util.Random;

public class Counter {

    private static int partition(int[] array, int start, int end) {
        int index = (int) (Math.random() * (end - start + 1)) + start;
        int temp = array[index];
        array[index] = array[end];
        array[end] = temp;
        int key = array[end];
        index = start - 1;
        for (int i = start; i < end; i++) {
            if (array[i] < key) {
                index++;
                temp = array[index];
                array[index] = array[i];
                array[i] = temp;
            }
        }
        array[end] = array[index + 1];
        array[index + 1] = key;

        return index + 1;
    }

    private static int select(int[] array, int start, int end, int rank) {
        if (start == end) {
            return start;
        }

        int index = partition(array, start, end);
        int k = index - start + 1;

        if (rank == k) {
            return index;
        } else if (rank < k) {
            return select(array, start, index - 1, rank);
        } else {
            return select(array, index + 1, end, rank - k);
        }
    }

    public static int select(int[] array, int rank) {
        if(rank > array.length) {
            throw new IllegalArgumentException("Rank must not larger than array length, rank: "
                    + rank + "array length: " + array.length);
        }
        return select(array, 0, array.length - 1, rank);
    }

    public static class Driver {

        public static void main(String[] args) {
            UnitTest.run(new Driver());
        }

        @Test
        public void testSelect() {
            Random random = new Random();
            int[] array = random.ints(20,0, 100)
                    .toArray();
            int[] copy = Arrays.copyOf(array, array.length);
            int rank = random.nextInt(array.length);
            int index = Counter.select(array, rank);
            boolean result = verifyRank(array, index);
            if(!result) {
                System.err.println("Select Test Failed");
            }
            System.out.println("Original Array: " + Arrays.toString(copy));
            System.out.println("Rank: " + rank);
            System.out.println("Index: " + index + ", Element: " + array[index]);
            System.out.println("Modified Array: " + Arrays.toString(array));

        }

        private static boolean verifyRank(int[] array, int rank) {
            for (int i = 0; i < rank; i++) {
                if(array[i] > array[rank]) {
                    return false;
                }
            }
            return true;
        }
    }
}
