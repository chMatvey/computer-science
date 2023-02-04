package com.github.chMatvey.algorithms.sort;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SelectionSortTest {

    @Test
    void sort() {
        Integer[] input = {11, 3, 7, 1, 2, 9, 0, 15, 2, 3};
        SelectionSort selectionSort = new SelectionSort();
        selectionSort.sort(input);

        assertEquals(List.of(0, 1, 2, 2, 3, 3, 7, 9, 11, 15), List.of(input));
    }
}