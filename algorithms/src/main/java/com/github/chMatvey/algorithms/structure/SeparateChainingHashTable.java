package com.github.chMatvey.algorithms.structure;

import lombok.AllArgsConstructor;

public class SeparateChainingHashTable<Key, Value> implements HashTable<Key, Value> {
    private int M = 97;
    private Node[] st = new Node[M];

    @Override
    public Value get(Key key) {
        int i = hash(key);
        for (Node x = st[i]; x != null; x = x.next)
            if (key.equals(x.key))
                return (Value) x.val;
        return null;
    }

    @Override
    public void put(Key key, Value value) {
        int i = hash(key);
        for (Node x = st[i]; x != null; x = x.next)
            if (key.equals(x.key)) {
                x.val = value;
                return;
            }
        st[i] = new Node(key, value, st[i]);
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    @AllArgsConstructor
    private static class Node {
        private final Object key;
        private Object val;
        private Node next;
    }
}
