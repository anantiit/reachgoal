package com.csfundamentals.disjointsets;

import java.util.Arrays;

/**
 * 
 * @author anant
 * @apiNote Disjoint sets are equivalence classes. Each equivalence relation is
 *          1.Reflexive aRa i.e a is related a for all a belong to S 2.Symmetric
 *          if aRb then bRa for all a,b belong to S 3.Transitive if aRb and bRc
 *          then aRc for all a,b,c belong to S. Disjoint sets can be applied on
 *          undirected graph but not on directed as it is not an equivalence
 *          relation/class for example it cannot be applied to directed graph as
 *          edge (a->b) in a directed graph is not symmetric i.e if a is
 *          connected to b, b may not be connected to a unless we have specific
 *          edge (b->a)
 */
public class DisjointSet {
	// public static int[] parent;

	/**
	 * find root of set in which element x is present
	 */
	public static int findByPathCompression(int[] parent, int x) {
		if (x < 0 || x > parent.length - 1) {
			return -1;
		}
		if (parent[x] < 0) {
			System.out.println("element " + x + "found root: " + x);
			return x;
		}
		if (parent[parent[x]] < 0) {
			System.out.println("element " + x + "found root: " + parent[x]);
			return parent[x];
		}
		// making all the nodes directly connected to the root.
		// all the nodes parent is root of the set
		return parent[x] = findByPathCompression(parent, parent[x]);
	}

	/**
	 * find root of set in which element x is present
	 */
	public static int find(int[] parent, int x) {
		if (x < 0 || x > parent.length - 1) {
			return -1;
		}
		if (parent[x] < 0) {
			System.out.println("element " + x + "found root: " + x);
			return x;
		}
		if (parent[parent[x]] < 0) {
			System.out.println("element " + x + "found root: " + parent[x]);
			return parent[x];
		}
		System.out.println("Disjoint Set parent array: " + Arrays.toString(parent));
		return find(parent, parent[x]);
	}

	/**
	 * find set x, y and then make one root as parent of other based on size. in
	 * parent array for each node we store the parent of it and at root we store the
	 * size of the disjoint set
	 */
	public static int unionBySize(int[] parent, int x, int y) {
		int root1 = find(parent, x);
		int root2 = find(parent, y);
		if (root1 == root2 && root1 != -1) {
			System.out.println("Not able to find the elements: " + x + " " + y);
			return -1;
		}
		int unionRoot = -1;
		if (parent[root1] < parent[root2]) {
			int root1Size = parent[root1];
			parent[root1] = root2;
			parent[root2] = parent[root2] + root1Size;
			unionRoot = root2;
		} else {
			int root2Size = parent[root2];
			parent[root2] = root1;
			parent[root1] = parent[root1] + root2Size;
			unionRoot = root1;
		}
		System.out.println("Disjoint Set parent array: " + Arrays.toString(parent));
		return unionRoot;
	}

	/**
	 * find set x, y and then make one root as parent of other based on size.in
	 * parent array for each node we store the parent of it and at root we store the
	 * height of the disjoint set
	 */
	public static int unionByHeight(int x, int y) {
		return 0;
	}

	/**
	 * Make Disjoint set
	 */
	public static int[] makeSet(int size) {
		int[] parent = new int[size];
		for (int i = 0; i < size; i++) {
			parent[i] = -1;
		}
		System.out.println("Disjoint Set parent array: " + Arrays.toString(parent));
		return parent;
		HashS
	}
}
