package com.github.chMatvey.algorithms.module4;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {
    @Test
    void basicSolve() {
        // Given
        Board initial = new Board(new int[][] {
                new int[] {0, 1, 3},
                new int[] {4, 2, 5},
                new int[] {7, 8, 6}
        });
        Board first = new Board(new int[][] {
                new int[] {1, 0, 3},
                new int[] {4, 2, 5},
                new int[] {7, 8, 6}
        });
        Board second = new Board(new int[][] {
                new int[] {1, 2, 3},
                new int[] {4, 0, 5},
                new int[] {7, 8, 6}
        });
        Board third = new Board(new int[][] {
                new int[] {1, 2, 3},
                new int[] {4, 5, 0},
                new int[] {7, 8, 6}
        });
        Board fourth = new Board(new int[][] {
                new int[] {1, 2, 3},
                new int[] {4, 5, 6},
                new int[] {7, 8, 0}
        });

        // When
        Solver solver = new Solver(initial);

        // Then
        assertTrue(solver.isSolvable());
        assertEquals(4, solver.moves());

        System.out.println("Minimum number of moves " + solver.moves());
        Iterator<Board> iterator = solver.solution().iterator();

        checkSolution(initial, iterator);
        checkSolution(first, iterator);
        checkSolution(second, iterator);
        checkSolution(third, iterator);
        checkSolution(fourth, iterator);
    }

    @Test
    void unsolved() {
        // Given
        Board initial = new Board(new int[][] {
                new int[] {1, 2, 3},
                new int[] {4, 5, 6},
                new int[] {8, 7, 0}
        });

        // When
        Solver solver = new Solver(initial);

        // Then
        assertEquals(-1, solver.moves());
        assertNull(solver.solution());
    }

    private static void checkSolution(Board expected, Iterator<Board> iterator) {
        assertTrue(iterator.hasNext());
        Board board = iterator.next();
        System.out.println(board.toString());
        assertEquals(expected, board);
    }
}