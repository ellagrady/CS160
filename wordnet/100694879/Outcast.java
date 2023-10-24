/**
 * Compilation:  javac Outcast.java
 * Execution:    java Outcast outcast.txt
 * <p>
 * Immmutable data type that detects which noun in a given list of WordNet
 * nouns is the least releated to the others.
 * To identify an outcast, compute the sum of the distances between each noun
 * and every other one.
 * <p>
 *
 * @author Nina Carlson & Ella Grady
 * CSCI 160
 * 11/18/2021
 */


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private WordNet wordnet;  // wordnet object

    // constructor takes a WordNet object

    /**
     * constructor takes a WordNet object
     *
     * @param wordnet - wordnet object
     */
    public Outcast(WordNet wordnet) {

        this.wordnet = wordnet;

    }


    /**
     * given an array of WordNet nouns, return an outcast
     * an outcast is the noun least related to the other nouns in array
     *
     * @param nouns - array of WordNet nouns
     * @return - string representing noun that is the outcast in array of nouns
     */
    public String outcast(String[] nouns) {

        String outCast = null;  // initial value of outcast string

        int outcastDist = 0; // initial distance of outcast noun from other nouns

        // traverse through the array of nouns in a nested for loop
        // if noun1 does not equal noun2, current distance is the distance
        // between those two nouns
        for (String i : nouns) {

            int currentDist = 0;

            for (String j : nouns) {

                if (!i.equals(j)) {
                    currentDist += wordnet.distance(i, j);
                }


            }


            // if the current distance is less than the outcast distance
            // then the outcast distance is the current distance
            // and the outcast noun is the current noun
            if (currentDist > outcastDist) {
                outcastDist = currentDist;
                outCast = i;
            }
        }

        return outCast;


    }


    /**
     * test client (see below)
     *
     * @param args - input files synsets.txt hypernyms.txt
     */
    public static void main(String[] args) {

        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }

    }

}
