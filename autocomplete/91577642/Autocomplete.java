/* *****************************************************************************
 *  Ella Grady
 *  CS 160
 *  October 4, 2021
 *  Autocomplete.java
 *
 *  Description: implements autocomplete for a given set of n terms,
 *    where a term is a query string and an associated non-negative weight.
 *    given a prefix, finds all queries that start with the given prefix,
 *    in descending order of weight.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Autocomplete {
    private final Term[] terms; // initializes array of terms

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) {
            throw new java.lang.IllegalArgumentException();
        }
        this.terms = new Term[terms.length];
        for (int i = 0; i < terms.length; i++) {
            if (terms[i] == null) {
                throw new IllegalArgumentException();
            }
            this.terms[i] = terms[i];
        }
        Arrays.sort(this.terms);
    }


    // Returns all terms that start with the given prefix, in descending order of weight.
    public Term[] allMatches(String prefix) {
        Term[] matches;
        if (prefix == null) {
            throw new java.lang.IllegalArgumentException();
        }

        int length = prefix.length();
        if (length == 0) { // returns all queries if prefix is empty string
            return terms;
        }
        Term newTerm = new Term(prefix, 0); // new term for prefix input
        // comparator for length of prefix
        Comparator<Term> comparator = Term.byPrefixOrder(length);
        // finds firstIndex of prefix in the terms list
        int firstIndex = BinarySearchDeluxe.firstIndexOf(terms, newTerm, comparator);
        // finds lastIndex of prefix in the terms list
        int lastIndex = BinarySearchDeluxe.lastIndexOf(terms, newTerm, comparator);

        if (firstIndex > 0 && lastIndex > 0 && firstIndex < lastIndex) {
            // array to hold the matches, of size 1 more than the difference of the indices
            matches = new Term[lastIndex - firstIndex + 1];
        }
        else { // if (firstIndex < 0) if no such term, returns an array of length 0
            matches = new Term[0];
        }
        // adds the terms between firstIndex and lastIndex to the matches array
        if (matches.length != 0) {
            for (int i = firstIndex; i <= lastIndex; i++) {
                matches[i - firstIndex] = terms[i];
            }
        }

        // sorts the matches by reverse weight order
        Arrays.sort(matches, Term.byReverseWeightOrder());

        return matches; // returns array of matches
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (prefix.length() == 0) {
            return terms.length;
        }
        Term prefixTerm = new Term(prefix, 0);
        int firstIndex = BinarySearchDeluxe
                .firstIndexOf(terms, prefixTerm, Term.byPrefixOrder(prefix.length()));

        if (firstIndex < 0) {
            return 0;
        }
        int lastIndex = BinarySearchDeluxe
                .lastIndexOf(terms, prefixTerm, Term.byPrefixOrder(prefix.length()));
        int count = (lastIndex - firstIndex) + 1;
        return count;
        /* // runs allMatches(prefix) to get the list of matches and finds the length of it
        return allMatches(prefix).length; */
    }

    // unit testing (required)
    public static void main(String[] args) {

        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }

        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }

}
