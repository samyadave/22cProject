/*
 * Defines a doubly-linked list class
 * @author Sol Valdimarsdottir
 * @author Sam Yadav
*/

import java.util.NoSuchElementException;

public class LinkedList<T> {
    private class Node {
        private T data;
        private Node next;
        private Node prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private int length;
    private Node first;
    private Node last;
    private Node iterator;

    /**** CONSTRUCTORS ****/

    /**
     * Instantiates a new LinkedList with default values
     * 
     * @precondition - None *************************************
     * @postcondition - New LinkedList object created with null values
     */
    public LinkedList() {
        this.first = null;
        this.last = null;
        this.iterator = null;
        this.length = 0;
    }

    /**
     * Converts the given array into a LinkedList
     * 
     * @param array the array of values to insert into this LinkedList
     * @postcondition new linked list is created with data from array
     */

    public LinkedList(T[] array) {
        // should handle "null" Object case, make a new empty linked list
        this();
        if (array == null) {
            return;
        } else {
            for (int i = 0; i < array.length; i++) {
                this.addLast(array[i]);
            }
        }

    }

    /**
     * Instantiates a new LinkedList by copying another List
     * 
     * @param original the LinkedList to copy
     * @postcondition a new List object, which is an identical,
     *                but separate, copy of the LinkedList original
     */
    public LinkedList(LinkedList<T> original) {
        if (original == null) {
            return;
        }
        if (original.length == 0) {
            length = 0;
            first = null;
            last = null;
            iterator = null;
        } else {
            Node temp = original.first;
            while (temp != null) {
                addLast(temp.data);
                temp = temp.next;
            }
            iterator = null;
        }
    }

    /**** ACCESSORS ****/

    /**
     * Returns the value stored in the first node
     * 
     * @precondition length isn't 0/no empty linked list
     * @return the value stored at node first
     * @throws NoSuchElementException when List is empty
     * @postcondition: method returns T data of the first data node of the linked
     *                 list
     */
    public T getFirst() throws NoSuchElementException {
        if (this.length == 0) {
            throw new NoSuchElementException("getHead(): List is empty.");
        }

        return first.data;
    }

    /**
     * Returns the value stored in the last node
     * 
     * @precondition length is not 0
     * @return the value stored in the node last
     * @throws NoSuchElementException when list is empty
     * @postcondition returns T data of the last node in linked list
     */
    public T getLast() throws NoSuchElementException {
        if (this.length == 0) {
            throw new NoSuchElementException("getHead(): List is empty.");
        }

        return this.last.data;
    }

    /**
     * Returns the data stored in the iterator node
     * 
     * @precondition iterator != null
     * @throw NullPointerException when iterator == null
     */
    public T getIterator() throws NullPointerException {
        if (this.iterator == null) {
            throw new NullPointerException("getIterator(): Cannot return null iterator.");
        }

        return this.iterator.data;
    }

    /**
     * Returns the current length of the LinkedList
     * 
     * @return the length of the LinkedList from 0 to n
     */
    public int getLength() {
        if (this.length < 0) {
            this.length = 0;
        }
        return this.length;
    }

    /**
     * Returns whether the LinkedList is currently empty
     * 
     * @return whether the LinkedList is empty
     */
    public boolean isEmpty() {
        return length == 0;
    }

    /**
     * Returns whether the iterator is offEnd, i.e. null
     * 
     * @return whether the iterator is null
     */
    public boolean offEnd() {
        return (this.iterator == null);
    }

    /**** MUTATORS ****/

    /**
     * Creates a new first element
     * 
     * @param data the data to insert at the front of the LinkedList
     * @postcondition a new node is inserted at the top of the linked list
     */
    public void addFirst(T data) {
        Node N = new Node(data);
        if (length == 0) {
            first = last = N;
        } else {
            N.next = first;
            first = N;
        }
        length++;

    }

    /**
     * Creates a new last element
     * 
     * @param data the data to insert at the end of the LinkedList
     * @postcondition a new node is inserted at the end of the list
     */
    public void addLast(T data) {
        if (length == 0) {
            first = last = new Node(data);
        } else {
            this.last.next = new Node(data);
            this.last.next.prev = this.last;
            this.last = this.last.next;
        }
        length++;
    }

    /**
     * Inserts a new element after the iterator
     * edge cases: iterator == last
     * 
     * @param data the data to insert
     * @precondition iterator != null
     * @throws NullPointerException when iterate == null
     */
    public void addIterator(T data) throws NullPointerException {
        if (this.iterator == null) {
            throw new NullPointerException("addIterator(): Cannot add in front of null!");
        } else if (this.iterator == this.last) {
            this.addLast(data);
        } else {
            this.iterator.next.prev = new Node(data);
            this.iterator.next.prev.next = this.iterator.next;
            this.iterator.next = this.iterator.next.prev;
            this.iterator.next.prev = this.iterator;
        }
        length++;
    }

    /**
     * removes the element at the front of the LinkedList
     * 
     * @precondition Shouldn't be an empty list
     * @postcondition the first node should be removed
     * @throws NoSuchElementException when the list is empty
     */
    public void removeFirst() throws NoSuchElementException {
        if (length == 0) {
            throw new NoSuchElementException("removeFirst(): Cannot remove from an empty List!");
        } else if (length == 1) {
            first = last = null;
            this.iterator = null;
        } // case where there's only one length
        else {
            if (this.iterator == first) {
                this.iterator = null;
            }
            this.first = this.first.next;
            this.first.prev = null; // disconnect from the old first node
        }

        length--;
    }

    /**
     * removes the element at the end of the LinkedList
     * 
     * @precondition list is not empty
     * @postcondition the last node is removed
     * @throws NoSuchElementException if list is empty
     */
    public void removeLast() throws NoSuchElementException {
        if (length == 0) {
            throw new NoSuchElementException("removeFirst(): Cannot remove from an empty List!");
        } else if (length == 1) {
            first = last = null;
            this.iterator = null;
        } // case where there's only one length
        else {
            // this.last.prev.next = null; //removes the forward link from 2nd last to last
            if (this.iterator == last) {
                this.iterator = null;
            }
            this.last = this.last.prev;
            this.last.next = null; // disconnect from the old last node
        }
        length--;
    }

    /**
     * removes the element referenced by the iterator
     * edge cases: iterator == last, iterator == first
     * 
     * @precondition Iterator cannot be null
     * @postcondition The node referenced by iterator will be removed
     * @throws NullPointerException when iterator is null
     */
    public void removeIterator() throws NullPointerException {
        if (this.iterator == null) {
            throw new NullPointerException("removeIterator(): Cannot remove from a null iterator.");
        }
        if (this.iterator == first) {
            this.removeFirst();
        } else if (this.iterator == last) {
            this.removeLast();
        } else {
            this.iterator.prev.next = this.iterator.next;
            this.iterator.next.prev = this.iterator.prev;
            length--;
        }
        this.iterator = null;

    }

    /**
     * places the iterator at the first node
     * 
     * @precondition length != 0
     * @postcondition iterator placed on first node
     * @throws NullPointerException when length = 0
     */
    public void positionIterator() throws NullPointerException {
        if (this.length == 0) {
            throw new NullPointerException("positionIterator(): Cannot position onto empty list.");
        }

        this.iterator = this.first;
    }

    /**
     * Moves the iterator one node towards the last
     *
     * @precondition iterator!=null
     * @postcondition iterator moves one node towards the end
     * @throws NullPointerException when iterator=null
     */
    public void advanceIterator() throws NullPointerException {
        if (this.iterator == null) {
            throw new NullPointerException("advanceIterator(): Cannot advance a null iterator!");
        } else {
            this.iterator = this.iterator.next;
        }
    }

    /**
     * Moves the iterator one node towards the first
     * edge case: when iterator is on first
     * 
     * @precondition iterator!=null
     * @postcondition iterator moves one node towards the beginning
     * @throws NullPointerException when iterator=null
     */
    public void reverseIterator() throws NullPointerException {
        if (this.iterator == null) {
            throw new NullPointerException("reverseIterator(): Cannot reverse a null iterator!");
        } else if (this.iterator == first) {
            this.iterator = this.last;
        } else {
            this.iterator = this.iterator.prev;
        }
    }

    /**** ADDITIONAL OPERATIONS ****/

    /**
     * Converts the LinkedList to a String, with each value separated by a blank
     * line At the end of the String, place a new line character
     * 
     * @return the LinkedList as a String
     */
    @Override
    public String toString() {
        String result = "";
        Node temp = first;
        while (temp != null) {
            result += temp.data + " ";
            temp = temp.next;
        }
        result += "\n";
        return result;
    }

    /**
     * Determines whether the given Object is another LinkedList, containing the
     * same data in the same order
     * 
     * @param o another Object
     * @return whether there is equality
     */
    @SuppressWarnings("unchecked") // good practice to remove warning here
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof LinkedList)) { // checking that object is a linkedlist
            return false;
        }

        LinkedList<T> obj = (LinkedList<T>) o; // letting the program know obj is linkedlist

        if (this.length != obj.getLength()) { // checking if both lists have the same length
            return false;
        }

        // Checking equality of each node
        Node thisTemp = this.first;
        Node listTemp = obj.first;
        while (thisTemp != null && listTemp != null) {
            if (!(thisTemp.data.equals(listTemp.data))) {
                return false;
            }
            thisTemp = thisTemp.next;
            listTemp = listTemp.next;
        }

        return true;
    }

    /** CHALLENGE METHODS */

    /**
     * Zippers two LinkedLists to create a third List which contains alternating
     * values from this list and the given parameter For example: [1,2,3] and
     * [4,5,6] -> [1,4,2,5,3,6] For example: [1, 2, 3, 4] and [5, 6] -> [1, 5, 2, 6,
     * 3, 4] For example: [1, 2] and [3, 4, 5, 6] -> [1, 3, 2, 4, 5, 6]
     * 
     * @param list the second LinkedList in the zipper
     * @return a new LinkedList, which is the result of zippering this and list
     * @postcondition this and list are unchanged
     */
    public LinkedList<T> zipperLists(LinkedList<T> list) {
        LinkedList<T> l = new LinkedList<T>();

        if (list.isEmpty() && this.isEmpty()) {
            return l;
        } else if (list.isEmpty()) {
            return this;
        } else if (this.isEmpty()) {
            return list;
        }

        Node thisTemp = this.first;
        Node listTemp = list.first;

        if (this.getLength() == list.getLength()) {
            while (thisTemp != null) {
                l.addLast(thisTemp.data);
                l.addLast(listTemp.data);
                thisTemp = thisTemp.next;
                listTemp = listTemp.next;
            }
        } else {
            Node temp3 = this.getLength() > list.getLength() ? list.first : this.first;
            while (temp3 != null) {
                l.addLast(thisTemp.data);
                l.addLast(listTemp.data);
                temp3 = temp3.next;
                thisTemp = thisTemp.next;
                listTemp = listTemp.next;
            }

            Node temp4 = this.getLength() > list.getLength() ? thisTemp : listTemp;

            while (temp4 != null) {
                l.addLast(temp4.data);
                temp4 = temp4.next;
            }

        }

        return l;

    }

    /**
     * Determines whether a LinkedList is reversible, i.e. the data is the same
     * written both forward and backward e.g. isReversible(1 2 3 2 1) -> true e.g.
     * isReversible(A B B A) -> true
     * 
     * @return whether the list is reversible
     */
    public boolean isReversible() {
        // make a new list that reverses the values using addLast then use equals to
        // compare the new list and the other
        LinkedList<T> l = new LinkedList<T>();

        if (length == 1) {
            return true;
        }

        Node thisTemp = this.first;
        while (thisTemp != null) {
            l.addFirst(thisTemp.data);
            thisTemp = thisTemp.next;
        }

        return this.equals(l);
    }

    /**
     * Determines at which index the iterator is located
     * Indexed from 0 to length - 1
     * 
     * @return the index number of the iterator
     * @precondition iterator != null
     * @throws NullPointerException when precondition is violated
     */
    public int getIteratorIndex() throws NullPointerException {
        if (offEnd()) {
            throw new NullPointerException("getIteratorIndex(): iterator Off the END!");
        }
        int i = 0;
        for (Node temp = this.first; !temp.equals(this.iterator); temp = temp.next) {
            i++;
        }

        return i;
    }

    /**
     * Searches the LinkedList for a given
     * element's index
     * 
     * @param data the data whose index to locate
     * @return the index of the data or -1 if
     *         the data is not contained in the LinkedList
     */
    public int findIndex(T data) {
        int i = 0;
        boolean found = false;
        Node temp = this.first;
        while (temp != null) {
            if (temp.data.equals(data)) {
                found = true;
                break;
            }
            temp = temp.next;
            i++;
        }

        return found ? i : -1;
    }

    /**
     * Advances the iterator to location within
     * the LinkedList specified by the given index
     * 
     * @param index the index at which to place the
     *              iterator.
     * @precondition iterator != null and index < size
     * @throws NullPointerException when the precondition is violated
     *
     */
    public void advanceIteratorToIndex(int index) throws NullPointerException {
        if (iterator == null || index >= this.length) {
            throw new NullPointerException("BRUH");
        }

        this.iterator = this.first;
        for (int i = 0; i < index; i++) {
            iterator = iterator.next;
        }
    }

}