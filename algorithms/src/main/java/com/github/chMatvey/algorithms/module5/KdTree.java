package com.github.chMatvey.algorithms.module5;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;

import static com.github.chMatvey.algorithms.module5.Point2dUtil.*;

public class KdTree implements PointSet {
    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;

    private Node root;
    private int size = 0;

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void insert(@NonNull Point2D point) {
        root = insert(root, null, point, VERTICAL);
    }

    @Override
    public boolean contains(@NonNull Point2D point) {
        return contains(root, point);
    }

    @Override
    public Iterable<Point2D> range(@NonNull RectHV rect) {
        Stack<Point2D> result = new Stack<>();

        if (root != null)
            range(root, rect, result);

        return result;
    }

    @Override
    public Point2D nearest(@NonNull Point2D point) {
        if (root == null)
            return null;

        return nearest(root, point, root.point);
    }

    public void draw() {
        if (root == null)
            return;

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.line(root.point.getX(), MIN, root.point.getX(), MAX);

        draw(root);
    }

    private boolean contains(Node node, Point2D point) {
        if (node == null)
            return false;
        if (node.point.equals(point))
            return true;
        else {
            Comparator<Point2D> comparator = node.orientation == VERTICAL ? xOrder : yOrder;
            int cmp = comparator.compare(point, node.point);
            if (cmp < 0)
                return contains(node.left, point);
            else
                return contains(node.right, point);
        }
    }

    private Node insert(Node node, Node parent, Point2D point, boolean orientation) {
        if (node == null) {
            size++;
            return new Node(point, orientation, parent);
        }
        if (node.point.equals(point))
            return node;
        else {
            Comparator<Point2D> comparator = node.orientation == VERTICAL ? xOrder : yOrder;
            int cmp = comparator.compare(point, node.point);
            if (cmp < 0)
                node.left = insert(node.left, node, point, !orientation);
            else
                node.right = insert(node.right, node, point, !orientation);
        }
        return node;
    }

    private void range(Node node, RectHV rect, Stack<Point2D> result) {
        if (rect.contains(node.point))
            result.push(node.point);

        if (node.left != null && rect.intersects(node.leftOrBottomRect()))
            range(node.left, rect, result);

        if (node.right != null && rect.intersects(node.rightOrTopRect()))
            range(node.right, rect, result);
    }

    private Point2D nearest(Node node, Point2D point, Point2D nearest) {
        if (node == null)
            return nearest;

        if (node.point.distanceSquaredTo(point) < nearest.distanceSquaredTo(point))
            nearest = node.point;
        /*
            Choose the closest space (Optimization)
            Simple implementation:

            if (node.left != null && node.leftOrBottomRect().distanceSquaredTo(point) < nearest.distanceSquaredTo(point))
                nearest = nearest(node.left, point, nearest);

            if (node.right != null && node.rightOrTopRect().distanceSquaredTo(point) < nearest.distanceSquaredTo(point))
                nearest = nearest(node.right, point, nearest);
        */

        if (node.left != null && node.right != null) {
            double distanceLeft = node.leftOrBottomRect().distanceSquaredTo(point);
            double distanceRight = node.rightOrTopRect().distanceSquaredTo(point);

            if (distanceLeft <= distanceRight && distanceLeft < nearest.distanceSquaredTo(point)) {
                nearest = nearest(node.left, point, nearest);
                if (distanceRight < nearest.distanceSquaredTo(point))
                    nearest = nearest(node.right, point, nearest);

            } else if (distanceRight < distanceLeft && distanceRight < nearest.distanceSquaredTo(point)) {
                nearest = nearest(node.right, point, nearest);
                if (distanceLeft < nearest.distanceSquaredTo(point))
                    nearest = nearest(node.left, point, nearest);
            }
        } else if (node.right == null && node.leftOrBottomRect().distanceSquaredTo(point) < nearest.distanceSquaredTo(point))
            nearest = nearest(node.left, point, nearest);

        else if (node.left == null && node.rightOrTopRect().distanceSquaredTo(point) < nearest.distanceSquaredTo(point))
            nearest = nearest(node.right, point, nearest);

        return nearest;
    }

    private void draw(@NonNull Node node) {
        StdDraw.setPenColor(StdDraw.BLACK);
        node.point.draw();

        if (node.left != null) {
            Point2D leftPoint = node.left.point;
            if (node.orientation == VERTICAL) {
                StdDraw.setPenColor(StdDraw.BLUE);
                RectHV leftReact = node.leftReact();
                StdDraw.line(leftReact.getXmin(), leftPoint.getY(), leftReact.getXmax(), leftPoint.getY());
            } else {
                StdDraw.setPenColor(StdDraw.RED);
                RectHV bottomRect = node.bottomRect();
                StdDraw.line(leftPoint.getX(), bottomRect.getYmin(), leftPoint.getX(), bottomRect.getYmax());
            }

            draw(node.left);
        }

        if (node.right != null) {
            Point2D rightPoint = node.right.point;
            if (node.orientation == VERTICAL) {
                StdDraw.setPenColor(StdDraw.BLUE);
                RectHV rightRect = node.rightRect();
                StdDraw.line(rightRect.getXmin(), rightPoint.getY(), rightRect.getXmax(), rightPoint.getY());
            } else {
                StdDraw.setPenColor(StdDraw.RED);
                RectHV topRect = node.topRect();
                StdDraw.line(rightPoint.getX(), topRect.getYmin(), rightPoint.getX(), topRect.getYmax());
            }

            draw(node.right);
        }
    }

    @RequiredArgsConstructor
    private static class Node {
        private final Point2D point;
        private final boolean orientation;
        private final Node parent;
        private Node left;
        private Node right;

        RectHV leftOrBottomRect() {
            return orientation == VERTICAL ? leftReact() : bottomRect();
        }

        RectHV rightOrTopRect() {
            return orientation == VERTICAL ? rightRect() : topRect();
        }

        RectHV leftReact() {
            assert (orientation == VERTICAL);

            if (parent == null)
                return new RectHV(MIN, MIN, point.getX(), MAX);
            else {
                RectHV parentReact = parent.left == this ? parent.bottomRect() : parent.topRect();
                return new RectHV(
                        parentReact.getXmin(),
                        parentReact.getYmin(),
                        point.getX(),
                        parentReact.getYmax()
                );
            }
        }

        RectHV rightRect() {
            assert (orientation == VERTICAL);

            if (parent == null)
                return new RectHV(point.getX(), MIN, MAX, MAX);
            else {
                RectHV parentReact = parent.left == this ? parent.bottomRect() : parent.topRect();
                return new RectHV(
                        point.getX(),
                        parentReact.getYmin(),
                        parentReact.getXmax(),
                        parentReact.getYmax()
                );
            }
        }

        RectHV bottomRect() {
            assert (orientation == HORIZONTAL);
            assert (parent != null);

            RectHV parentReact = parent.left == this ? parent.leftReact() : parent.rightRect();
            return new RectHV(
                    parentReact.getXmin(),
                    parentReact.getYmin(),
                    parentReact.getXmax(),
                    point.getY()
            );
        }

        RectHV topRect() {
            assert (orientation == HORIZONTAL);
            assert (parent != null);

            RectHV parentReact = parent.left == this ? parent.leftReact() : parent.rightRect();
            return new RectHV(
                    parentReact.getXmin(),
                    point.getY(),
                    parentReact.getXmax(),
                    parentReact.getYmax()
            );
        }
    }
}
