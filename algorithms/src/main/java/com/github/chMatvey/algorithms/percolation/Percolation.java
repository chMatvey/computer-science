package com.github.chMatvey.algorithms.percolation;

/** Percolation(int n) - creates n-by-n grid, with all sites initially blocked **/
public interface Percolation {
    /** opens the site (row, col) if it is not open already **/
    void open(int row, int col);

    /** is the site (row, col) open? **/
    boolean isOpen(int row, int col);

    /** is the site (row, col) full? **/
    boolean isFull(int row, int col);

    /** returns the number of open sites **/
    int numberOfOpenSites();

    /** does the system percolate? **/
    boolean percolates();
}
