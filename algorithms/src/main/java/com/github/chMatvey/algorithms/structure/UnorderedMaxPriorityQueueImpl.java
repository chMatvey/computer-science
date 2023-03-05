package com.github.chMatvey.algorithms.structure;

import static com.github.chMatvey.algorithms.sort.SortMethods.exchange;
import static com.github.chMatvey.algorithms.sort.SortMethods.less;

public class UnorderedMaxPriorityQueueImpl<T extends Comparable<T>> implements MaxPriorityQueue<T> {
    private final T[] pq;
    private int N;

    public UnorderedMaxPriorityQueueImpl(int capacity) {
        // todo replace to resizing array
        pq = (T[]) new Comparable[capacity];
    }

    @Override
    public void insert(T v) {
        pq[N++] = v;
    }

    @Override
    public T delMax() {
        T result = max();
        N--;
        return result;
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    @Override
    public T max() {
        int max = 0;
        for (int i = 1; i < N; i++)
            if (less(pq, max, i))
                max = i;
        exchange(pq, max, N - 1);
        return pq[N - 1];
    }

    @Override
    public int size() {
        return N;
    }
}
