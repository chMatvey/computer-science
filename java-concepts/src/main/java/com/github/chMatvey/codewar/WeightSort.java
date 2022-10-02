package com.github.chMatvey.codewar;

import lombok.Getter;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

public class WeightSort {
    @Getter
    static class Weight {
        private final long original;
        private final long parsed;

        Weight(long original) {
            long num = original;
            long sum = 0;
            while (num > 0) {
                sum += num % 10;
                num /= 10;
            }
            this.original = original;
            this.parsed = sum;
        }
    }

    static final Comparator<Weight> WEIGHT_COMPARATOR = (w1, w2) -> w1.parsed == w2.parsed ?
            valueOf(w1.original).compareTo(valueOf(w2.original)) :
            Long.compare(w1.parsed, w2.parsed);

    public static String orderWeight(String string) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(string);
        return matcher.results()
                .map(match -> string.substring(match.start(), match.end()))
                .map(Long::parseLong)
                .map(Weight::new)
                .sorted(WEIGHT_COMPARATOR)
                .map(Weight::getOriginal)
                .map(Object::toString)
                .collect(Collectors.joining(" "));
    }
}
