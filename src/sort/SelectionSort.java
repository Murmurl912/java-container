package sort;

public interface SelectionSort extends Sort {

    @Override
    default void sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int min = i;
            // find the smallest in the array
            for (int j = i + 1; j < array.length; j++) {
                if(array[min] > array[j]) {
                    min = j;
                }
            }
            int temp = array[min];
            array[min] = array[i];
            array[i] = temp;
        }
    }

}
