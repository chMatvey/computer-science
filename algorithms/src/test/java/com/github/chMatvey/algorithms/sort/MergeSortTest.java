package com.github.chMatvey.algorithms.sort;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {

    @Test
    void sort() {
        Integer[] input = {3, 7, 1, 2, 9, 0};
        MergeSort sort = new MergeSort();
        sort.sort(input);

        assertEquals(List.of(0, 1, 2, 3, 7, 9), List.of(input));
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

        MergeSort sort = new MergeSort();
        sort.sort(actual);

        assertEquals(expected, List.of(actual));
    }
}