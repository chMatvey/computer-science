package com.github.chMatvey.algorithms.structure.tree;

public interface BinaryNode<Key extends Comparable<Key>, Value> {
    Key getKey();

    Value getValue();

    BinaryNode<Key, Value> getLeft();

    BinaryNode<Key, Value> getRight();
}
