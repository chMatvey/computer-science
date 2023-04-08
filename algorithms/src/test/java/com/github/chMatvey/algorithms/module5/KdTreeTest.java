package com.github.chMatvey.algorithms.module5;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Iterator;

import static com.github.chMatvey.algorithms.module5.TestUtil.createKdTree;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;

class KdTreeTest {
    @Test
    void contains() {
        KdTree kdTree = createKdTree();

        assertTrue(kdTree.contains(new Point2D(0.2, 0.1)));
    }

    @Test
    void notContains() {
        KdTree kdTree = createKdTree();

        assertFalse(kdTree.contains(new Point2D(0.2, 0.4)));
    }

    @Test
    void range() {
        KdTree kdTree = createKdTree();
        RectHV rectHV = new RectHV(0.05, 0.4, 0.15, 0.7);

        Iterable<Point2D> range = kdTree.range(rectHV);
        Iterator<Point2D> iterator = range.iterator();

        assertTrue(iterator.hasNext());
        Point2D point = iterator.next();
        assertEquals(new Point2D(0.1, 0.55), point);
        assertFalse(iterator.hasNext());
    }

    @Test
    void nearest() {
        KdTree kdTree = createKdTree();
        Point2D point = new Point2D(0.07, 0.62);

        Point2D nearest = kdTree.nearest(point);

        assertEquals(new Point2D(0.1, 0.55), nearest);
    }

    @Test
    void size() {
        KdTree kdTree = createKdTree();

        assertEquals(10, kdTree.size());
    }

    @Test
    void isEmpty() {
        KdTree kdTree = createKdTree();

        assertFalse(kdTree.isEmpty());
    }

    @Test
    void isNotEmpty() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.1, 0.2));

        assertFalse(kdTree.isEmpty());
    }

    @Test
    void test100kPoints() throws URISyntaxException, IOException {
        URL resource = getClass().getResource("/module5/input100k.txt");
        Path inputFile = Path.of(requireNonNull(resource).toURI());

        KdTree kdTree = new KdTree();

        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile.toFile()))) {
            reader.lines()
                    .map(line -> line.split(" "))
                    .map(point -> new Point2D(Double.parseDouble(point[0]), Double.parseDouble(point[1])))
                    .forEach(kdTree::insert);
        }

        Point2D nearest = kdTree.nearest(new Point2D(0.1, 0.2));

        assertNotNull(nearest);
    }

    @Test
    void test5a() {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));

        Point2D queryPoint = new Point2D(0.683, 0.723);

        Point2D nearest = kdTree.nearest(queryPoint);

        assertEquals(new Point2D(0.9, 0.6), nearest);
    }

    @Test
    void test5a2() {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.372, 0.497));
        kdTree.insert(new Point2D(0.564, 0.413));
        kdTree.insert(new Point2D(0.226, 0.577));
        kdTree.insert(new Point2D(0.144, 0.179));
        kdTree.insert(new Point2D(0.083, 0.51));
        kdTree.insert(new Point2D(0.32, 0.708));
        kdTree.insert(new Point2D(0.417, 0.362));
        kdTree.insert(new Point2D(0.862, 0.825));
        kdTree.insert(new Point2D(0.785, 0.725));
        kdTree.insert(new Point2D(0.499, 0.208));

        Point2D queryPoint = new Point2D(0.09, 0.045);

        Point2D nearest = kdTree.nearest(queryPoint);

        assertEquals(new Point2D(0.144, 0.179), nearest);
        assertEquals(0.020872, nearest.distanceSquaredTo(queryPoint), 0.000001);
    }

    @Test
    void test5b() {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.375, 1.0));
        kdTree.insert(new Point2D(0.75, 0.875));
        kdTree.insert(new Point2D(0.25, 0.5));
        kdTree.insert(new Point2D(0.625, 0.375));
        kdTree.insert(new Point2D(1.0, 0.25));

        Point2D queryPoint = new Point2D(0.875, 0.125);

        Point2D nearest = kdTree.nearest(queryPoint);

        assertEquals(new Point2D(1.0, 0.25), nearest);
    }

    @Test
    void test6b() {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.125, 0.125));
        kdTree.insert(new Point2D(0.75, 0.875));
        kdTree.insert(new Point2D(0.375, 1.0));
        kdTree.insert(new Point2D(0.875, 0.0));
        kdTree.insert(new Point2D(0.0, 0.625));

        Point2D queryPoint = new Point2D(0.5, 0.5);

        Point2D nearest = kdTree.nearest(queryPoint);

        assertEquals(new Point2D(0.75, 0.875), nearest);
    }
}