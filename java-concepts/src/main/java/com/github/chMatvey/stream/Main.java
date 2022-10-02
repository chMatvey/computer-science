package com.github.chMatvey.stream;

import java.util.Optional;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Optional<String> first = Stream.of(" a", "1", "3 ", "", "2", "7", "9", "5", "", "a")
                .map(String::strip)
                .map(String::toUpperCase)
                .peek(System.out::println)
                .filter(s -> !s.isEmpty())
                .findFirst();

        System.out.println(first);
    }
}
