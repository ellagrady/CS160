/******************************************************************************
 *  Name: Ella Grady
 *
 *  Compilation:  javac-algs4 Influence.java
 *  Execution:    java-algs4 Influence
 *  Dependencies: SimplifiedDigraph.java In.java StdOut.java java.util.ArrayList
 *  Data files in txt: triangle_2Edges, triangle_3Edges, graph_5Nodes, tinyDG
 *
 *  For each given diagraph, compute each node's influence (i.e. its reach), and
 *  create an influence diagaph where each node is directly connected to the
 *  nodes within its reach in the original diagraph.
 *
 *  For example, if a given diagraph has edges 0 -> 1 and 1 ->2, the influence
 *  diagraph should have these two edges and a new edge 0->2, since 2 is
 *  reachable from 0 in the original graph.
 *
 *  For a bonus point, find the top influencers in the digraph.
 *
 ******************************************************************************/


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Influence {
    // to store the infludence graph, to be computed from the given graph
    private SimplifiedDigraph influenceGraph;

    // useful for traversal, needed for computing the influence graph
    private boolean[] marked;

    /**
     * Computes the influence graph of the {@code givenGraph}.
     *
     * @param givenGraph the given digraph
     *                   <p>
     *                   To do:
     *                   (a) give a brief, high-level description of your solution.
     *                   IDEA : for every vertex in the graph, the
     *                   *                   constructor calls to the computeNodeInfluence method
     *                   where for
     *                   every vertex in the graph starting from the given node
     *                   adjs are found for the current node, and for each adj node to the
     *                   *                   currentone an edge is added from the given index to the
     *                   adj node
     *
     *                   <p>
     *                   (b) what is the time complexity of your solution in big O notation?
     *                   Justify your answer.
     *                   time: O(V) where V = total num vertices,
     *                   *      *                   the constructor will call to the
     *                   computeNodeInfluence method
     *                   *                          for each node, which will look at all of the
     *                   vertices in the graph
     *                   <p>
     *                   (c) Wwhat is the memory usage of your solution in big O notation?
     *                   Justify your answer.
     *                   time: O(V + E) where V = total num vertices and E = total num edges,
     *                   *      *                   for every vertex, edges are added, creating more
     *                   edges
     *                   for each vertex, changing the total number of edges
     */
    public Influence(SimplifiedDigraph givenGraph) {
        influenceGraph = new SimplifiedDigraph(givenGraph.V());
        marked = new boolean[influenceGraph.V()];
        for (int fromIndex = 0; fromIndex < givenGraph.V(); fromIndex++)
            computeNodeInfluence(influenceGraph, fromIndex);
    }


    /**
     * To Do: add code and, if needed, additional helper methods to
     * compute the influence of the from node, create its edges
     * in the influence graph
     *
     * @param givenGraph the given graph
     * @param fromIndex  the index of the from node, whole influence is computed
     *                   in this method and saved as edges in the influence graph
     *                   <p>
     *                   IDEA : for every vertex in the graph starting from the given node, the
     *                   adjs are found for the current node, and for each adj node to the
     *                   currentone
     *                   an edge is added from the given index to the adj node
     *                   time: (worst case) O(V) where V = total num vertices in the worst case,
     *                   if fromIndex = 0, then the method will look at all
     *                   of the vertices in the graph
     */
    private void computeNodeInfluence(SimplifiedDigraph givenGraph, int fromIndex) {

        Iterable<Integer> adj = influenceGraph.adj(fromIndex);
        for (int a : adj) {
            Iterable<Integer> nextadj = influenceGraph.adj(a);
            StdOut.print("a " + a + " ");
            for (int b : nextadj) {
                influenceGraph.addEdge(fromIndex, b);
            }
        }


    }


    /**
     * computes the top influencers in the graph. A node's influence
     * is the number of nodes it can reach, which is also the number of its
     * edges in the influence graph. Top influences have the max influence
     * in the graph.
     *
     * @return an interable of integers representing the node indices of top
     * influencers
     * <p>
     * <p>
     * IDEA: topInfluencers goes through the graph, finding the adj for each
     * vertex, then calculating the influence of that vertex. If that current
     * influence is greater than the overall maxInfluence, the maxInfluence
     * is reset and that vertex is added to the list, while the previous
     * vertex is removed. If the current influence is equal to the max, then
     * the max is kept the same, but the vertex is added to the list.
     * <p>
     * time complexity: O(V) where V = num vertices
     * because the method accesses every vertex in the graph
     */
    public Iterable<Integer> topInfluencers() {
        //initialize the maxInflucence to -1 and bitShots to an empty list
        int maxInfluence = -1;
        ArrayList<Integer> bigShots = new ArrayList<Integer>();

        // ToDo: add code to compute the bigShots, aka top influencers
        for (int i = 0; i < influenceGraph.V(); i++) {
            Iterable<Integer> adj = influenceGraph.adj(i);
            int currentInfluence = 0;
            for (int a : adj) {
                currentInfluence += 1;
            }
            if (currentInfluence > maxInfluence) {
                maxInfluence = currentInfluence;
                while (!bigShots.isEmpty()) {
                    bigShots.clear();
                }
                bigShots.add(i);
            }
            else if (currentInfluence == maxInfluence) {
                bigShots.add(i);
            }
        }
        return bigShots;
    }

    /**
     * generates a given graph's influence graph, displays it and lists
     * the top influencers
     *
     * @param filename the file specifying the given graph
     *                 <p>
     *                 no need to change this method
     */
    private static void genDispInfluenceGraph(String filename) {
        In in = new In(filename);
        SimplifiedDigraph givenGraph = new SimplifiedDigraph(in);
        StdOut.printf("\n---- %s: Original Graph----\n", filename);
        StdOut.println(givenGraph);

        Influence influence = new Influence(givenGraph);

        StdOut.println("---- Influence Graph ----");
        StdOut.println(influence.influenceGraph);
        StdOut.println("Top influencer(s): " + influence.topInfluencers());
    }

    /**
     * tests the {@code Influence} class with several graph files
     * and diplays the results
     *
     * @param args the command-line arguments, not used
     *             <p>
     *             you may add more test cases using your own test graphs
     *             you can also temporarily comment out some test cases below, to
     *             focus on one or more test cases at a time
     */
    public static void main(String[] args) {
        genDispInfluenceGraph("triangle2Edges.txt");
        genDispInfluenceGraph("triangle3Edges.txt");
        /* genDispInfluenceGraph("graph5Nodes.txt");
        genDispInfluenceGraph("graphIsolatedNodes.txt");
        genDispInfluenceGraph("tinyDG.txt"); */
    }
}
