package com.github.chMatvey.builder;

public class Builder {
    public static void main(String[] args) {
        Point point = Point.builder()
                .x(1)
                .y(2)
                .build();
        System.out.println(point.x());
    }
}
