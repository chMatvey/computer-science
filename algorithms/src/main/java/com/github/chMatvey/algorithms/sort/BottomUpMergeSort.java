package com.github.chMatvey.algorithms.sort;

/**
 * Merge sort implementation without recursion
 */
public class BottomUpMergeSort implements Sort {
    @Override
    public <T extends Comparable<T>> void sort(T[] a) {
        int N = a.length;
        T[] aux = (T[]) new Comparable[a.length];
        for (int sz = 1; sz < N; sz *= 2)
            for (int lo = 0; lo < N - sz; lo += sz * 2)
                merge(a, aux, lo, lo + sz - 1, Math.min(lo + sz * 2 - 1, N - 1));
    }

    protected <T extends Comparable<T>> void merge(T[] a, T[] aux, int low, int middle, int high) {
        System.arraycopy(a, low, aux, low, high + 1 - low);

        int i = low;
        int j = middle + 1;
        for (int k = low; k <= high; k++) {
            if (i > middle)                 a[k] = aux[j++];
            else if (j > high)              a[k] = aux[i++];
            else if (less(aux[j], aux[i]))  a[k] = aux[j++];
            else                            a[k] = aux[i++];
        }
    }
}
