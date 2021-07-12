package com.csfundamentals.graphs;

import java.util.Arrays;

import com.csfundamentals.disjointsets.DisjointSet;
import com.csfundamentals.graphs.Graph.Edge;

public class KruskalMinimumSpanningTree {
	static void KruskalMST(Graph graph) {
		// Tnis will store the resultant MST
		int V = graph.V;
		int E = graph.E;
		Edge result[] = new Edge[V];

		// Step 1: Sort all the edges in non-decreasing
		// order of their weight. If we are not allowed to
		// change the given graph, we can create a copy of
		// array of edges
		Arrays.sort(graph.edge);

		int[] parent = DisjointSet.makeSet(V);
		// Number of edges to be taken is equal to V-1
		int e = 0;
		int i = 0;
		while (e < V - 1 && i < graph.edge.length - 1) {
			// Step 2: Pick the smallest edge. And increment
			// the index for next iteration
			Edge next_edge = graph.edge[i++];

			int x = DisjointSet.find(parent, next_edge.src);
			int y = DisjointSet.find(parent, next_edge.dest);

			// If including this edge does't cause cycle,
			// include it in result and increment the index
			// of result for next edge
			if (x != y) {
				result[e++] = next_edge;
				DisjointSet.unionBySize(parent, x, y);
			}
			// Else discard the next_edge
		}

		// print the contents of result[] to display
		// the built MST
		System.out.println("Following are the edges in " + "the constructed MST");
		int minimumCost = 0;
		for (int j = 0; j < V - 1; ++j) {
			System.out.println(result[j].src + " -- " + result[j].dest + " == " + result[j].weight);
			minimumCost += result[j].weight;
		}
		System.out.println("Minimum Cost Spanning Tree " + minimumCost);
	}

	// Driver Code
	public static void main(String[] args) {

		/*
		 * Let us create following weighted graph 10 0--------1 | \ | 6| 5\ |15 | \ |
		 * 2--------3 4
		 */
		int V = 4; // Number of vertices in graph
		int E = 5; // Number of edges in graph
		Graph graph = new Graph(V, E);

		// add edge 0-1
		graph.edge[0].src = 0;
		graph.edge[0].dest = 1;
		graph.edge[0].weight = 10;

		// add edge 0-2
		graph.edge[1].src = 0;
		graph.edge[1].dest = 2;
		graph.edge[1].weight = 6;

		// add edge 0-3
		graph.edge[2].src = 0;
		graph.edge[2].dest = 3;
		graph.edge[2].weight = 5;

		// add edge 1-3
		graph.edge[3].src = 1;
		graph.edge[3].dest = 3;
		graph.edge[3].weight = 15;

		// add edge 2-3
		graph.edge[4].src = 2;
		graph.edge[4].dest = 3;
		graph.edge[4].weight = 4;

		// Function call
		KruskalMST(graph);
	}
}
