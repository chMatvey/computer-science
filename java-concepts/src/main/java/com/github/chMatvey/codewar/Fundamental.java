package com.github.chMatvey.codewar;

import java.math.BigDecimal;
import java.util.Arrays;

import static java.math.RoundingMode.HALF_UP;

public class Fundamental {
    public static Double multiply(Double a, Double b) {
        return a * b;
    }

    public static int stringToNumber(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("String must contains number", e);
        }
    }

    public static String findNeedle(Object[] haystack) {
        String searchItem = "needle";
        for (int i = 0; i < haystack.length; i++) {
            if (searchItem.equals(haystack[i])) {
                return "found the needle at position " + (i + 1);
            }
        }
        return "not found";
    }

    public static String fakeBin(String numberString) {
        return numberString.chars()
                .map(c -> c < '5' ? '0' : '1')
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static boolean zeroFuel(double distanceToPump, double mpg, double fuelLeft) {
        return distanceToPump <= mpg *  fuelLeft;
    }

    public static String areYouPlayingBanjo(String name) {
        return name != null && !name.isEmpty() &&
                (name.charAt(0) == 'R' || name.charAt(0) == 'r') ?
                "q" :
                "w";
    }

    public static char getGrade(int s1, int s2, int s3) {
        int score = BigDecimal.valueOf(s1 + s2 + s3)
                .divide(BigDecimal.valueOf(3), HALF_UP)
                .intValue();
        if (score < 60) return 'F';
        else if (score < 70) return 'D';
        else if (score < 80) return 'C';
        else if (score < 90) return 'B';
        else return 'A';
    }

    public static boolean betterThanAverage(int[] classPoints, int yourPoints) {
        double average = Arrays.stream(classPoints).average().orElse(0);
        return yourPoints > average;
    }
}
