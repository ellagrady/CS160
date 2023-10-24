/**
 * The AugmentedData class representes a Comparable object of Augmented Data types
 * It allows for viewing the array id and item id's of an augmenteddata object
 * through several methods and supports a standard compareTo of two augmenteddata
 * objects.
 */
public class AugmentedData implements Comparable<AugmentedData> {

    private int key; // the key of this item
    private int arrayId; // the id of the array that the key belongs to
    private int itemId; // the item id (array index) of this key in its array

    // constructor for AugmentedData type
    // initializes a new AugmentedData type from the given arguments
    // Params:
    //      key : object's value
    //      arrayId : the array id for the object
    //      itemId : the item id for the object
    public AugmentedData(int key, int arrayId, int itemId) {
        this.key = key;
        this.arrayId = arrayId;
        this.itemId = itemId;
    }

    // returns the arrayId for the AugmentedData object
    public int getArrayId() {
        return arrayId;
    }

    // returns the itemId for the AugmentedData object
    public int getItemId() {
        return itemId;
    }

    /**
     * Compares two AuxData objects, based on their keys, arrayIds, and itemIds
     *
     * @return the value {@code 0} if the argument that is equal to this;
     * a negative integer if this is considered "less" than that and
     * should go before that in a sorted, non-increasing sequence;
     * and a positive integer if this is "larger" than that and
     * should go after that in a sorted sequence.
     * The compareTo definiton is important for stable sort, when possible
     */
    @Override
    public int compareTo(AugmentedData that) {
        if (this.key != that.key) // if the keys are not the same find difference
            return this.key - that.key;
        if (this.arrayId != that.arrayId) // if arrayIds not same find difference
            return this.arrayId - that.arrayId;
        return this.itemId - that.itemId; // find difference of itemIds
    }

    // convert the AugmentedData object to a string represented as
    // (key, arrayId, itemId)
    @Override
    public String toString() {
        return "(" + key + ", " + arrayId + ", " + itemId + ")";
    }
}
