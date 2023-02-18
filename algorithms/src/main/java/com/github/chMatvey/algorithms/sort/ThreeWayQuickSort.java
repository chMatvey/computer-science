package com.github.chMatvey.algorithms.sort;

public class ThreeWayQuickSort implements Sort {
    @Override
    public <T extends Comparable<T>> void sort(T[] a) {
        sort(a, 0, a.length - 1);
    }

    /**
     * @param lo array index inclusive
     * @param hi array index inclusive
     */
    public <T extends Comparable<T>> void sort(T[] a, int lo, int hi) {
        if (hi < lo) return;
        int lt = lo;
        int gt = hi;
        T v = a[lo];
        int i = lo + 1;
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0) exchange(a, lt++, i++);
            else if (cmp > 0) exchange(a, i, gt--);
            else i++;
        }

        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }
}
