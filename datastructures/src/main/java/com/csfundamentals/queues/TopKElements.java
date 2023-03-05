package com.csfundamentals.queues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.TreeSet;

class Solution {
	static ArrayList<Integer> kTop(int[] a, int n, int k) {
		// code here.
		PriorityQueue<Node> heap = new PriorityQueue<Node>();
		ArrayList<Integer> list = new ArrayList<Integer>();
		Node top = new Node(a[0], 1);
		heap.add(top);
		list.add(a[0]);
		for (int i = 1; i < a.length; i++) {
			topKElementToList(heap, list, a[i], k);
		}
		return list;

		// System.out.println();
	}

	public static void topKElementToList(PriorityQueue<Node> pq, ArrayList<Integer> list, int key, int maxpqSize) {
		Iterator<Node> itr = pq.iterator();
		boolean foundKey = false;
		while (itr.hasNext()) {
			Node cur = itr.next();
			if (key == cur.value) {
				foundKey = true;
				cur.frequency++;
			}
			if (pq.size() > maxpqSize) {
				itr.remove();
			}
		}
		if (!foundKey) {
			pq.add(new Node(key, 1));
		}

		TreeSet<Node> set = new TreeSet<Node>();
		Iterator<Node> itr1 = pq.iterator();
		while (itr1.hasNext()) {
			set.add(itr1.next());
		}
		for (Node node : set) {
			list.add(node.value);
		}

	}
}

class Node implements Comparable<Node> {
	public int value;
	public int frequency;

	Node(int value, int frequency) {
		this.value = value;
		this.frequency = frequency;
	}

	@Override
	public int compareTo(Node o) {
		if (this.frequency < o.frequency) {
			return 1;
		}
		if (this.frequency > o.frequency) {
			return -1;
		} else if (this.value < o.value) {
			return -1;
		} else if (this.value > o.value) {
			return 1;
		} else
			return 0;
	}

}

// { Driver Code Starts.
/*
 * 1 6 5 14 12 15 19 10 1
 */
// Driver class
public class TopKElements {

	// Driver code
	public static void main(String[] args) throws IOException {
		// Taking input using buffered reader
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int testcases = Integer.parseInt(br.readLine());
		// looping through all testcases
		while (testcases-- > 0) {
			String line1 = br.readLine();
			String[] ele = line1.trim().split("\\s+");
			int n = Integer.parseInt(ele[0]);
			int k = Integer.parseInt(ele[1]);
			String line = br.readLine();
			String[] elements = line.trim().split("\\s+");
			int a[] = new int[n];
			for (int i = 0; i < n; i++)
				a[i] = Integer.parseInt(elements[i]);
			Solution ob = new Solution();
			ArrayList<Integer> ans = new ArrayList<Integer>();
			// System.out.println(a[0]+" "+n+" "+k);
			ans = ob.kTop(a, n, k);
			for (int i = 0; i < ans.size(); i++) {
				System.out.print(ans.get(i) + " ");
			}
			System.out.println();
		}
	}
}
// } Driver Code Ends
