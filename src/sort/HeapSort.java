package sort;

public interface HeapSort extends Sort {

    @Override
    default void sort(int[] array) {
        // heapify
        for(int i = parent(array.length - 1); i >= 0; i--) {
            sink(array, i, array.length);
        }

        // delete max
        int size = array.length;
        int temp;
        while (size > 0) {
            temp = array[0];
            array[0] = array[--size];
            array[size] = temp;
            sink(array, 0, size);
        }
    }

    private void sink(int[] array, int parent, int size) {
        int temp;
        for(int left = left(parent),
            right = right(parent),
            lager = left; parent < array.length; ) {
            if(right < size && less(array, left, right)) {
                lager = right;
            }
            temp = array[lager];
            array[lager] = array[parent];
            array[parent] = temp;

            parent = lager;
            left = right(parent);
            right = left(parent);
            lager = left;
        }
    }

    private static int left(int parent) {
        return 2 * parent + 1;
    }

    private static int right(int parent) {
        return 2 * parent + 2;
    }

    private static int parent(int child) {
        return (child - 1) / 2;
    }

    private static boolean less(int[] array, int a, int b) {
        return array[a] < array[b];
    }

    private static boolean greater(int[] array, int a, int b) {
        return array[a] > array[b];
    }

    private static boolean equal(int[] array, int a, int b) {
        return array[a] == array[b];
    }
}
