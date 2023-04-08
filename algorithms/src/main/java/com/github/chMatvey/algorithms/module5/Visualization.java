package com.github.chMatvey.algorithms.module5;

import edu.princeton.cs.algs4.StdDraw;

public class Visualization {
    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        RectHV rectHV = new RectHV(0.05, 0.4, 0.15, 0.7);
        Point2D point = new Point2D(0.07, 0.62);

        kdTree.insert(new Point2D(0.4, 0.5));
        kdTree.insert(new Point2D(0.6, 0.4));
        kdTree.insert(new Point2D(0.25, 0.6));
        kdTree.insert(new Point2D(0.2, 0.1));
        kdTree.insert(new Point2D(0.1, 0.55)); // intersects with rectangle
        kdTree.insert(new Point2D(0.3, 0.75));
        kdTree.insert(new Point2D(0.5, 0.35));
        kdTree.insert(new Point2D(0.85, 0.9));
        kdTree.insert(new Point2D(0.75, 0.8));
        kdTree.insert(new Point2D(0.6, 0.2));

        StdDraw.setPenColor(StdDraw.GREEN);
        rectHV.draw();
        StdDraw.setPenColor(StdDraw.MAGENTA);
        point.draw();
        kdTree.draw();
    }
}
