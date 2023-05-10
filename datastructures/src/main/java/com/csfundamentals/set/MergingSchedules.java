package com.csfundamentals.set;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

//Prblem statement: {{1,3},{1,4},{2,4},{5,7},{9,10},{6,8}}-->{{1,4}{5,8}}
public class MergingSchedules {
	public static List<Node> mergingSchedules(List<Node> nodes) {
		Set<Node> set = new TreeSet<Node>((a, b) -> (a.start == b.start) ? a.end - b.end : a.start - b.start);
		set.addAll(nodes);
		Iterator<Node> itr = set.iterator();
		Node prev = itr.next();
		List<Node> result = new ArrayList<Node>();
		result.add(prev);
		while (itr.hasNext()) {
			Node cur = itr.next();
			if (prev.end >= cur.start) {
				if (cur.end > prev.end)
					prev.end = cur.end;
			} else {
				result.add(cur);
			}
			prev = result.get(result.size() - 1);
		}
		return result;
	}

	public static void main(String args[]) {
//{{1,3},{1,4},{2,4},{5,7},{9,10},{6,8}}
		Node node1 = new Node(1, 3);
		Node node2 = new Node(1, 4);
		Node node3 = new Node(2, 4);
		Node node4 = new Node(5, 8);
		Node node5 = new Node(5, 11);
		Node node6 = new Node(6, 8);
		Node node7 = new Node(9, 10);
		List<Node> nodes = new ArrayList<Node>();
		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);
		nodes.add(node4);
		nodes.add(node5);
		nodes.add(node6);
		nodes.add(node7);
		System.out.println(mergingSchedules(nodes));
	}
}

class Node {
	int start;
	int end;

	public Node() {

	}

	public Node(int start, int end) {
		super();
		this.start = start;
		this.end = end;
	}

	@Override
	public String toString() {
		return "{" + start + "," + end + "}";
	}

//	@Override
//	public int compareTo(Node n) {
//		if (this.start <= n.start) {
//			return -1;
//		}
//		return 1;
//	}
}
