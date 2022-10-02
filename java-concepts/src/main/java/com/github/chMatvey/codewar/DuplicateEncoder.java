package com.github.chMatvey.codewar;

import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class DuplicateEncoder {
    static String encode(String word) {
        String inLowerCase = word.toLowerCase();
        Map<Integer, Long> charsCount = inLowerCase.chars()
                .boxed()
                .collect(groupingBy(identity(), counting()));

        return inLowerCase.chars()
                .map(c -> charsCount.get(c) == 1 ? '(' : ')')
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
