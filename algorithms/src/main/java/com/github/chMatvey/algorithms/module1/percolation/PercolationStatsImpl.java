package com.github.chMatvey.algorithms.module1.percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public final class PercolationStatsImpl implements PercolationStats {
    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;

    public PercolationStatsImpl(int n, int trials) {
        if (n < 1)
            throw new IllegalArgumentException("n must be more than zero");
        if (trials < 1)
            throw new IllegalArgumentException("trials must be more than zero");

        double[] results = runExperiment(n, trials);

        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);
        confidenceLo = mean - (1.96 * stddev() / Math.sqrt(trials));
        confidenceHi = mean + (1.96 * stddev() / Math.sqrt(trials));
    }

    @Override
    public double mean() {
        return mean;
    }

    @Override
    public double stddev() {
        return stddev;
    }

    @Override
    public double confidenceLo() {
        return confidenceLo;
    }

    @Override
    public double confidenceHi() {
        return confidenceHi;
    }

    private double[] runExperiment(int n, int trials) {
        double[] results = new double[trials];
        double nSquare = n * n;

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new PercolationImpl(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniformInt(0, n) + 1;
                int col = StdRandom.uniformInt(0, n) + 1;
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            }
            results[i] = (double) percolation.numberOfOpenSites() / nSquare;
        }
        return results;
    }
}
