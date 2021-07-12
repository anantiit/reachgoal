package com.csfundamentals.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class BSTFromList {
	public static int findRoot(List<List<Integer>> nodes) {
		HashMap<Integer, Node> mapTree = new HashMap<Integer, Node>();
		Set<Integer> nodeSet = new HashSet<Integer>();
		for (int i = 0; i < nodes.size(); i++) {
			List<Integer> nodeContent = nodes.get(i);
			Node left = null;
			Node right = null;
			Node node = new Node(nodeContent.get(0), left, right);
			nodeSet.add(nodeContent.get(0));
			if (nodeContent.get(1) != -1) {
				left = new Node(nodeContent.get(1));
				node.left = left;
			}
			if (nodeContent.get(2) != -1) {
				right = new Node(nodeContent.get(2));
				node.right = right;
			}
			mapTree.put(node.data, node);
		}
		for (Entry<Integer, Node> entry : mapTree.entrySet()) {
			Node node = entry.getValue();
			if (node.left != null) {
				System.out.println(nodeSet.remove(node.left.data));
				node.left = mapTree.get(node.left.data);
			}
			if (node.right != null) {
				System.out.println(nodeSet.remove(node.right.data));
				node.right = mapTree.get(node.right.data);
			}
		}
		System.out.println(nodeSet);
		if (nodeSet.size() > 1)
			return -1;
		Iterator<Integer> itr = nodeSet.iterator();
		return itr.next();
	}

	public static void main(String args[]) {
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();
		List<Integer> list3 = new ArrayList<Integer>();
		List<Integer> list4 = new ArrayList<Integer>();
		List<Integer> list5 = new ArrayList<Integer>();
		List<Integer> list6 = new ArrayList<Integer>();
		List<Integer> list7 = new ArrayList<Integer>();
		list1.add(15);
		list1.add(13);
		list1.add(17);
		list2.add(17);
		list2.add(-1);
		list2.add(-1);
		list3.add(13);
		list3.add(-1);
		list3.add(-1);
		list4.add(3);
		list4.add(-1);
		list4.add(-1);
		list5.add(7);
		list5.add(-1);
		list5.add(-1);
		list6.add(5);
		list6.add(3);
		list6.add(7);
		list7.add(10);
		list7.add(5);
		list7.add(15);
		List<List<Integer>> nodeList = new ArrayList<List<Integer>>();
		nodeList.add(list1);
		nodeList.add(list2);
		nodeList.add(list3);
		nodeList.add(list4);
		nodeList.add(list5);
		nodeList.add(list6);
		nodeList.add(list7);
		System.out.println(findRoot(nodeList));
	}
}
