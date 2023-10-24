/* *****************************************************************************
 *  Ella Grady
 *  CS 160
 *  October 4, 2021
 *  Term.java
 *
 *  Description: data type Term.java that represents an autocomplete term — a
 *  query string and an associated integer weight.
 *  supports comparing terms by three different orders: lexicographic order by
 *  query string (the natural order);
 *  in descending order by weight (an alternate order);
 *  and lexicographic order by query string but using only the first r characters
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Term implements Comparable<Term> {

    private String query; // initialize the string query
    private long weight; // initialize the long weight

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null || weight < 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.query = query;
        this.weight = weight;

    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightComparator();
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0) {
            throw new java.lang.IllegalArgumentException();
        }
        return new PrefixOrderComparator(r);
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return this.query.compareTo(that.query);
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return (Long.toString(weight) + "\t" + query);
    }

    private static class ReverseWeightComparator implements Comparator<Term> {
        public int compare(Term first, Term second) {
            if (first.weight == second.weight) { // if weights are same
                return 0;
            }
            else if (first.weight > second.weight) { // if first weight is greater
                return -1;
            }
            else { // if second weight is greater
                return 1;
            }
        }
    }

    private static class PrefixOrderComparator implements Comparator<Term> {
        // input of number of first r characters in query
        private int r;

        // initializes PrefixOrderComparator
        private PrefixOrderComparator(int r) {
            this.r = r;
        }

        public int compare(Term first, Term second) {
            String firstPrefix;
            String secondPrefix;

            if (first.query.length() < r) { // if first length less then r
                firstPrefix = first.query; // firstPrefix is full first query string
            }
            else { // if first length greater than or equal to r
                // firstPrefix is substring of first r characters in first query string
                firstPrefix = first.query.substring(0, r);
            }

            if (second.query.length() < r) { // if second length less than r
                secondPrefix = second.query; // secondPrefix is full second query string
            }
            else { // if second length greater than or equal to r
                // secondPrefix is substring of first r characters in second query string
                secondPrefix = second.query.substring(0, r);
            }

            return firstPrefix.compareTo(secondPrefix);
            // return comparison value of firstPrefix to secondPrefix
            // -1 if firstPrefix is less than secondPrefix,
            // 1 if firstPrefix is greater,
            // or 0 if they are equal
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term[] terms = {
                new Term("may", 3), new Term("june", 6), new Term("july", 4), new Term("august", 5)
        };
        for (Term term : terms) {
            StdOut.print(term.toString() + "\t");
        }
        StdOut.println();

        Arrays.sort(terms, Term.byPrefixOrder(2));
        for (Term term : terms) {
            StdOut.print(term.toString() + "\t");
        }
        StdOut.println();

        Arrays.sort(terms, Term.byReverseWeightOrder());
        for (Term term : terms) {
            StdOut.print(term.toString() + "\t");
        }
        StdOut.println();
    }
}
