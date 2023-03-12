package com.github.chMatvey.algorithms.structure;

import edu.princeton.cs.algs4.Queue;
import lombok.NonNull;

import java.util.Optional;

public class BinarySearchTreeImpl<Key extends Comparable<Key>, Value> implements BinarySearchTree<Key, Value> {
    private Node root;

    @Override
    public Optional<Value> get(Key key) {
        Node current = root;
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp < 0) current = current.left;
            else if (cmp > 0) current = current.right;
            else return Optional.of(current.value);
        }
        return Optional.empty();
    }

    @Override
    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    @Override
    public void delete(Key key) {
        root = delete(root, key);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return size(root);
    }

    @Override
    public Optional<Key> floor(Key key) {
        return floor(root, key);
    }

    @Override
    public Optional<Key> ceiling(Key key) {
        return ceiling(root, key);
    }

    @Override
    public int rank(Key key) {
        return rank(key, root);
    }

    @Override
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        inorder(root, queue);
        return queue;
    }

    @Override
    public void deleteMin() {
        root = deleteMin(root);
    }

    @Override
    public void deleteMax() {
        root = deleteMax(root);
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null)
            return new Node(key, value, 1);

        int cmp = key.compareTo(node.key);
        if (cmp < 0)
            node.left = put(node.left, key, value);
        else if (cmp > 0)
            node.right = put(node.right, key, value);
        else
            node.value = value;

        node.count = size(node.left) + size(node.right) + 1;

        return node;
    }

    private Node delete(Node node, Key key) {
        if (node == null)
            return null;

        int cmp = key.compareTo(node.key);

        if (cmp < 0)
            node.left = delete(node.left, key);
        else if (cmp > 0)
            node.right = delete(node.right, key);
        else {
            if (node.left == null)
                return node.right;
            if (node.right == null)
                return node.left;

            Node toDelete = node;
            node = min(toDelete.right);
            node.right = deleteMin(toDelete.right);
            node.left = toDelete.left;
        }

        node.count = size(node.left) + size(node.right) + 1;
        return node;
    }

    private int size(Node node) {
        return node == null ? 0 : node.count;
    }

    private Optional<Key> floor(Node current, Key key) {
        if (current == null)
            return Optional.empty();

        int cmp = key.compareTo(current.key);

        if (cmp == 0) return Optional.of(current.key);

        if (cmp < 0) return floor(current.left, key);

        Optional<Key> largestRightLessThanCurrent = floor(current.right, key);
        return largestRightLessThanCurrent.isPresent() ? largestRightLessThanCurrent : Optional.of(current.key);
    }

    private Optional<Key> ceiling(Node current, Key key) {
        if (current == null)
            return Optional.empty();

        int cmp = key.compareTo(current.key);

        if (cmp == 0) return Optional.of(current.key);

        if (cmp > 0) return ceiling(current.right, key);

        Optional<Key> smallestKeyMoreThanKey = ceiling(current.left, key);
        return smallestKeyMoreThanKey.isPresent() ? smallestKeyMoreThanKey : Optional.of(current.key);
    }

    private int rank(Key key, Node node) {
        if (node == null)
            return 0;

        int cmp = key.compareTo(node.key);

        if (cmp < 0)
            return rank(key, node.left);
        else if (cmp > 0)
            return 1 + size(node.left) + rank(key, node.right);
        else
            return size(node.left);
    }

    private void inorder(Node node, Queue<Key> queue) {
        if (node == null)
            return;
        inorder(node.left, queue);
        queue.enqueue(node.key);
        inorder(node.right, queue);
    }

    private Node deleteMin(Node node) {
        if (node.left == null)
            return node.right;

        node.left = deleteMin(node.left);
        node.count = 1 + size(node.left) + size(node.right);

        return node;
    }

    private Node deleteMax(Node node) {
        if (node.right == null)
            return node.left;

        node.right = deleteMax(node.right);
        node.count = 1 + size(node.left) + size(node.right);

        return node;
    }

    private Node min(Node node) {
        return node.left == null ? node : min(node.left);
    }

    private class Node {
        @NonNull
        private final Key key;

        @NonNull
        private Value value;

        private Node left;
        private Node right;

        /**
         * number of node in subtree
         */
        @NonNull
        private int count;

        public Node(Key key, Value val, int count) {
            this.key = key;
            this.value = val;
            this.count = count;
        }
    }
}
