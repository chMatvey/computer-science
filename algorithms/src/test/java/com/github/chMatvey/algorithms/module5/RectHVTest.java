package com.github.chMatvey.algorithms.module5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectHVTest {
    @Test
    void contains() {
        RectHV rect = TestUtil.createRect();
        Point2D point = new Point2D(0.6, 0.5);

        assertTrue(rect.contains(point));
    }

    @Test
    void notContains() {
        RectHV rect = TestUtil.createRect();
        Point2D point = new Point2D(0.1, 0.4);

        assertFalse(rect.contains(point));
    }

    @Test
    void distanceTo() {
        RectHV rect = TestUtil.createRect();
        Point2D point = new Point2D(0.1, 0.4);

        assertEquals(0.3, rect.distanceTo(point), 0.01);
    }

    @Test
    void distanceToZero() {
        RectHV rect = TestUtil.createRect();
        Point2D point = new Point2D(0.0, 0.0);

        assertEquals(0.5, rect.distanceTo(point), 0.01);
    }

    @Test
    void distanceToInsidePoint() {
        RectHV rect = TestUtil.createRect();
        Point2D point = new Point2D(0.6, 0.5);

        assertEquals(0.0, rect.distanceTo(point), 0.01);
    }

    @Test
    void intersects() {
        RectHV first = new RectHV(0.0, 0.0, 0.4, 1);
        RectHV second = new RectHV(0.1, 0.4, 0.2, 0.7);

        assertTrue(first.intersects(second));
        assertTrue(second.intersects(first));
    }
}