package com.github.chMatvey.algorithms.module4;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class Ball {
    private double rx, ry; // position
    private double velocityX, velocityY;
    private final double radius;

    public Ball() {
        rx = StdRandom.uniformDouble();
        ry = StdRandom.uniformDouble();

        velocityX = StdRandom.uniformDouble();
        velocityY = StdRandom.uniformDouble();

        radius = StdRandom.uniformDouble();
    }

    public void move(double dt) {
        // Check for collision with the walls
        if ((rx + velocityX * dt < radius) || (rx + velocityX * dt > 1.0 - radius)) {
            velocityX *= -1;
        }
        if ((ry + velocityX * dt < radius) || (ry + velocityY * dt > 1.0 - radius)) {
            velocityY *= -1;
        }

        rx = rx + velocityX * dt;
        ry = ry + velocityY * dt;
    }

    public void draw() {
        StdDraw.filledCircle(rx, ry, radius);
    }
}
