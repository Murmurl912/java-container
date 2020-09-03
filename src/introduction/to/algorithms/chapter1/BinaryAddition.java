package introduction.to.algorithms.chapter1;

import util.Disable;
import util.Test;
import util.UnitTest;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class BinaryAddition {

    public static int[] add(int[] a, int[] b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);
        assert a.length == b.length;

        int[] c = new int[a.length + 1];
        for (int i = a.length - 1, j = a.length; i > -1; i--, j--) {
            c[j] += a[i] + b[i];
            if(c[j] > 0) {
                c[j - 1] = c[j] / 2;
                c[j] = c[j] % 2;
            }
        }
        return c;
    }


    public static class Driver {

        public static void main(String[] args) {
            UnitTest.run(new Driver());
        }

        @Disable
        @Test
        public void testConvert() {
            Random random = new Random();
            int a = random.nextInt(0xffff - 1);
            int[] binary = getBinary(a);
            int b = (int)getNumber(binary);

            System.out.println("Binary of " + a + ": " + Arrays.toString(binary));
            System.out.println("Number: " + b);
        }

        @Test
        public void testAdd() {
            Random random = new Random();
            int a = random.nextInt(0xffff - 1);
            int b = random.nextInt(0xffff - 1);

            int[] binaryA = getBinary(a);
            int[] binaryB = getBinary(b);
            int[] binaryC = add(binaryA, binaryB);
            long number = getNumber(binaryC);
            long desire = a + b;
            if(number != desire) {
                System.err.println("Binary Add Test Failed");
            }
            System.out.println("Binary of " + a + ": \t   " + Arrays.toString(binaryA));
            System.out.println("Binary of " + b + ": \t   " + Arrays.toString(binaryB));
            System.out.println("Binary of " + desire + ": \t" + Arrays.toString(binaryC));
        }

        public static int[] getBinary(int num) {
            int[] binary = new int[32];

            for (int i = binary.length - 1; i > -1; i--) {
                binary[i] = (num << i) >>> binary.length - 1;
            }

            return binary;
        }

        public static long getNumber(int[] binary) {
            long number = 0;
            for (int bit : binary) {
                number = number * 2 + bit;
            }
            return number;
        }

    }
}
