/**
 * Compilation:  javac WordNet.java
 * Execution:    java WordNet synsets.txt hypernyms. txt
 * <p>
 * Immutable data type to build the WordNet digraph
 * Each vertex v is an integer that represents a synset
 * Each directed edge v --> w represents that w is a  hypernym of v
 * <p>
 *
 * @author Nina Carlson & Ella Grady
 * CSCI 160
 * 11/18/2021
 */


import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;


public class WordNet {

    private HashMap<String, Bag<Integer>> synsetmap; // map nouns to IDs
    private HashMap<Integer, String> storesynsets; // IDs to synset

    private ShortestCommonAncestor shortestCommonAncestor;  // shortest common ancestor object

    private Digraph digraph;  // digraph object


    /**
     * Constructor that takes the name of the two input files
     *
     * @param synsets   - name of the input file containing list of synsets
     * @param hypernyms - name of the input file containing list of hypernyms
     */
    public WordNet(String synsets, String hypernyms) {

        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }


        // initialize hashmaps to store key value pairs
        synsetmap = new HashMap<String, Bag<Integer>>(); // noun to id
        storesynsets = new HashMap<Integer, String>();  // id to noun

        // number of synsets in list of synsets
        // read in synsets using helper method getSynsets()
        int numSynsets = getSynsets(synsets);

        // wordnet graph
        digraph = new Digraph(numSynsets);

        // read in hypernyms using helper method readHypernyms()
        readHypernyms(hypernyms);

        // initialize sca object that takes the graph
        shortestCommonAncestor = new ShortestCommonAncestor(digraph);


    }


    /**
     * Helper method to return number of synsets in list
     *
     * @param synsets - name of input file containing list of synsets
     * @return - int value representing number of synests in list
     */
    private int getSynsets(String synsets) {

        if (synsets == null) {
            throw new IllegalArgumentException();
        }

        In in = new In(synsets);  // read in file

        int count = 0; // counter to represent number of synsets read in

        // parse while there are still lines to parse
        while (in.hasNextLine()) {
            count++;
            String line = in.readLine();
            // split at each comma to separate the words
            // put words into array
            String[] words = line.split(",");
            // id corresponds to index value of each word in array
            int id = Integer.parseInt(words[0]);
            // put values into hashmap: id --> noun
            storesynsets.put(id, words[1]);
            // create new array and split at each word
            String[] nouns = words[1].split(" ");
            for (String n : nouns) {
                if (synsetmap.get(n) != null) {
                    // add id of nouns to integer bag if noun is not null
                    Bag<Integer> bag = synsetmap.get(n);
                    bag.add(id);
                }
                else {
                    // otherwise create new bag and add to hashmap synsetmap
                    Bag<Integer> bag = new Bag<Integer>();
                    bag.add(id);
                    synsetmap.put(n, bag);
                }
            }
        }
        // return number of synsets in list
        return count;
    }


    /**
     * private helper method to read in hypernyms from hypernyms.txt
     *
     * @param hypernyms - text input file
     */
    private void readHypernyms(String hypernyms) {
        if (hypernyms == null) {
            throw new IllegalArgumentException();
        }

        // read from hypernyms.txt
        In in = new In(hypernyms);
        while (in.hasNextLine()) {
            String line = in.readLine();
            // parse at each comma to separate words
            String[] words = line.split(",");
            // ID of each word corresponds to index
            int id = Integer.parseInt(words[0]);
            for (int i = 1; i < words.length; i++) {
                int val = Integer.parseInt(words[i]);
                // construct digraph by adding edges
                digraph.addEdge(id, val);
            }
        }
    }


    /**
     * the set of all WordNet nouns
     *
     * @return - an iterable of strings representing the set of all nouns
     */
    public Iterable<String> nouns() {

        return synsetmap.keySet();

    }


    /**
     * is the word a wordnet noun?
     *
     * @param word - string representing a given noun in wordnet
     * @return - boolean value
     */
    public boolean isNoun(String word) {

        if (word == null) {
            throw new IllegalArgumentException();
        }
        // return storesynsets.containsValue(word);
        return synsetmap.containsKey(word);


    }


    /**
     * a synset (second field of synsets.txt) that is a shortest common ancestor
     * of noun1 and noun2 (defined below)
     *
     * @param noun1 - string representing a given noun in wordnet
     * @param noun2 - string representing a second noun in wordnet
     * @return - string synset that is a shortest commmon ancestor of noun1 and noun2
     */
    public String sca(String noun1, String noun2) {

        if (!isNoun(noun1) || !(isNoun(noun2))) {
            throw new IllegalArgumentException();
        }

        Bag<Integer> one = synsetmap.get(noun1); // id(s) of noun1
        Bag<Integer> two = synsetmap.get(noun2); // id(s) of noun2

        // call ancestorSubset() to retreive a shortest common ancestor of noun1 and noun2
        String synset = storesynsets.get(shortestCommonAncestor.ancestorSubset(one, two));

        return synset;


    }


    /**
     * distance between noun1 and noun2
     *
     * @param noun1 - string representing a noun in wordnet
     * @param noun2 - string representing another noun in wordnet
     * @return - int value representing distance between noun1 and noun2
     */
    public int distance(String noun1, String noun2) {

        if (!isNoun(noun1) || !isNoun(noun2)) {
            throw new IllegalArgumentException();
        }

        Bag<Integer> one = synsetmap.get(noun1);  // id(s) of noun1
        Bag<Integer> two = synsetmap.get(noun2); // id(s) of noun2

        // call lengthSubset() to retrieve length between noun1 and noun2
        return shortestCommonAncestor.lengthSubset(one, two);


    }


    /**
     * unit testing (required)
     *
     * @param args - input file
     */
    public static void main(String[] args) {

        WordNet wordNet = new WordNet("synsets15.txt", "hypernyms15Path.txt");
        // System.out.println(wordNet.sca());


    }

}

