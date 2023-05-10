package com.csfundamentals.algo.problemsolving;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

/*
 * Given a list of [origin, destination] pairs (you could think of them as plane tickets), sort them into a single continuous route.



Sample Input


[
    ["SFO", "EWR");
    [" SJC", "LAX");
    ["DFW", "SJC");
    ["EWR", "OAK");
    ["LAX", "SFO"]
]
Desired output


["DFW", "SJC", "LAX", "SFO", "EWR", "OAK"]


complexity: o(n) 

 */
public class FindItenaryGivenTickets {
	public static List<String> findItinerary(String[][] tickets) {
		Map<String, PriorityQueue<String>> targets = new HashMap<>();
		for (String[] ticket : tickets)
			targets.computeIfAbsent(ticket[0], k -> new PriorityQueue()).add(ticket[1]);
		List<String> route = new LinkedList();
		Stack<String> stack = new Stack<>();
		stack.push("EW");
		while (!stack.empty()) {
			while (targets.containsKey(stack.peek()) && !targets.get(stack.peek()).isEmpty())
				stack.push(targets.get(stack.peek()).poll());
			route.add(0, stack.pop());
		}
		return route;
	}

	/*
	 * https:leetcode.com/problems/minimum-number-of-operations-to-move-all-balls-
	 * to-each-box/description/ You have n boxes. You are given a binary string
	 * boxes of length n, where boxes[i] is '0' if the ith box is empty, and '1' if
	 * it contains one ball.
	 * 
	 * In one operation, you can move one ball from a box to an adjacent box. Box i
	 * is adjacent to box j if abs(i - j) == 1. Note that after doing so, there may
	 * be more than one ball in some boxes. Input: boxes = "001011" Output:
	 * [11,8,5,4,3,4]
	 */public static int[] minOperations(String boxes) {
		int n = boxes.length();
		int[] answer = new int[n];
		List<Integer> ballInd = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			if (boxes.charAt(i) == '1') {
				ballInd.add(i);
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j : ballInd) {
				answer[i] += Math.abs(i - j);

			}
		}
		return answer;
	}
	/*
	 * Question 2: Input is a string of numbers. Let 1=A, 2=B, ……26=Z Find the
	 * number of possible combinations of string formed by decoding the input string
	 * 
	 * 
	 * Eg: INPUT = "45" Strings Possible : DE Output : 1 Eg : INPUT = "123" Strings
	 * possible : ABC, LC, AW Output : 3
	 * 
	 * 
	 * 1001 -1
	 * 
	 * 
	 * 1010 1
	 * 
	 * 
	 * 11111 AAAAA 1 KKA AKK KAK KAAA AKAA AAKA AAAK 8
	 */

	public static int numDecodings(String s) {
		int n = s.length();
		int ans[] = new int[n + 1];
		if (s.charAt(0) - '0' == 0) {
			return 0;
		}
		ans[0] = 1;
		ans[1] = 1; // For character at index 0
		for (int i = 2; i <= n; i++) {
			int current = s.charAt(i - 1) - '0';
			int prev = s.charAt(i - 2) - '0';
			int twoDigitNo = 10 * prev + current;
			if (current >= 1 && current <= 9) {
				ans[i] += ans[i - 1];
			}
			if (i > 1 && twoDigitNo >= 10 && twoDigitNo <= 26) {
				ans[i] += ans[i - 2];
			}
		}
		return ans[n]; // number of decodings till character at index n-1
	}

	public static LinkedList<String> reconstructItinerary(String[][] tickets) {
		Map<String, PriorityQueue<String>> targets = new HashMap<>();
		LinkedList<String> route = new LinkedList<String>();
		for (String[] ticket : tickets)
			targets.computeIfAbsent(ticket[0], k -> new PriorityQueue()).add(ticket[1]);
		for (String s : targets.keySet()) {
			if (!targets.get(s).isEmpty())
				visit(s, targets, route);
			System.out.println("route:" + route);
		}
		System.out.println("targets:" + targets);
		ListIterator<String> itr = route.listIterator();
		String prev = null;
		String cur = null;
		while (itr.hasNext()) {
			cur = itr.next();
			if (cur.equals(prev)) {
				itr.remove();
			}
			prev = cur;
		}
		return route;
	}

	static void visit(String airport, Map<String, PriorityQueue<String>> targets, LinkedList<String> route) {
		while (targets.containsKey(airport) && !targets.get(airport).isEmpty())
			visit(targets.get(airport).poll(), targets, route);
		route.add(0, airport);
	}

	/*
	 * PhonePe: Problem Solving Round:
	 * 
	 * Given S string of length find all possible subsequence occurrences of another
	 * string “bob” Eg: S=bbobob
	 * 
	 * Given a tree where each node has happiness index check if the each node is
	 * truly happy . Node will be truly happy if there is no such node which is
	 * having happiness index same as it from the root to till the node path.
	 * 
	 */
	public static void main(String args[]) {
		String[][] ticketMap = { { "SFO", "EWR" }, { "SJC", "LAX" }, { "DFW", "SJC" }, { "EWR", "OAK" },
				{ "LAX", "SFO" }, { "OAK", "DFW" }, { "SFO", "DFW" }, { "SFO", "SJC" }, { "EWR", "DFW" } };
//
//		String[][] ticketMap = { { "MUC", "LHR" }, { "JFK", "MUC" }, { "SFO", "SJC" }, { "LHR", "SFO" } };
		System.out.println(reconstructItinerary(ticketMap));
//		String boxes = "001011";
		String encoding = "1001";
		String encoding1 = "45020";
		String encoding2 = "123";
//		// Output: [11,8,5,4,3,4]
//		System.out.println(Arrays.toString(minOperations(boxes)));
		System.out.println(numDecodings(encoding2));
	}
}
