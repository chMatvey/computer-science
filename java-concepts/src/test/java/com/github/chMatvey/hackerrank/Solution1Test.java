package com.github.chMatvey.hackerrank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Solution1Test {
    @Test
    void firstDigitInNumber() {
        int number = 89_354;
        int tenPower = (int) Math.pow(10, (int) Math.log10(number));
        int firstDigit = number / tenPower;
        int roundedToTenPower = firstDigit * tenPower;

        assertEquals(8, firstDigit);
        assertEquals(80_000, roundedToTenPower);

        System.out.println(firstDigit);
        System.out.println(roundedToTenPower);
    }

}