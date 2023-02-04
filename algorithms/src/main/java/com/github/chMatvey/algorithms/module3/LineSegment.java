package com.github.chMatvey.algorithms.module3;

public class LineSegment {
    private final Point p;
    private final Point q;

    public LineSegment(Point p, Point q) {
        if (p == null || q == null)
            throw new IllegalArgumentException("Point must not be null");
        this.p = p;
        this.q = q;
    }

    public void draw() {
        p.drawTo(q);
    }

    @Override
    public String toString() {
        return p + " -> " + q;
    }
}
