/**
 * Compilation:  javac PointST.java
 * Execution:    java PointST value
 * <p>
 * Mutable data type PointST.java uses a red-black BST to represent
 * a symbol table whose keys are two-dimensional points, by implementing
 * this API.
 * Implementation supports size() in constant time; put(), get(), contains()
 * in O(logn) time; points(), nearest(), range() in O(n) time where n is the
 * number of points in the symbol table
 * <p>
 *
 * @author Nina Carlson & Ella Grady
 * CSCI 160
 * 11/2/21
 */


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StopwatchCPU;

public class PointST<Value> {

    private RedBlackBST<Point2D, Value> symboltable; // symbol table using red-black BST


    /**
     * Construct an empty symbol table of points
     */
    public PointST() {
        symboltable = new RedBlackBST<Point2D, Value>();
    }


    /**
     * Determines if the symbol table is empty
     *
     * @return - boolean determining whether or not symbol table is empty
     */
    public boolean isEmpty() {
        return symboltable.isEmpty();
    }


    /**
     * determines number of points in the symbol table
     *
     * @return - int value representing size of symbol table
     */
    public int size() {
        return symboltable.size();
    }


    /**
     * associate the value val with point p
     * throw exception is p or val is null
     *
     * @param p   - point of type Point2D
     * @param val - value of type Value associated with p
     */
    public void put(Point2D p, Value val) {
        if (p == null || val == null) {
            throw new IllegalArgumentException();
        }
        symboltable.put(p, val);
    }


    /**
     * value associated with point p
     * throw execption is p is null
     *
     * @param p - point of type Point2D
     * @return - Value associated with point p
     */
    public Value get(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return symboltable.get(p);
    }


    /**
     * determines if the symbol table contains point p
     * throw exception is p is null
     *
     * @param p - point of type Point2D
     * @return - boolean value evaluating presence of point p in symbol table
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return symboltable.contains(p);
    }


    /**
     * all points in the symbol table
     *
     * @return - iterable representing all points in symbol table
     */
    public Iterable<Point2D> points() {
        return symboltable.keys();
    }


    /**
     * all points that are inside the rectangle or on the boundary
     * throw exception is rectangle is null
     *
     * @param rect - bounding box of type RectHV
     * @return - iterable representing all points within boundary of bounding box
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }


        Queue<Point2D> contained = new Queue<Point2D>();

        for (Point2D p : points()) {
            if (rect.contains(p)) {
                contained.enqueue(p);
            }
        }
        // Iterator<Point2D> points = contained.iterator();
        return contained;
    }


    /**
     * a nearest neighbor of point p
     * throw exception if p is null
     * return null if symbol table is empty
     *
     * @param p - point of type Point2D
     * @return - a point nearest to point p
     */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (symboltable.isEmpty()) {
            return null;
        }
        // double[] distances = new double[size()];
        // int i = 0;
        Point2D nearest = null; // point to be returned
        double minDistance = 10000000.0; // arbitrary place holding value
        for (Point2D other : points()) { // check each point in symbol table
            double currentdistance = p.distanceSquaredTo(other);
            if (currentdistance < minDistance && !p.equals(other)) {
                minDistance = currentdistance;
                nearest = other;
            }
            else if (p.equals(other)) {
                return p;
            }
        }

        return nearest;

    }


    /**
     * unit testing (required)
     *
     * @param args - input file
     */
    public static void main(String[] args) {

        String filename = args[0];
        In in = new In(filename);
        PointST<Integer> kdtree = new PointST<>();

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

        // RedBlackBST<Point2D, Double> symboltable = new RedBlackBST<>();
        PointST<Double> tree = new PointST<Double>();
        Point2D point = new Point2D(0.5, 0.5);
        StdOut.println("empty? " + tree.isEmpty());
        StdOut.println("size: " + tree.size());
        StdOut.println("put: " + point.toString() + " 5.0");
        tree.put(point, 5.0);
        StdOut.println("empty? " + tree.isEmpty());
        StdOut.println("size: " + tree.size());
        StdOut.println("get: " + tree.get(point));
        StdOut.println("contains? " + tree.contains(point));
        StdOut.println("all points: " + tree.points());
        RectHV rectangle = new RectHV(0.0, 0.0, 1.0, 1.0);
        Point2D point2 = new Point2D(0.7, 0.7);
        tree.put(point2, 2.0);
        StdOut.println("empty? " + tree.isEmpty());
        StdOut.println("size: " + tree.size());
        Point2D point3 = new Point2D(0.8, 0.8);
        tree.put(point3, 2.0);
        StdOut.println("empty? " + tree.isEmpty());
        StdOut.println("size: " + tree.size());
        StdOut.println("range: " + tree.range(rectangle));
        StdOut.println("nearest: " + tree.nearest(point));

         */
    }

}
