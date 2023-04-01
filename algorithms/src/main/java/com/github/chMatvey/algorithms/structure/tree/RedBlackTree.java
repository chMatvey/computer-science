package com.github.chMatvey.algorithms.structure.tree;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * All operations exclude put and delete are the same as in {@link BinarySearchTreeImpl}
 */
public abstract class RedBlackTree<Key extends Comparable<Key>, Value> implements BinarySearchTree<Key, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    @Override
    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null)
            return new Node(key, value, RED);

        int cmp = key.compareTo(node.key);

        if (cmp < 0)
            node.left = put(node.left, key, value);
        else if (cmp > 0)
            node.right = put(node.right, key, value);
        else
            node.value = value;

        if (isRed(node.right) && !isRed(node.left))
            node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left))
            node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right))
            flipColors(node);

        return node;
    }

    private Node rotateLeft(Node node) {
        assert isRed(node.right);

        Node newParent = node.right;
        node.right = newParent.left;
        newParent.left = node;
        newParent.color = node.color;
        node.color = RED;

        return newParent;
    }

    private Node rotateRight(Node node) {
        assert isRed(node.left);

        Node newParent = node.left;
        node.left = newParent.right;
        newParent.right = node;
        newParent.color = node.color;
        node.color = RED;

        return newParent;
    }

    private void flipColors(Node node) {
        assert (!isRed(node));
        assert (isRed(node.left));
        assert (isRed(node.right));

        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    private boolean isRed(Node node) {
        return node == null || node.color == RED;
    }

    @Getter
    @RequiredArgsConstructor
    private class Node implements BinaryNode<Key, Value> {
        @NonNull final Key key;
        @NonNull Value value;
        @NonNull boolean color;

        Node left;
        Node right;
    }
}
