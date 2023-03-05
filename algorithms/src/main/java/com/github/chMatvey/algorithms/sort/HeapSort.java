package com.github.chMatvey.algorithms.sort;

public class HeapSort implements Sort {
    @Override
    public <T extends Comparable<T>> void sort(T[] a) {
        int N = a.length;
        T[] pq = (T[]) new Comparable[N + 1];
        System.arraycopy(a, 0, pq, 1, N);
        sort(pq, N);
        System.arraycopy(pq, 1, a, 0, N);
    }

    public <T extends Comparable<T>> void sort(T[] pq, int N) {
        for (int k = N / 2; k >= 1; k--)
            sink(pq, k, N);
        while (N > 1) {
            exchange(pq, 1, N);
            sink(pq, 1, --N);
        }
    }

    private <T extends Comparable<T>> void sink(T[] pq, int k, int N) {
        while (k * 2 <= N) {
            int j = k * 2;
            if (j < N && less(pq[j], pq[j + 1])) j += 1;
            if (!less(pq[k], pq[j])) break;
            exchange(pq, k, j);
            k = j;
        }
    }
}
