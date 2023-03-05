package com.github.chMatvey.algorithms.module4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void hamming() {
        Board board = new Board(new int[][] {
                new int[] {8, 1, 3},
                new int[] {4, 0, 2},
                new int[] {7, 6, 5}
        });

        assertEquals(5, board.hamming());
    }

    @Test
    void manhattan() {
        Board board = new Board(new int[][] {
                new int[] {8, 1, 3},
                new int[] {4, 0, 2},
                new int[] {7, 6, 5}
        });

        assertEquals(10, board.manhattan());
    }

    @Test
    void isGoal() {
        Board board = new Board(new int[][] {
                new int[] {1, 2, 3},
                new int[] {4, 5, 6},
                new int[] {7, 8, 0}
        });

        assertTrue(board.isGoal());
    }

    @Test
    void createAnThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Board(new int[][] {
                new int[] {1, 2, 3},
                new int[] {4, 5, 6},
                new int[] {7, 0}
        }));
    }
}