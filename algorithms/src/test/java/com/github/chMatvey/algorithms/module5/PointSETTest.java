package com.github.chMatvey.algorithms.module5;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class PointSETTest {
    @Test
    void range() {
        PointSetImpl pointSet = TestUtil.createPointSet();
        RectHV rect = TestUtil.createRect();

        Iterable<Point2D> range = pointSet.range(rect);
        Iterator<Point2D> iterator = range.iterator();

        assertTrue(iterator.hasNext());
        Point2D point = iterator.next();
        assertEquals(new Point2D(0.6, 0.5), point);
        assertFalse(iterator.hasNext());
    }

    @Test
    void nearest() {
        PointSetImpl pointSet = TestUtil.createPointSet();
        Point2D point = new Point2D(0.2, 0.4);

        Point2D nearest = pointSet.nearest(point);

        assertEquals(new Point2D(0.1, 0.4), nearest);
    }
}