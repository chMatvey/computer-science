package com.github.chMatvey.algorithms.structure;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ElementaryLinkedListBasedSymbolTableTest {
    @Test
    void create() {
        SymbolTable<String, Integer> symbolTable = new ElementaryLinkedListBasedSymbolTable<>();
        symbolTable.put("D", 5);
        symbolTable.put("A", 9);
        symbolTable.put("C", 1);
        symbolTable.put("A", 2);
        symbolTable.put("B", 7);

        symbolTable.delete("C");

        assertEquals(3, symbolTable.size());
        assertEquals(2, symbolTable.get("A").orElse(-1));
        assertTrue(symbolTable.get("C").isEmpty());

        Iterable<String> iterable = symbolTable.keys();
        Iterator<String> iterator = iterable.iterator();

        assertEquals("B", iterator.next());
        assertEquals("A", iterator.next());
        assertEquals("D", iterator.next());
        assertFalse(iterator.hasNext());

        assertThrows(NoSuchElementException.class, iterator::next);
    }

}