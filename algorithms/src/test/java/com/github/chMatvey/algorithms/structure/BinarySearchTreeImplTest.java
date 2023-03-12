package com.github.chMatvey.algorithms.structure;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeImplTest {
    @Test
    void createTree() {
        BinarySearchTreeImpl<String, Integer> binarySearchTree = createBinarySearchTree();
        binarySearchTree.put("A", 0);
        binarySearchTree.put("M", 0);

        assertTrue(binarySearchTree.get("NotContain").isEmpty());
        assertEquals(0, binarySearchTree.get("A").orElse(-1));
        assertEquals(0, binarySearchTree.get("M").orElse(-1));
    }

    @Test
    void size() {
        BinarySearchTreeImpl<String, Integer> binarySearchTree = createBinarySearchTree();

        assertFalse(binarySearchTree.isEmpty());
        assertEquals(8, binarySearchTree.size());
    }

    @Test
    void floor() {
        BinarySearchTreeImpl<String, Integer> binarySearchTree = createBinarySearchTree();

        assertEquals("E", binarySearchTree.floor("G").orElse("Null"));
        assertEquals("C", binarySearchTree.floor("D").orElse("Null"));
    }

    @Test
    void ceiling() {
        BinarySearchTreeImpl<String, Integer> binarySearchTree = createBinarySearchTree();

        assertEquals("R", binarySearchTree.ceiling("Q").orElse("Null"));
        assertTrue(binarySearchTree.ceiling("Z").isEmpty());
    }

    @Test
    void rank() {
        BinarySearchTreeImpl<String, Integer> binarySearchTree = createBinarySearchTree();

        assertEquals(1, binarySearchTree.rank("B"));
        assertEquals(5, binarySearchTree.rank("Q"));
        assertEquals(7, binarySearchTree.rank("T"));
    }

    @Test
    void keys() {
        BinarySearchTreeImpl<String, Integer> binarySearchTree = createBinarySearchTree();
        Iterator<String> keysIterator = binarySearchTree.keys().iterator();

        assertEquals("A", keysIterator.next());
        assertEquals("C", keysIterator.next());
        assertEquals("E", keysIterator.next());
        assertEquals("H", keysIterator.next());
        assertEquals("M", keysIterator.next());
        assertEquals("R", keysIterator.next());
        assertEquals("S", keysIterator.next());
        assertEquals("X", keysIterator.next());
        assertFalse(keysIterator.hasNext());
        assertThrows(NoSuchElementException.class, keysIterator::next);
    }

    @Test
    void deleteMin() {
        BinarySearchTreeImpl<String, Integer> binarySearchTree = createBinarySearchTree();
        binarySearchTree.deleteMin();
        binarySearchTree.deleteMin();

        assertEquals(6, binarySearchTree.size());
        assertFalse(binarySearchTree.contains("A"));
        assertFalse(binarySearchTree.contains("C"));
    }

    @Test
    void deleteMax() {
        BinarySearchTreeImpl<String, Integer> binarySearchTree = createBinarySearchTree();
        binarySearchTree.deleteMax();
        binarySearchTree.deleteMax();

        assertEquals(6, binarySearchTree.size());
        assertTrue(binarySearchTree.get("X").isEmpty());
        assertTrue(binarySearchTree.get("S").isEmpty());
    }

    @Test
    void delete() {
        BinarySearchTreeImpl<String, Integer> binarySearchTree = createBinarySearchTree();

        binarySearchTree.delete("C");
        binarySearchTree.delete("E");
        binarySearchTree.delete("Z");
        binarySearchTree.delete("S");

        assertEquals(5, binarySearchTree.size());
        assertFalse(binarySearchTree.contains("C"));
        assertFalse(binarySearchTree.contains("E"));
        assertFalse(binarySearchTree.contains("S"));

        Iterator<String> keysIterator = binarySearchTree.keys().iterator();

        assertEquals("A", keysIterator.next());
        assertEquals("H", keysIterator.next());
        assertEquals("M", keysIterator.next());
        assertEquals("R", keysIterator.next());
        assertEquals("X", keysIterator.next());
        assertFalse(keysIterator.hasNext());
    }

    private static BinarySearchTreeImpl<String, Integer> createBinarySearchTree() {
        BinarySearchTreeImpl<String, Integer> binarySearchTree = new BinarySearchTreeImpl<>();
        binarySearchTree.put("S", 1);
        binarySearchTree.put("E", 2);
        binarySearchTree.put("X", 3);
        binarySearchTree.put("A", 4);
        binarySearchTree.put("R", 5);
        binarySearchTree.put("C", 6);
        binarySearchTree.put("H", 7);
        binarySearchTree.put("M", 8);

        return binarySearchTree;
    }
}