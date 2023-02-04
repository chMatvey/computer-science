package com.github.chMatvey.algorithms.sort;

public class InsertionSort implements Sort {
    @Override
    public <T extends Comparable<T>> void sort(T[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++)
            for (int j = i; j > 0; j--)
                if (less(a[j], a[j - 1]))
                    exchange(a, j, j - 1);
                else break;
    }

    public <T extends Comparable<T>> void sort(T[] a, int low, int high) {
        int N = high + 1;
        for (int i = low; i < N; i++)
            for (int j = i; j > low; j--)
                if (less(a[j], a[j - 1]))
                    exchange(a, j, j - 1);
                else break;
    }
}
