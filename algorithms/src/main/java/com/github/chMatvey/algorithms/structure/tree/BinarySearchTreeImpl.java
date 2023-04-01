package com.github.chMatvey.algorithms.structure.tree;

import edu.princeton.cs.algs4.Queue;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

public class BinarySearchTreeImpl<Key extends Comparable<Key>, Value> implements BinarySearchTree<Key, Value>, SearchTree<Key, Value> {
    @Getter
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

    @Override
    public Key min() {
        return min(root).key;
    }

    @Override
    public Key max() {
        return max(root).key;
    }

    @Override
    public int height() {
        return height(root);
    }

    @Override
    public Iterable<Key> levelOrderKeys() {
        Queue<Key> queue = new Queue<>();

        for (int i = 0; i < height(root); i++) {
            levelOrderKeys(root, i, queue);
        }

        return queue;
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

    private Node max(Node node) {
        return node.right == null ? node : max(node.right);
    }

    private int height(Node node) {
        if (node == null)
            return 0;

        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());

        return Integer.max(leftHeight, rightHeight) + 1;
    }

    private void levelOrderKeys(BinaryNode<Key,Value> node, int level, Queue<Key> queue) {
        if (node == null)
            return;
        if (level == 0) {
            queue.enqueue(node.getKey());
        } else {
            levelOrderKeys(node.getLeft(), level - 1, queue);
            levelOrderKeys(node.getRight(), level - 1, queue);
        }
    }

    @Getter
    @RequiredArgsConstructor
    private class Node implements BinaryNode<Key, Value> {
        @NonNull final Key key;
        @NonNull Value value;

        Node left;
        Node right;

        /**
         * number of node in subtree
         */
        @NonNull int count;
    }
}
