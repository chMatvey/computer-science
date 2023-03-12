package com.github.chMatvey.algorithms.module3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.chMatvey.algorithms.module3.CollinearPointsUtil.nullValidation;
import static com.github.chMatvey.algorithms.module3.CollinearPointsUtil.sortAndCheckDuplicates;

public class BruteCollinearPoints implements CollinearPoints {
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        nullValidation(points);
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        sortAndCheckDuplicates(pointsCopy);
        if (pointsCopy.length < 4) {
            segments = new LineSegment[0];
            return;
        }

        List<LineSegment> segmentList = new ArrayList<>();
        for (int i = 0; i < pointsCopy.length - 3; i++)
            for (int j = i + 1; j < pointsCopy.length - 2; j++)
                for (int k = j + 1; k < pointsCopy.length - 1; k++)
                    for (int m = k + 1; m < pointsCopy.length; m++) {
                        double slope1 = pointsCopy[i].slopeTo(pointsCopy[j]);
                        double slope2 = pointsCopy[i].slopeTo(pointsCopy[k]);
                        double slope3 = pointsCopy[i].slopeTo(pointsCopy[m]);
                        if (slope1 == slope2 && slope1 == slope3)
                            segmentList.add(new LineSegment(pointsCopy[i], pointsCopy[m]));
                    }
        segments = segmentList.toArray(new LineSegment[0]);
    }

    @Override
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }

    @Override
    public int numberOfSegments() {
        return segments.length;
    }
}
