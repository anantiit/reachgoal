package com.csfundamentals.strings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubString {

	public static boolean findIfSubString(String s1, String s2) {
		int i = 0;
		int j = 0;
		while (i < s1.length() && j < s2.length()) {
			if (s2.charAt(j) == s1.charAt(i)) {
				j++;
				i++;
			} else if (j == 0 && i < s1.length()) {
				i++;
			} else {
				j = 0;
			}
		}
		if (j == s2.length()) {
			System.out.println("S2 is substring of S1");
			return true;
		}
		System.out.println("S2 is not substring of S1");
		return false;
	}

	public static int numOfOccurances(String s1, String s2) {
		int i = 0;
		int j = 0;
		int n = s1.length();
		int m = s2.length();
		if (m > n) {
			return -1;
		}
		int count = 0;
		while (i < n) {
			while (i < n && j < m) {
				if (s2.charAt(j) == s1.charAt(i)) {
					j++;
					i++;
				} else if (j == 0 && i < n) {
					i++;
				} else {
					j = 0;
				}
				if (j == m) {
					System.out.println("S2 occured at:" + (i - m));
					count++;
					if (i - m >= 0) {
						j = 0;
					}
				}
			}
		}
		// System.out.println("S2 is not substring of S1");
		return count;
	}

	public static boolean isTrulyHappy(Node root) {
		Set<Integer> seenValues = new HashSet<>();
		return isTrulyHappyHelper(root, seenValues);
	}

	private static boolean isTrulyHappyHelper(Node node, Set<Integer> seenValues) {
		if (seenValues.contains(node.val)) {
			return false;
		}

		seenValues.add(node.val);

		for (Node child : node.children) {
			if (!isTrulyHappyHelper(child, new HashSet<>(seenValues))) {
				return false;
			}
		}

		return true;
	}

	private static boolean isTrulyHappyMax(Node node) {
		for (Node child : node.children) {
			if (node.val <= child.val) {
				return false;
			}
			if (!isTrulyHappyMax(child)) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		Node root = new Node(0);
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		Node node5 = new Node(5);
		Node node6 = new Node(6);

//		root.children.add(node1);
//		root.children.add(node2);
//		node1.children.add(node3);
//		node2.children.add(node4);
//		node2.children.add(node5);
//		node4.children.add(node6);
//
//		System.out.println(isTrulyHappy(root)); // false
//
//		Node root2 = new Node(1);
//		Node node7 = new Node(2);
//		Node node8 = new Node(3);
//		Node node9 = new Node(4);
//		Node node10 = new Node(5);
//		Node node11 = new Node(6);
//
//		root2.children.add(node7);
//		root2.children.add(node8);
//		node7.children.add(node9);
//		node7.children.add(node10);
//		node8.children.add(node11);
//		System.out.println(isTrulyHappy(root2));
		Node root3 = new Node(10);
		root3.children.add(node5);
		root3.children.add(node6);
		node5.children.add(node4);
		node5.children.add(node1);
		node6.children.add(node2);
		node6.children.add(node3);

		System.out.println(isTrulyHappyMax(root3));

		String s1 = "baafsafsda";
		String s2 = "afs";
		numOfOccurances(s1, s2);
	}
}

class Node {
	int val;
	List<Node> children;

	public Node(int val) {
		this.val = val;
		children = new ArrayList<>();
	}
}

class BinaryNode {
	int val;
	BinaryNode left;
	BinaryNode right;

	public BinaryNode(int val) {
		this.val = val;
	}
}
