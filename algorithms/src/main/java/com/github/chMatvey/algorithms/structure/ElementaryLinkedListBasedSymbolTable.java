package com.github.chMatvey.algorithms.structure;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ElementaryLinkedListBasedSymbolTable<Key, Value> implements SymbolTable<Key, Value> {
    private Node head;
    private int size;

    @Override
    public void put(Key key, Value value) {
        Node current = head;
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }
        head = new Node(key, value, head);
        size++;
    }

    @Override
    public Optional<Value> get(Key key) {
        Node current = head;
        while (current != null) {
            if (current.key.equals(key))
                return Optional.of(current.value);
            current = current.next;
        }
        return Optional.empty();
    }

    @Override
    public void delete(Key key) {
        if (isEmpty()) return;

        if (head.key.equals(key)) {
            head = head.next;
            size--;
            return;
        }

        Node prev = head;
        Node current = head.next;
        while (current != null) {
            if (current.key.equals(key)) {
                prev.next = current.next;
                size--;
                return;
            }
            prev = current;
            current = current.next;
        }
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
        final Key[] keys = (Key[]) new Object[size];
        final int limit = size;

        Node current = head;
        for (int i = 0; i < size; i++) {
            keys[i] = current.key;
            current = current.next;
        }

        return () -> new Iterator<>() {
            int current;

            @Override
            public boolean hasNext() {
                return current != limit;
            }

            @Override
            public Key next() {
                if (current == limit)
                    throw new NoSuchElementException();
                return keys[current++];
            }
        };
    }

    @AllArgsConstructor
    @RequiredArgsConstructor
    private class Node {
        final Key key;
        Value value;
        Node next;
    }
}
