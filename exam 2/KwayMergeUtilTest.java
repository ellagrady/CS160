
/**
 * Your Name:
 *
 */

public class KwayMergeUtilTest {


    private static AugmentedData[][] createAugmentedData(int[][]data) {
        int numSourceArrays = data.length;
        AugmentedData[][] augData = new AugmentedData[numSourceArrays][];
        for (int i = 0; i < numSourceArrays; i++) {
            int srcArrayLength = data[i].length;
            augData[i] = new AugmentedData[srcArrayLength];
            for (int j = 0; j < srcArrayLength; j++)
                augData[i][j] = new AugmentedData(data[i][j], i, j);
        }
        return augData;
    }


    private static void print2DAugArray(AugmentedData[][] augData) {
        if (augData != null) {
            for (int i = 0; i < augData.length; i++) {
                for (int j = 0; j < augData[i].length; j++)
                    System.out.print(augData[i][j]);
                System.out.println();
            }
        }
    }

    private static void print1DAugArray(AugmentedData[] augData) {
        if (augData != null) {
            for (AugmentedData augDataItem : augData)
                System.out.println(augDataItem);
        }
    }

    private static void mergeAndPrint(int[][]data, int numTotalItems) {
        AugmentedData[][] augData = createAugmentedData(data);
        //You may uncomment the following two lines to see what's in the 2D augdata
        //print2DAugArray(augData);
        //System.out.println();

        AugmentedData[] sorted = KwayMerge.merge(augData, numTotalItems);
        print1DAugArray(sorted);
    }

    /**
     * Expected print of the 1D sorted array?
     *
     */
    private static void testEasyArrayMerge() {
        int[][] data = {{2, 5}, {3, 17}, {1, 7}};
        int numTotalItems = 6;
        mergeAndPrint(data, numTotalItems);
    }

    /**
     * Expected print of the 1D sorted array:
     *
     */
    private static void testJaggedArrayMerge() {
        int[][] data = {{2, 5, 22}, {3}, {1, 7, 9}};
        int numTotalItems = 7;
        mergeAndPrint(data, numTotalItems);
    }

    /**
     * Expected print of the 1D sorted array:
     *
     */
    private static void testStability() {
        int[][] data = {{1, 1}, {1, 1, 1}, {1}};
        int numTotalItems = 6;
        mergeAndPrint(data, numTotalItems);
    }


    /**
     * Expected print of the 1D sorted array:
     *
     */
    private static void testSmallGeneral() {
        int[][] data = {{1, 2, 3}, {2, 4}, {3},  {-4, 8, 12, 16}};
        int numTotalItems = 10;
        mergeAndPrint(data, numTotalItems);
    }

    public static void main(String[] args) {
        System.out.println(" **** test easy array merge ****");
        testEasyArrayMerge();

        System.out.println("\n **** test jagged array merge ****");
        testJaggedArrayMerge();

        System.out.println("\n **** test jagged array merge ****");
        testStability();

        System.out.println("\n **** test small, general array merge ****");
        testSmallGeneral();

    }
}
