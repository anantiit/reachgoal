package com.csfundamentals.trees;

public class TreeProblems {
	public static Node mirrorTree(Node root) {
		if (root == null) {
			return null;
		}
		Node rightNode = root.right;
		if (root.left != null) {
			root.right = mirrorTree(root.left);
		}
		if (rightNode != null) {
			root.left = mirrorTree(rightNode);
		}
		return root;
	}

	public static void inOrderTraversal(Node root) {
		if (root == null) {
			return;
		}
		if (root.left != null) {
			inOrderTraversal(root.left);
		}

		System.out.print(root.data + " ");
		if (root.right != null) {
			inOrderTraversal(root.right);
		}
	}

	public static void preOrderTraversal(Node root) {
		if (root == null) {
			return;
		}
		System.out.print(root.data + " ");
		if (root.left != null) {
			preOrderTraversal(root.left);
		}
		if (root.right != null) {
			preOrderTraversal(root.right);
		}
	}

	public static void postOrderTraversal(Node root) {
		if (root == null) {
			return;
		}
		if (root.left != null) {
			postOrderTraversal(root.left);
		}
		System.out.print(root.data + " ");
		if (root.right != null) {
			postOrderTraversal(root.right);
		}
	}

	/*
	 * Given a binary tree, where every node value is a Digit from 1-9 .Find the sum
	 * of all the numbers which are formed from root to leaf paths.
	 * https://www.geeksforgeeks.org/sum-numbers-formed-root-leaf-paths/ There are 4
	 * leaves, hence 4 root to leaf paths: Path Number 6->3->2 632 6->3->5->7 6357
	 * 6->3->5->4 6354 6->5>4 654 Answer = 632 + 6357 + 6354 + 654 = 13997
	 */
	public static int rootToLeafPathSum(Node root, String num) {
		if (root == null) {
			return 0;
		}
		if (root.left == null && root.right == null) {
			return Integer.parseInt(num + root.data);
		}
		int sum = 0;
		if (root.left != null) {
			sum = rootToLeafPathSum(root.left, num + root.data);
		}
		if (root.right != null) {
			sum = sum + rootToLeafPathSum(root.right, num + root.data);
		}
		return sum;
	}

	static int getCount(Node root, int l, int h) {
		// Your code here
		if (root == null) {
			return 0;
		}
		int count = 0;
		if (l <= root.data && root.data <= h) {
			count++;
		}
		if (root.data < h) {
			count = count + getCount(root.right, l, h);
		}
		if (root.data > l) {
			count = count + getCount(root.left, l, h);
		}
		return count;
	}

	public static boolean isBST(Node root) {
		Node prev = root;
		return isBST(root, prev);
	}

	public static boolean isBST(Node root, Node prev) {
		if (root == null) {
			return true;
		}
		if (root.left != null && !isBST(root.left, prev)) {
			return false;
		}
		if (prev != null && prev.data > root.data) {
			return false;
		}
		prev.data = root.data;
		if (root.right != null && !isBST(root.right, prev)) {
			return false;
		}
		return true;
	}

	public static void main(String args[]) {
		Node root = new Node(5);
		root.left = new Node(3);
		root.right = new Node(6);
		root.right.right = new Node(7);
		root.left.left = new Node(2);
		root.left.right = new Node(4);
		// root.left.right.right = new Node();
		root.left.right.left = new Node(1);
		System.out.println(isBST(root));
		System.out.println(getCount(root, 2, 8));
		System.out.println(rootToLeafPathSum(root, ""));
		preOrderTraversal(root);
		System.out.println();
		inOrderTraversal(root);
		System.out.println();
		mirrorTree(root);
		preOrderTraversal(root);
		System.out.println();
		inOrderTraversal(root);
		System.out.println();
	}
}
