package com.github.chudakov.starter.util;

import java.util.List;

public class StringUtil {

    private static final List<String> dictionary = List.of(
            "findBy"
    );

    public static String decapitalize(String string) {
        for (String word : dictionary) {
            if (word.equalsIgnoreCase(string)) {
                return word;
            }
        }
        return string;
    }
}
