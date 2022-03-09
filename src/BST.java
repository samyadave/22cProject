/**
 * BST.java
 * @author Meigan Lu
 * @author Eileen Huynh
 * @author Sol Valdimarsdottir
 * @author Sam Yadav
 * @author Brandon Ho
 * CIS 22C Course Project
 */

import java.util.NoSuchElementException;
import java.util.Comparator;

public class BST<T> {
    private class Node {
        private T data;
        private Node left;
        private Node right;
        
        public Node(T data) {
            this.data = data;
            left = null;
            right = null;
        }
    }
    
    private Node root;
    private Comparator<T> comparator;
    
    /***CONSTRUCTORS***/
    
    /**
     * Default constructor for BST
     * sets root to null
     */
    public BST() {
      root = null;
    }
   
    /**
     * Copy constructor for BST
     * @param bst the BST to make
     * a copy of 
     */
    public BST(BST<T> bst) {
    	if (bst == null) {
			return;
		}
		if (bst.getHeight() == 0) {
			bst = new BST<T>();
		} else {
			copyHelper(bst.root);
		}
    }
    
    /**
     * Helper method for copy constructor
     * @param node the node containing
     * data to copy
     */
    private void copyHelper(Node node) {
    	if (node == null) {
        	return;
        } else {
        	insert(node.data);
        	copyHelper(node.left);
        	copyHelper(node.right);
        }
    }

    public BST(Comparator<T> comparator) {
        this.comparator = comparator;
    }
    
    /***ACCESSORS***/
    
    /**
     * Returns the data stored in the root
     * @precondition !isEmpty()
     * @return the data stored in the root
     * @throws NoSuchElementException when
     * precondition is violated
     */
    public T getRoot() throws NoSuchElementException {
        if (isEmpty()) {
        	throw new NoSuchElementException("getRoot(): Binary tree is empty. No root!");
        }
    	return root.data;
    }
    
    /**
     * Determines whether the tree is empty
     * @return whether the tree is empty
     */
    public boolean isEmpty() {
       if (getSize() == 0) {
    	   return true;
       } else {
    	   return false;
       }
    }
    
    /**
     * Returns the current size of the 
     * tree (number of nodes)
     * @return the size of the tree
     */
    public int getSize() {
        return getSize(root);
    }
    
    /**
     * Helper method for the getSize method
     * @param node the current node to count
     * @return the size of the tree
     */
    private int getSize(Node node) {
       if(node == null) {
    	   return 0;
       }else {
    	   return getSize(node.left) + 1 + getSize(node.right);
       }
    }
    
    /**
     * Returns the height of tree by
     * counting edges.
     * @return the height of the tree
     */
    public int getHeight() {
        return getHeight(root);
    }
    
    /**
     * Helper method for getHeight method
     * @param node the current
     * node whose height to count
     * @return the height of the tree
     */
    private int getHeight(Node node) {
        if (node == null) {
        	return -1;
        } 
        int left = getHeight(node.left);
        int right = getHeight(node.right);
        
        if(left > right) {
        	return left + 1;
        } else {
        	return right + 1;
        }
    }
    
    /**
     * Returns the smallest value in the tree
     * @precondition !isEmpty()
     * @return the smallest value in the tree
     * @throws NoSuchElementException when the
     * precondition is violated
     */
    public T findMin() throws NoSuchElementException{
    	if (isEmpty()) {
        	throw new NoSuchElementException("findMin(): Binary tree is empty. No min!");
    	} else {
        	return findMin(root);
        }
    }
    
    /**
     * Helper method to findMin method
     * @param node the current node to check
     * if it is the smallest
     * @return the smallest value in the tree
     */
    private T findMin(Node node) {
    	if (node.left != null) {
    		return findMin(node.left);
    	} else {
    		return node.data;
    	}
    }
    
    /**
     * Returns the largest value in the tree
     * @precondition !isEmpty()
     * @return the largest value in the tree
     * @throws NoSuchElementException when the
     * precondition is violated
     */
    public T findMax() throws NoSuchElementException{
    	if (isEmpty()) {
        	throw new NoSuchElementException("findMax(): Binary tree is empty. No max!");
    	} else {
        	return findMax(root);
        }
    }
    
    /**
     * Helper method to findMax method
     * @param node the current node to check
     * if it is the largest
     * @return the largest value in the tree
     */
    private T findMax(Node node) {
    	if (node.right != null) {
    		return findMax(node.right);
    	} else {
    		return node.data;
    	}
    }
    
    /***MUTATORS***/
    
    /**
     * Inserts a new node in the tree
     * @param data the data to insert
     */
    public void insert(T data) {
    	if (root == null) {
            root = new Node(data);
        } else {
            insert(data, root);
        }
    }
    
    /**
     * Helper method to insert
     * Inserts a new value in the tree
     * @param data the data to insert
     * @param node the current node in the
     * search for the correct location
     * in which to insert
     */
    private void insert(T data, Node node, Comparator<T> comparator ) {  //UPDATE
        //sortbyName n = new sortbyName<>();
    	if (data.comparator.compare(node.data) <= 0) { 
    		if (node.left == null) {
    			node.left = new Node(data);
    		} else {
    			insert(data, node.left);
    		}
    	} else {
    		if (node.right == null) {
    			node.right = new Node(data);
    		} else {
    			insert(data, node.right);
    		}
    	}
    }
    
    /**
     * Removes a value from the BST
     * @param data the value to remove
     * @precondition !isEmpty()
     * @throws IllegalStateException when BST is empty
     */
    public void remove(T data) throws IllegalStateException {
      if(isEmpty()) {
    	  throw new IllegalStateException("remove(): Cannot remove from an empty binary tree.");
      }
      root =  remove(data, root);
    }
    
    /**
     * Helper method to the remove method
     * @param data the data to remove
     * @param node the current node
     * @return an updated reference variable
     */
    private Node remove(T data, Node node) { //UPDATE
    	if(node == null) {
    		return node;
    	} else if(data.compareTo(node.data) < 0) {
    		node.left = remove(data, node.left);
    	}else if(data.compareTo(node.data) > 0) {
    		node.right = remove(data, node.right);
    	}else {
    		if(node.right == null && node.left == null) {
    			node = null;
    		}else if(node.right == null && node.left != null) {
    			node = node.left;
    		}else if(node.left == null && node.right!= null) {
    			node = node.right;
    		}else {
    			T min = findMin(node.right);
    			node.data = min;
    			node.right = remove(min, node.right);
    		}
    	}
        return node;
    }
    
        
    /***ADDITIONAL OPERATIONS***/

    
    
    /**
     * Searches for a specified value
     * in the tree
     * @param data the value to search for
     * @return whether the value is stored
     * in the tree
     */
    public boolean search(T data) {
    	if(root == null) {
            return false;
        } else {
            return search(data, root);
        }
    }
    
    /**
     * Helper method for the search method
     * @param data the data to search for
     * @param node the current node to check
     * @return whether the data is stored
     * in the tree
     */
    private boolean search(T data, Node node) { //UPDATE
        if (data.compareTo(node.data) == 0) {
        	return true;
        } else if (data.compareTo(node.data) < 0) {
        	if (node.left == null) {
        		return false;
        	} else {
        		return search(data, node.left);
        	}
        } else {
        	if (node.right == null) {
        		return false;
        	} else {
        		return search(data, node.right);
        	}
        }
    }
    
    /**
     * Determines whether a BST is balanced
     * using the definition given in the course
     * lesson notes
     * Note that we will consider an empty tree
     * to be trivially balanced
     * @return whether the BST is balanced
     */
    public boolean isBalanced() {
        if(root == null) {
            return true;
        }
        return isBalanced(root);
    }
    
    /**
     * Helper method for isBalanced
     * to determine if a BST is balanced
     * @param n a Node in the tree
     * @return whether the BST is balanced
     * at the level of the given Node
     */
    private boolean isBalanced(Node n) {
        if(n != null) {
            if(Math.abs(getHeight(n.left) - getHeight(n.right)) > 1) {
                return false;
            }
            return isBalanced(n.left) && isBalanced(n.right);
        }
        return true;
    }
    
    /**
     * Returns a String containing the data
     * in post order
     * @return a String of data in post order
     */
    public String preOrderString() {
    	StringBuilder preOrder = new StringBuilder();
        preOrderString(root, preOrder);
        return preOrder + "\n";
    }
    
    /**
     * Helper method to preOrderString
     * Inserts the data in pre order into a String
     * @param node the current Node
     * @param preOrder a String containing the data
     */
    private void preOrderString(Node node, StringBuilder preOrder) {
    	if (node == null) {
        	return;	
        } else {
        	preOrder.append(node.data + " ");
        	preOrderString(node.left, preOrder);
        	preOrderString(node.right, preOrder);
        }
    }
    
    /**
     * Returns a String containing the data
     * in order
     * @return a String of data in order
     */
    public String inOrderString() {
    	StringBuilder inOrder = new StringBuilder();
        inOrderString(root, inOrder);
        return inOrder + "\n";
    }
    
    /**
     * Helper method to inOrderString
     * Inserts the data in order into a String
     * @param node the current Node
     * @param inOrder a String containing the data
     */
    private void inOrderString(Node node, StringBuilder inOrder) {
    	if (node == null) {
        	return;
        } else {
        	inOrderString(node.left, inOrder);
        	inOrder.append(node.data + " ");
        	inOrderString(node.right, inOrder);
        }
    }
    
    /**
     * Returns a String containing the data
     * in post order
     * @return a String of data in post order
     */
    public String postOrderString() {
    	StringBuilder postOrder = new StringBuilder();
    	postOrderString(root, postOrder);
        return postOrder + "\n";
    }
    
    /**
     * Helper method to postOrderString
     * Inserts the data in post order into a String
     * @param node the current Node
     * @param postOrder a String containing the data
     */
    private void postOrderString(Node node, StringBuilder postOrder) {
    	if(node == null) {
    		return;
    	} else {
    		postOrderString(node.left, postOrder);
    		postOrderString(node.right, postOrder);
    		postOrder.append(node.data + " ");
    	}
    }
    

    /***CHALLENGE METHODS***/
    
    /**
     * Creates a BST of minimal height given an array of values
     * @param array the list of values to insert
     * @precondition array must be sorted in ascending order
     * @throws IllegalArgumentException when the array is
     * unsorted
     */
    public BST(T[] array) throws IllegalArgumentException {
    	if(array == null) {
    		return;
    	}
    	for(int i = 0; i < array.length - 1; i++) {
    		if(array[i].compareTo(array[i+1]) >= 0) {
    			throw new IllegalArgumentException("BST Array: Array is unsorted.");
    		}
    	}
    	int low = 0;
    	int high = array.length - 1;
    	int mid = array.length/ 2;
    	//create a pyramid structure
    	insert(array[mid]); //root
    	int midleft = mid / 2;
    	insert(array[midleft]);
		while (low < mid) {
			if(array[low].compareTo(array[midleft]) == 0) {
				low++;
			} else {
				insert(array[low]);
				low++;
			}
		}
		int midright = (high + mid + 1) / 2;
		insert(array[midright]);
		mid = mid + 1;
		while(mid <= high) {
			if(array[mid].compareTo(array[midright]) == 0) {
				mid++;
			} else {
				insert(array[mid]);
				mid++;
			}
		}	
    }
    
}

class sortbyName implements Comparator<Product> {
    /**
     * Compares the primary keys (name) of two Product objects
     * @param p1 first product to compare
     * @param p2 second prodcut to compare
     * @return an int based on if p1 is >, <, or = to p2
     */
    @Override
    public int compare(Product p1, Product p2) {
        if(p1.equals(p2)) {
            return 0;
        }else if(!(p1.getName().equals(p2.getName()))) {
            return name.compareTO(p2.name)
        }
        return -1;
    }
}

class sortbyType implements Comparator<Product> {
     /**
     * Compares the secondary keys (type) of two Product objects
     * @param p1 first product to compare
     * @param p2 second prodcut to compare
     * @return an int based on if p1 is >, <, or = to p2
     */
    @Override
    public int compare(Product p1, Product p2) {
        return -1;
    }
}