package com.csfundamentals.graphs;

public class Graph {
	int V, E;
	Edge[] edge;

	Graph(int nV, int nE) {
		V = nV;
		E = nE;
		edge = new Edge[E];
		for (int i = 0; i < E; i++) {
			edge[i] = new Edge();
		}
	}

	// class to represent edge
	class Edge implements Comparable<Edge> {
		int src, dest, weight;

		@Override
		public int compareTo(Edge o) {
			if (o != null) {
				if (this.weight < o.weight) {
					return -1;
				} else
					return 1;
			}
			return 0;
		}

	}

}
