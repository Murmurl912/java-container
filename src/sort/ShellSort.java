package sort;

public interface ShellSort extends Sort {
    @Override
    default void sort(int[] array) {
        int gap = 1;
        while (gap < array.length / 3)
            gap = 3 * gap + 1;

        while (gap > 0) {
            for(int i = gap; i < array.length; i++) {
                int key = array[i];
                for(int j = i;
                    j >= gap && array[j] < array[j - gap]; j -= gap) {
                    array[j] = array[j - gap];
                    array[j - gap] = key;
                }
            }
            gap /= 3;
        }
    }
}
