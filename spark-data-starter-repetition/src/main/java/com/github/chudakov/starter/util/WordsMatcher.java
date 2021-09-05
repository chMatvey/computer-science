package com.github.chudakov.starter.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WordsMatcher {

    public static String findAndRemoveIfPiecesMatch(Set<String> options, List<String> pieces) {
        StringBuilder match = new StringBuilder(pieces.remove(0).toLowerCase());
        List<String> remainingOptions = options.stream()
                .map(String::toLowerCase)
                .filter(option -> option.startsWith(match.toString()))
                .collect(Collectors.toList());
        if (remainingOptions.isEmpty()) {
            return "";
        }
        while (remainingOptions.size() > 1) {
            match.append(pieces.remove(0).toLowerCase());
            remainingOptions.removeIf(option -> !option.startsWith(match.toString()));
        }
        while (!remainingOptions.get(0).equals(match.toString())) {
            match.append(pieces.remove(0).toLowerCase());
        }

        return StringUtil.decapitalize(match.toString());
    }
}
