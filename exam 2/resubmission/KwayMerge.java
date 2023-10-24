import edu.princeton.cs.algs4.MinPQ;

/**
 * Your Name: Ella Grady
 */

//potential useful methods: constructor, isEmpty(), deleteMin(), insert(...)
public class KwayMerge {


    // This class KwayMerge should not be instantiated.
    // initializes an empty array
    private KwayMerge() {
    }

    // merge together the sorted arrays in augData and write the sorted result to standard output


    /**
     * @param augData       a 2D array, with each row already sorted. There should
     *                      be no change to this array by the end of this method.
     * @param numTotalItems the number of total elements in the 2D array
     * @return a 1D array with all the elements from the 2D array sorted
     * <p>
     * Questions for you:
     * Given that the argument augData has M rows and N total elements, what is the
     * time and space usage of your solution, in big O notation?
     *
     * <p>
     * Time complexity of your solution: O(NlogM), because the heap will never be bigger
     * than N elements so it will take O(lgN) for both deleteMin and insert and there are O(n)
     * operations (one insert/one delete min for each element) so the running time will be
     * O(NlogM)
     * <p>
     * Extra memory usage, not including the argument 2D array and the resulting  1D array:
     * O(M). A binary heap will use space complexity of the size it holds, and at any given
     * time the priority queue will hold as many elements as there are rows in the 2d array
     * <p>
     * Briefly justify your answer
     */
    public static AugmentedData[] merge(AugmentedData[][] augData, int numTotalItems) {
        // min priority queue
        MinPQ<AugmentedData> queue = new MinPQ<AugmentedData>();
        // one d array to be added to and returned
        AugmentedData[] output = new AugmentedData[numTotalItems];
        // add first element of each sub array to min pq
        for (int i = 0; i < augData.length; i++) {
            queue.insert(augData[i][0]);
        }
        int index = 0; // index counter for one d array
        while (!queue.isEmpty()) { // while the queue is not empty
            AugmentedData min = queue.delMin(); // find the min object in queue
            int arrayId = min.getArrayId(); // get the row
            int itemId = min.getItemId(); // get the col
            // if the row and col are in bounds
            if (arrayId < augData.length && itemId < (augData[arrayId].length - 1)) {
                // insert the next element in the sub array into the que
                queue.insert(augData[arrayId][itemId + 1]);
            }
            // if the index is less than length of array
            if (index < (numTotalItems)) {
                output[index] = min; // set that element to the min
            }
            index++; // increment the index counter
        }
        return output;

    }


}



