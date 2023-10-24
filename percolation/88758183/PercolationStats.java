/* *****************************************************************************
 *  Ella Grady
 *  CS 160
 *  September 20, 2021
 *  PercolationStats.java
 *
 *  Description: Calculates the statistics of the Percolation.java class,
 *              finds mean, standard deviation, and the 95% confidence interval
 *              for a percolation system of n-by-n grid size
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private int numTrials;
    private double[] results;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }


        numTrials = trials;
        results = new double[numTrials];

        for (int t = 0; t < numTrials; t++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(0, n);
                int col = StdRandom.uniform(0, n);
                percolation.open(row, col);
            }
            int numOpen = percolation.numberOfOpenSites();
            double result = (double) numOpen / (n * n);
            results[t] = result;
        }
        // quadratic
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results); // constant
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results); // linear + constant
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(numTrials)); //
        // linear
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(numTrials));
        // linear
    }

    // test client (see below)
    public static void main(String[] args) {
        int gridSize = Integer.parseInt(args[0]);
        // StdOut.println("args[0] " + args[0]);
        // StdOut.println("args[1] " + args[1]);
        int trialCount = Integer.parseInt(args[1]);


        Stopwatch timer = new Stopwatch();
        PercolationStats percstats = new PercolationStats(gridSize, trialCount);

        System.out.printf("mean()           = %.6f%n", percstats.mean());
        System.out.printf("stddev()         = %.6f %n", percstats.stddev());
        System.out.printf("confidenceLow()  = %.6f %n", percstats.confidenceLow());
        System.out.printf("confidenceHigh() = %.6f %n", percstats.confidenceHigh());
        double time = timer.elapsedTime();
        StdOut.println("elapsed time     = " + time);
    }

}
