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

	public static void main(String args[]) {
		Node left3 = new Node(6, null, null);
		Node right3 = new Node(7, null, null);
		Node left2 = new Node(4, null, null);
		Node right2 = new Node(5, null, null);
		Node left1 = new Node(2, left2, right2);
		Node right1 = new Node(3, left3, right3);
		Node root = new Node(1, left1, right1);
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
