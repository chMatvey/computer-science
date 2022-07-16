package com.github.chMatvey.functional;

import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Functional {
    public static void main(String[] args) throws IOException {
        searchWordInDirectory();
    }

    public static void searchWordInDirectory() throws IOException {
        final String SEARCH_WORD = "Java";
        Path dir = Paths.get(".");
        try (Stream<Path> files = Files.walk(dir)) {
            long count = files.filter(p -> p.toString().endsWith(".java"))
                    .peek(System.out::println)
                    .flatMap(Functional::sneakyLines)
                    .filter(s -> s.contains(SEARCH_WORD))
                    .peek(System.out::println)
                    .count();

            System.out.printf("Found %d strings", count);
        }
    }

    @SneakyThrows
    public static Stream<String> sneakyLines(Path path) {
        return Files.lines(path);
    }
}
