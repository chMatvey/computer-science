package com.github.chMatvey.algorithms.module5;

import edu.princeton.cs.algs4.StdDraw;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static com.github.chMatvey.algorithms.module5.Point2dUtil.MAX;
import static com.github.chMatvey.algorithms.module5.Point2dUtil.MIN;

@Getter
@EqualsAndHashCode
public class Point2D implements Comparable<Point2D> {
    private final double x;
    private final double y;

    public Point2D(double x, double y) {
        if (Double.isNaN(x) || Double.isNaN(y))
            throw new IllegalArgumentException("Coordinates cannot be NaN");
        if (Double.isInfinite(x) || Double.isInfinite(y))
            throw new IllegalArgumentException("Coordinates cannot be infinite");
        if (x < MIN || x > MAX || y < MIN || y > MAX)
            throw new IllegalArgumentException("Coordinates must be in [%f; %f]".formatted(MIN, MAX));
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Point2D that) {
        return Math.sqrt(distanceSquaredTo(that));
    }

    public double distanceSquaredTo(Point2D that) {
        double dx = x - that.x;
        double dy = y - that.y;
        return Math.pow(dx, 2) + Math.pow(dy, 2);
    }

    public void draw() {
        StdDraw.filledCircle(x, y, 0.01);
        // StdDraw.point(x, y);
    }

    @Override
    public int compareTo(Point2D that) {
        if (y < that.y) return -1;
        else if (y > that.y) return 1;
        else return Double.compare(x, that.x);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
