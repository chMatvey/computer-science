package com.github.chMatvey.algorithms.module3;

import java.util.Arrays;

public class CollinearPointsUtil {
    static void nullValidation(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Array must bet not null");
        for (Point point : points)
            if (point == null)
                throw new IllegalArgumentException("Array must not contains null elements");
    }

    static void sortAndCheckDuplicates(Point[] points) {
        if (points.length > 1) {
            Arrays.sort(points);
            for (int i = 1; i < points.length; i++)
                if (points[i - 1].compareTo(points[i]) == 0)
                    throw new IllegalArgumentException("Array must not contains duplicate points");
        }
    }
}
