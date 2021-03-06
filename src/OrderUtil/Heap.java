package OrderUtil;

/**
 * Heap.java
 * @author Meigan Lu
 * @author Eileen Huynh
 * @author Sol Valdimarsdottir
 * @author Sam Yadav
 * @author Brandon Ho
 * CIS 22C Course Project
 */

//Maybe when ordering stuff we can keep track of when the person makes an order with the time import class

import java.util.Comparator;

import ProductUtil.ToString;

import java.util.ArrayList;

public class Heap<T> {

    private int heapSize;
    private ArrayList<T> heap;

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
        heapSize = 0;
        buildHeap(comparator);
    }

    /**
     * Constructor for the Heap class
     * 
     * @param size size of arraylist
     *             Calls buildHeap
     */
    public Heap(int size) {
        this.heap = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            heap.add(null);
        }
        this.heapSize = 0;
    }

    /** Mutators */

    /**
     * Converts an ArrayList into a valid
     * min heap. Called by constructor
     * Calls helper method heapify
     */
    public void buildHeap(Comparator<T> comparator) { // probably fix
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
        if (hasLeft(left) && (comparator.compare(getElement(left), getElement(indexMin)) > 0)) {
            indexMin = left;
        }
        // compares element of right child to smallest element
        if (hasRight(right) && (comparator.compare(getElement(right), getElement(indexMin)) > 0)) {
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
        if (heapSize == 0) {
            heap.set(1, key);
            heapSize++; // need or no need?
        } else {
            heapSize++; // need or no need?
            heap.set(heapSize, key);
            heapifyUp(heapSize, key, comparator);
        }

    }

    /**
     * Helper method for insert. Bubbles an element up to its proper location
     * 
     * @param index the current index of the key
     * @param key   the data
     * @precondition
     */
    private void heapifyUp(int index, T key, Comparator<T> c) {
        int indexMin = getParent(index);

        if (hasParent(index) && (c.compare(key, heap.get(indexMin)) < 0)) {
            T temp = heap.get(indexMin);
            heap.set(indexMin, key);
            heap.set(index, temp);
            heapifyUp(indexMin, key, c);
        }
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
    }

    /** Accessors */

    /**
     * returns the minimum element (highest priority)
     * 
     * @return the max value
     */
    public T getMin() {
        if (heapSize == 0) {
            return null;
        }
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

        return index / 2 > 0 ? index / 2 : 1;
    }

    /**
     * returns true or false whether the index
     * has a parent or not
     * 
     * @param index index to check
     * @return if index has a parent
     */
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
        if (index > heap.size() || index <= 0) {
            throw new IndexOutOfBoundsException("getLeft(): index out of bounds!");
        }
        return index + index;
    }

    /**
     * checks if the left child is null
     * 
     * @param index index of parent
     * @return if left child of index is null
     */
    public boolean hasLeft(int index) {

        return index * 2 < heapSize;
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

    /**
     * checks if the right child is null
     * 
     * @param index index of parent
     * @return if right child of index is null
     */
    public boolean hasRight(int index) {
        if (2 * index + 1 < heap.size()) {
            return heap.get(2 * index + 1) != null;
        }

        return false;
    }

    /**
     * finds the index of an element
     * 
     * @param data customer order to search for
     * @return index of the order in heap
     */
    public int getIndex(T data, Comparator<T> comparator) {
        for (int i = 1; i <= heapSize; i++) {
            if (comparator.compare(heap.get(i), data) == 0) {
                return i;
            }
        }
        return -1;
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
     * checks if heap is empty
     * 
     * @return if size of heap is 0
     */
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

    /**
     * searches for an order within the heap
     * 
     * @param data order to search for
     * @return order if found
     */
    public T search(T data, Comparator<T> comparator) {
        if (isEmpty()) {
            return null;
        } else {
            int index = 1;
            return search(data, index, comparator);
        }
    }

    /**
     * helper method for search, goes through entire
     * heap to find matching data
     * 
     * @param data  order to search for
     * @param index starting point to search at
     * @return element if found, null if not
     */
    private T search(T data, int index, Comparator<T> comparator) {
        if (heap.get(index) == null) {
            return null;
        }
        if (comparator.compare(data, heap.get(index)) == 0) {
            return heap.get(index);
        } else if (comparator.compare(data, heap.get(index)) < 0) {
            if (!hasLeft(index)) {
                return null;
            } else {
                return search(data, getLeft(index), comparator);
            }
        } else {
            if (!hasRight(index)) {
                return null;
            } else {
                return search(data, getRight(index), comparator);
            }
        }
    }

    /**
     * Creates a String of all elements in the heap
     */
    @Override
    public String toString() {
        String str = "";
        for (int i = 1; i <= heapSize; i++) {
            str += heap.get(i) != null ? heap.get(i).toString() : "";
        }
        return str;
    }

    /**
     * For specific case string
     * 
     * @param t
     * @return
     */
    public String toStr(ToString<T> t) {
        String str = "";
        for (int i = 1; i <= heapSize; i++) {
            str += heap.get(i) != null ? t.toStr(heap.get(i)) + "\n" : "";
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

}
