
/**
 * @author
 * Final Project
 */

import java.util.Comparator;
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
    public Heap(ArrayList<T> data, Comparator<T> comparator) {

    }

    /** Mutators */

    /**
     * Converts an ArrayList into a valid
     * max heap. Called by constructor
     * Calls helper method heapify
     */
    public void buildHeap(Comparator<T> comparator) {

    }

    /**
     * helper method to buildHeap, remove, and sort
     * bubbles an element down to its proper location within the heap
     * 
     * @param index an index in the heap
     */
    private void heapify(int index, Comparator<T> comparator) throws IndexOutOfBoundsException {

    }

    /**
     * Inserts the given data into heap
     * Calls helper method heapIncreaseKey
     * 
     * @param key the data to insert
     */
    public void insert(T key, Comparator<T> comparator) {

    }

    /**
     * Helper method for insert. Bubbles an element up to its proper location
     * 
     * @param index the current index of the key
     * @param key   the data
     * @precondition
     */
    private void heapIncreaseKey(int index, T key, Comparator<T> comparator) throws IndexOutOfBoundsException {

    }

    /**
     * removes the element at the specified index
     * Calls helper method heapify
     * 
     * @param index the index of the element to remove
     */
    public void remove(int index, Comparator<T> comparator) throws IndexOutOfBoundsException {

    }

    /** Accessors */

    /**
     * returns the maximum element (highest priority)
     * 
     * @return the max value
     */
    public T getMax() {
        return null;
    }

    /**
     * returns the maximum element (highest priority)
     * 
     * @return the max value
     */
    public T search(T data, Comparator<T> comparator) {
        return null;
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
        return -1;
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
        return -1;
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
     * Gets the element at the specified index
     * 
     * @param index at which to access
     * @return the element at index * @precondition
     * @throws IndexOutOfBoundsException
     */
    public T getElement(int index) throws IndexOutOfBoundsException {
        return null;
    }

    /** Additional Operations */

    /**
     * Creates a String of all elements in the heap
     */
    @Override
    public String toString() {
        return "";
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