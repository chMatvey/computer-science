package com.github.chMatvey.algorithms.module5;

import lombok.NonNull;

public interface PointSet {
    boolean isEmpty();

    int size();

    void insert(@NonNull Point2D point);

    boolean contains(@NonNull Point2D point);

    Iterable<Point2D> range(@NonNull RectHV rect);

    Point2D nearest(@NonNull Point2D point);
}
