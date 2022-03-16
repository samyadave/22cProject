package UserUtil;

/**
* HashTable.java
* @author Sam Yadav
* @author Sol Valdimarsdottir
* CIS 22C, Lab 5
*/
import java.util.ArrayList;

public class HashTable<T> {

    private int numElements;
    private ArrayList<LinkedList<T>> Table;

    /**
     * Constructor for the HashTable
     * class. Initializes the Table to
     * be sized according to value passed
     * in as a parameter
     * Inserts size empty Lists into the
     * table. Sets numElements to 0
     *
     * @param size the table size
     */
    public HashTable(int size) {
        this.Table = new ArrayList<>();
        this.numElements = 0;

        for (int i = 0; i < size; i++) {
            this.Table.add(new LinkedList<>());
        }

    } // O(n)

    /**
     * Constructor for HashTable class
     * Inserts the contents of the given array
     * into the Table at the appropriate indices
     *
     * @param array an array of elements to insert
     * @param size  the size of the Table
     */
    public HashTable(T[] array, int size) {
        if (array == null) {
            return;
        }
        this.Table = new ArrayList<>();
        this.numElements = 0;

        for (int i = 0; i < size; i++) { // size of table != length of array
            this.Table.add(new LinkedList<>()); // creating bucckes for the hash table
        }
        for (int i = 0; i < array.length; i++) {
            this.add(array[i]);
        }

    }

    /** Accessors */

    /**
     * returns the hash value in the Table
     * for a given Object
     *
     * @param t the Object
     * @return the index in the Table
     */
    private int hash(T t) {
        return Math.abs(t.hashCode()) % Table.size();
    } // O(n)

    /**
     * Counts the number of elements at this index
     *
     * @param index the index in the Table
     * @precondition index > Table.size - 1 || index < 0
     * @return the count of elements at this index
     * @throws IndexOutOfBoundsException when
     *                                   the precondition is violated
     */
    public int countBucket(int index) throws IndexOutOfBoundsException {
        if (index > this.Table.size() || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index value!");
        }

        return this.Table.get(index).getLength(); // お茶を飲みますか？
        // Return's the length of the table's index (all LinkedList functions)
    }// バババカ O(1)

    /**
     * Determines total number of elements in the Table
     *
     * @return total number of elements
     */
    public int getNumElements() {
        return this.numElements;
    } // O(1)

    /**
     * Accesses a specified element in the Table
     *
     * @param t the element to locate
     * @return the bucket number where the element
     *         is located or -1 if it is not found
     * @precondition t != null
     * @throws NullPointerException when the
     *                              precondition is violated
     */
    public int find(T t) throws NullPointerException {
        if (t == null) {
            throw new NullPointerException("BRUH");
        }

        // hash it, and then check if it's in that linked list of value

        LinkedList<T> bucket = Table.get(this.hash(t));
        if (bucket.findIndex(t) != -1) {
            return this.hash(t); // the bucket's index value is represented by this.hash(t)
        }
        return -1;
    } // best case O(1), average case O(1), worst case O(n)

    /**
     * Determines whether a specified element is in
     * the Table
     *
     * @param t the element to locate
     * @return whether the element is in the Table
     * @precondition n != null
     * @throws NullPointerException when the
     *                              precondition is violated
     */
    public boolean contains(T t) throws NullPointerException {
        if (t == null) {
            throw new NullPointerException("BRUH");
        }

        // hash it, and then check if it's in that linked list of value

        LinkedList<T> bucket = Table.get(this.hash(t));
        if (bucket.findIndex(t) != -1) {
            return true; // the bucket's index value is represented by this.hash(t)
        }
        return false;
    } // best case O(1), average case O(1), worst case O(n)

    /** Mutators */

    /**
     * Inserts a new element in the Table
     * at the end of the chain of the
     * correct bucket
     *
     * @param t the element to insert
     * @precondition t != null
     * @throws NullPointerException when the
     *                              precondition is violated
     */
    public void add(T t) throws NullPointerException {
        if (t == null) {
            throw new NullPointerException("BRUH");
        }

        Table.get(hash(t)).addLast(t);
        this.numElements++;
    } // O(1)

    /**
     * Removes the given element from the Table
     *
     * @param t the element to remove
     * @precondition t != null
     * @return whether t exists and was removed from the Table
     * @throws NullPointerException when the precondition
     *                              is violated
     */
    public boolean delete(T t) throws NullPointerException {
        if (t == null) {
            throw new NullPointerException("BRUH");
        }
        if (!this.contains(t)) {
            return false;
        } else {
            LinkedList<T> bucket = Table.get(this.hash(t));
            bucket.positionIterator();
            bucket.advanceIteratorToIndex(bucket.findIndex(t));
            bucket.removeIterator();
            numElements--;

            return true;
        }
    } // O(1)

    /**
     * Resets the hash table back to the
     * empty state, as if the one argument
     * constructor has just been called.
     */
    public void clear() {
        int size = this.Table.size();
        this.Table = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            this.Table.add(new LinkedList<>());
        }
        numElements = 0;
    } // O(n)

    /** Additional Methods */

    /**
     * Computes the load factor
     *
     * @return the load factor
     */
    public double getLoadFactor() {
        return (double) numElements / (double) Table.size();
    } // O(1)

    /**
     * Creates a String of all elements at a given bucket
     *
     * @param bucket the index in the Table
     * @return a String of elements, separated by spaces
     *         with a new line character at the end
     */
    public String bucketToString(int bucket) {
        return Table.get(bucket).toString();
    } // worst case O(n), best/average O(1)

    /**
     * Creates a String of the bucket number
     * followed by a colon followed by the first
     * element at each bucket followed
     * by a new line
     * For empty buckets, add the bucket number
     * followed by a colon followed by empty
     *
     * @return a String of all first elements
     *         at each bucket
     */
    public String rowToString() {
        String 何でも = "";
        for (int index = 0; index < this.Table.size(); index++) {
            何でも += "Bucket " + index + ": ";
            if (Table.get(index).getLength() != 0) {
                何でも += Table.get(index).getFirst().toString() + "\n";
            } else {
                何でも += "empty\n";
            }
        }
        return 何でも;
    } // O(n)

    /**
     * Starting at the 0th bucket, and continuing
     * in order until the last bucket,concatenates all
     * elements at all buckets into one String, with
     * a new line between buckets and one more new line at the
     * end of the entire String
     */
    @Override
    public String toString() {
        String 何でも = "";
        for (int index = 0; index < this.Table.size(); index++) {
            if (Table.get(index).getLength() != 0) {
                何でも += Table.get(index).toString();
            }
        }
        return 何でも + "\n";
    } // O(n)

    /**
     * FINAL PROJECT ADDITIONS
     * 
     */

    /**
     * Get the full object from passing an object with hashcode properties
     * 
     * @precondition t != null
     * @param t Object with only hashcode properties
     * @return Full object with all details
     * @throws NullPointerException if null passed
     */
    public T get(T t) throws NullPointerException {
        if (t == null) {
            throw new NullPointerException();
        }

        return this.Table.get(hash(t)).getFirst().equals(t) ? this.Table.get(hash(t)).getFirst() : null;
    }
}