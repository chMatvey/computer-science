package com.github.chMatvey.algorithms.structure;

import java.util.Optional;

public interface SymbolTable<Key, Value> {
    void put(Key key, Value value);

    Optional<Value> get(Key key);

    void delete(Key key);

    default boolean contains(Key key) {
        return get(key).isPresent();
    }

    boolean isEmpty();

    int size();

    Iterable<Key> keys();
}
