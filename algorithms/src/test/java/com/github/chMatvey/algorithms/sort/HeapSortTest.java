package com.github.chMatvey.algorithms.sort;

import com.github.chMatvey.algorithms.test.util.SortTestUtil;
import com.github.chMatvey.algorithms.test.util.TestSortData;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HeapSortTest {
    @Test
    void sort() {
        Integer[] input = {11, 3, 7, 1, 2, 9, 0, 15, 2, 3};
        HeapSort heapSort = new HeapSort();
        heapSort.sort(input);

        assertEquals(List.of(0, 1, 2, 2, 3, 3, 7, 9, 11, 15), List.of(input));
    }

    @Test
    void sortBigArray() {
        TestSortData<Integer> testData = SortTestUtil.generateTestData(1_000_000);
        HeapSort heapSort = new HeapSort();
        heapSort.sort(testData.getGivenArray());

        assertTrue(SortTestUtil.isSorted(testData.getGivenArray()));
        assertEquals(testData.getExpectedList(), List.of(testData.getGivenArray()));
    }
}