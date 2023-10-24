/* *****************************************************************************
 *  Name:    Ella Grady
 *  NetID:   aturing
 *  Precept: P00
 *
 *
 *  Description: Creates a data type that models an n-by-n board with
 *      sliding tiles. Uses instance variables 2d array for board, int size,
 *      int hammingCount for hamming distance, and int manhattanDistance to count
 *      the manhattan distance for the constructor Board(int[][] tiles). The method
 *      toString() creates a string representation of a board. tileAt(row, col) returns
 *      the tile at a given position, size() returns size of board, hamming() returns
 *      the number of tiles out of place, manhattan() returns the sum of the manhattan
 *      distances between the tiles and goal, isGoal() checks if the board is the goal board
 *      equals(object y) checks if the board is equal to another given object. neighbors()
 *      generates all the neighboring boards of the current board, and isSolvable() checks
 *      if the current board is solvable.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int[][] board; // instance of 2d array for board
    private int size; // instance for size of board
    private int hammingCount; // hamming distance count
    private int manhattanDistance; // manhattan distance count

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.size = tiles.length; // sets size to length of board
        this.board = new int[size][size]; // creates board of size of input board
        hammingCount = 0; // sets hamming equal to 0 to begin
        manhattanDistance = 0; // sets manhattan equal to 0 to begin

        // move through board
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // sets each element in board equal to tiles' equivalent element
                this.board[i][j] = tiles[i][j];
                // if the tile is not equal to the goal tile
                if (this.board[i][j] != i * size + j + 1) {
                    if (this.board[i][j] == 0) { // if tile is 0 don't do anything
                        hammingCount += 0;
                        manhattanDistance += 0;
                    }
                    else {
                        // if tile is not 0
                        hammingCount += 1; // increment hamming by one
                        int goal = this.board[i][j] - 1;
                        manhattanDistance += (Math.abs((goal / size) - i) + Math
                                .abs((goal % size) - j)); // calculate the manhattan distance
                        // StdOut.println("increase of mannhattan " + (Math.abs(goal[0] - i) + Math
                        // .abs(goal[1] - j)));
                    }
                }
            }
        }
        // set the final position
        this.board[size - 1][size - 1] = tiles[size - 1][size - 1];
    }

    // string representation of this board
    public String toString() {
        String print = "";
        print += size + "\n";
        // increment through board
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                print += String.format("%2d ", (board[i][j]));
            }
            print += "\n";
        }
        return print;
    }

    // tile at (row, col) or 0 if blank
    public int tileAt(int row, int col) {
        // if the position is within the board's range return the element
        if (row >= 0 && row < size && col >= 0 && col < size) {
            return board[row][col];
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    // board size n{
    public int size() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        return hammingCount;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        // if the board is the goal the hamming count will be equal to 0
        return 0 == hammingCount;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true; // if they are same return true
        if (y == null) return false; // if input is null return false
        // if classes not same return false
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y; // create a board object out of y
        if (this.size != that.size) { // if sizes not same return false
            return false;
        }
        // otherwise increment through the board
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                // if any element is not the same return false
                if (that.board[i][j] != this.board[i][j]) {
                    return false;
                }
            }
        }
        // otherwise return true
        return true;
    }

    // finds position in board of given element
    private int[] position(int element) {
        int[] position = new int[2]; // creates array for the coordinates
        // increments through board
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // checks if given tile is equal to given element
                if (tileAt(i, j) == element) {
                    position[0] = i;
                    position[1] = j;
                }
            }
        }
        return position;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int[] zero = position(0); // finds position of empty tile
        int x_zero = zero[0]; // x coordinate of empty tile
        int y_zero = zero[1]; // y coordinate of empty tile

        Stack<Board> neighbors = new Stack<Board>();
        // creates new boards for each possible neighbor and adds to the stack
        if (x_zero > 0) { // if the empty tile has a left neighbor, swap positions
            neighbors.push(new Board(swap(x_zero, y_zero, x_zero - 1, y_zero)));
        }
        if (y_zero > 0) { // if the empty tile has a neighbor above, swap positions
            neighbors.push(new Board(swap(x_zero, y_zero, x_zero, y_zero - 1)));
        }
        if (x_zero < size - 1) { // if the empty tile has a right neighbor, swap positions
            neighbors.push(new Board(swap(x_zero, y_zero, x_zero + 1, y_zero)));
        }
        if (y_zero < size - 1) { // if the empty tile has a neighbor below, swap positions
            neighbors.push(new Board(swap(x_zero, y_zero, x_zero, y_zero + 1)));
        }

        return neighbors;
    }

    // makes a copy of the board
    private int[][] copy(int[][] tiles) {
        int[][] copy = new int[size][size]; // copy of board with same dimensions
        // increments through board making each position in copy equal to tiles'
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                copy[i][j] = tiles[i][j];
            }
        }
        return copy;
    }

    // swaps element at one position with a second position
    private int[][] swap(int rowone, int colone, int rowtwo, int coltwo) {
        int[][] copied = copy(board); // creates copy of the board
        int temp = copied[rowone][colone]; // sets temp to first element
        // sets first element's position to the second element
        copied[rowone][colone] = copied[rowtwo][coltwo];
        // sets second element's position to the first element
        copied[rowtwo][coltwo] = temp;
        return copied;
    }

    // is this board solvable?
    public boolean isSolvable() {
        boolean solvable = false; // whether board is solvable
        int inversions = 0; // count for num inversions
        if (isGoal()) {
            solvable = true;
            return solvable;
        }
        // increments through board with two elements
        for (int first_r = 0; first_r < size; first_r++) {
            for (int first_c = 0; first_c < size; first_c++) {
                for (int second_r = 0; second_r < size; second_r++) {
                    for (int second_c = 0; second_c < size; second_c++) {
                        int first = board[first_r][first_c]; // first element
                        int second = board[second_r][second_c]; // second element
                        // if the first element is less than second element
                        if (first < second && first != 0 && second != 0) {
                            // if the first element occurs after the second element,
                            if (first_r > second_r) {
                                inversions++;
                            }
                            if (first_r == second_r && first_c > second_c) {
                                inversions++;
                            }
                        }
                    }
                }
            }
        }

        // if the size is even
        if (size % 2 == 0) {
            // finds coordinates of empty tile
            int[] zeroPosition = position(0);
            /// finds sum of inversions and the row of empty tile
            int sum = inversions + zeroPosition[0];
            // if the sum is odd, board is solvable
            if (sum % 2 != 0) {
                solvable = true;
            }
        }
        // if board size is odd
        else {
            // if number of inversions is even board is solvable
            if (inversions % 2 == 0) {
                solvable = true;
            }
        }
        return solvable;
    }

    // helper method for main testing to convert a 1d array to a board
    private static int[][] arraytoboard(int[] arr, int width) {
        int[][] test = new int[width][width];
        int idx = 0;

        for (int i = 0; i < test.length; i++) {
            for (int j = 0; j < test.length; j++) {
                test[i][j] = arr[idx++];
            }
        }

        return test;
    }

    // unit testing (required)
    public static void main(String[] args) {
        int[][] test = new int[3][3];
        int[][] goalBoard = new int[4][4];
        int[][] puzzle1 = new int[2][2];
        int[] onepuzzle = { 1, 0, 3, 2 };
        int[] numbers = {
                3, 1, 6, 4,
                5, 0, 9, 7,
                10, 2, 11, 8,
                13, 15, 14, 12
        };
        int[] goal = { 1, 2, 3, 4, 5, 6, 7, 8, 0 };
        int idx = 0;

        test = arraytoboard(numbers, 4);
        goalBoard = arraytoboard(goal, 3);
        puzzle1 = arraytoboard(onepuzzle, 2);

        Board board = new Board(puzzle1);
        StdOut.println(board);

        StdOut.println("Board's size: " + board.size());
        StdOut.println("Is it goal board? " + board.isGoal());
        StdOut.println("is it solvable? " + board.isSolvable());
        StdOut.println("Manhattan: " + board.manhattan());
        StdOut.println("Hamming: " + board.hamming());
        StdOut.println();

        int n = 1;
        Iterable<Board> neighbors = board.neighbors();
        for (Board b : neighbors) {
            StdOut.println("Neighbor " + n++);
            StdOut.println(b);
        }


        StdOut.println("Is board equal to board? " + board.equals(board));
        StdOut.println("Is board equal to null? " + board.equals(null));
    }
}
