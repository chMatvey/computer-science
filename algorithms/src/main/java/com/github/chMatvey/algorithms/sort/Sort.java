package com.github.chMatvey.algorithms.sort;

public interface Sort {
    <T extends Comparable<T>> void sort(T[] a);

    default <T extends Comparable<T>> boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }

    default <T extends Comparable<T>> void exchange(T[] a, int i, int j) {
        T swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}
