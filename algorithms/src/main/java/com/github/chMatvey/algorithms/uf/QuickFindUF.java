package com.github.chMatvey.algorithms.uf;

public class QuickFindUF implements UF {
    private final int[] id;

    /** O(n) **/
    public QuickFindUF(int n) {
        id = new int[n];
        for (int i = 0; i < n; i++) id[i] = i;
    }

    /** O(1) **/
    @Override
    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    /** O(n) If already union - O(1) **/
    @Override
    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        if (pid != qid)
            for (int i = 0; i < id.length; i++)
                if (id[i] == pid) id[i] = qid;
    }

    @Override
    public int find(int p) {
        return id[p];
    }

    @Override
    public int count() {
        return id.length;
    }
}
