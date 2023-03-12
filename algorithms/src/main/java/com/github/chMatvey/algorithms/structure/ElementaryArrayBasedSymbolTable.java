package com.github.chMatvey.algorithms.structure;

import java.util.Optional;

public class ElementaryArrayBasedSymbolTable<Key extends Comparable<Key>, Value> implements SymbolTable<Key, Value> {
    private Key[] keys;
    private Value[] values;
    private int size;

    @Override
    public void put(Key key, Value value) {
        // need to shift all greater keys
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public Optional<Value> get(Key key) {
        if (isEmpty()) return Optional.empty();
        int i = rank(key);
        return i < size && keys[i].compareTo(key) == 0 ? Optional.of(values[i]) : Optional.empty();
    }

    @Override
    public void delete(Key key) {
        // need to shift all greater keys
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterable<Key> keys() {
        throw new RuntimeException("Not Implemented");
    }

    private int rank(Key key) {
        // binary search
        int lo = 0;
        int hi = size - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;
        }
        // if not found then return closest left
        return lo;
    }
}
