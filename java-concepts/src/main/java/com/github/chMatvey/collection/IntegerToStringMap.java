package com.github.chMatvey.collection;

import java.util.*;

import static java.lang.String.valueOf;

public class IntegerToStringMap {
    public static void main(String[] args) {
        Map<Integer, String> integerToStringMap = getIntegerToStringMap(10);
        System.out.println(integerToStringMap);
    }

    public static Map<Integer, String> getIntegerToStringMap(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count must be positive number or zero!");
        }

        return new AbstractMap<>() {
            @Override
            public Set<Entry<Integer, String>> entrySet() {
                return new AbstractSet<>() {
                    @Override
                    public Iterator<Entry<Integer, String>> iterator() {
                        return new Iterator<>() {
                            int next = 0;

                            @Override
                            public boolean hasNext() {
                                return next < count;
                            }

                            @Override
                            public Entry<Integer, String> next() {
                                if (next == count) {
                                    throw new NoSuchElementException();
                                }
                                var result = new SimpleImmutableEntry<>(next, valueOf(next));
                                next++;
                                return result;
                            }
                        };
                    }

                    @Override
                    public int size() {
                        return count;
                    }
                };
            }

            @Override
            public boolean containsKey(Object key) {
                return key instanceof Integer &&
                        ((Integer) key) >= 0 &&
                        ((Integer) key) < count;
            }

            @Override
            public String get(Object key) {
                return containsKey(key) ? key.toString() : null;
            }
        };
    }
}
