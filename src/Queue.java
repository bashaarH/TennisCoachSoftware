 

import java.io.Serializable;

public class Queue implements Serializable {

	private Node head;
	private Node tail;
	private int size;

	/*
	 * Default constructor for the Queue class
	 */
	public Queue() {
		Node node = new Node();
		head = node;
		tail = node;
		size = 1;
	}

	/*
	 * Constructor for the Queue class with a given value as the parameter
	 */
	public Queue(Object value) {
		Node node = new Node(value);
		head = node;
		tail = node;
		size = 1;
	}

	/*
	 * Add a node to this Queue object
	 */
	public void enqueue(Object value) {

		if (head.getValue() == null) {
		
			head = new Node(value);
			tail = head;
			return;
		}
		Node node = new Node(value, head);
		// Set the previous link of the head as the new node
		head.setPrev(node);

		// Set the new node as the new head
		head = head.getPrev();
		size = size + 1;
	}

	/*
	 * Remove the tail of this Queue object
	 */
	public void dequeue() {
		tail = tail.getPrev();
		tail.setNext(null);
		size = size - 1;
	}

	/*
	 * Returns the value stored in the head of this queue
	 */
	public Object getHead() {
		return head.getValue();
	}

	/*
	 * Returns the value stored in the tail of this queue
	 */
	public Object getTail() {
		return tail.getValue();
	}

	/*
	 * Returns the size of this queue
	 */
	public int getSize() {
		return size;
	}

	/*
	 * Returns an array of all the values stored in this queue
	 */
	public Object[] getValues() {
		Node node = head;
		// System.out.println(size);
		Object[] output = new Object[size];
		for (int i = 0; i < size; i++) {
			output[i] = node.getValue();
			node = node.getNext();
		}
		return output;

	}
	

	/*
	 * Prints all the values in this queue
	 */

	public void print() {
		Node node = head;
		// Object[] output = new Object[size];
		for (int i = 0; i < size; i++) {
			System.out.println(((Statistic) node.getValue()));
			node = node.getNext();
		}
	}

	private class Node implements Serializable {
		private Node prev;
		private Node next;
		private Object value;

		/*
		 * Default constructor for the Node Class
		 */
		public Node() {
			prev = null;
			next = null;
			value = null;
		}

		/*
		 * Constructor for Node class with the value as parameter;
		 */
		public Node(Object value) {
			this.value = value;
			prev = null;
			next = null;
		}

		/*
		 * Constructor for Node class with a value and the next node as the parameters
		 */
		public Node(Object value, Node next) {
			this.value = value;
			this.next = next;
			prev = null;
		}

		/*
		 * Returns the value stored in the Node
		 */
		public Object getValue() {
			return value;
		}

		/*
		 * Returns the node after this node
		 */
		public Node getNext() {
			return next;
		}

		/*
		 * Returns the node before this Node
		 */
		public Node getPrev() {
			return prev;
		}

		/*
		 * Sets the reference for the node after this node
		 */
		public void setNext(Node node) {
			this.next = node;
		}

		/*
		 * Sets the reference for the node before this node
		 */
		public void setPrev(Node node) {
			this.prev = node;
		}
	}
}