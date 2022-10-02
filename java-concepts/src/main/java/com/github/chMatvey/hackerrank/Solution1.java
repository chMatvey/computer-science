package com.github.chMatvey.hackerrank;

import java.io.IOException;
import java.util.Scanner;
import java.util.function.Predicate;

public class Solution1 {
    static final int ODD = 1;
    static final int PRIME = 2;
    static final int PALINDROME = 3;

    static final Predicate<Integer> isOdd = n -> n % 2 == 1;

    static final Predicate<Integer> isPrime = n -> {
        if (n < 0) n *= -1;
        if (n < 2) return false;
        for (int i = 2; i < n / 2; i++) {
            if (n % i == 0) return false;
        }
        return true;
    };

    static final Predicate<Integer> isPalindrome = n -> {
        if (n < 0) n *= -1;
        if (n < 10) return true;

        while (n > 9) {
            int tenPower = (int) Math.pow(10, (int) Math.log10(n));
            int firstDigit = n / tenPower;
            int lastDigit = n % 10;
            if (firstDigit != lastDigit) return false;
            int roundedToTenPower = firstDigit * tenPower;
            n = (n - roundedToTenPower) / 10;
        }
        return true;
    };

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int testCaseCount = scanner.nextInt();

        while (testCaseCount > 0) {
            testCaseCount--;
            String result = switch (scanner.nextInt()) {
                case ODD -> isOdd.test(scanner.nextInt()) ? "ODD" : "EVEN";
                case PRIME -> isPrime.test(scanner.nextInt()) ? "PRIME" : "COMPOSITE";
                case PALINDROME -> isPalindrome.test(scanner.nextInt()) ? "PALINDROME" : "NOT PALINDROME";
                default -> null;
            };
            if (result == null) throw new IllegalArgumentException("Unexpected condition to check");
            System.out.println(result);
        }
    }
}
