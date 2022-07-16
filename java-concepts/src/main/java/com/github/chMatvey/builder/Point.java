package com.github.chMatvey.builder;

import lombok.Builder;

public record Point(int x, int y) {
    @Builder public Point {}
}
