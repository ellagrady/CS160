/* *****************************************************************************
 *  Ella Grady
 *  CS 160
 *  September 20, 2021
 *  Percolation.java
 *
 *  Description: creates a model of a percolation system of size n
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid; // array of boolean values
    private int length; // length of array
    private int top; // virtual-top node
    private int bottom; // virtual-bottom node
    private WeightedQuickUnionUF weightedGrid; // union find object, WeightedQuickUnionUF test
    private WeightedQuickUnionUF weightedFull;
    // union find object to handle backwash, WeightedQuickUnionUF test
    // private QuickUnionUF weightedGrid; // union find object, QuickUnionUF test
    // private QuickUnionUF weightedFull; // union find object to handle backwash, QuickUnionUF test
    private int numOpen; // count for number of open elements in grid

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        length = n;
        grid = new boolean[n][n];
        top = (n * n);
        bottom = (n * n) + 1;
        weightedGrid = new WeightedQuickUnionUF(n * n + 3); // WeightedQuickUnionUF test
        weightedFull = new WeightedQuickUnionUF(n * n + 2); // WeightedQuickUnionUF test
        // weightedGrid = new QuickUnionUF(n * n + 3); // QuickUnionUF test
        // weightedFull = new QuickUnionUF(n * n + 2); // QuickUnionUF test
        numOpen = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                grid[r][c] = false;
            }
        }

        /* for (int c = 0; c < length; c++) {
            int topIndex = convert(0, c);
            weightedGrid.union(top, topIndex);

            int bottomIndex = convert(length - 1, c);
            weightedGrid.union(bottom, bottomIndex);
        }*/
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateSite(row, col);

        int index = convert(row, col);

        if (!isOpen(row, col)) {


            // int index = convert(row, col);

            if (row == 0) {
                weightedGrid.union(index, top);
                weightedFull.union(index, top);
            }
            if (row == length - 1) {
                weightedGrid.union(index, bottom);
            }
            grid[row][col] = true;
            numOpen++;
            if ((row - 1) >= 0) {
                // if (validIndices(row - 1, col)) {
                if (isOpen(row - 1, col)) {
                    weightedGrid.union(index, convert(row - 1, col));
                    /* StdOut.println("row - 1, col");
                    StdOut.println("index: " + index);
                    StdOut.println("convert: " + convert(row - 1, col)); */
                    weightedFull.union(index, convert(row - 1, col));
                }
            }
            if (length > (row + 1)) {
                // if (validIndices(row + 1, col)) {
                if (isOpen(row + 1, col)) {
                    weightedGrid.union(index, convert(row + 1, col));
                    /* StdOut.println("row + 1, col");
                    StdOut.println("index: " + index);
                    StdOut.println("convert: " + convert(row + 1, col)); */
                    weightedFull.union(index, convert(row + 1, col));
                }
            }
            if ((col - 1) >= 0) {
                // if (validIndices(row, col - 1)) {
                if (isOpen(row, col - 1)) {
                    weightedGrid.union(index, convert(row, col - 1));
                    /* StdOut.println("row, col - 1");
                    StdOut.println("index: " + index);
                    StdOut.println("convert: " + convert(row, col - 1)); */
                    weightedFull.union(index, convert(row, col - 1));
                }
            }
            if (length > (col + 1)) {
                // if (validIndices(row, col + 1)) {
                if (isOpen(row, col + 1)) {
                    weightedGrid.union(index, convert(row, col + 1));
                    /* StdOut.println("row, col + 1");
                    StdOut.println("index: " + index);
                    StdOut.println("convert: " + convert(row - 1, col)); */
                    weightedFull.union(index, convert(row, col + 1));
                }
            }
        }
    }

    /* // checks if given indices are valid for the grid
    private boolean validIndices(int row, int col) {
        return (row > 0 && col > 0 && row < length && col < length);
    } */

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateSite(row, col);
        // StdOut.println(row);
        // StdOut.println(col);
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateSite(row, col);
        // StdOut.println(row);
        // StdOut.println(col);

        /* if (!grid[row][col]) {
            return false;
        } */

        int index = convert(row, col);
        // StdOut.println(top);
        // StdOut.println(index);
        return weightedFull.connected(index, top);

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        // StdOut.println("find(top) " + weightedGrid.find(top));
        // StdOut.println("find(bottom) " + weightedGrid.find(bottom));
        return (weightedGrid.connected(top, bottom));
    }

    // converts 2d indices to 1d index
    private int convert(int row, int col) {
        validateSite(row, col);
        // StdOut.println("row " + row);
        // StdOut.println("col " + col);
        // StdOut.println("length " + length);
        // StdOut.println("index " + ((row * length) + col));
        return (row * length) + col;
    }

    // throws out of bounds exception if element's indices aren't valid
    private void validateSite(int row, int col) {
        if (row < 0 || row > length - 1 || col < 0 || col > length - 1) {
            throw new IllegalArgumentException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Percolation per = new Percolation(2);
        StdOut.println(per.isOpen(1, 1));
        per.open(1, 1);
        StdOut.println(per.isOpen(1, 1));
        StdOut.println(per.isOpen(0, 1));
        per.open(0, 1);
        StdOut.println(per.isOpen(0, 1));
        StdOut.println(per.percolates());
    }


}
