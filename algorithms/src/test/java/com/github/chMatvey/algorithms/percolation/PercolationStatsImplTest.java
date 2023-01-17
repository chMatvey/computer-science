package com.github.chMatvey.algorithms.percolation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PercolationStatsImplTest {
    @Test
    void test() {
        double sharpThreshold = 0.593;
        PercolationStats stats = new PercolationStatsImpl(200, 200);

        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = ["
                + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");

        assertTrue(stats.confidenceLo() < sharpThreshold);
        assertTrue(stats.confidenceHi() > sharpThreshold);
    }
}