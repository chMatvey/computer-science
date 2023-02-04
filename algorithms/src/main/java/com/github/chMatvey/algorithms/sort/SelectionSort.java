package com.github.chMatvey.algorithms.sort;

public class SelectionSort implements Sort {
    @Override
    public <T extends Comparable<T>> void sort(T[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++)
                if (less(a[j], a[min]))
                    min = j;
            exchange(a, i, min);
        }
    }
}
