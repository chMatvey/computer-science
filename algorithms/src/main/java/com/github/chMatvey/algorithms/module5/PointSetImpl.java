package com.github.chMatvey.algorithms.module5;

import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;

@NoArgsConstructor
public class PointSetImpl implements PointSet {
    private final TreeSet<Point2D> points = new TreeSet<>();

    @Override
    public boolean isEmpty() {
        return points.isEmpty();
    }

    @Override
    public int size() {
        return points.size();
    }

    @Override
    public void insert(@NonNull Point2D point) {
        points.add(point);
    }

    @Override
    public boolean contains(@NonNull Point2D point) {
        return points.contains(point);
    }

    @Override
    public Iterable<Point2D> range(@NonNull RectHV rect) {
        return points.stream()
                .filter(rect::contains)
                .collect(Collectors.toSet());
    }

    @Override
    public Point2D nearest(@NonNull Point2D point) {
        return points.stream()
                .min(Comparator.comparingDouble(p -> p.distanceSquaredTo(point)))
                .orElse(null);
    }

    public void draw() {
        points.forEach(Point2D::draw);
    }
}
