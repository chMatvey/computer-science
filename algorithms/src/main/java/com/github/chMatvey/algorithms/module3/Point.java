package com.github.chMatvey.algorithms.module3;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    public static final int MIN_BORDER = 0;
    public static final int MAX_BORDER = 32_767;
    private static boolean outsideBorder(int c) {
        return c < MIN_BORDER || c > MAX_BORDER;
    }

    private final int x;
    private final int y;

    public static Point of(int x, int y) {
        return new Point(x, y);
    }

    public Point(int x, int y) {
        if (outsideBorder(x))
            throw new IllegalArgumentException("The x coordinate outside the borders.");
        if (outsideBorder(y))
            throw new IllegalArgumentException("The y coordinate outside the borders.");
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(x, y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if (x == that.x) {
            return y == that.y ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        } else if (y == that.y) {
            return 0;
        }
        return ((double) (that.y - y)) / (that.x - x);
    }

    public Comparator<Point> slopeOrder() {
        return (p1, p2) -> Double.compare(slopeTo(p1), slopeTo(p2));
    }

    @Override
    public int compareTo(Point that) {
        if (y < that.y) return -1;
        else if (y > that.y) return 1;
        else return Integer.compare(x, that.x);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
