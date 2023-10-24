import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

/**
 * Your Name:
 */

//potential useful methods: constructor, isEmpty(), deleteMin(), insert(...)
public class KwayMerge {

    // This class KwayMerge should not be instantiated.
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
     * <p>
     * Time complexity of your solution: O(M^2 + N^2) because the algorithm utilizes two nested for
     * loops
     * to go through the rows and elements
     * <p>
     * Extra memory usage, not including the argument 2D array and the resulting  1D array:
     * <p>
     * Briefly justify your answer
     */
    public static AugmentedData[] merge(AugmentedData[][] augData, int numTotalItems) {
        AugmentedData[] oned = new AugmentedData[numTotalItems];
        MinPQ<AugmentedData[]> pq = new MinPQ<AugmentedData[]>();
        int mergeItems = numTotalItems;

        if (mergeItems == 0) {
            return oned;
        }
        AugmentedData[] firsthalfarray = new AugmentedData[augData[0].length];
        for (int i = (augData.length / 2) - 1; i > 0; i /= 2) {
            for (int j = 0; j < i; j++) {
                for (int k = 0; k < augData[0].length; k++) {
                    mergeItems /= 2;
                    AugmentedData[][] firsthalf = new AugmentedData[i][augData[0].length];
                    firsthalf[j][k] = augData[j][k];
                    firsthalfarray = merge(firsthalf, mergeItems);


                }
            }

        }
        pq.insert(firsthalfarray);
        AugmentedData[] secondhalfarray = new AugmentedData[augData[0].length];
        for (int i = augData.length - 1; i > 0; i /= 2) {
            for (int j = 0; j < i; j++) {
                for (int k = 0; k < augData[0].length; k++) {
                    mergeItems /= 2;
                    AugmentedData[][] secondhalf = new AugmentedData[i][augData[0].length];
                    secondhalf[j][k] = augData[j][k];
                    secondhalfarray = merge(secondhalf, mergeItems);

                }
            }
        }
        pq.insert(secondhalfarray);

        AugmentedData[] min = pq.min();
        StdOut.println(min);
        pq.delMin();
        for (int j = 0; j < augData.length; j++) {
            for (int k = 0; k < min.length; k++) {
                if (min[j].compareTo(augData[j][k]) == 0) {
                    int index = augData[j][k].getItemId();
                    oned[index * numTotalItems] = min[k];
                }
            }

        }
        return oned;
    }
}



