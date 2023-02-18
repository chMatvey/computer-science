package com.github.chMatvey.algorithms.sort;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SortMethods {
    public static <T extends Comparable<T>> boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }

    public static <T extends Comparable<T>> void exchange(T[] a, int i, int j) {
        T swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static <T extends Comparable<T>> int partition(T[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        T v = a[lo];
        while (true) {
            while (less(a[++i], v))
                if (i == hi) break;

            while (less(v, a[--j]))
                if (j == lo) break; // redundant since a[lo] acts as sentinel

            if (i >= j) break;
            exchange(a, i, j);
        }

        exchange(a, lo, j);
        return j;
    }
}
