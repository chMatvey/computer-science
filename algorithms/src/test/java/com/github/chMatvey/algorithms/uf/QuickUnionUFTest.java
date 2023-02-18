package com.github.chMatvey.algorithms.uf;

import com.github.chMatvey.algorithms.uf.QuickUnionUF;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuickUnionUFTest {
    @Test
    void test1() {
        QuickUnionUF uf = new QuickUnionUF(10);
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

    @Test
    void test2() {
        int n = 1_000_000;
        QuickUnionUF uf = new QuickUnionUF(n);
        Random random = new Random();
        for (int i = 0; i < n / 2; i++) {
            int p = random.nextInt(n);
            int q = random.nextInt(n);
            uf.union(p, q);
        }
    }
}