package com.github.chMatvey.algorithms.sort;

import edu.princeton.cs.algs4.StdRandom;

public class QuickSort implements Sort {
    private static final int CUTOFF = 7;
    private final InsertionSort insertionSort = new InsertionSort();

    @Override
    public <T extends Comparable<T>> void sort(T[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    /**
     * @param lo array index inclusive
     * @param hi array index inclusive
     */
    private <T extends Comparable<T>> void sort(T[] a, int lo, int hi) {
        if (hi <= lo + CUTOFF - 1) {
            insertionSort.sort(a, lo, hi);
            return;
        }
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    /**
     * @param lo array index inclusive
     * @param hi array index inclusive
     */
    private <T extends Comparable<T>> int partition(T[] a, int lo, int hi) {
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
