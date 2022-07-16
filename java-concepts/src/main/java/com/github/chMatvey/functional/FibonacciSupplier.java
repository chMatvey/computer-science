package com.github.chMatvey.functional;

import java.math.BigDecimal;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FibonacciSupplier implements Supplier<BigDecimal> {
    private BigDecimal current = BigDecimal.ZERO;
    private BigDecimal next = BigDecimal.ONE;

    @Override
    public BigDecimal get() {
        BigDecimal prev = current;
        current = next;
        next = next.add(prev);
        return prev;
    }

    public static void main(String[] args) {
        Stream.generate(new FibonacciSupplier())
                .limit(10)
                .forEach(System.out::println);
    }
}
