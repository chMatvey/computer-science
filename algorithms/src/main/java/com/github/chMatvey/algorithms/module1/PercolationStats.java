package com.github.chMatvey.algorithms.module1;

/** PercolationStats(int n, int trials) - perform independent trials on an n-by-n grid **/
public interface PercolationStats {
    /** sample mean of per colation threshold **/
    double mean();

    /** sample standard deviation of percolation threshold **/
    double stddev();

    /** low endpoint of 95% confidence interval **/
    double confidenceLo();

    /** high endpoint of 95% confidence interval **/
    double confidenceHi();
}
