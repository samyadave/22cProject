package OrderUtil;

/**
 * @author
 * Final Project
 */

//Maybe when ordering stuff we can keep track of when the person makes an order with the time import class

import java.util.Comparator;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class Heap<T> {

    private int heapSize;
    private ArrayList<T> heap;

    /**
     * Priority is to be assigned according to the date and time of the order and
     * the shipping speed selected by the customer
     * Priority Shipping Greatest to Least: Overnight shipping, rush shipping,
     * standard shipping
     * 
     * The arrayList<T>
     * 
     */

    /**
     * Constructors/
     * 
     * /**
     * Constructor for the Heap class
     * 
     * @param data an unordered ArrayList
     *             Calls buildHeap
     */
    public Heap(ArrayList<T> data, Comparator<T> comparator) {

    }

    /** Mutators */

    /**
     * Converts an ArrayList into a valid
     * max heap. Called by constructor
     * Calls helper method heapify
     */
    public void buildHeap(Comparator<T> comparator) {
        for (int i = (heapSize / 2); i < 1; i--) {
            heapify(i, comparator);
        }
    }

    /**
     * helper method to buildHeap, remove, and sort
     * bubbles an element down to its proper location within the heap
     * 
     * @param index an index in the heap
     */
    private void heapify(int index, Comparator<T> comparator) throws IndexOutOfBoundsException {
        int indexMax = getParent(index);
        int left = get_left(index);
        int right = get_right(index);

        // compares element of left child to parent
        if (comparator.compare(getElement(left), getElement(indexMax)) > 0) {
            indexMax = left;
        }
        // compares element of right child to largest element
        if (comparator.compare(getElement(right), getElement(indexMax)) > 0) {
            indexMax = right;
        }
        // if parent is not largest value, swap with largest child
        if (index != indexMax) {
            T temp = getElement(index);
            heap.set(index, getElement(indexMax));
            heap.set(indexMax, temp);
            heapify(index, comparator);
        }
    }

    /**
     * Inserts the given data into heap
     * When placing an order, it adds a new order to the heap
     * Calls helper method heapIncreaseKey
     * 
     * @param key the data to insert
     */
    public void insert(T key, Comparator<T> comparator) {
        heapSize++;
        heapIncreaseKey(heapSize, key, comparator);
    }

    /**
     * Helper method for insert. Bubbles an element up to its proper location
     * 
     * @param index the current index of the key
     * @param key   the data
     * @precondition
     */
    private void heapIncreaseKey(int index, T key, Comparator<T> comparator) throws IndexOutOfBoundsException {
        if (comparator.compare(key, heap.get(index)) > 0) {
            T item = heap.get(index);
            item = key;
        }
        while (index > 1 && comparator.compare(getElement(getParent(index)), heap.get(index)) < 0) {
            T temp = heap.get(index);
            // swap the heap at index to equal
            heap.set(index, getElement(getParent(index)));
            heap.set(getParent(index), temp);
            index = getParent(index);
        }
    }

    /**
     * removes the element at the specified index
     * Calls helper method heapify
     * 
     * @param index the index of the element to remove
     */
    public void remove(int index, Comparator<T> comparator) throws IndexOutOfBoundsException {
        if (heapSize == 0 || index > heapSize || index < 0) {
            throw new IndexOutOfBoundsException(
                    "Heap remove(): Index is either out of bounds, or heap has no values to remove!");
        }
        // when removing something from a heap
        heap.remove(index);
        heapSize--;
        heap.insert(index, heap.get(index).getLast());

        /**
         * 2. Replace the deletion node
         * with the "fartest right node" on the lowest level
         * of the Binary Tree
         * (This step makes the tree into a "complete binary tree")
         * 
         * 3. Heapify (fix the heap):
         * 
         * if ( value in replacement node < its parent node )
         * Filter the replacement node UP the binary tree
         * else
         * Filter the replacement node DOWN the binary tree
         */

    }

    /** Accessors */

    /**
     * returns the maximum element (highest priority)
     * 
     * @return the max value
     */
    public T getMax() {
        return heap.get(1);
    }

    /**
     * returns the maximum element (highest priority)
     * we search in the shopping cart,
     * 
     * @return the
     */
    public T search(T data, Comparator<T> comparator) {

    }

    /**
     * returns the location (index) of the
     * parent of the element stored at index
     * 
     * @param index the current index
     * @return the index of the parent
     * @precondition
     * @throws IndexOutOfBoundsException
     */
    public int getParent(int index) throws IndexOutOfBoundsException {
        if (index >= heapSize || index <= 0) {
            throw new IndexOutOfBoundsException("getParent(): Index is likely out of bounds!");
        }
        return index / 2;
    }

    /**
     * returns the location (index) of the
     * left child of the element stored at index
     * 
     * @param index the current index
     * @return the index of the left child
     * @precondition
     * @throws IndexOutOfBoundsException
     */
    public int get_left(int index) throws IndexOutOfBoundsException {
        if (index >= heapSize || index <= 0) {
            throw new IndexOutOfBoundsException("get_left(): Index is likely out of bounds!");
        }
        return index + index;
    }

    /**
     * returns the location (index) of the
     * right child of the element stored at index
     * 
     * @param index the current index
     * @return the index of the right child
     * @precondition
     * @throws IndexOutOfBoundsException
     */
    public int get_right(int index) throws IndexOutOfBoundsException {
        if (index >= heapSize || index <= 0) {
            throw new IndexOutOfBoundsException("get_right(): Index is likely out of bounds!");
        }
        return index + index + 1;
    }

    /**
     * returns the heap size (current number of elements)
     * 
     * @return the size of the heap
     */
    public int getHeapSize() {
        return heapSize;
    }

    /**
     * Gets the element at the specified index
     * 
     * @param index at which to access
     * @return the element at index * @precondition
     * @throws IndexOutOfBoundsException
     */
    public T getElement(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= heapSize) {
            throw new IndexOutOfBoundsException("getElement: Index out of bounds!");
        }
        return heap.get(index);
    }

    /** Additional Operations */

    /**
     * Creates a String of all elements in the heap
     */
    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < heapSize; i++) {
            str += getElement(i) + "\n";
        }
        return str;
    }

    /**
     * Uses the heap sort algorithm to
     * sort the heap into ascending order
     * Calls helper method heapify
     * 
     * @return an ArrayList of sorted elements
     * @postcondition heap remains a valid heap
     */
    public ArrayList<T> sort(Comparator<T> comparator) {
        return new ArrayList<T>();
    }

    class shippingComparator implements Comparator<Order> {
        /**
         * Compares the shipping time of two Product objects,
         * then compares the time of order of two Product objects
         * 
         * @param p1 first product to compare
         * @param p2 second prodcut to compare
         * @return an int based on if p1 is >, <, or = to p2
         */
        @Override
        public int compare(Order p1, Order p2) {
            if (p1.equals(p2)) {
                return 0;
            }
            return Integer.compare(p1.getShippingSpeed(), (p2.getShippingSpeed()));
        }
    }

    class dateComparator implements Comparator<Order> {
        /**
         * Compares the shipping time of two Product objects,
         * then compares the time of order of two Product objects
         * 
         * @param p1 first product to compare
         * @param p2 second prodcut to compare
         * @return an int based on if p1 is >, <, or = to p2
         */
        @Override
        public int compare(Order p1, Order p2) {
            if (p1.equals(p2)) {
                return 0;
            }
            return p1.getDate().compareTo(p2.getDate());
        }
    }

}
