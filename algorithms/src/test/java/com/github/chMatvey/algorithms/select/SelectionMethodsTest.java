package com.github.chMatvey.algorithms.select;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectionMethodsTest {

    @Test
    void quickSelect() {
        Integer[] input = {11, 3, 7, 1, 2, 9, 0, 15, 2, 3};
        Integer result = SelectionMethods.quickSelect(input, 5);

        assertEquals(3, result);
    }
}