package com.github.chMatvey.algorithms.module1.uf;

import com.github.chMatvey.algorithms.module1.uf.QuickFindUF;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuickFindUFTest {
    @Test
    void test1() {
        QuickFindUF uf = new QuickFindUF(10);
        uf.union(1, 2);
        uf.union(3, 4);
        uf.union(5, 6);
        uf.union(7, 8);
        uf.union(9, 7);
        uf.union(9, 1);
        uf.union(8, 2);
        uf.union(0, 5);

        assertTrue(uf.connected(3, 4));
        assertTrue(uf.connected(1, 9));
        assertTrue(uf.connected(0, 6));

        assertFalse(uf.connected(0, 9));
        assertFalse(uf.connected(1, 6));
        assertFalse(uf.connected(4, 7));
    }
}