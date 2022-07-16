package com.github.chMatvey;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

public class JavaConceptsTest {
    @Test
    void floatPointNumber() {
        double x = 0.1;
        x += 0.1;
        x += 0.1;
        System.out.println(x == 0.3);
        System.out.println(x);
    }

    @Test
    void bigDecimalTest() {
        BigDecimal bigDecimal = new BigDecimal("0.1")
                .add(new BigDecimal("0.1"))
                .add(new BigDecimal("0.1"));
        System.out.println(bigDecimal.doubleValue() == 0.3);
        System.out.println(bigDecimal);
    }

    @Test
    void narrowingPrimitiveConversion() {
        double doubleNumber = Double.MAX_VALUE;
        System.out.println(doubleNumber);

        int intNumber = (int) doubleNumber;
        System.out.println(intNumber == Integer.MAX_VALUE);
        System.out.println(intNumber);

        short shortNumber = (short) intNumber;
        System.out.println(shortNumber);

        long longNumber = Long.MAX_VALUE;
        intNumber = (int) longNumber;
        System.out.println(intNumber);

        byte b = Integer.MAX_VALUE + Integer.MAX_VALUE;
        System.out.println(b);
    }

    @Test
    void numberConversion() {
        double a = Long.MAX_VALUE;
        long b = Long.MAX_VALUE;
        int c = 1;
        System.out.println(a + b + c);
        System.out.println(c + b + a);
    }

    @Test
    void charConversion() {
        char c = 'a';
        c *= 1.2;
        System.out.println(c);
    }

    @Test
    void array() {
        int[] a = new int[10];
        int[][][] b = new int[3][2][1];
        int[][] c = {{1, 2, 3}, {4, 5}, {6}};

        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.deepToString(b));
        System.out.println(Arrays.deepToString(c));
    }
}
