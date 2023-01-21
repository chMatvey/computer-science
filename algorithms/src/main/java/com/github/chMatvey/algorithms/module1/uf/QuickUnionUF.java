package com.github.chMatvey.algorithms.module1.uf;

public class QuickUnionUF implements UF {
    private final int[] id;

    /** O(n) **/
    public QuickUnionUF(int n) {
        id = new int[n];
        for (int i = 0; i < n; i++) id[i] = i;
    }

    @Override
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    /** Worst - O(n) **/
    @Override
    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        id[i] = j;
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
        while (i != id[i]) i = id[i];
        return i;
    }
}
