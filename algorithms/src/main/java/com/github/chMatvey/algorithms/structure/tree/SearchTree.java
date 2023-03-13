package com.github.chMatvey.algorithms.structure.tree;

import com.github.chMatvey.algorithms.structure.SymbolTable;

import java.util.Optional;

/**
 * Every node's keys larger than all keys in left subtree
 * Every node's keys smaller than all keys in right subtree
 * <br/>
 * Performance:
 * <br/>
 * If keys randomly shuffled search and insert operations are 1.39 lg N.
 * But if deletions (Hibbard deletions) allowed search, insert and deletion becomes square root N.
 */
public interface SearchTree<Key extends Comparable<Key>, Value> extends SymbolTable<Key, Value> {
    /**
     * Largest key <= a given key
     */
    Optional<Key> floor(Key key);

    /**
     * Smallest key >= a given key
     */
    Optional<Key> ceiling(Key key);

    /**
     * How many keys < a given key
     */
    int rank(Key key);

    void deleteMin();

    void deleteMax();

    Key min();

    Key max();
}
