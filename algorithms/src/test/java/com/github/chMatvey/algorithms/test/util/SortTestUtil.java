package com.github.chMatvey.algorithms.test.util;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@UtilityClass
public class SortTestUtil {
    public static TestSortData<Integer> generateTestData(int arraySize) {
        int max = arraySize * 10;
        int count = arraySize;
        Random random = new Random();

        List<Integer> numbers = Stream
                .generate(() -> random.nextInt(max + 1))
                .limit(count)
                .toList();

        List<Integer> sorted = List.copyOf(numbers)
                .stream()
                .sorted()
                .toList();

        Integer[] given = numbers.toArray(new Integer[0]);

        return TestSortData.<Integer>builder()
                .givenArray(given)
                .expectedList(sorted)
                .build();
    }

    public static <T extends Comparable<T>> boolean isSorted(T[] a) {
        return isSorted(a, 0, a.length);
    }

    public static <T extends Comparable<T>> boolean isSorted(T[] a, int start, int end) {
        for (int i = start; i < end - 1; i++)
            if (a[i].compareTo(a[i + 1]) > 0)
                return false;
        return true;
    }
}
