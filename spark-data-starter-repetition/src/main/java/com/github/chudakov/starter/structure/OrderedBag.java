package com.github.chudakov.starter.structure;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class OrderedBag<T> {
    private final List<T> list;

    public OrderedBag(T[] args) {
        this.list = new ArrayList<>(asList(args));
    }

    public T takeAndRemove() {
        return list.remove(0);
    }

    public int size() {
        return list.size();
    }
}
