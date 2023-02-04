package com.github.chMatvey.algorithms.sort;

public class MergeSort implements Sort {
    private static final int CUTOFF = 7;
    private final InsertionSort insertionSort = new InsertionSort();

    @Override
    public <T extends Comparable<T>> void sort(T[] a) {
        T[] auxiliary = (T[]) new Comparable[a.length];
        sort(a, auxiliary, 0, a.length - 1);
    }

    /**
     * @param a source array to be sorted
     * @param aux temporally array
     * @param low start index, inclusive
     * @param high end index, inclusive
     */
    protected <T extends Comparable<T>> void sort(T[] a, T[] aux, int low, int high) {
        if (high <= low + CUTOFF - 1) {
            insertionSort.sort(a, low, high);
            return;
        }
        int middle = low + (high - low) / 2;
        sort(a, aux, low, middle);
        sort(a, aux, middle + 1, high);
        // Partially sorted. Example 0 1 2 '3' '4' 5 6
        if (!less(a[middle + 1], a[middle])) return;
        merge(a, aux, low, middle, high);
    }

    /**
     * @param a source array to be sorted
     * @param aux temporally array
     * @param low start index, inclusive
     * @param middle middle index
     * @param high end index, inclusive
     */
    protected <T extends Comparable<T>> void merge(T[] a, T[] aux, int low, int middle, int high) {
        assert isSorted(a, low, middle);
        assert isSorted(a, middle + 1, high);

        System.arraycopy(a, low, aux, low, high + 1 - low);

        int i = low;
        int j = middle + 1;
        for (int k = low; k <= high; k++) {
            if (i > middle)                 a[k] = aux[j++];
            else if (j > high)              a[k] = aux[i++];
            else if (less(aux[j], aux[i]))  a[k] = aux[j++];
            else                            a[k] = aux[i++];
        }

        assert isSorted(a, low, high);
    }

    static <T extends Comparable<T>> boolean isSorted(T[] a, int start, int end) {
        for (int i = start; i < end - 1; i++)
            if (a[i].compareTo(a[i + 1]) > 0)
                return false;
        return true;
    }
}
