package playground;

import util.UnitTest;

import java.util.TreeMap;

public class LengthOfLongestSubstring {

    public static void main(String[] args) {
        UnitTest.equal(3, lengthOfLongestSubstring("abcabcab"));
        UnitTest.equal(1, lengthOfLongestSubstring("a"));

    }

    public static int lengthOfLongestSubstring(String s) {

        int max = 0;
        TreeMap<Character, Integer> string = new TreeMap<>();

        for(int start = 0, i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if(string.containsKey(ch)) {
                int index = string.get(ch);

                // shrink indexes
                for(int j = start; j <= index; j++) {
                    string.remove(s.charAt(j));
                }
                // move window forward
                start = index + 1;
            }
            // grow window
            string.put(ch, i);

            max = Math.max(i - start + 1, max);

        }

        return max;
    }
}
