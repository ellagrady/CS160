/* *****************************************************************************
 *  Ella Grady
 *  CS 160
 *  October 4, 2021
 *  BinarySearchDeluxe.java
 *
 *  Description: implements binary searching a sorted array that contains more
 *  than one key equal to the search key, to find the index of either the first
 *  or the last such key.
 *
 **************************************************************************** */


import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class BinarySearchDeluxe {
    // Returns the index of the first key in the input sorted array a[]
    // that is equal to the given search key, for the input comparator
    // or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new java.lang.IllegalArgumentException();
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] == null) {
                throw new java.lang.IllegalArgumentException();
            }
        }


        int low = 0;
        int high = a.length - 1;

        while (low <= high) {
            int middle = low + (high - low) / 2; // find middle

            if (comparator.compare(key, a[middle]) < 0) { // handles if middle is greater than key
                high = middle - 1; // looks at left half of tree
                // StdOut.println("a");
            }
            else if (comparator.compare(key, a[middle]) > 0) { // handles if middle is less than key
                low = middle + 1; // looks at right half of tree
                // StdOut.println("b");
            }
            else if (comparator.compare(a[middle - 1], a[middle])
                    == 0) { // handles if middle and element
                // before middle are equal
                high = middle - 1; // looks at left half of tree
            }
            else if (comparator.compare(key, a[middle])
                    == 0) { // handles if middle element is equal to key
                return middle;
            }
            // StdOut.println(middle);
        }
        return -1;
    }

    // Returns the index of the last key in the input sorted array a[]
    // that is equal to the given search key, for the input comparator
    // or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {

        if (a == null || key == null || comparator == null) {
            throw new java.lang.IllegalArgumentException();
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] == null) {
                throw new java.lang.IllegalArgumentException();
            }
        }

        int low = 0;
        int high = a.length - 1;

        while (low <= high) {
            int middle = low + (high - low) / 2; // find middle

            if (comparator.compare(key, a[middle]) < 0) { // checks if middle is greater than
                // key
                high = middle - 1; // looks at left side of tree
                // StdOut.println("a");
            }
            else if (comparator.compare(key, a[middle]) > 0) { // checks if middle is less
                // than key
                low = middle + 1; // looks at right side of tree
                // StdOut.println("b");
            }

            else {  // if middle and key are equal
                if (middle == a.length - 1) { // checks if middle is last element in list
                    return middle;
                }
                // checks if the element after middle is less than the key
                else if (comparator.compare(key, a[middle + 1]) < 0) {
                    return middle;
                }
                // otherwise looks at right side of tree
                else {
                    low = middle + 1;
                }

                // StdOut.println(middle);
            }
        }
        return -1;
    }


    // unit testing (required)
    public static void main(String[] args) {
        String[] list = { "a", "a", "c", "d", "e", "c", "h", "h" };
        StdOut.println(BinarySearchDeluxe.lastIndexOf(list, "a", Comparator.naturalOrder()));
    }
}
