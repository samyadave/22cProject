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
    public Heap(ArrayList<T> data, Comparator<T> comparator) { // still idk
        heap = new ArrayList<>(data);
        heapSize = data.size();
        buildHeap(heap, comparator);
    }

    /** Mutators */

    /**
     * Converts an ArrayList into a valid
     * min heap. Called by constructor
     * Calls helper method heapify
     */
    public void buildHeap(ArrayList<T> data, Comparator<T> comparator) { // probably fix
        for (int i = (heapSize / 2); i < 1; i--) {
            heapifyDown(i, comparator);
        }
    }

    /**
     * helper method to buildHeap, remove, and sort
     * bubbles an element down to its proper location within the heap
     * Follows the minHeap property
     * 
     * @param index an index in the heap
     */
    private void heapifyDown(int index, Comparator<T> comparator) throws IndexOutOfBoundsException {
        int indexMin = getParent(index);
        int left = getLeft(index);
        int right = getRight(index);

        // compares element of left child to parent
        if (hasLeft(left) && (comparator.compare(getElement(left), getElement(indexMin)) < 0)) {
            indexMin = left;
        }
        // compares element of right child to smallest element
        if (hasRight(right) && (comparator.compare(getElement(right), getElement(indexMin)) < 0)) {
            indexMin = right;
        }
        // if parent is not smallest value, swap with smallest child
        if (index != indexMin) {
            T temp = getElement(index);
            heap.set(index, getElement(indexMin));
            heap.set(indexMin, temp);
            heapifyDown(indexMin, comparator);
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
        heapSize++; // need or no need?
        heapifyUp(heapSize, key, comparator);
    }

    /**
     * Helper method for insert. Bubbles an element up to its proper location
     * 
     * @param index the current index of the key
     * @param key   the data
     * @precondition
     */
    private void heapifyUp(int index, T key, Comparator<T> comparator) {
        heap.add(key);
        int indexMin = getParent(index);

        if (hasParent(index) && (comparator.compare(key, getElement(indexMin)) < 0)) {
            T temp = getElement(indexMin);
            heap.set(indexMin, key);
            heap.set(index, temp);
            heapifyUp(indexMin, key, comparator);
        }

        /*
         * if (comparator.compare(key, heap.get(index)) > 0) {
         * T item = heap.get(index);
         * item = key;
         * }
         * while (index>1 && comparator.compare(getElement(getParent(index)),
         * heap.get(index))<0) {
         * T temp = heap.get(index);
         * //swap the heap at index to equal
         * heap.set(index, getElement(getParent(index)));
         * heap.set(getParent(index), temp);
         * index = getParent(index);
         * }
         */
    }

    /**
     * removes the element at the specified index
     * Calls helper method heapify
     * 
     * @param index the index of the element to remove
     */
    public void remove(int index, Comparator<T> comparator) throws IndexOutOfBoundsException {
        if (heapSize == 0 || index > heapSize || index < 1) {
            throw new IndexOutOfBoundsException(
                    "Heap remove(): Index is either out of bounds, or heap has no values to remove!");
        }

        if (index == heapSize) { // if removing very last element
            heap.remove(index);
            heapSize--;
        } else { // removing in middle of list
            T temp = getElement(index);
            heap.set(index, getElement(heapSize));
            heap.set(heapSize, temp);
            heap.remove(heapSize);
            heapSize--;
            heapifyDown(index, comparator);
        }
        // heap.remove(index);
        // heapSize--;
        // heap.insert(getElement(heapSize)
    }

    /** Accessors */

    /**
     * returns the minimum element (highest priority)
     * 
     * @return the max value
     */
    public T getMin() {
        return heap.get(1);
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
        if (index > heapSize || index <= 0) {
            throw new IndexOutOfBoundsException("getParent(): Index is likely out of bounds!");
        }
        return index / 2;
    }

    public boolean hasParent(int index) {
        if (index <= 1) {
            return false;
        } else {
            return true;
        }
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
    public int getLeft(int index) throws IndexOutOfBoundsException {
        if (index > heapSize || index <= 0) {
            throw new IndexOutOfBoundsException("getLeft(): index out of bounds!");
        }
        return index + index;
    }

    public boolean hasLeft(int index) {
        T left = getElement(index);
        return left != null;
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
    public int getRight(int index) throws IndexOutOfBoundsException {
        if (index > heapSize || index <= 0) {
            throw new IndexOutOfBoundsException("getRight(): index out of bounds!");
        }
        return index + index + 1;
    }

    public boolean hasRight(int index) {
        T right = getElement(index);
        return right != null;
    }

    /**
     * returns the heap size (current number of elements)
     * 
     * @return the size of the heap
     */
    public int getHeapSize() {
        return heapSize;
    }

    public boolean isEmpty() {
        return getHeapSize() == 0;
    }

    /**
     * Gets the element at the specified index
     * 
     * @param index at which to access
     * @return the element at index * @precondition
     * @throws IndexOutOfBoundsException
     */
    public T getElement(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > heapSize) {
            throw new IndexOutOfBoundsException("getElement: Index out of bounds!");
        }
        return heap.get(index);
    }

    /** Additional Operations */

    public T search(T data, Comparator<T> comparator){
        if(isEmpty()){
            return 
        }
    }

    /**
     * Creates a String of all elements in the heap
     */
    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i <= heapSize; i++) {
            str += "Order #" + i + ": " + getElement(i) + "\n";
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

    /**
     * TODO: Returns the according order
     * 
     * @param customerLogin
     */
    public void getOrder(String customerLogin) {

    }

}
