/**
 * Compilation:  javac ShortestCommonAncestor.java
 * Execution:    java ShortestCommonAncestor digraph
 * <p>
 * Immmutable data type that detects the shortest common ancestor between
 * two verticies.
 * A shortest ancestral path of two subsets A and B is a shortest ancestral path
 * among all pairs of verticies v and w, with v in A and w in B.
 * <p>
 *
 * @author Nina Carlson & Ella Grady
 * CSCI 160
 * 11/18/2021
 */


import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class ShortestCommonAncestor {

    private Digraph graph; // for making copy


    /**
     * constructor takes a rooted DAG as argument
     *
     * @param G - rooted DAG
     */
    public ShortestCommonAncestor(Digraph G) {

        if (G == null) {
            throw new NullPointerException();
        }
        DirectedCycle cycle = new DirectedCycle(G); // cycle of G
        if (cycle.hasCycle() || !isRooted(G)) {
            throw new IllegalArgumentException();
        }

        // construct copy of digraph object
        graph = new Digraph(G);

    }


    /**
     * Private helper method to check if vertex is a valid vertex in digraph
     *
     * @param v - vertex
     * @return - boolean value
     */
    private boolean validVertex(int v) {
        return v >= 0 && v < graph.V();
    }


    /**
     * private helper method to check whether graph is a DAG
     *
     * @param g - a digraph
     * @return - boolean value determining whether g is a DAG
     */
    private boolean isRooted(Digraph g) {
        int numRoots = 0;  // counter of roots in graph
        for (int i = 0; i < g.V(); i++) {
            if (g.outdegree(i) == 0) {  // number of outgoing verticies
                numRoots += 1;
            }
        }

        // if there is more or less than 1 root it is not a
        if (numRoots != 1) {
            return false;
        }

        return true;

    }


    /**
     * length of shortest ancestral path between v and w
     *
     * @param v - vertex in graph
     * @param w - vertex in graph
     * @return - integer value representing shortest ancestral path between v and w
     */
    public int length(int v, int w) {

        // assert that v and w are valid verticies
        if (!validVertex(v) || !validVertex(w)) {
            throw new NullPointerException();
        }

        // assert that the graph is a DAG
        if (!isRooted(graph)) {
            throw new IllegalArgumentException();
        }

        // create breadth first direct bath objects for each vertex
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(graph, w);

        // make call to private helper method
        return getLength(bfs, bfs2);
    }


    /**
     * private helper method to get length between two paths
     *
     * @param pathV - of type BreadthFirstDirectedPath represents path of v
     * @param pathW - of type BreadthFisrtDirectedPath represents path of w
     * @return - int value representing length between pathV and pathW
     */
    private int getLength(BreadthFirstDirectedPaths pathV, BreadthFirstDirectedPaths pathW) {

        // stack to hold lengths between paths
        Stack<Integer> lengthstack = new Stack<Integer>();
        int length = -1;  // initial value
        int placeHolder = 1000000;  // arbitrary value, placeholder for now

        // traverse the graph
        // if there is a path between v and given vertex and w and given vertex
        // placeholder becomes sum of those distances
        for (int i = 0; i < graph.V(); i++) {
            if (pathV.hasPathTo(i) && pathW.hasPathTo(i)) {
                placeHolder = pathV.distTo(i) + pathW.distTo(i);

                // if stack is empty then push placeholder to top
                // otherwise if placeholder is less than top stack value
                // push placeholder to the top
                // we want shortest length
                if (lengthstack.isEmpty()) {
                    lengthstack.push(placeHolder);
                }
                else if (placeHolder < lengthstack.peek()) {
                    lengthstack.push(placeHolder);
                }
            }
        }
        // if stack is not empty then take the top length from the stack
        if (!lengthstack.isEmpty()) {
            length = lengthstack.peek();
        }

        // return the length between the two paths
        return length;

    }


    /**
     * a shortest common ancestor of vertices v and w
     *
     * @param v - vertex in digraph
     * @param w - vertex in digraph
     * @return - integer value representing a shortest common ancestor of vertices
     */
    public int ancestor(int v, int w) {

        // assert that vertex is valid
        if (!validVertex(v) || !(validVertex(w))) {
            throw new NullPointerException();
        }

        // assert that graph is a DAG
        if (!isRooted(graph)) {
            throw new IllegalArgumentException();
        }

        // create two BreadthFirstDirectedPaths for v and w
        BreadthFirstDirectedPaths pathV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths pathW = new BreadthFirstDirectedPaths(graph, w);


        int ancestor = -1;  // initial value for ancestor

        Stack<Integer> ancestors = new Stack<Integer>();  // stack for ancestors
        Stack<Integer> length = new Stack<Integer>();  // stack for lengths

        int placeHolder = 1000000; // arbitrary value, placeholder for nw


        // traverse the graph
        // if there is a path between designated vertex and v and designated vertex and i
        // then placeholder equals sum of those two distances
        // we will compare this value to the top value in length stack
        // if it is less than then push it to the top of length and push
        // designated vertex to top of ancestor.

        for (int i = 0; i < graph.V(); i++) {
            if (pathV.hasPathTo(i) && pathW.hasPathTo(i)) {
                placeHolder = pathV.distTo(i) + pathW.distTo(i);

                if (length.isEmpty()) {
                    length.push(placeHolder);
                    ancestors.push(i);
                }
                else if (placeHolder < length.peek()) {
                    length.push(placeHolder);
                    ancestors.push(i);
                }
            }
        }

        if (!ancestors.isEmpty()) {
            ancestor = ancestors.peek();
        }

        // return ancestor value at top of ancestor peek
        return ancestor;

    }


    /**
     * length of shortest ancestral path of vertex subsets A and B
     *
     * @param subsetA - iterable representing vertex subset A
     * @param subsetB - iterable representing vertex subset B
     * @return - integer value representing shortest path of subsets A and B
     */
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null) {
            throw new NullPointerException();
        }
        Queue<Integer> a = new Queue<>();
        Queue<Integer> b = new Queue<>();
        for (Integer integer : subsetA) {
            a.enqueue(integer);
        }
        for (Integer integer : subsetB) {
            b.enqueue(integer);
        }

        if (a.size() == 0 || b.size() == 0) {
            throw new IllegalArgumentException();
        }

        // int minLength = 1000000; // placeholder

        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(graph, subsetA);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(graph, subsetB);

        // assert that the graph is a DAG
        if (!isRooted(graph)) {
            throw new IllegalArgumentException();
        }

        // traverse both subsets
        // current length is length between a and b.
        // if this is greater than minLength then min length equals current length
        // return the min length.

        /* for (int a : subsetA ) {
            for (int b : subsetB) {
                int currentLength = length(a, b);

                if (minLength > currentLength) {
                    minLength = currentLength;
                }
            }
        }


        return minLength; */
        return getLength(bfs, bfs2);
    }


    /**
     * a shortest common ancestor of vertex subsets A and B
     *
     * @param subsetA - iterable of subset A
     * @param subsetB - iterable of subset B
     * @return - int value representing shortest common ancestor of vertex subsets a and b
     */
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {

        if (subsetA == null || subsetB == null) {
            throw new NullPointerException();
        }
        Queue<Integer> a = new Queue<>();
        Queue<Integer> b = new Queue<>();
        for (Integer integer : subsetA) {
            a.enqueue(integer);
        }
        for (Integer integer : subsetB) {
            b.enqueue(integer);
        }

        if (a.size() == 0 || b.size() == 0) {
            throw new IllegalArgumentException();
        }

        // two breadthfirstdirectedpaths objects for subset a and subset b

        // create two BreadthFirstDirectedPaths for v and w
        BreadthFirstDirectedPaths pathV = new BreadthFirstDirectedPaths(graph, subsetA);
        BreadthFirstDirectedPaths pathW = new BreadthFirstDirectedPaths(graph, subsetB);


        int ancestor = -1;  // initial value for ancestor

        Stack<Integer> ancestors = new Stack<Integer>();  // stack for ancestors
        Stack<Integer> length = new Stack<Integer>();  // stack for lengths

        int placeHolder = 1000000; // arbitrary value, placeholder for nw


        // traverse the graph
        // if there is a path between designated vertex and v and designated vertex and i
        // then placeholder equals sum of those two distances
        // we will compare this value to the top value in length stack
        // if it is less than then push it to the top of length and push
        // designated vertex to top of ancestor.

        for (int i = 0; i < graph.V(); i++) {
            if (pathV.hasPathTo(i) && pathW.hasPathTo(i)) {
                placeHolder = pathV.distTo(i) + pathW.distTo(i);

                if (length.isEmpty()) {
                    length.push(placeHolder);
                    ancestors.push(i);
                }
                else if (placeHolder < length.peek()) {
                    length.push(placeHolder);
                    ancestors.push(i);
                }
            }
        }


        if (!ancestors.isEmpty()) {
            ancestor = ancestors.peek();
        }

        // return ancestor value at top of ancestor peek
        return ancestor;
    }


    /**
     * unit testing (required)
     *
     * @param args - input file
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()) {
            String v = StdIn.readLine();
            String[] varray = v.split(" ");
            Queue<Integer> vints = new Queue<>();
            for (int i = 0; i < varray.length; i++)
                vints.enqueue(Integer.parseInt(varray[i]));
            String w = StdIn.readLine();
            String[] warray = v.split(" ");
            Queue<Integer> wints = new Queue<>();
            for (int i = 0; i < warray.length; i++)
                wints.enqueue(Integer.parseInt(warray[i]));
            int length = sca.lengthSubset(vints, wints);
            int ancestor = sca.ancestorSubset(vints, wints);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

}

