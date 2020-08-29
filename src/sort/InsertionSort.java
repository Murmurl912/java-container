package sort;

public interface InsertionSort extends Sort {

    @Override
    default void sort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            // search inserting position
            for (int j = i; j > 0 && array[j] < array[j-1]; j--) {
                array[j] = array[j-1];
                array[j-1] = key;
            }
        }
    }
}
