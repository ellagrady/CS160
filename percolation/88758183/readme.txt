/* *****************************************************************************
 *  Name:   Ella Grady
 *  CS 160
 *  September 20, 2021
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 *
 *  Operating system:
 *  Compiler:
 *  Text editor / IDE:
 *
 *  Have you taken (part of) this course before:
 *  Have you taken (part of) the Coursera course Algorithms, Part I or II:
 *
 *  Hours to complete assignment (optional): 25+
 *
 **************************************************************************** */

Programming Assignment 1: Percolation


/* *****************************************************************************
 *  Describe the data structures (i.e., instance variables) you used to
 *  implement the Percolation API.
 **************************************************************************** */
To implement Percolation I used several instance variables. I had simple int variables to
track the size of the array, the virtual-top node, virtual-bottom node, and number
of open sites. I also had a boolean 2d array to hold the values of whether the sites were
open. Finally, I used two WeightedQuickUF objects to one, keep track of the full percolation
grid, including the bottom node, and a second one to handle backwash that was essentially
the same as the first one, but without the connection to the bottom node.


/* *****************************************************************************
 *  Briefly describe the algorithms you used to implement each method in
 *  the Percolation API.
 **************************************************************************** */
open(): After I validate that the row and column were not out of bounds, I converted
    2d indexes to a 1d index that could be unioned. Next I checked if the site in the
    boolean array was already open, and if it wasn't I began moving through the possible
    cases for neighbors. If the row was the first row, I unioned the site to the virtual-top.
    If the row was the bottom row, I unioned the site to the virtual-bottom. I also marked
    the site as open in the boolean array, and incremented the count of open sites. Next I
    checked where the row and column were, and once determing that I checked which of its
    neighbors were open, and unioned the current site to its open neighbors.
isOpen(): Once I validated that the row and column were within bounds, I simply returned the
    value of the boolean array at the site of row, column.
isFull(): Once I validated that the row and column were within bounds, I converted the 2d
    indexes of row and column to a 1d index that was then used to return whether the current
    index was connected to the virtual-top node.
numberOfOpenSites(): Throughout the open() method, when I would set the boolean array values
    to true, I would also increment the instance variable I created to count the number of
    open sites. For this method, I simply returned by variable numOpen that is updated by open().
percolates(): This method returns the boolean value of whether the virtual-top and virtual-bottom
    nodes are connected in the overall WeightedQuickUF object.

/* *****************************************************************************
 *  Perform computational experiments to estimate the running time of
 *  PercolationStats.java for various values of n and T when implementing
 *  Percolation.java with QuickFindUF.java (not QuickUnionUF.java). Use a
 *  "doubling" hypothesis, where you successively increase either n or T by
 *  a constant multiplicative factor (not necessarily 2).
 *
 *  To do so, fill in the two tables below. Each table must have 5-10
 *  data points, ranging in time from around 0.25 seconds for the smallest
 *  data point to around 30 seconds for the largest one. Do not include
 *  data points that take less than 0.25 seconds.
 **************************************************************************** */

(keep T constant)
 T = 100
 multiplicative factor (for n) = 1.5

 n          time (seconds)       ratio         log ratio
--------------------------------------------------------
200         0.65
300         1.9                 2.923           1.547
450         5.198               2.736           1.452
675         18.741              3.605           1.849
1013        69.437              3.705           1.889



(keep n constant)
 n = 100
 multiplicative factor (for T) = 4

 T          time (seconds)       ratio         log ratio
--------------------------------------------------------
300         0.359
1200        1.221               3.401           1.766
4800        4.07                3.333           1.737
19200       16.13               3.963           1.987
76800       63.084              3.910           1.967



/* *****************************************************************************
 *  Using the empirical data from the above two tables, give a formula
 *  (using tilde notation) for the running time (in seconds) of
 *  PercolationStats.java as function of both n and T, such as
 *
 *       ~ 5.3*10^-8 * n^5.0 * T^1.5
 *
 *  Briefly explain how you determined the formula for the running time.
 *  Recall that with tilde notation, you include both the coefficient
 *  and exponents of the leading term (but not lower-order terms).
 *  Use two significant figures for each coefficient and exponent
 *  (e.g., 5.3*10^-8 or 5.0).
 **************************************************************************** */

QuickFindUF running time (in seconds) as a function of n and T:

    ~9.4*10^-7 * n^4.4 *T^2.0
       _______________________________________
    To find the tilde notation I found at what values of n and T T(N) and T(T),
    respectively, equals approximately 1.  T(n) equals 1 around n=245, and T(T)
    equals 1 around T = 1030. Next, using the formula T(n) = a*n^b where b = logx/logy,
    I found that T(n) = 2.769*10^-11 * n^4.419 and T(T) = 9.426*10^-7 * T^2. Combining
    those, the running time for WeightedQuickUnionUF is 9.4*10^-7 * n^4.4 *T^2.0.


/* *****************************************************************************
 *  Repeat the previous two questions, but using WeightedQuickUnionUF
 *  (instead of QuickFindUF).
 **************************************************************************** */

(keep T constant)
 T = 100
 multiplicative factor (for n) = 2

 n          time (seconds)       ratio         log ratio
--------------------------------------------------------
200         0.311
400         1.011               3.251           1.701
800         3.76                3.719           1.895
1600        27.117              5.616           2.489
3200        152.223             5.613           2.489



(keep n constant)
 n = 100
 multiplicative factor (for T) = 5

 T          time (seconds)       ratio         log ratio
--------------------------------------------------------
500         0.341
2500        1.308               3.836           1.939
12500       5.866               4.485           2.165
62500       27.795              4.769           2.254
312500      138.04              4.966           2.312


WeightedQuickUnionUF running time (in seconds) as a function of n and T:

    ~1.9*10^4.0 * n^3.0 * T^1.3
       _______________________________________
    To find the tilde notation I found at what values of n and T T(N) and T(T),
    respectively, equals approximately 1.  T(n) equals 1 around n=400, and T(T)
    equals 1 around T = 2000. Next, using the formula T(n) = a*n^b where b = logx/logy,
    I found that T(n) = 1.56*10^-8 * n^3 and T(T) = 1.96*10^4 * T^1.3. Combining those,
    the running time for WeightedQuickUnionUF is 1.9*10^4.0 * n^3.0 * T^1.3.



/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */




/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **************************************************************************** */

I attended Professor Han's office hours on Wednesday 9/15, Josh's TA hours
on Wednesday 9/15, and Jack's TA hours on Thursday 9/16.

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */

I struggled for days because my percolation model was only unioning from the bottom
up but not from the top down and could not figure it out. Jack was able to help
me figure out what was causing that.


/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */

