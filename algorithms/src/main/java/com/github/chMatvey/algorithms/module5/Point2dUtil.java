package com.github.chMatvey.algorithms.module5;

import lombok.experimental.UtilityClass;

import java.util.Comparator;

import static java.util.Comparator.comparingDouble;

@UtilityClass
public class Point2dUtil {
    public static final double MAX = 1.0;
    public static final double MIN = 0.0;

    public static Comparator<Point2D> xOrder = comparingDouble(Point2D::getX);
    public static Comparator<Point2D> yOrder = comparingDouble(Point2D::getY);
}
