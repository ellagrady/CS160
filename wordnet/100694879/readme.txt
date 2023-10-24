Nina Carlson & Ella Grady
Programming Assignment 6: WordNet
11/18/2021

/* *****************************************************************************
 *  Please take a moment now to fill out the mid-semester survey:
 *  https://forms.gle/diTbj5r4o4xXbJm89
 *
 *  If you're working with a partner, please do this separately.
 *
 *  Type your initials below to confirm that you've completed the survey.
 **************************************************************************** */

 N/A



/* *****************************************************************************
 *  Describe concisely the data structure(s) you used to store the
 *  information in synsets.txt. Why did you make this choice?
 **************************************************************************** */
We used two hash maps to store the information in synsets.txt. We chose to use hashmaps
because the typical running time for hash maps is O(1), and we wanted to keep in mind the time
requirements for the WordNet class.

The first hashmap is called synsetmap and maps the noun, which is represented by a string, to
the ID of that noun, which is represented by an integer value. In this hashmap we used a bag of integer
values to represent the IDs of the nouns, since one word can have multiple ID values. This hashmap
is meant to be used to convert from an integer value ID to a synset noun. The second hashmap
is called storesynsets and it maps the IDs to the nouns. This hashmap is meant to be used to convert
from a synset noun to the integer value IDs that identify that word.


/* *****************************************************************************
 *  Describe concisely the data structure(s) you used to store the
 *  information in hypernyms.txt. Why did you make this choice?
 **************************************************************************** */
 We used a digraph to store the information in hypernyms.txt because the information in that
 file corresponds to a directed acylic graph, so using a digraph made the most sense based
 on the problem description.

/* *****************************************************************************
 *  Describe concisely the algorithm you use in the constructor of
 *  ShortestCommonAncestor to check if the digraph is a rooted DAG.
 *  What is the order of growth of the worst-case running times of
 *  your algorithm? Express your answer as a function of the
 *  number of vertices V and the number of edges E in the digraph.
 *  (Do not use other parameters.) Use Big Theta notation to simplify
 *  your answer.
 **************************************************************************** */


Description:  We use a for loop to traverse the verticies of the graph. We check if the specified
vertex edge is directed out of a vertex in the digraph. If it is not, then we increase the counter variable that
represents the number of roots in the graph. If this number is not equal to 1, then the graph is not a DAG. If this number
is equal to 1, then it is a DAG.



Order of growth of running time: O(V + E)


/* *****************************************************************************
 *  Describe concisely your algorithm to compute the shortest common ancestor
 *  in ShortestCommonAncestor. For each method, give the order of growth of
 *  the best- and worst-case running times. Express your answers as functions
 *  of the number of vertices V and the number of edges E in the digraph.
 *  (Do not use other parameters.) Use Big Theta notation to simplify your
 *  answers.
 *
 *  If you use hashing, assume the uniform hashing assumption so that put()
 *  and get() take constant time per operation.
 *
 *  Be careful! If you use a BreadthFirstDirectedPaths object, don't forget
 *  to count the time needed to initialize the marked[], edgeTo[], and
 *  distTo[] arrays.
 **************************************************************************** */

Description: First we check that the verticies are valid and that the graph is acylic.
Then we create two BreadthFirstDirectedPaths objects for paths v and w. We create two stacks:
one of type integer to represent the length, and one of type integer to represent the ancestor.
We iterate over all the verticies and calculate the total distance between v and i and w and i
where i = each vertex. The smallest sum computed corresponds to the vertex that is the shortest
common ancestor.


                                 running time
method                  best case            worst case
--------------------------------------------------------
length()                   V                  E + V

ancestor()                 V                  E + V

lengthSubset()             V                  E + V

ancestorSubset()           V                  E + V



/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */
 No bugs/limitations that we are aware of.


/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **************************************************************************** */
 (we might get help later)



/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */
 No serious problems.


/* *****************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 **************************************************************************** */
 We assert that we followed the protocol as described on the assignment page. Ella and Nina
 coded and shared ideas together in the same room at all times. Ella and Nina also worked on the
 readme.txt file together.



/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
 No other comments.s
