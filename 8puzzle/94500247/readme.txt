/* *****************************************************************************
 *  Name:     Ella Grady
 *  NetID:
 *  Precept:

 *
 *  Hours to complete assignment (optional): 15
 *
 **************************************************************************** */

Programming Assignment 4: Slider Puzzle


/* *****************************************************************************
 *  Explain briefly how you represented the Board data type.
 **************************************************************************** */
 To represent the board data type I used instance variables 2d array for board, int size,
 int hammingCount for hamming distance, and int manhattanDistance to count
 the manhattan distance for the constructor Board(int[][] tiles). The method
 toString() creates a string representation of a board. tileAt(row, col) returns
 the tile at a given position, size() returns size of board, hamming() returns
 the number of tiles out of place, manhattan() returns the sum of the manhattan
 distances between the tiles and goal, isGoal() checks if the board is the goal board
 equals(object y) checks if the board is equal to another given object. neighbors()
 generates all the neighboring boards of the current board, and isSolvable() checks
 if the current board is solvable.



/* *****************************************************************************
 *  Explain briefly how you represented a search node
 *  (board + number of moves + previous search node).
 **************************************************************************** */

 To represent a search node I used a private comparable class that included instance
 variables for a board, the number of moves, the priority of a board, and the searchNode
 previousNode. The SearchNode constructor took input of a board, number of moves, and
 a previousNode then set the instance variables equal to each of those inputs. The priority
 variable was set to equal the sum of the number of moves and the manhattan distance for the
 board. Finally a compareTo(searchNode that) was used to compare two SearchNode objects based
 on the difference of their priorities.




/* *****************************************************************************
 *  Explain briefly how you detected unsolvable puzzles.
 *
 *  What is the order of growth of the running time of your isSolvable()
 *  method in the worst case as function of the board size n? Use big Theta
 *  notation to simplify your answer, e.g., Theta(n log n) or Theta(n^3).
 **************************************************************************** */

Description: For isSolvable() a boolean is initialy set to false then a inversions counter
is set to 0. There are four nested for statements to look at two different elements in
the board to compare if the first element is less than the second but occurs after the
second element in the board, where it increments inversions by one if it is. Then the
algorithm checks if the board size is even, and if it is, finds the coordinates of the
empty tile in the board. Then it finds the sum of the inversions and the empty tile's row
and if that sum is odd, then the board is solvable so it sets the initial boolean variable
to be true. If the board size is odd, however and the number of inversions is even, then
the board is solvable so the initial boolean variable is set to be true. Finally, the
algorithim will return the boolean value.



Order of growth of running time: Theta( n^4 )



/* *****************************************************************************
 *  For each of the following instances, give the minimum number of moves to
 *  solve the instance (as reported by your program). Also, give the amount
 *  of time your program takes with both the Hamming and Manhattan priority
 *  functions. If your program can't solve the instance in a reasonable
 *  amount of time (say, 5 minutes) or memory, indicate that instead. Note
 *  that your program may be able to solve puzzle[xx].txt even if it can't
 *  solve puzzle[yy].txt and xx > yy.
 **************************************************************************** */


                 min number          seconds
     instance     of moves     Hamming     Manhattan
   ------------  ----------   ----------   ----------
   puzzle28.txt     28          0.47        0.069
   puzzle30.txt     30          0.06        0.073
   puzzle32.txt     32          0.731       0.905
   puzzle34.txt     34          0.234       0.319
   puzzle36.txt     36          2.781       2.935
   puzzle38.txt     38          0.054       0.061
   puzzle40.txt     40          0.829       0.943
   puzzle42.txt     42          4.111       4.146



/* *****************************************************************************
 *  If you wanted to solve random 4-by-4 or 5-by-5 puzzles, which
 *  would you prefer: a faster computer (say, 2x as fast), more memory
 *  (say 2x as much), a better priority queue (say, 2x as fast),
 *  or a better priority function (say, one on the order of improvement
 *  from Hamming to Manhattan)? Why?
 **************************************************************************** */

If I wanted to solve random 4x4 or 5x5 puzzles I would want a better priority function
because if the priority function was more efficient it would likely solve the other
issues, allowing for it to be solved quicker and using less memory if I check
to ensure that there are no repeats of neighbors still.




/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */



/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **************************************************************************** */

I attended ta hours with Jack on Thursday night.



/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */

I encountered an OutofMemory heap space error that Jack helped me solve because I
was unnecessarily creating a goal board for each board in the priority queue so I
was doubling the memory I was using for no reason.

/* *****************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 **************************************************************************** */







/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
