package com.github.chMatvey.algorithms.module4;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class Solver {
    private final Deque<Board> solution;
    private final int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("Initial can not be null");

        MinPQ<SearchNode> priorityQueue = new MinPQ<>();
        priorityQueue.insert(new SearchNode(initial, null));
        priorityQueue.insert(new SearchNode(initial.twin(), null));

        while (!priorityQueue.min().board.isGoal()) {
            SearchNode searchNode = priorityQueue.delMin();
            for (Board neighbor : searchNode.board.neighbors())
                if (searchNode.prev == null || !searchNode.prev.board.equals(neighbor))
                    priorityQueue.insert(new SearchNode(neighbor, searchNode));
        }

        Deque<Board> deque = new ArrayDeque<>();
        SearchNode current = priorityQueue.min();
        int boardMoves = current.moves;
        while (current.prev != null) {
            deque.addFirst(current.board);
            current = current.prev;
        }
        deque.addFirst(current.board);

        if (current.board.equals(initial)) {
            this.solution = deque;
            this.moves = boardMoves;
        } else {
            this.solution = null;
            this.moves = -1;
        }
    }

    public boolean isSolvable() {
        return moves != -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution == null ? null : new ArrayList<>(solution);
    }

    private static class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode prev;
        private final int moves;
        private final int manhattan;

        SearchNode(Board board, SearchNode prev) {
            this.board = board;
            this.prev = prev;
            this.moves = prev == null ? 0 : prev.moves + 1;
            this.manhattan = board.manhattan();
        }

        @Override
        public int compareTo(SearchNode that) {
            return Integer.compare(manhattan + moves, that.manhattan + that.moves);
        }
    }
}
