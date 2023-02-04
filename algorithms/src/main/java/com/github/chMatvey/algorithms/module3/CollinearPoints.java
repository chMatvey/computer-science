package com.github.chMatvey.algorithms.module3;

public interface CollinearPoints {
    LineSegment[] segments();

    default int numberOfSegments() {
        return segments().length;
    }
}
