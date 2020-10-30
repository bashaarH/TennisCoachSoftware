


import java.util.ArrayList;
import java.io.*;

public class BinaryTree implements Serializable {

	private Node root;
	private ArrayList<Player> list; // A list of all the players in this binary tree stored in alphabetical order

	/*
	 * Default constructor for the binary tree class
	 */
	public BinaryTree() {
		this.root = null;
		list = new ArrayList<Player>();
	}

	/*
	 * Constructor for BinaryTree class with a Player object as the parameter
	 */
	public BinaryTree(Player player) {
		this.root = new Node((Object) player);
		list = new ArrayList<Player>();
	}

	/*
	 * Adds the given player to this BinaryTree 
	 */
	public void addPlayer(Player player) {
		if (root == null) {
			root = new Node(player);
			return;
		}
		add(root, player);
	}

	/*
	 * Recursively find an appropriate location for the new player and add it to
	 * this BinaryTree
	 */
	private void add(Node start, Player player) {
		Node node = new Node(player);
		String name = player.getName();
		String nodeName = ((Player) start.getValue()).getName(); // Name of the player who is already stored in the
																	// binary tree
		if (test(name, nodeName, 0)) // If the new name comes after the stored name
		{
			if (start.getRight() != null) {
				add(start.getRight(), player);
			} else {
				node.setParent(start);
				start.setRight(node);
				return;
			}
		} else if (!test(name, nodeName, 0)) // If the new name comes before the stored name
		{
			if (start.getLeft() != null) {
				add(start.getLeft(), player);
			} else {
				node.setParent(start);
				start.setLeft(node);
				return;
			}

		}
	}

	/*
	 * Searches this binary tree for the player with the given name
	 */
	public Player search(String name) {
		return search(name, root);
	}

	/*
	 * Searches the binary tree for the player with the given name starting from the
	 * given Node
	 */
	private Player search(String name, Node start) {
		String nodeName = ((Player) start.getValue()).getName();
		if (name.equals(nodeName))
			return (Player) start.getValue();
		if (test(name, nodeName, 0)) // If the given name comes after the stored name
		{
			return search(name, start.getRight());
		} else // If the new name comes before the stored name
		{
			return search(name, start.getLeft());
		}

	}

	/*
	 * Returns the node that contains the player with the specified name
	 */
	private Node searchNode(String name, Node start) {
		String nodeName = ((Player) start.getValue()).getName();
		if (name.equals(nodeName))
			return start;
		if (test(name, nodeName, 0)) // If the given name comes after the stored name
		{
			return searchNode(name, start.getRight());
		} else // If the new name comes before the stored name
		{
			return searchNode(name, start.getLeft());
		}
	}

	/*
	 * Returns an ArrayList of all the players in this binary tree in alphabetical
	 * order
	 */
	public ArrayList<Player> getPlayerList() {
		list = new ArrayList<Player>();
		inorder(root);
		return list;
	}

	/*
	 * A utility method for an in-order transversal of this binary tree
	 */
	private void inorder(Node start) {
		if (start != null) {
			inorder(start.getLeft());
			list.add((Player) start.getValue());
			inorder(start.getRight());
		}
	}

	/*
	 * Utility method to determine if the first string comes before the second
	 * string when put in alphabetical order
	 */
	private boolean test(String first, String second, int index) {
		if (first.length() == index || second.length() == index) {
			return false;
		}
		if (first.charAt(index) > second.charAt(index))
			return true;
		else if (second.charAt(index) > first.charAt(index))
			return false;

		return test(first, second, index + 1);
	}

	private class Node implements Serializable {
		Object value;
		Node left, right, parent;

		/*
		 * Default constructor for the Node class
		 */
		Node() {
			value = null;
			right = null;
			left = null;
			parent = null;
		}

		/*
		 * Constructor for the Node class with a value as the parameter
		 */
		Node(Object value) {
			this.value = value;
			right = null;
			left = null;
		}

		/*
		 * Sets the value of this Node to the specified value
		 */
		public void setValue(Object value) {
			this.value = value;
		}

		/*
		 * Sets the reference of the right node of this node
		 */
		public void setRight(Node node) {
			this.right = node;
		}

		/*
		 * Sets the reference of the left node of this node
		 */
		public void setLeft(Node node) {
			this.left = node;
		}

		/*
		 * Sets the reference for the parent node
		 */
		public void setParent(Node node) {
			this.parent = node;
		}

		/*
		 * Returns the value stored in this node
		 */
		public Object getValue() {
			return value;
		}

		/*
		 * Returns the left node of this node
		 */
		public Node getLeft() {
			return left;
		}

		/*
		 * Returns the right node of this node
		 */
		public Node getRight() {
			return right;
		}

		/*
		 * Returns the parent node of this node
		 */
		public Node getParent() {
			return parent;
		}

	}
}
