package com.csfundamentals.graphs;

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
