package com.github.chMatvey.algorithms.sort;

import com.github.chMatvey.algorithms.test.util.SortTestUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {
    @Test
    void sort() {
        int max = 100;
        int count = 100;
        Random random = new Random();

        List<Integer> numbers = Stream
                .generate(() -> random.nextInt(max + 1))
                .limit(count)
                .toList();

        List<Integer> expected = List.copyOf(numbers)
                .stream()
                .sorted()
                .toList();

        Integer[] actual = new Integer[numbers.size()];
        numbers.toArray(actual);

        QuickSort quickSort = new QuickSort();
        quickSort.sort(actual);

        assertEquals(expected, List.of(actual));
    }

    @Test
    void sortBigArray() {
        int max = 10_000_000;
        int count = 1_000_000;
        Random random = new Random();

        List<Integer> numbers = Stream
                .generate(() -> random.nextInt(max + 1))
                .limit(count)
                .toList();

        List<Integer> expected = List.copyOf(numbers)
                .stream()
                .sorted()
                .toList();

        Integer[] actual = new Integer[numbers.size()];
        numbers.toArray(actual);

        QuickSort quickSort = new QuickSort();
        quickSort.sort(actual);

        assertEquals(expected, List.of(actual));
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