package com.github.chMatvey.algorithms.structure.tree;

public interface BinarySearchTree<Key extends Comparable<Key>, Value> extends SearchTree<Key, Value> {
    BinaryNode<Key, Value> getRoot();
}
