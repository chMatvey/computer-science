package com.github.chMatvey.algorithms.module6;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FourSumProblemTest {
    @Test
    void hasQuadrupletHappyCase() {
        int[] array = {1, 2, 0, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        assertTrue(FourSumProblem.hasQuadruplet(array));
    }
}