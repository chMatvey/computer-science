package com.github.chMatvey.algorithms.module1.percolation;

import com.github.chMatvey.algorithms.module1.uf.UF;
import com.github.chMatvey.algorithms.module1.uf.WeightedQuickUnionUF;

/** Row and Col indexes start with 1 **/
public class PercolationImpl implements Percolation {
    private final boolean[][] table;
    private final int size;
    private final int virtualTop = 0;
    private final int virtualBottom;
    private final UF uf;

    private int opened = 0;

    /** O(n^2) **/
    public PercolationImpl(int n) {
        if (n < 1)
            throw new IllegalArgumentException("n must be more than zero");
        table = new boolean[n][n];
        size = n;
        virtualBottom = n * n + 1;
        uf = new WeightedQuickUnionUF(virtualBottom + 1);
    }

    @Override
    public void open(int row, int col) {
        checkRange(row, col);
        if (table[row - 1][col - 1])
            return;

        table[row - 1][col - 1] = true;
        opened++;
        int currIndex = toIndex(row, col);

        if (row == 1)
            uf.union(virtualTop, currIndex);
        else if (row == size)
            uf.union(virtualBottom, currIndex);

        if (hasTopOpenNeighbor(row, col))
            uf.union(currIndex, toIndex(row - 1, col));

        if (hasRightOpenNeighbor(row, col))
            uf.union(currIndex, toIndex(row, col + 1));

        if (hasBottomOpenNeighbor(row, col))
            uf.union(currIndex, toIndex(row + 1, col));

        if (hasLeftOpenNeighbor(row, col))
            uf.union(currIndex, toIndex(row, col - 1));
    }

    @Override
    public boolean isOpen(int row, int col) {
        checkRange(row, col);
        return table[row - 1][col - 1];
    }

    @Override
    public boolean isFull(int row, int col) {
        checkRange(row, col);
        return uf.connected(toIndex(row, col), virtualTop);
    }

    @Override
    public int numberOfOpenSites() {
        return opened;
    }

    @Override
    public boolean percolates() {
        return uf.connected(virtualTop, virtualBottom);
    }

    private boolean hasTopOpenNeighbor(int row, int col) {
        return row > 1 && isOpen(row - 1, col);
    }

    private boolean hasRightOpenNeighbor(int row, int col) {
        return col < size && isOpen(row, col + 1);
    }

    private boolean hasBottomOpenNeighbor(int row, int col) {
        return row < size && isOpen(row  + 1, col);
    }

    private boolean hasLeftOpenNeighbor(int row, int col) {
        return col > 1 && isOpen(row, col - 1);
    }

    private int toIndex(int row, int col) {
        return size * (row - 1) + col;
    }

    private void checkRange(int row, int col) {
        if (row < 1 || row > size)
            throw new IllegalArgumentException("Row outside the range");
        if (col < 1 || col > size)
            throw new IllegalArgumentException("Col outside the range");
    }
}
