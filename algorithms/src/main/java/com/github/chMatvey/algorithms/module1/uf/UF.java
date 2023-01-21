package com.github.chMatvey.algorithms.module1.uf;

public interface UF {
    boolean connected(int p, int q);

    void union(int p, int q);

    int find(int p);

    int count();
}
