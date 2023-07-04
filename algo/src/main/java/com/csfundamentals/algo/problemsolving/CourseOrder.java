package com.csfundamentals.algo.problemsolving;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

//https://leetcode.com/problems/course-schedule/?envType=featured-list&envId=top-interview-questions
//https://leetcode.com/problems/course-schedule-ii/?envType=featured-list&envId=top-interview-questions
public class CourseOrder {
	public static void main(String[] args) {
		// Each pair array represents [person height, number of persons who are taller
		// than him in front]
		int[][] courses = { { 1, 0 }, { 2, 0 }, { 3, 1 }, { 3, 2 } };
		int[][] courses1 = { { 4, 9 }, { 10, 4 }, { 10, 9 }, { 10, 6 }, { 2, 1 }, { 2, 7 }, { 3, 4 }, { 3, 5 } };
		int[][] courses2 = { { 0, 2 }, { 1, 0 }, { 0, 1 } };
		CourseOrder co = new CourseOrder();
		int[][] courses3 = { { 0, 2 }, { 1, 2 }, { 3, 1 }, { 3, 0 } };
		System.out.println(co.findOrder(4, courses3));
	}

	public int[] findOrder1(int n, int[][] p) {
		List<List<Integer>> adjList = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			adjList.add(new ArrayList<>());
		}
		for (int i = 0; i < p.length; i++) {
			adjList.get(p[i][1]).add(p[i][0]);
		}
		System.out.println(adjList.toString());
		Queue<Integer> bfsq = new LinkedList<>();
		int topologicalOrdering[] = new int[n];
		for (int i = 0; i < n; i++) {
			for (int k : adjList.get(i)) {
				topologicalOrdering[k]++;
			}
		}
		System.out.println(Arrays.toString(topologicalOrdering));
		for (int i = 0; i < topologicalOrdering.length; i++) {
			if (topologicalOrdering[i] == 0) {
				bfsq.offer(i);
			}
		}
		System.out.println(bfsq);
		int result[] = new int[n];
		int i = 0;
		while (!bfsq.isEmpty()) {
			int x = bfsq.poll();
			result[i++] = x;
			for (int j : adjList.get(x)) {
				topologicalOrdering[j]--;
				if (topologicalOrdering[j] == 0) {
					bfsq.offer(j);
				}
			}
		}
		return i == n ? result : new int[0];
	}

	static int WHITE = 1;
	static int GRAY = 2;
	static int BLACK = 3;

	boolean isPossible;
	Map<Integer, Integer> color;
	Map<Integer, List<Integer>> adjList;
	List<Integer> topologicalOrder;

	private void init(int numCourses) {
		this.isPossible = true;
		this.color = new HashMap<Integer, Integer>();
		this.adjList = new HashMap<Integer, List<Integer>>();
		this.topologicalOrder = new ArrayList<Integer>();

		// By default all vertces are WHITE
		for (int i = 0; i < numCourses; i++) {
			this.color.put(i, WHITE);
		}
	}

	private boolean dfs(int node) {

		// Don't recurse further if we found a cycle already
		if (!this.isPossible) {
			return false;
		}

		// Start the recursion
		this.color.put(node, GRAY);

		// Traverse on neighboring vertices
		for (Integer neighbor : this.adjList.getOrDefault(node, new ArrayList<Integer>())) {
			if (this.color.get(neighbor) == WHITE) {
				this.dfs(neighbor);
			} else if (this.color.get(neighbor) == GRAY) {
				// An edge to a GRAY vertex represents a cycle
				this.isPossible = false;
				return false;
			}
		}

		// Recursion ends. We mark it as black
		this.color.put(node, BLACK);
		this.topologicalOrder.add(node);
		return true;
	}

	public boolean findOrder(int numCourses, int[][] prerequisites) {

		this.init(numCourses);

		// Create the adjacency list representation of the graph
		for (int i = 0; i < prerequisites.length; i++) {
			int dest = prerequisites[i][0];
			int src = prerequisites[i][1];
			List<Integer> lst = adjList.getOrDefault(src, new ArrayList<Integer>());
			lst.add(dest);
			adjList.put(src, lst);
		}

		// If the node is unprocessed, then call dfs on it.
		for (int i = 0; i < numCourses; i++) {
			if (this.color.get(i) == WHITE) {
				if (!this.dfs(i)) {
					return false;
				}
			}
		}

		int[] order;
		if (this.isPossible) {
			order = new int[numCourses];
			for (int i = 0; i < numCourses; i++) {
				order[i] = this.topologicalOrder.get(numCourses - i - 1);
			}
		} else {
			order = new int[0];
		}
		System.out.println(Arrays.toString(order));
		return true;
	}
}
