package dp;

public class FibonacciSequence {

    private static int fibonacciRecursive(int n) {
        if(n < 3) {
            return 1;
        }

        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }


    private static int fibonacciIteration(int n) {
        if(n < 2) {
            return 1;
        }

        int a = 1, b = 1;
        int temp = 0;
        int current = 0;
        for(int i = 3; i <= n; i++) {
            current = a + b;
            a = b;
            b = current;
        }

        return current;
    }

    private static int fibonacciDP(int n) {
        if(n < 3) {
            return 1;
        }

        int[] memeo = new int[n];
        memeo[1] = memeo[0] = 1;

        for(int i = 3; i <= n; i++) {
            memeo[i - 1] = memeo   [i - 2] + memeo[i - 3];
        }

        return memeo[n - 1];
    }

    public static void main(String[] args) {
        System.out.println("Fibonacci 10: " + fibonacciRecursive(10));
        System.out.println("Fibonacci 10: " + fibonacciIteration(10));
        System.out.println("Fibonacci 10: " + fibonacciDP(10));
    }
}
