package com.github.chMatvey.algorithms.structure;

import static com.github.chMatvey.algorithms.sort.SortMethods.exchange;
import static com.github.chMatvey.algorithms.sort.SortMethods.less;

/**
 * <p>
 * T in nodes.
 * Parent's node not smaller than children's nodes.
 * </p>
 * <p>
 * Array representation.
 * Largest key is a[1].
 * Children node of k are 2k and 2k + 1, for example children of a[1] are a[2] and a[3].
 * Parent node of k is k/2, for example parent of a[2] and a[3] is a[1].
 * </p>
 * <p>
 * When child node value becomes larger than parent:
 * 1) Exchange value in child node with parent;
 * 2) Repeat until heap order restored.
 * It calls swim operation {@link #swim(int)}.
 * </p>
 * <p>
 * When parent's value becomes smaller than one or both children's:
 * 1) Exchange parent value with value in larger child;
 * 2) Repeat until heap order restored.
 * It calls sink operation {@link #sink(int)}.
 * </p>
 */
public class BinaryHeap<T extends Comparable<T>> implements MaxPriorityQueue<T> {
    private static final int MIN_CAPACITY = 8;

    private T[] pq = (T[]) new Comparable[MIN_CAPACITY];
    int N = 0;

    @Override
    public void insert(T v) {
        if (N + 1 == pq.length)
            resize(pq.length * 2);

        pq[++N] = v;
        swim(N);
    }

    @Override
    public T delMax() {
        if (isEmpty())
            throw new IllegalArgumentException("Heap is empty");
        T max = pq[1];
        exchange(pq, 1, N--);
        sink(1);
        pq[N + 1] = null;

        if (N + 1 == pq.length / 4 && pq.length / 2 >= MIN_CAPACITY)
            resize(pq.length / 2);

        return max;
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    @Override
    public T max() {
        return pq[1];
    }

    @Override
    public int size() {
        return N;
    }

    private void swim(int k) {
        while (k > 1 && less(pq, k / 2, k)) {
            exchange(pq, k, k / 2);
            k /= 2;
        }
    }

    private void sink(int k) {
        while (k * 2 <= N) {
            int j = k * 2;
            if (j < N && less(pq, j, j + 1)) j += 1;
            if (!less(pq, k, j)) break;
            exchange(pq, k, j);
            k = j;
        }
    }

    private void resize(int capacity) {
        T[] copy = (T[]) new Comparable[capacity];
        System.arraycopy(pq, 0, copy, 0, N + 1);
        pq = copy;
    }
}
