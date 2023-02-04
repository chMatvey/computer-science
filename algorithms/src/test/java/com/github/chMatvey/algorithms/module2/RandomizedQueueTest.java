package com.github.chMatvey.algorithms.module2;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class RandomizedQueueTest {

    @Test
    void isEmpty() {
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        assertTrue(queue.isEmpty());
    }

    @Test
    void size() {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        queue.enqueue("1");
        queue.enqueue("2");
        queue.enqueue("3");
        queue.dequeue();
        queue.sample();

        assertEquals(2, queue.size());
    }

    @Test
    void iteratorShouldHasQueueSizeElements() {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        queue.enqueue("1");
        queue.enqueue("2");
        queue.enqueue("3");

        Iterator<String> iterator = queue.iterator();

        assertTrue(iterator.hasNext());
        assertNotNull(iterator.next());

        assertTrue(iterator.hasNext());
        assertNotNull(iterator.next());

        assertTrue(iterator.hasNext());
        assertNotNull(iterator.next());

        assertFalse(iterator.hasNext());
    }
}