Programming Assignment 7: Seam Carving
Ella Grady
December 6, 2021
CS 160

/* *****************************************************************************
 *  Describe concisely your algorithm to find a horizontal (or vertical)
 *  seam.
 **************************************************************************** */
    To find the vertical seam my algorithm begins by converting the picture
    to 2d array of energy values. Two more 2d arrays are created, distTo to hold
    all of the energies and edgeTo to hold all of the columns. Next, if the value
    in distTo at one row past the current element is greater than sum of distTo
    and energymatrix that element is set to the sum of the current element in distTo
    and the element in edgeTo at one row past the current indicies. Next, if the
    current column - 1 will not be out of range the same check as before is done
    on the column before the previous one and row ahead of the current one. Next,
    if the current column + 1 will not be out of range the same check as before is
    done on the column ahead of the previous one and row ahead of the current one.
    For every column in the bottom row of the distTo array the element was compared
    to the minenergy value, initially set to positive infinity. If the current elemnt
    was less, then the minenergy was set to that value and the min col was set to the
    current column. Finally, the array for the seam was added to, accessing the
    values in edgeTo at each row in the mincol.


/* *****************************************************************************
 *  Describe what makes an image suitable to the seam-carving approach
 *  (in terms of preserving the content and structure of the original
 *  image, without introducing visual artifacts). Describe an image that
 *  would not work well.
 **************************************************************************** */
    An image is suitable to the seam-carving approach if there is an area of the
    picture that does not have major components and is relatively clear, with only
    the background making it up, as this will allow for seam-carving that does
    not disrupt or remove major parts of the picture. If an image was not like this
    it would not work well for this approach as it would ruin the focus and integrity
    of the picture.


/* *****************************************************************************
 *  Perform computational experiments to estimate the running time to reduce
 *  a W-by-H image by one column and one row (i.e., one call each to
 *  findVerticalSeam(), removeVerticalSeam(), findHorizontalSeam(), and
 *  removeHorizontalSeam()). Use a "doubling" hypothesis, where you
 *  successively increase either W or H by a constant multiplicative
 *  factor (not necessarily 2).
 *
 *  To do so, fill in the two tables below. Each table must have 5-10
 *  data points, ranging in time from around 0.25 seconds for the smallest
 *  data point to around 30 seconds for the largest one.
 **************************************************************************** */

(keep W constant)
 W = 2000
 multiplicative factor (for H) = x2

 H           time (seconds)      ratio       log ratio
------------------------------------------------------
125             0.273
250             0.401           1.47        0.56
500             0.54            1.32        0.40
1000            0.917           1.69        0.76
2000            1.64            1.78        0.83
4000            3.183           1.94        0.96
8000            6.469           2.09        1.06
16000           14.109          2.18        1.12
32000           37.153          2.63        1.40

(keep H constant)
 H = 2000
 multiplicative factor (for W) = x2

 W           time (seconds)      ratio       log ratio
------------------------------------------------------
250             0.286
500             0.495            1.73           0.79
1000            0.946            1.91           0.93
2000            1.651            1.75           0.81
4000            3.183            1.93           0.95
8000            6.251            1.96           0.97
16000           13.033           2.08           1.06
32000           34.941           2.68           1.42


/* *****************************************************************************
 *  Using the empirical data from the above two tables, give a formula
 *  (using tilde notation) for the running time (in seconds) as a function
 *  of both W and H, such as
 *
 *       ~ 5.3*10^-8 * W^5.1 * H^1.5
 *
 *  Briefly explain how you determined the formula for the running time.
 *  Recall that with tilde notation, you include both the coefficient
 *  and exponents of the leading term (but not lower-order terms).
 *  Round each coefficient and exponent to two significant digits.
 **************************************************************************** */


Running time (in seconds) to find and remove one horizontal seam and one
vertical seam, as a function of both W and H:


    ~  3.287 × 10^-7 * W^1.4 * H^1.42
       To find the running time I calculated the exponents using the equation
       log(ratio)/log(multi. factor), and then calculated the coefficient using
       data points from both of the tables.




/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */


/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **************************************************************************** */


/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */


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
