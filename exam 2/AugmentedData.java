public class AugmentedData implements Comparable<AugmentedData> {

    private int key; // the key of this item
    private int arrayId; // the id of the array that the key belongs to
    private int itemId; // the item id (array index) of this key in its array

    public AugmentedData(int key, int arrayId, int itemId) {
        this.key = key;
        this.arrayId = arrayId;
        this.itemId = itemId;
    }

    public int getArrayId() {
        return arrayId;
    }

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
        if (this.key != that.key)
            return this.key - that.key;
        if (this.arrayId != that.arrayId)
            return this.arrayId - that.arrayId;
        return this.itemId - that.itemId;
    }

    @Override
    public String toString() {
        return "(" + key + ", " + arrayId + ", " + itemId + ")";
    }
}
