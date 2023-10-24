/**
 * Your Name: Ella Grady
 */

public class KwayMergeUtilTest {

    // convert a 2d array of int elements to a 2d array of AugmentedData types
    private static AugmentedData[][] createAugmentedData(int[][] data) {
        int numSourceArrays = data.length; // length of data to be length of augData
        AugmentedData[][] augData
                = new AugmentedData[numSourceArrays][]; // 2d array of augData type
        for (int i = 0; i < numSourceArrays; i++) { // iterate through data
            int srcArrayLength = data[i].length; // length of interior array
            augData[i]
                    = new AugmentedData[srcArrayLength]; // make each row of augData a 1d augData array
            for (int j = 0; j < srcArrayLength; j++) // iterate through the specific row
                augData[i][j] = new AugmentedData(data[i][j], i,
                                                  j); // assign each element of augdata to be a augData object
        }
        return augData; // return the 2d augData array
    }

    // print the 2dAugArray
    private static void print2DAugArray(AugmentedData[][] augData) {
        if (augData != null) { // as long as input array is not null
            for (int i = 0; i < augData.length; i++) {
                for (int j = 0; j < augData[i].length; j++) // iterate through array
                    System.out.print(augData[i][j]); // print the element
                System.out.println(); // print new line after each row
            }
        }
    }

    // print a 1d AugmentedData array
    private static void print1DAugArray(AugmentedData[] augData) {
        if (augData != null) { // as long as input array is not null
            for (AugmentedData augDataItem : augData) // iterate through array
                System.out.println(augDataItem); // print the element
        }
    }

    // convert a 2d array of integers to an AugmentedData 2darray then merge
    // the rows into a 1d array and print
    private static void mergeAndPrint(int[][] data, int numTotalItems) {
        //
        AugmentedData[][] augData = createAugmentedData(data);

        //You may uncomment the following two lines to see what's in the 2D augdata
        print2DAugArray(augData);
        System.out.println();

        // create a 1d augData array by merging the 2d array
        AugmentedData[] sorted = KwayMerge.merge(augData, numTotalItems);
        print1DAugArray(sorted);
    }

    /**
     * Expected print of the 1D sorted array? 1, 2, 3, 5, 7, 17
     */

    // tests the merge for a small array of 6 elements
    private static void testEasyArrayMerge() {
        int[][] data = { { 2, 5 }, { 3, 17 }, { 1, 7 } };
        int numTotalItems = 6;
        mergeAndPrint(data, numTotalItems);
    }

    /**
     * Expected print of the 1D sorted array: 1, 2, 3, 5, 7, 9, 22
     */
    // tests merge for a 2d array with different length rows
    private static void testJaggedArrayMerge() {
        int[][] data = { { 2, 5, 22 }, { 3 }, { 1, 7, 9 } };
        int numTotalItems = 7;
        mergeAndPrint(data, numTotalItems);
    }

    /**
     * Expected print of the 1D sorted array: 1, 1, 1, 1, 1, 1
     */
    // test the merge for a 2d array with different length rows
    // but where all elements are equal
    private static void testStability() {
        int[][] data = { { 1, 1 }, { 1, 1, 1 }, { 1 } };
        int numTotalItems = 6;
        mergeAndPrint(data, numTotalItems);
    }


    /**
     * Expected print of the 1D sorted array: -4, 1, 2, 2, 3, 3, 4, 8, 12, 16
     */
    // test merge for 2d array of different length rows
    private static void testSmallGeneral() {
        int[][] data = { { 1, 2, 3 }, { 2, 4 }, { 3 }, { -4, 8, 12, 16 } };
        int numTotalItems = 10;
        mergeAndPrint(data, numTotalItems);
    }

    public static void main(String[] args) {
        System.out.println(" **** test easy array merge ****");
        testEasyArrayMerge();

        System.out.println("\n **** test jagged array merge ****");
        testJaggedArrayMerge();

        System.out.println("\n **** test stability array merge ****");
        testStability();

        System.out.println("\n **** test small, general array merge ****");
        testSmallGeneral();

    }
}
