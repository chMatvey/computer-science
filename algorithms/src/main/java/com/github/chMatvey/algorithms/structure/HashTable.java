package com.github.chMatvey.algorithms.structure;

public interface HashTable<Key, Value> {
    Value get(Key key);

    void put(Key key, Value value);
}
