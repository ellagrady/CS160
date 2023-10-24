Programming Assignment 5: K-d Trees
Nina Carlson & Ella Grady
CSCI 160
11/2/21


/* *****************************************************************************
 *  Describe the Node data type you used to implement the
 *  2d-tree data structure.
 **************************************************************************** */
 We have a private class node in which we initialize a left subtree, right subtree, bounding box,
 a point, a value associated with that point, and an integer value representing the depth of the
 node. We then have a node constructor within the private class in which we initialize all of these
 variables to create a node object.

/* *****************************************************************************
 *  Describe your method for range search in a k-d tree.
 **************************************************************************** */
First we determine that the bounding box is not null. Then we create a queue to
hold all of the points contained within the bounding box or on its boundaries. Then
we make a call to our private helper method range(), using the root.

The private helper method takes the current node x, the bounding box rect, and the
queue of contained points contained. We make recursive calls to add all of the appropriate
points to the contained queue. First we check if the current node is null and if so we simply
return the empty queue. This is the base case. Then we check if the input rectangle intersects the
node's rectangle. If it does we check further to see if the rectangle contains the node's point. If both of
these conditions are satisfied we ass the node's point to the queue. Then we recursively call range() on node's
left and right children. Lastly we return the queue.


/* *****************************************************************************
 *  Describe your method for nearest neighbor search in a k-d tree.
 **************************************************************************** */
 For nearest(), we created a new point of type Point2D called nearest that is equal
 to the root's point initially. We make a call to the private helper method nearest() in
 the public method, using the root as the cloeset neighbor to point p.

 The private helper method nearest() takes a point p, a node neighbor representing
 the closest neighbor to point p, and a point nearest representing the closest point
 to point p. First we check whether the neighbor is null. If the neighbor node is null,
 then the nearest input is the point nearest. Otherwise, if the distance squared between the neighbor's
 rectangle and the point is greater than the distance squared between nearest point and p, return nearest.
 If the distance squared between p and neighbor's point is less than the distance squared between p
 and the nearest point, set the nearest point to be the neighbor's point. If the left subtree of the
 neighbor node is not null and the node's rectangle contains p, then make a recursive call on the left
 and right subtress. Otherwise, just make recursive calls on the left and right subtrees.


/* *****************************************************************************
 *  How many nearest-neighbor calculations can your PointST implementation
 *  perform per second for input1M.txt (1 million points), where the query
 *  points are random points in the unit square?
 *
 *  Fill in the table below, using one digit after the decimal point
 *  for each entry. Use at least 1 second of CPU time. Do not use -Xint.
 *  (Do not count the time to read the points or to build the 2d-tree.)
 *
 *  Repeat the same question but with your KdTreeST implementation.
 *
 **************************************************************************** */


                 # calls to         /   CPU time     =   # calls to nearest()
                 client nearest()       (seconds)        per second
                ------------------------------------------------------
PointST:           50                   3.3               15.1

KdTreeST:          1000000              4.8               209150.3

Note: more calls per second indicates better performance.


/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */
No known bugs/limitations to our knowledge.

/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **************************************************************************** */
TA hours on 10/28/21 - Jack
TA hours on 11/1/21 - Josh

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */
no serious problems.

/* *****************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 **************************************************************************** */

We assert that we followed the protocol as described on the assignment page. Ella and Nina
coded and shared ideas together in the same room at all times. Ella and Nina also worked on the
readme.txt file together and attended TA hours togther (except for Monday).


/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on  how helpful the class meeting was and on how much you learned
 * from doing the assignment, and whether you enjoyed doing it.
 **************************************************************************** */
No other comments.
