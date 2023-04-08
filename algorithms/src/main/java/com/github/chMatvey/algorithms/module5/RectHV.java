package com.github.chMatvey.algorithms.module5;

import edu.princeton.cs.algs4.StdDraw;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class RectHV {
    private final double xmin;
    private final double ymin;
    private final double xmax;
    private final double ymax;

    public RectHV(double xmin, double ymin, double xmax, double ymax) {
        if (Double.isNaN(xmin) || Double.isNaN(ymin) || Double.isNaN(xmax) || Double.isNaN(ymax))
            throw new IllegalArgumentException("Coordinates cannot be NaN");
        if (Double.isInfinite(xmin) || Double.isInfinite(ymin) || Double.isInfinite(xmax) || Double.isInfinite(ymax))
            throw new IllegalArgumentException("Coordinates cannot be infinite");
        if (xmax < xmin || ymax < ymin)
            throw new IllegalArgumentException("Invalid rectangle");
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
    }

    public boolean contains(Point2D p) {
        return p.getX() >= xmin && p.getX() <= xmax && p.getY() >= ymin && p.getY() <= ymax;
    }

    public boolean intersects(RectHV that) {
        return xmax >= that.xmin && ymax >= that.ymin
                && that.xmax >= xmin && that.ymax >= ymin;
    }

    public double distanceTo(Point2D p) {
        return Math.sqrt(distanceSquaredTo(p));
    }

    public double distanceSquaredTo(Point2D p) {
        double dx = 0.0, dy = 0.0;

        if (p.getX() < xmin)
            dx = p.getX() - xmin;
        else if (p.getX() > xmax)
            dx = p.getX() - xmax;

        if (p.getY() < ymin)
            dy = p.getY() - ymin;
        else if (p.getY() > ymax)
            dy = p.getY() - ymax;

        return Math.pow(dx, 2) + Math.pow(dy, 2);
    }

    public void draw() {
        StdDraw.line(xmin, ymin, xmax, ymin);
        StdDraw.line(xmax, ymin, xmax, ymax);
        StdDraw.line(xmax, ymax, xmin, ymax);
        StdDraw.line(xmin, ymax, xmin, ymin);
    }

    @Override
    public String toString() {
        return "[" + this.xmin + ", " + this.xmax + "] x [" + this.ymin + ", " + this.ymax + "]";
    }
}
