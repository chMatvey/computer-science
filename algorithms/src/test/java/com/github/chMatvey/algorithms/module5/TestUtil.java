package com.github.chMatvey.algorithms.module5;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtil {
    static PointSetImpl createPointSet() {
        PointSetImpl pointSet = new PointSetImpl();
        pointSet.insert(new Point2D(0.0, 0.0));
        pointSet.insert(new Point2D(0.1, 0.4));
        pointSet.insert(new Point2D(0.6, 0.5));

        return pointSet;
    }

    static RectHV createRect() {
        return new RectHV(0.4, 0.3, 0.8, 0.6);
    }

    static KdTree createKdTree() {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.4, 0.5));
        kdTree.insert(new Point2D(0.6, 0.4));
        kdTree.insert(new Point2D(0.25, 0.6));
        kdTree.insert(new Point2D(0.2, 0.1));
        kdTree.insert(new Point2D(0.1, 0.55));
        kdTree.insert(new Point2D(0.3, 0.75));
        kdTree.insert(new Point2D(0.5, 0.35));
        kdTree.insert(new Point2D(0.85, 0.9));
        kdTree.insert(new Point2D(0.75, 0.8));
        kdTree.insert(new Point2D(0.6, 0.2));

        return kdTree;
    }
}
