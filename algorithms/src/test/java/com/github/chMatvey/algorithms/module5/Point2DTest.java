package com.github.chMatvey.algorithms.module5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Point2DTest {
    @Test
    void distanceToZero() {
        Point2D zero = new Point2D(0, 0);
        Point2D point = new Point2D(3, 4);

        assertEquals(5, zero.   distanceTo(point));
    }
}