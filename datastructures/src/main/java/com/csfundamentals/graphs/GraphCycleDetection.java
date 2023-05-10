package com.csfundamentals.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.csfundamentals.disjointsets.DisjointSet;

public class GraphCycleDetection {
	// Driver Code
	public static void main(String[] args) {
		/*
		 * Let us create the following graph 0 | \ | \ 1-----2
		 */

		int V = 3, E = 2;
		Graph graph = new Graph(V, E);

		// add edge 0-1
		graph.edge[0].src = 0;
		graph.edge[0].dest = 1;

		// add edge 1-2
		graph.edge[1].src = 1;
		graph.edge[1].dest = 2;

		// add edge 0-2
		// graph.edge[2].src = 0;
		// graph.edge[2].dest = 2;

		if (isCycle(graph) == 1)
			System.out.println("Graph contains cycle");
		else
			System.out.println("Graph doesn't contain cycle");

		ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> adj0 = new ArrayList<Integer>();
		ArrayList<Integer> adj1 = new ArrayList<Integer>(List.of(2));
		ArrayList<Integer> adj2 = new ArrayList<Integer>(List.of(1, 3));
		ArrayList<Integer> adj3 = new ArrayList<Integer>(List.of(2));
		adj.add(adj0);
		adj.add(adj1);
		adj.add(adj2);
		adj.add(adj3);
		if (isCycle(4, adj))
			System.out.println("Graph contains cycle");
		else
			System.out.println("Graph doesn't contain cycle");

	}

	public static boolean isCycle(int V, ArrayList<ArrayList<Integer>> adj) {
		// Code here
		boolean visited[] = new boolean[V];
		int parent[] = new int[V];
		Arrays.fill(parent, -1);
		Queue<Integer> q = new LinkedList<Integer>();
		for (int i = 0; i < adj.size(); i++) {
			if (!visited[i]) {
				q.add(i);
			}
			while (!q.isEmpty()) {
				int v = q.poll();
				if (v >= adj.size()) {
					continue;
				}
				for (int next : adj.get(v)) {
					// visited from some other node which is not its parent
					if (visited[next] && parent[v] != next) {
						System.out.println("visited[next]:" + visited[next] + " parent[next]:" + parent[next] + " next:"
								+ next + " v:" + v);
						return true;
					}
					parent[next] = v;
					visited[v] = true;
					if (!visited[next]) {
						q.add(next);
					}
				}
			}
		}
		return false;
	}

	public static int isCycle(Graph graph) {
		int V = graph.V;
		int E = graph.E;
		int[] parent = DisjointSet.makeSet(V);
		for (int i = 0; i < E; i++) {
			int root1 = DisjointSet.find(parent, graph.edge[i].src);
			int root2 = DisjointSet.find(parent, graph.edge[i].dest);
			if (root1 == root2) {
				// As source and destination are connected to one vertex named root so they form
				// a cycle
				return 1;
			}
			DisjointSet.unionBySize(parent, root1, root2);
		}
		return 0;
	}
}
