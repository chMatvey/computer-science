package com.github.chMatvey.algorithms.module4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Board {
    private final int[][] tiles;
    private final int dimension;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        int n = tiles.length;
        if (n < 2 || n >= 128)
            throw new IllegalArgumentException("Tiles size must be 2 ≤ n < 128");
        for (int i = 0; i < n; i++)
            if (tiles[i].length != n)
                throw new IllegalArgumentException("Incorrect size of tile at index " + i);

        this.dimension = n;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, n);
    }

    // create a bord and swap tile at first position to second
    private Board(int[][] tiles, Position first, Position second) {
        this.dimension = tiles.length;
        this.tiles = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++)
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, dimension);

        int temp = this.tiles[first.i][first.j];
        this.tiles[first.i][first.j] = this.tiles[second.i][second.j];
        this.tiles[second.i][second.j] = temp;
    }

    public int dimension() {
        return dimension;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (isWrongPosition(i, j)) hamming++;

        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (isWrongPosition(i, j))
                    manhattan += manhattanDistance(i, j);

        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> result = new ArrayList<>();
        Position blankPosition = blankPosition();
        int i = blankPosition.i;
        int j = blankPosition.j;

        if (i > 0)
            result.add(new Board(tiles, blankPosition, new Position(i - 1, j)));
        if (i < dimension - 1)
            result.add(new Board(tiles, blankPosition, new Position(i + 1, j)));
        if (j > 0)
            result.add(new Board(tiles, blankPosition, new Position(i, j - 1)));
        if (j < dimension - 1)
            result.add(new Board(tiles, blankPosition, new Position(i, j + 1)));

        return result;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (tiles[0][0] != 0 && tiles[0][1] != 0)
            return new Board(tiles, new Position(0, 0), new Position(0, 1));
        else
            return new Board(tiles, new Position(1, 0), new Position(1, 1));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Board board = (Board) object;
        return Arrays.deepEquals(tiles, board.tiles);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(tiles.length);
        for (int[] tile : tiles)
            builder.append("\n ")
                    .append(Arrays.stream(tile).boxed()
                            .map(Object::toString)
                            .collect(Collectors.joining("  ")));
        return builder.toString();
    }

    private boolean isWrongPosition(int i, int j) {
        return tiles[i][j] != 0 && tiles[i][j] != i * dimension + j + 1;
    }

    private int manhattanDistance(int i, int j) {
        // position = index + 1
        // index = row * n + col, where col < n
        int index = tiles[i][j] - 1;
        int row = Math.abs(index / dimension - i);
        int col = Math.abs(index % dimension - j);
        return row + col;
    }

    private Position blankPosition() {
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (tiles[i][j] == 0) return new Position(i, j);

        throw new RuntimeException("Can not find blank position");
    }

    private static class Position {
        private final int i, j;

        public Position(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            Position position = (Position) object;
            if (i != position.i) return false;
            return j == position.j;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j);
        }
    }
}
