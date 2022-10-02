package com.github.chMatvey.stream;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collector;

public class MyCollector {
    private MyCollector() {}

    public static <T, R> Collector<T, ?, Optional<R>> minMax(
            Comparator<? super T> cmp,
            BiFunction<? super T, ? super T, ? extends R> finisher
    ) {
        class Accumulator {
            T min;
            T max;
            boolean present;

            void add(T t) {
                if (present) {
                    if (cmp.compare(t, min) < 0) min = t;
                    if (cmp.compare(t, max) > 0) max = t;
                } else {
                    min = max = t;
                    present = true;
                }
            }

            Accumulator combine(Accumulator other) {
                if (!other.present) return this;
                if (!present) return other;
                if (cmp.compare(other.min, min) < 0) min = other.min;
                if (cmp.compare(other.max, max) > 0) max = other.max;
                return this;
            }

            Optional<R> result() {
                return present ? Optional.of(finisher.apply(min, max)) : Optional.empty();
            }
        }

        return Collector.of(
                Accumulator::new,
                Accumulator::add,
                Accumulator::combine,
                Accumulator::result
        );
    }
}
