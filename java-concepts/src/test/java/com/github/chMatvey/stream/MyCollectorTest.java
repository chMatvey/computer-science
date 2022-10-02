package com.github.chMatvey.stream;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.stream.Stream;

import static com.github.chMatvey.stream.MyCollector.minMax;

class MyCollectorTest {
    @Test
    void testMyCollector() {
        Stream.of("one", "two", "three", "four", "five", "six", "seven")
                .collect(minMax(Comparator.comparing(String::length), (min, max) -> min + " | " + max))
                .ifPresent(System.out::println);
    }
}