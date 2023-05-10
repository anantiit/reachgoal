package com.csfundamentals.trees;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//{ Driver Code Starts
//Contributed by Sudarshan Sharma
import java.util.LinkedList;
import java.util.Queue;

class Node {
	int data;
	Node left;
	Node right;

	Node(int data) {
		this.data = data;
		left = null;
		right = null;
	}

	Node(int data, Node left, Node right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + data;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (data != other.data)
			return false;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		return true;
	}

	@Override
	public String toString() {
		// return "Node [data=" + data + ", left=" + left + ", right=" + right + "]";
		return "" + data;
	}

}

public class GfG {

	static Node buildTree(String str) {

		if (str.length() == 0 || str.charAt(0) == 'N') {
			return null;
		}

		String ip[] = str.split(" ");
		// Create the root of the tree
		Node root = new Node(Integer.parseInt(ip[0]));
		// Push the root to the queue

		Queue<Node> queue = new LinkedList<>();

		queue.add(root);
		// Starting from the second element

		int i = 1;
		while (queue.size() > 0 && i < ip.length) {

			// Get and remove the front of the queue
			Node currNode = queue.peek();
			queue.remove();

			// Get the current node's value from the string
			String currVal = ip[i];

			// If the left child is not null
			if (!currVal.equals("N")) {

				// Create the left child for the current node
				currNode.left = new Node(Integer.parseInt(currVal));
				// Push it to the queue
				queue.add(currNode.left);
			}

			// For the right child
			i++;
			if (i >= ip.length)
				break;

			currVal = ip[i];

			// If the right child is not null
			if (!currVal.equals("N")) {

				// Create the right child for the current node
				currNode.right = new Node(Integer.parseInt(currVal));

				// Push it to the queue
				queue.add(currNode.right);
			}
			i++;
		}

		return root;
	}

	static void printInorder(Node root) {
		if (root == null)
			return;

		printInorder(root.left);
		System.out.print(root.data + " ");

		printInorder(root.right);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int t = Integer.parseInt(br.readLine());

		while (t-- > 0) {
			String s = br.readLine();
			Node root = buildTree(s);
			Tree1 g = new Tree1();
			System.out.println(g.treePathsSum(root));
			// System.out.println();
		}
	}
}

//} Driver Code Ends

/*
 * Complete the function below. Node is as follows: class Tree { int data; Tree
 * left,right; Tree(int d){ data=d; left=null; right=null; } }
 */
class Tree1 {
	public static long treePathsSum(Node root) {
		// add code here.
		long treePathsSum = 0l;
		Queue<Node> q = new LinkedList<Node>();
		q.add(root);
		while (!q.isEmpty()) {
			Node curr = q.poll();
			int currData = curr.data;
			if (curr.left == null && curr.right == null) {
				treePathsSum = treePathsSum + curr.data;
			}
			Node leftNode = curr.left;
			if (leftNode != null) {
				int leftNodeData = leftNode.data;
				int leftDigits = leftNode.data / 10;
				leftNodeData = (int) (currData * Math.pow(10, leftDigits)) + leftNodeData;
				leftNode.data = leftNodeData;
				q.add(leftNode);
			}
			Node rightNode = curr.right;
			if (rightNode != null) {
				int rightNodeData = rightNode.data;
				int rightDigits = rightNode.data / 10;
				rightNodeData = (int) (currData * Math.pow(10, rightDigits)) + rightNodeData;
				rightNode.data = rightNodeData;
				q.add(rightNode);
			}
		}
		return treePathsSum;
	}
}