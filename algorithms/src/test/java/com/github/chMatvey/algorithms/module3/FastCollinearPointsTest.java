package com.github.chMatvey.algorithms.module3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FastCollinearPointsTest {
    @Test
    void calculateSegments1() {
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

        FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points);
        LineSegment[] segments = fastCollinearPoints.segments();

        assertEquals(2, fastCollinearPoints.numberOfSegments());
        assertEquals(2, segments.length);
        assertEquals("(10000, 0) -> (0, 10000)", segments[0].toString());
        assertEquals("(3000, 4000) -> (20000, 21000)", segments[1].toString());
    }

    @Test
    void calculateSegments2() {
        Point[] points = new Point[] {
                Point.of(19000, 10000),
                Point.of(18000, 10000),
                Point.of(32000, 10000),
                Point.of(21000, 10000),
                Point.of(1234, 5678),
                Point.of(14000, 10000)
        };

        FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points);
        LineSegment[] segments = fastCollinearPoints.segments();

        assertEquals(1, fastCollinearPoints.numberOfSegments());
        assertEquals(1, segments.length);

        assertEquals("(14000, 10000) -> (32000, 10000)", segments[0].toString());
    }
}