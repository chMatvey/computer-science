package com.github.chMatvey.collection;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Collection {
    public static void main(String[] args) {
        StreamSupport.stream(fibonacci().spliterator(), false)
                .limit(10)
                .forEach(System.out::println);

        nCopies("Java", 5).forEach(System.out::println);
        nCopies("Type Script", 4).forEach(System.out::println);

        Set<Integer> rangeSet = rangeSet(0, Integer.MAX_VALUE);
        System.out.println(rangeSet.size());
        System.out.println(rangeSet.contains(-1));
        System.out.println(rangeSet(0, 0));
        System.out.println(rangeList(0, 10));
        System.out.println(rangeList(0, 0).size());
        System.out.println(rangeList(1, 10).subList(2, 9));
    }

    static Iterable<BigDecimal> fibonacci() {
        return () -> new Iterator<>() {
            private BigDecimal current = BigDecimal.ZERO;
            private BigDecimal next = BigDecimal.ONE;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public BigDecimal next() {
                BigDecimal prev = current;
                current = next;
                next = next.add(prev);
                return prev;
            }
        };
    }

    static <T> Iterable<T> nCopies(T value, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Negative count: " + count);
        }
        return () -> new Iterator<>() {
            int rest = count;

            @Override
            public boolean hasNext() {
                return rest > 0;
            }

            @Override
            public T next() {
                if (rest == 0) {
                    throw new NoSuchElementException();
                }
                rest--;
                return value;
            }
        };
    }

    private static void checkParamsForRangeCollection(int fromInclusive, int toExclusive) {
        if (fromInclusive > toExclusive) {
            throw new IllegalArgumentException("First argument must be less than second!");
        }
        long size = (long) toExclusive - fromInclusive;
        if (size > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Too much set size!");
        }
    }

    static Set<Integer> rangeSet(int fromInclusive, int toExclusive) {
        checkParamsForRangeCollection(fromInclusive, toExclusive);
        return new AbstractSet<>() {
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<>() {
                    int next = fromInclusive;

                    @Override
                    public boolean hasNext() {
                        return next < toExclusive;
                    }

                    @Override
                    public Integer next() {
                        if (next == toExclusive) {
                            throw new NoSuchElementException();
                        }
                        return next++;
                    }
                };
            }

            @Override
            public int size() {
                return toExclusive - fromInclusive;
            }

            @Override
            public boolean contains(Object object) {
                return object instanceof Integer &&
                        (Integer) object >= fromInclusive &&
                        (Integer) object < toExclusive;
            }
        };
    }

    static List<Integer> rangeList(int fromInclusive, int toExclusive) {
        checkParamsForRangeCollection(fromInclusive, toExclusive);
        return new RangeList(fromInclusive, toExclusive);
    }

    @RequiredArgsConstructor
    private static class RangeList extends AbstractList<Integer> implements RandomAccess {
        private final int fromInclusive;
        private final int toExclusive;

        @Override
        public Integer get(int index) {
            if (index < 0 || index >= size()) {
                throw new IndexOutOfBoundsException();
            }
            return fromInclusive + index;
        }

        @Override
        public int size() {
            return toExclusive - fromInclusive;
        }

        @Override
        public boolean contains(Object object) {
            return object instanceof Integer &&
                    (Integer) object >= fromInclusive &&
                    (Integer) object < toExclusive;
        }

        @Override
        public int indexOf(Object object) {
            if (contains(object)) {
                return ((Integer) object) - fromInclusive;
            }
            return -1;
        }

        @Override
        public int lastIndexOf(Object o) {
            return indexOf(o);
        }

        @Override
        public List<Integer> subList(int fromIndex, int toIndex) {
            if (fromIndex < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (toIndex > size()) {
                throw new IndexOutOfBoundsException();
            }
            if (fromIndex >= toIndex) {
                throw new IllegalArgumentException("First argument must be less than first or equal it!");
            }
            return new RangeList(fromInclusive + fromIndex, fromInclusive + toIndex);
        }

        @Override
        public Stream<Integer> stream() {
            return IntStream.range(fromInclusive, toExclusive).boxed();
        }

        @Override
        public Stream<Integer> parallelStream() {
            return stream().parallel();
        }
    }
}
