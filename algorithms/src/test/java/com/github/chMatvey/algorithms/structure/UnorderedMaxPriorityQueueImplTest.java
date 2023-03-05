package com.github.chMatvey.algorithms.structure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnorderedMaxPriorityQueueImplTest {
    @Test
    void test() {
        UnorderedMaxPriorityQueueImpl<String> priorityQueue = new UnorderedMaxPriorityQueueImpl<>(5);
        priorityQueue.insert("X");
        priorityQueue.insert("M");
        priorityQueue.insert("C");
        priorityQueue.insert("Z");
        priorityQueue.insert("A");

        assertFalse(priorityQueue.isEmpty());
        assertEquals(5, priorityQueue.size());
        assertEquals("Z", priorityQueue.delMax());
        assertEquals("X", priorityQueue.delMax());
        assertEquals("M", priorityQueue.delMax());
        assertEquals("C", priorityQueue.delMax());
        assertEquals("A", priorityQueue.delMax());
        assertEquals(0, priorityQueue.size());
        assertTrue(priorityQueue.isEmpty());
    }
}