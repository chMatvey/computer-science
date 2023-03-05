package com.github.chMatvey.algorithms.structure;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListDequeTest {
    @Test
    void isEmpty() {
        LinkedListDeque<String> deque = new LinkedListDeque<>();

        assertTrue(deque.isEmpty());
    }

    @Test
    void size() {
        LinkedListDeque<String> deque = new LinkedListDeque<>();
        deque.addFirst("item1");
        deque.addLast("item2");
        deque.addLast("item3");
        deque.removeLast();
        deque.removeLast();

        assertEquals(1, deque.size());
    }

    @Test
    void shouldBeCorrectOrder() {
        LinkedListDeque<String> deque = new LinkedListDeque<>();
        deque.addLast("1");
        deque.addLast("2");
        deque.addFirst("3");
        deque.addFirst("4");
        deque.addLast("5");

        assertEquals("4", deque.removeFirst());
        assertEquals("3", deque.removeFirst());
        assertEquals("1", deque.removeFirst());
        assertEquals("2", deque.removeFirst());
        assertEquals("5", deque.removeFirst());
        assertTrue(deque.isEmpty());
    }

    @Test
    void shouldBeCorrectReverseOrder() {
        LinkedListDeque<String> deque = new LinkedListDeque<>();
        deque.addLast("1");
        deque.addLast("2");
        deque.addFirst("3");
        deque.addFirst("4");
        deque.addLast("5");

        assertEquals("5", deque.removeLast());
        assertEquals("2", deque.removeLast());
        assertEquals("1", deque.removeLast());
        assertEquals("3", deque.removeLast());
        assertEquals("4", deque.removeLast());
        assertTrue(deque.isEmpty());
    }

    @Test
    void shouldBeCorrectOrderInIterator() {
        LinkedListDeque<String> deque = new LinkedListDeque<>();
        deque.addLast("1");
        deque.addLast("2");
        deque.addFirst("3");
        deque.addFirst("4");
        deque.addLast("5");

        Iterator<String> iterator = deque.iterator();

        assertEquals("4", iterator.next());
        assertEquals("3", iterator.next());
        assertEquals("1", iterator.next());
        assertEquals("2", iterator.next());
        assertEquals("5", iterator.next());
        assertFalse(iterator.hasNext());
    }
}