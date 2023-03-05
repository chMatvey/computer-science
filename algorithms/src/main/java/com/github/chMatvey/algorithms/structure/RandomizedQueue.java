package com.github.chMatvey.algorithms.structure;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Queue<Item> {
    private Item[] items;
    private int N = 0; // last index

    private final static int DEFAULT_CAPACITY = 1;

    public RandomizedQueue() {
        items = (Item[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Item can not be null.");

        if (N == items.length)
            resize(items.length * 2);

        items[N++] = item;
    }

    @Override
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty.");

        // Get random elements
        int randomIndex = StdRandom.uniformInt(N);
        Item item = items[randomIndex];

        // Put last element to taken position
        items[randomIndex] = items[N - 1];
        items[N - 1] = null;

        if (N == items.length / 4)
            resize(items.length / 2);

        N--;
        return item;
    }

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty.");
        return items[StdRandom.uniformInt(N)];
    }

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<>() {
            private int count = N;

            @Override
            public boolean hasNext() {
                return count != 0;
            }

            @Override
            public Item next() {
                if (!hasNext())
                    throw new NoSuchElementException();

                // Get random element
                int randomIndex = StdRandom.uniformInt(count);
                Item item = items[randomIndex];

                // Replace random element with las element and decrement count
                items[randomIndex] = items[count - 1];
                items[--count] = item;

                return item;
            }
        };
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, copy, 0, N);
        items = copy;
    }
}
