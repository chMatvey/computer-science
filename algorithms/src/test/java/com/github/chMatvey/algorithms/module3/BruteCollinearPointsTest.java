package com.github.chMatvey.algorithms.module3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BruteCollinearPointsTest {
    @Test
    void calculateSegments() {
        Point[] points = new Point[] {
                Point.of(10000, 0),
                Point.of(0, 10000),
                Point.of(3000, 7000),
                Point.of(7000, 3000),
                Point.of(20000, 21000),
                Point.of(3000, 4000),
                Point.of(14000, 15000),
                Point.of(6000, 7000)
        };

        BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);
        LineSegment[] segments = bruteCollinearPoints.segments();

        assertEquals(2, bruteCollinearPoints.numberOfSegments());
        assertEquals(2, segments.length);
        assertEquals("(10000, 0) -> (0, 10000)", segments[0].toString());
        assertEquals("(3000, 4000) -> (20000, 21000)", segments[1].toString());
    }

    @Test
    void throwExceptionIfContainsDuplicatePoints() {
        Point[] points = new Point[] {
                Point.of(10000, 0),
                Point.of(0, 10000),
                Point.of(3000, 7000),
                Point.of(3000, 7000)
        };

        assertThrows(IllegalArgumentException.class, () -> new BruteCollinearPoints(points));
    }
}