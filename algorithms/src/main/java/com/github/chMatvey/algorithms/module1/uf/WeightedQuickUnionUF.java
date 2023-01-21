package com.github.chMatvey.algorithms.module1.uf;

public class WeightedQuickUnionUF implements UF {
    private final int[] id;
    private final int[] sz;

    /** O(n) **/
    public WeightedQuickUnionUF(int n) {
        id = new int[n];
        sz = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    @Override
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    /** Worst - O(log(n)) **/
    @Override
    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (i == j) return;
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
    }

    @Override
    public int find(int p) {
        return root(p);
    }

    @Override
    public int count() {
        return id.length;
    }

    private int root(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }
}
