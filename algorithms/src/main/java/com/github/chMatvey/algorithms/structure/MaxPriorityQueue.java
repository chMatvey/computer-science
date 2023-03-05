package com.github.chMatvey.algorithms.structure;

public interface MaxPriorityQueue<T extends Comparable<T>> {
    void insert(T v);
    T delMax();
    boolean isEmpty();
    T max();
    int size();
}
