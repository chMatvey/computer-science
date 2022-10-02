package com.github.chMatvey.codewar;

public class Solution {
    public static boolean validParentheses(String parens) {
        final char OPEN = '(';
        final char CLOSE = ')';

        int sum = 0;
        for (int i : parens.chars().toArray()) {
            if (i != OPEN && i != CLOSE) continue;
            sum += (i == OPEN ? 1 : -1);
            if (sum < 0) return false;
        }
        return sum == 0;
    }

    public static int[] twoSum(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (target == numbers[i] + numbers[j]) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }
}
