package introduction.to.algorithms.chapter9;

import java.util.PriorityQueue;

import java.util.Random;
import java.util.Arrays;

public class PriorityCounter {

    /**
     * find the kth largest element
     * in an array
     * @param array search array
     * @param rank rank
     * @return element
     */
    public static int select(int[] array, int rank) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        for(int integer : array) {
            queue.offer(integer);
            if(queue.size() > rank) {
                queue.poll();
            }
        }

        assert !queue.isEmpty();

        return queue.peek();
    }

    public static void main(String[] args) {
        Random random = new Random();
        int[] array = random.ints(10, 0, 100).toArray();
        int rank = 0;
        int element = select(array, rank = random.nextInt(array.length));
        System.out.println("Element: " + element + ", Rank: " + rank);
        System.out.println("Array: " + Arrays.toString(array));
    }
}
