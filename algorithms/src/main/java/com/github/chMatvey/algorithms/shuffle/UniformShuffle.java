package com.github.chMatvey.algorithms.shuffle;

import edu.princeton.cs.algs4.StdRandom;

public class UniformShuffle {
    public static <T> void shuffle(T[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = StdRandom.uniformInt(i + 1); // between 0 and i
            exchange(a, i, r);
        }
    }

    public static <T> void exchange(T[] a, int i, int j) {
        T swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}
