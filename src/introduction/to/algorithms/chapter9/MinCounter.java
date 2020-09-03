package introduction.to.algorithms.chapter9;

public class MinCounter {
    public static int index(int[] array) {
        if(array.length < 1) {
            return -1;
        }

        if(array.length == 1) {
            return 0;
        }
        int index = 0;
        for (int i = 0, min = array[0]; i < array.length; i++) {
            if(array[i] < min) {
                index = i;
                min = array[i];
            }
        }

        return index;
    }
}
