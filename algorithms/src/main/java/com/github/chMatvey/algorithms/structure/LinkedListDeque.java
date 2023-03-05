package com.github.chMatvey.algorithms.structure;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListDeque<Item> implements Deque<Item> {
    private Node<Item> head;
    private Node<Item> tail;

    private int size = 0;

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Item can not be null.");

        Node<Item> oldHead = head;
        head = new Node<>(item);

        if (tail == null) {
            tail = head;
        } else {
            oldHead.prev = head;
            head.next = oldHead;
        }
        size++;
    }

    @Override
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Item can not be null.");

        Node<Item> oldTail = tail;
        tail = new Node<>(item);

        if (head == null) {
            head = tail;
        } else {
            oldTail.next = tail;
            tail.prev = oldTail;
        }
        size++;
    }

    @Override
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty.");

        Item item = head.item;
        head = head.next;

        if (head == null)
            tail = null;
        else
            head.prev = null;

        size--;
        return item;
    }

    @Override
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty.");

        Item item = tail.item;
        tail = tail.prev;

        if (tail == null)
            head = null;
        else
            tail.next = null;

        size--;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<>() {
            private Node<Item> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                Item item = current.item;
                current = current.next;
                return item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private static class Node<Item> {
        Node<Item> prev;
        Node<Item> next;
        Item item;

        public Node(Item item) {
            this.item = item;
        }
    }
}
