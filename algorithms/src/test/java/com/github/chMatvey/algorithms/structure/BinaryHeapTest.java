package com.github.chMatvey.algorithms.structure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryHeapTest {
    @Test
    void test() {
        BinaryHeap<String> binaryHeap = new BinaryHeap<>();
        binaryHeap.insert("Q");
        binaryHeap.insert("W");
        binaryHeap.insert("A");
        binaryHeap.insert("Z");
        binaryHeap.insert("D");
        binaryHeap.insert("X");
        binaryHeap.insert("R");
        binaryHeap.insert("T");
        binaryHeap.insert("G");
        binaryHeap.insert("B");

        assertEquals("Z", binaryHeap.delMax());
        assertEquals("X", binaryHeap.delMax());
        assertEquals("W", binaryHeap.delMax());
        assertEquals(7, binaryHeap.size());
    }
}