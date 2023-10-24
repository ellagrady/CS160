/**
 * Compilation:  javac KdTreeST.java
 * Execution:    java KdTreeST value
 * <p>
 * Mutable data type KdTreeST.java that uses a 2d-tree to implement the same API as PointST.java
 * A 2D tree is a generalization of a BST to two dimensional keys.
 * Build a BST with points in the nodes, using the x and y coordinates of the points as keys
 * in stricly alternating sequence, starting with the x coordinates.
 * <p>
 *
 * @author Nina Carlson & Ella Grady
 * CSCI 160
 * 11/2/21
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StopwatchCPU;

public class KdTreeST<Value> {

    private Node root; // root of the tree
    private int size; // size of the tree

    private class Node {
        private Node left; // left subtree
        private Node right; // right subtree
        private Point2D point; // point
        private Value value; // associated value data
        private RectHV rectangle; // bounding box
        private int depth; // depth


        /**
         * Node constructor
         *
         * @param point     - point of type Point2D
         * @param rectangle - bounding box of type RectHV
         * @param value     - value of type Value
         * @param depth     - int value representing depth of the node
         */
        public Node(Point2D point, RectHV rectangle, Value value, int depth) {
            this.point = point; // point
            this.rectangle = rectangle; // rectangle for point
            this.value = value; // value of point
            this.depth = depth; // depth of node
            //StdOut.println(point + " " + depth);

            right = null; // node's right child
            left = null; // node's left child

        }
    }


    /**
     * construct an empty KdTreeST
     */
    public KdTreeST() {
        root = null;
        size = 0;

    }

    // is the symbol table empty?

    /**
     * Determine whether the symbol table is empty
     *
     * @return - boolean representing empty or not
     */
    public boolean isEmpty() {
        return size() == 0;
    }


    /**
     * number of points in symbol table
     *
     * @return - int representing size of symbol table
     */
    public int size() {
        //  StdOut.println(size);
        return size;

    }


    /**
     * associate the value val with point p
     * if p is null throw exception
     * if val is null throw exception
     *
     * @param p   - point of type Point2D
     * @param val - value of type Value
     */
    public void put(Point2D p, Value val) {
        // if input null throw exception
        if (p == null || val == null) {
            throw new IllegalArgumentException();
        }
        // full plane rectangle
        double posInfinity = Double.POSITIVE_INFINITY;
        double negInfinity = Double.NEGATIVE_INFINITY;
        // full plane rectangle
        RectHV plane = new RectHV(negInfinity, negInfinity, posInfinity, posInfinity);
        if (root == null) {
            root = new Node(p, plane, val, 0);
        }
        else {
            root = put(root, p, val, null);
        }
        root.rectangle = plane;
        size += 1;
    }

    // helper put method

    /**
     * private helper put method
     * makes recursive calls to determine where on the tree to traverse
     *
     * @param x    - current node
     * @param p    - point of type Point2D
     * @param val  - value associated with p
     * @param node - root node
     * @return - current node x
     */
    private Node put(Node x, Point2D p, Value val, Node node) {
        // if current node is null then bounding box is at root
        if (x == null)
            return new Node(p, makeRectangles(p, node), val, node.depth + 1);
        // if point at current node equals p then it is the same point
        // decrease size of tree since it is the same point
        if (x.point.equals(p)) {
            x.value = val;
            size -= 1;
        }
        // if the current node is on an even level
        else if (x.depth % 2 == 0) {
            // and if point at current node is less than point
            if (p.x() < x.point.x())
                // go to the left of current node
                x.left = put(x.left, p, val, x);
            else
                // otherwise go to the right of current node
                x.right = put(x.right, p, val, x);
        }
        // else the current node is on an odd level so compare y coordinates
        else {
            // if point at current node is less than point
            if (p.y() < x.point.y())
                // go to the left of current node
                x.left = put(x.left, p, val, x);
            else
                // otherwise go right of current node
                x.right = put(x.right, p, val, x);
        }
        return x;
    }


    /**
     * private helper method for making rectangles
     * bounding boxes
     *
     * @param p    - point of type Point2D
     * @param node - current node
     * @return - bounding box of type RectHV
     */
    private RectHV makeRectangles(Point2D p, Node node) {
        RectHV rect; // new rectangle

        // if level is even
        if (node.depth % 2 == 0) {
            // if point's x is less than node's x-coordinate
            if (p.x() < node.point.x()) {
                // create rectangle taking x-coordinate of node in to account as xmax
                return new RectHV(node.rectangle.xmin(), node.rectangle.ymin(), node.point.x(),
                                  node.rectangle.ymax());
            }
            else {
                // create rectangle taking x-coordinate of node in to account as xmin
                return new RectHV(node.point.x(), node.rectangle.ymin(), node.rectangle.xmax(),
                                  node.rectangle.ymax());
            }
        }
        // if level is odd
        else {
            // create rectangle taking y-coordinate of node into account as ymax
            if (p.y() < node.point.y()) {
                return new RectHV(node.rectangle.xmin(), node.rectangle.ymin(),
                                  node.rectangle.xmax(), node.point.y());
            }
            else {
                // create rectangle taking y-coordinate of node into account as ymin
                return new RectHV(node.rectangle.xmin(), node.point.y(), node.rectangle.xmax(),
                                  node.rectangle.ymax());
            }
        }
    }


    /**
     * value associated with point p
     * throw exception if p is null
     *
     * @param p - point of type Point2D
     * @return - value associated with point p
     */
    public Value get(Point2D p) {
        // if input is null throw exception
        if (p == null) {
            throw new IllegalArgumentException();
        }
        // call to helper method
        return get(root, p);
    }


    /**
     * private helper method for get()
     * makes recursive calls to get subtrees
     * throws exception if current node is null
     *
     * @param x - current node
     * @param p - point of type Point2D
     * @return - value associated with point p
     */
    private Value get(Node x, Point2D p) {
        // if node is null return null
        if (x == null) {
            return null;
        }
        // if the node's point equals the point return node's value
        if (x.point.equals(p)) {
            return x.value;
        }
        // if the level is even compare x-coordinates
        if (x.depth % 2 == 0) {
            // if the point's x-coordinate is less than node's
            if (p.x() < x.point.x()) {
                // recursive call for left subtree
                return get(x.left, p);
            }
            // otherwise
            else {
                // recursive call for right subtree
                return get(x.right, p);
            }
        }
        // if odd level compare y-coordinates
        else {
            // if point's y-coordinate is less than node's
            if (p.y() < x.point.y()) {
                // recursive call for left subtree
                return get(x.left, p);
            }
            // otherwise
            else {
                // recursive call for right subtree
                return get(x.right, p);
            }
        }

    }

    /**
     * determine if the symbol table contains point p
     *
     * @param p - point of type Point2D
     * @return - boolean value representing whether or not symbol table contains p
     */
    public boolean contains(Point2D p) {
        return get(p) != null;
    }


    /**
     * all points in the symbol table
     *
     * @return - iterable representing all points in symbol table
     */
    public Iterable<Point2D> points() {
        // if symbol table is empty return empty queue
        if (isEmpty()) {
            return new Queue<Point2D>();
        }
        Queue<Point2D> points = new Queue<Point2D>(); // queue of points
        Queue<Node> nodes = new Queue<Node>(); // queue of nodes
        nodes.enqueue(root); // add root to queue of nodes
        while (!nodes.isEmpty()) { // so long as nodes is not empty
            Node x = nodes.dequeue(); // remove first node from queue
            if (x == null) { // if that node is null go to next iteration of loop
                continue;
            }
            points.enqueue(x.point); // add point of the node to queue of points
            nodes.enqueue(x.left); // add the left subtree to the queue of nodes
            nodes.enqueue(x.right); // add the right subtree to the queue of nodes
        }
        //StdOut.println(points);
        return points; // return queue of all points in symbol table
    }


    /**
     * all points that are inside the rectangle or on the boundary
     * throw exception if rectangle is null
     *
     * @param rect - bounding box of type RectHV
     * @return - iterable representing all points in boundary of rect
     */
    public Iterable<Point2D> range(RectHV rect) {
        // if the rectangle is null throw exception
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        // queue to add points that are in the rectangle
        Queue<Point2D> contained = new Queue<Point2D>();
        if (isEmpty()) {
            return contained;
        }
        // call to helper method
        contained = range(root, rect, contained);

        // return an iterable
        return contained;
    }

    // helper method for range

    /**
     * private helper method for range()
     * makes recursive calls to determine points in rectangle
     *
     * @param x         - current node
     * @param rect      - bounding box of type RectHV
     * @param contained - iterable of points in range
     * @return - Queue of points in bounding box
     */
    private Queue<Point2D> range(Node x, RectHV rect, Queue<Point2D> contained) {
        // base case
        if (x == null) {
            return contained;
        }
        // if the input rectangle intersects the node's rectangle
        if (rect.intersects(x.rectangle)) {
            // and the rectangle contains the node's point
            if (rect.contains(x.point)) {
                // add the node's point to the queue
                contained.enqueue(x.point);
            }
            // recursively call range on node's left and right children
            contained = range(x.left, rect, contained);
            contained = range(x.right, rect, contained);
        }
        // return the queue
        return contained;
    }


    /**
     * a nearest neighbor of point p
     * null if the symbol table is empty
     * throw exception if p is null
     *
     * @param p - point of type Point2D
     * @return - point that is nearest to p
     */
    public Point2D nearest(Point2D p) {
        // if input null throw exception
        if (p == null) {
            throw new IllegalArgumentException();
        }
        // if table is empty return null
        if (isEmpty()) {
            return null;
        }
        // nearest point equal to root's point
        Point2D nearest = new Point2D(root.point.x(), root.point.y());
        // call to helper method
        nearest = nearest(p, root, nearest);
        return nearest;
    }


    /**
     * private helper method for nearest()
     *
     * @param p        - point of type Point2D
     * @param neighbor - neighbor node of p
     * @param nearest  - nearest point to p
     * @return - nearest point to p
     */
    private Point2D nearest(Point2D p, Node neighbor, Point2D nearest) {
        // if neighbor is null the nearest is input nearest
        if (neighbor == null) {
            return nearest;
        }
        // if the distance between neighbor's rectangle and point is greater
        // than distance between nearest point and point return nearest
        if (neighbor.rectangle.distanceSquaredTo(p) > nearest.distanceSquaredTo(p)) {
            return nearest;
        }
        // if distance between point and neighbor's point is less than distance
        // between point and the nearest point set the nearest point to be the neighbor's point
        if (p.distanceSquaredTo(neighbor.point) < p.distanceSquaredTo(nearest)) {
            nearest = neighbor.point;
        }
        // if the left subtree of neighbor is not null and the node's rectangle contains the point
        if (neighbor.left != null && neighbor.left.rectangle.contains(p)) {
            // recursive call on the left and right subtrees
            nearest = nearest(p, neighbor.left, nearest);
            nearest = nearest(p, neighbor.right, nearest);
        }
        // otherwise
        else {
            // recursive call on the right and left subtrees
            nearest = nearest(p, neighbor.right, nearest);
            nearest = nearest(p, neighbor.left, nearest);
        }
        return nearest;
    }


    /**
     * k nearest neighbors of point p
     * null if the symbol table is empty
     * throw exception if p is null
     *
     * @param p - point of type Point2D
     * @param k - int value representing number of nearest neighbors to p
     * @return - iterable of points nearest to p
     */
    public Iterable<Point2D> nearest(Point2D p, int k) {
        // if input is null throw exception
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        // if the table is empty return null
        if (isEmpty()) {
            return null;
        }
        // if the size of the tree is less than k return all points
        if (size <= k) {
            return points();
        }
        MaxPQ<Point2D> knearest = new MaxPQ<>(); // max pq to hold the k nearest points
        Queue<Point2D> queue = new Queue<>(); // queue to be returned
        knearest = nearest(root, p, k, knearest); // call to helper method
        // for all points in knearest add to output queue
        for (Point2D point : knearest) {
            queue.enqueue(point);
        }
        return queue;
    }


    /**
     * private helper method to find the k nearest points to p
     *
     * @param x       - current node
     * @param p       - point of type Point2D
     * @param k       - number of points closest to p
     * @param points- max priority queue representing points closest to p
     * @return - iterable points showing which points are closest to p
     */
    // helper method to find the k nearest points to p
    private MaxPQ<Point2D> nearest(Node x, Point2D p, int k, MaxPQ<Point2D> points) {
        // if input node is null return the pq
        if (x == null) {
            return points;
        }
        // if the pq is smaller than k, add the node's point
        if (points.size() < k) {
            points.insert(x.point);
        }

        // if the pq is not smaller than k, and the distance between the
        // query point and the largest point in pq is greater than distance
        // between the point and the node's point delete the max and add
        // the node's point to the pq
        else if (p.distanceSquaredTo(points.max()) > p.distanceSquaredTo(x.point)) {
            points.delMax();
            points.insert(x.point);
        }

        // if the left child node isn't null and its rectangle contains the
        // query point make recursive calls to nearest on the left then right
        // child nodes
        if (x.left != null && x.left.rectangle.contains(p)) {
            points = nearest(x.left, p, k, points);
            points = nearest(x.right, p, k, points);
        }
        // otherwise
        else {
            // make recursive calls to nearest on the right then left child nodes
            points = nearest(x.right, p, k, points);
            points = nearest(x.left, p, k, points);
        }
        // return the queue
        return points;
    }


    /**
     * unit testing (required)
     *
     * @param args - input file
     */
    public static void main(String[] args) {

        String filename = args[0];
        In in = new In(filename);
        KdTreeST<Integer> kdtree = new KdTreeST<>();

        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();

            Point2D p = new Point2D(x, y);
            kdtree.put(p, i);
        }


        StopwatchCPU stopwatch = new StopwatchCPU();

        int m = 1000000;


        for (int i = 0; i < m; i++) {
            double x = StdRandom.uniform(0.0, 1.0);
            double y = StdRandom.uniform(0.0, 1.0);
            Point2D point = new Point2D(x, y);
            kdtree.nearest(point);
        }

        double time = stopwatch.elapsedTime();
        double callsPerSecond = m / time;

        StdOut.println("elapsed time: " + time);
        StdOut.println("calls per second: " + callsPerSecond);



            /*

            Point2D point = new Point2D(2, 3);
        Point2D point2 = new Point2D(4, 2);
        Point2D point3 = new Point2D(4, 5);
        Point2D point4 = new Point2D(3, 3);
        Point2D point5 = new Point2D(1, 5);
        Point2D point6 = new Point2D(4, 4);
        kdtree.put(point, 0);
        kdtree.put(point2, 1);
        kdtree.put(point3, 2);
        kdtree.put(point4, 3);
        kdtree.put(point5, 4);
        kdtree.put(point6, 5);
        StdOut.println(kdtree.get(point4));
        StdOut.println("size: " + kdtree.size());

             */


    }

}





