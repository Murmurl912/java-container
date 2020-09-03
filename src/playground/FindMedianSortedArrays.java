package playground;

import java.util.Random;
import java.util.stream.LongStream;

public class FindMedianSortedArrays {

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        for(int i = nums1.length / 2, j = nums2.length / 2; i < nums1.length || j < nums2.length;) {
            int leftMax = Math.max(nums1[i], nums2[j]);
            int rightMin = Math.min(nums1[i + 1], nums2[j + 1]);

            if(leftMax > rightMin) {
                // needs to change index
            } else {

            }
        }

        return 0;
    }

    public static void main(String[] args) {
//        LongStream stream = new Random().longs(1000);
//        stream.forEach(aLong -> {
//            int result = myAtoi(Long.toString(aLong));
//            if(aLong != result) {
//                System.out.println("Overflow: long: " + aLong + ", int: " + result);
//            }
//        });
        myAtoi("4193 with words");
    }
    public static int myAtoi(String str) {
        int length = str.length();
        int state = 0;
        char ch;
        boolean positive = true;
        StringBuilder builder = new StringBuilder();
        loop: for(int i = 0; i < length; i++) {
            ch = str.charAt(i);
            switch(state) {
                case 0: // state for blank
                    switch(ch) {
                        case ' ':
                        case '\t':
                        case '\n':
                        case '\r':
                            break;
                        case '+':
                            state = 1;
                            break;
                        case '-':
                            positive = false;
                            state = 1;
                            break;
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            state = 2;
                            builder.append(ch);
                            break;
                        default:
                            return 0;
                    }
                    break;
                case 1:  // state for sign
                    switch(ch) {
                        case ' ':
                        case '\t':
                        case '\n':
                        case '\r':
                            // ignore white space
                            break;
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            builder.append(ch);
                            state = 2;
                            break;
                        default:
                            // sign symbol followed by non digit character
                            return 0;
                    }
                    break;
                case 2: // state for number
                    // number delimit by other character
                    switch (ch) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            builder.append(ch);
                            break;
                        default:
                            break loop;
                    }
                    break;
            }
        }

        switch (state) {
            case 0:
            case 1:
                return 0;
            case 2:
            case 3:
                break;
            default:
                throw new IllegalStateException();
        }
        var context = new Object() {
            int number = 0;
        };
        int offset =  '0';
        try {
            builder.chars().forEach(character -> {

                int term = Integer.MAX_VALUE / 10;

                if(term < context.number) {
                    throw new RuntimeException("overflow");
                }


                context.number = character - offset + context.number * 10;
                if(context.number < 0) {
                    throw new RuntimeException("overflow");
                }
            });

        } catch (RuntimeException e) {
            if(positive) {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
            }
        }

        return positive ? context.number : -context.number;
    }

}
