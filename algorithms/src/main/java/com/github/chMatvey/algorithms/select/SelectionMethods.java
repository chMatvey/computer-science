package com.github.chMatvey.algorithms.select;

import edu.princeton.cs.algs4.StdRandom;
import lombok.experimental.UtilityClass;

import static com.github.chMatvey.algorithms.sort.SortMethods.partition;

@UtilityClass
public class SelectionMethods {
    public static <T extends Comparable<T>> T select(T[] a, int k) {
        return quickSelect(a, k);
    }

    public static <T extends Comparable<T>> T quickSelect(T[] a, int k) {
        StdRandom.shuffle(a);
        int lo = 0;
        int hi = a.length - 1;
        while (hi > lo) {
            int j = partition(a, lo, hi);
            if (j < k) lo = j + 1;
            else if (j > k) hi = j - 1;
            else return a[k];
        }
        return a[k];
    }
}
