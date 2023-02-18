package com.github.chMatvey.algorithms.sort;

import com.github.chMatvey.algorithms.test.util.SortTestUtil;
import com.github.chMatvey.algorithms.test.util.TestSortData;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ThreeWayQuickSortTest {
    @Test
    void sort() {
        Integer[] input = {11, 3, 7, 1, 2, 9, 0, 15, 2, 3};
        ThreeWayQuickSort threeWayQuickSort = new ThreeWayQuickSort();
        threeWayQuickSort.sort(input);

        assertEquals(List.of(0, 1, 2, 2, 3, 3, 7, 9, 11, 15), List.of(input));
    }

    @Test
    void sortBigArray() {
        TestSortData<Integer> testData = SortTestUtil.generateTestData(1_000_000);
        ThreeWayQuickSort threeWayQuickSort = new ThreeWayQuickSort();
        threeWayQuickSort.sort(testData.getGivenArray());

        assertTrue(SortTestUtil.isSorted(testData.getGivenArray()));
        assertEquals(testData.getExpectedList(), List.of(testData.getGivenArray()));
    }

    @Test
    void sortArrayWithManyEqualKeys() {
        Random random = new Random();
        List<Integer> numbers = Stream
                .generate(() -> random.nextInt(10 + 1))
                .limit(1_000_000)
                .toList();
        Integer[] given = numbers.toArray(new Integer[0]);
        ThreeWayQuickSort threeWayQuickSort = new ThreeWayQuickSort();
        threeWayQuickSort.sort(given);

        assertTrue(SortTestUtil.isSorted(given));
    }
}