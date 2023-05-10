package com.csfundamentals.trees;

import java.util.ArrayList;
import java.util.List;

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

	static int getCountBetweenLHInBST(Node root, int l, int h) {
		// Your code here
		if (root == null) {
			return 0;
		}
		int count = 0;
		if (l <= root.data && root.data <= h) {
			count++;
		}
		if (root.data < h) {
			count = count + getCountBetweenLHInBST(root.right, l, h);
		}
		if (root.data > l) {
			count = count + getCountBetweenLHInBST(root.left, l, h);
		}
		return count;
	}

	public static boolean isBST(Node root) {
		if (root == null) {
			return true;
		}
		if ((root.left != null && root.data < root.left.data) || (root.right != null && root.data > root.right.data)) {
			return false;
		}
		boolean isLeftBST = false;
		if (root.left != null) {
			isLeftBST = isBST(root.left);
		}
		if (!isLeftBST) {
			return false;
		}
		if (root.right != null) {
			return isBST(root.right);
		}
		return true;
	}

	private static boolean findPath(Node root, Node target, List<Node> path) {
		if (target == null || root == null) {
			return false;
		}
		if (target.equals(root)) {
			path.add(target);
			return true;
		}
		boolean left = false;
		boolean right = false;
		if (root.left != null)
			left = findPath(root.left, target, path);
		if (!left && root.right != null)
			right = findPath(root.right, target, path);
		if (left || right) {
			path.add(root);
			return true;
		}
		return false;

	}

	public static List<Node> findPath(Node root, Node target1, Node target2) {
		List<Node> path = new ArrayList<>();
		if (root == null) {
			return path;
		}
		List<Node> path1 = new ArrayList<>();
		List<Node> path2 = new ArrayList<>();
		findPath(root, target1, path1);
		findPath(root, target2, path2);
		System.out.println(path1);
		System.out.println(path2);
		int i = path1.size() - 1;
		int j = path2.size() - 1;
		while (i >= 0 && j >= 0) {
			if (path1.get(i).equals(path2.get(j))) {
				i--;
				j--;
			} else {
				j++;
				break;
			}
		}
		int k = 0;
		while (k <= i) {
			path.add(path1.get(k++));
		}
		while (j >= 0) {
			path.add(path2.get(j--));
		}
		System.out.println(path);
		return path;
	}

	// Problem - Given a binary tree how to find the position of right most node in
	// last level of complete binary tree?
	// helper function
	int getLeftHeight(Node node) {
		int leftHeight = 0;
		while (node != null) {
			leftHeight++;
			node = node.left;
		}
		return leftHeight;
	}

	int getRightMostElement(Node node) {
		int h = getLeftHeight(node);
		// base case will reach when RightMostElement which is our ans is found
		if (h == 1)
			return node.data;
		// ans lies in rightsubtree
		else if ((h - 1) == getLeftHeight(node.right))
			return getRightMostElement(node.right);
		// ans lies in left subtree
		else
			return getRightMostElement(node.left);
	}

	public static void main(String args[]) {
		Node root = new Node(1);
		root.left = new Node(2);
		root.right = new Node(7);
		root.left.left = new Node(3);
		root.left.right = new Node(6);
		root.left.left.left = new Node(4);
		root.left.left.right = new Node(5);
		// System.out.println(findPath(root, root.left.left.right, root.left.right));
		// root.left.right.right = new Node();
//		root.left.right.left = new Node(1);
		System.out.println(isBST(root));
//		System.out.println(getCountBetweenLHInBST(root, 2, 8));
		// System.out.println(rootToLeafPathSum(root, ""));
//		preOrderTraversal(root);
//		System.out.println();
//		inOrderTraversal(root);
//		System.out.println();
//		preOrderTraversal(root);
//		mirrorTree(root);
//		System.out.println();
//		preOrderTraversal(root);
//		System.out.println();
//		inOrderTraversal(root);
//		System.out.println();
	}

}

class Bool {
	boolean val = false;

	public void setVal(boolean val) {
		this.val = val;
	}
}
