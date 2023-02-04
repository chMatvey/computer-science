package com.github.chMatvey.algorithms.module3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.github.chMatvey.algorithms.module3.CollinearPointsUtil.nullValidation;
import static com.github.chMatvey.algorithms.module3.CollinearPointsUtil.sortAndCheckDuplicates;

public class FastCollinearPoints implements CollinearPoints {
    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        nullValidation(points);
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        sortAndCheckDuplicates(pointsCopy);
        if (pointsCopy.length < 4) {
            segments = new LineSegment[0];
            return;
        }

        List<LineSegment> segmentList = new ArrayList<>();

        Point[] aux = new Point[pointsCopy.length];
        System.arraycopy(pointsCopy, 0, aux, 0, pointsCopy.length);

        for (Point point : pointsCopy) {
            Comparator<Point> comparator = point.slopeOrder();
            Arrays.sort(aux, comparator);
            // aux[0] = point -> start with 1
            int index = 1;
            for (int i = 2; i < aux.length; i++) {
                if (comparator.compare(aux[index], aux[i]) == 0)
                    continue;
                if (i - index > 2 && isSmallest(aux, index, i, point))
                    segmentList.add(new LineSegment(point, biggest(aux, index, i)));
                index = i;
            }
            if (aux.length - index > 2 && isSmallest(aux, index, aux.length, point))
                segmentList.add(new LineSegment(point, biggest(aux, index, aux.length)));
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

    /**
     * @param aux - sorted points by origin point
     * @param start - inclusive
     * @param end - exclusive
     * @param p - origin point
     */
    private boolean isSmallest(Point[] aux, int start, int end, Point p) {
        for (int i = start; i < end; i++)
            if (p.compareTo(aux[i]) > 0)
                return false;
        return true;
    }

    /**
     * @param start - inclusive
     * @param end - exclusive
     */
    private Point biggest(Point[] aux, int start, int end) {
        Point result = aux[start];
        for (int i = start + 1; i < end; i++)
            if (aux[i].compareTo(result) > 0)
                result = aux[i];
        return result;
    }
}
