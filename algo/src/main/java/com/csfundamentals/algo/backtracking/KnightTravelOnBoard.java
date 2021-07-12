package com.csfundamentals.algo.backtracking;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class KnightTravelOnBoard {
	public static int minTraversalPath = Integer.MAX_VALUE;
	static int xMove[] = { 2, 1, -1, -2, -2, -1, 1, 2 };
	static int yMove[] = { 1, 2, 2, 1, -1, -2, -2, -1 };

	public static boolean knightTravel(int[][] chessBoard, int srcRow, int srcCol, int destRow, int destCol,
			int hopCount, int n, int xMove[], int yMove[]) {
//		if (srcRow > n - 1 || srcCol > n - 1 || srcRow < 0 || srcCol < 0 || chessBoard[srcRow][srcCol] == 1) {
//			return false;
//		}
//		System.out.println(Arrays.toString(chessBoard));

		// already some minimum path is found so no need to travel further
		if (minTraversalPath < hopCount) {
			return false;
		}
		if (srcRow == destRow && srcCol == destCol) {
			if (minTraversalPath > hopCount) {
				minTraversalPath = hopCount;
				System.out.println("hopCount:" + hopCount);
			}
			return true;
		}
		boolean solution = false;

		for (int i = 0; i < 8; i++) {
			int nextX = xMove[i] + srcRow;
			int nextY = yMove[i] + srcCol;
			if (nextX > n - 1 || nextY > n - 1 || nextX < 0 || nextY < 0
					|| (chessBoard[nextX][nextY] != 0 && chessBoard[nextX][nextY] < hopCount + 1)) {
				continue;
			}
			// making it visited
			chessBoard[nextX][nextY] = hopCount + 1;
			if (knightTravel(chessBoard, nextX, nextY, destRow, destCol, hopCount + 1, n, xMove, yMove)) {
				solution = true;
			} else {
				// back track
				chessBoard[nextX][nextY] = 0;
			}
		}
		return solution;

	}

	public static int knightTourUsingBFS(int[][] chessBoard, int srcRow, int srcCol, int destRow, int destCol,
			int hopCount, int n, int xMove[], int yMove[]) {
		Queue<Node> q = new LinkedList<Node>();
		Node srcNode = new Node(srcRow, srcCol);
		q.add(srcNode);
		while (!q.isEmpty()) {
			Node currNode = q.poll();
			for (int i = 0; i < 8; i++) {
				int nextX = xMove[i] + currNode.x;
				int nextY = yMove[i] + currNode.y;
				if (nextX == destRow && nextY == destCol) {
					chessBoard[nextX][nextY] = chessBoard[currNode.x][currNode.y] + 1;
					return chessBoard[nextX][nextY];
				}
				if (nextX > n - 1 || nextY > n - 1 || nextX < 0 || nextY < 0 || chessBoard[nextX][nextY] != 0) {
					continue;
				}
				Node nextNode = new Node(nextX, nextY);
				q.add(nextNode);
				chessBoard[nextX][nextY] = chessBoard[currNode.x][currNode.y] + 1;
			}
		}
		return -1;

	}

	public static void main(String[] args) {
		int[][] chessBoard = new int[8][8];
		int srcRow = 0;
		int srcCol = 0;
		int destRow = 0;
		int destCol = 6;
		int n = 8;
		int hopCount = 0;
//		if (knightTravel(chessBoard, srcRow, srcCol, destRow, destCol, hopCount, n, xMove, yMove)) {
//			System.out.println("minTraversalPath : " + minTraversalPath);
//		}
		System.out.println(knightTourUsingBFS(chessBoard, srcRow, srcCol, destRow, destCol, hopCount, n, xMove, yMove));
		for (int i = 0; i < 8; i++) {
			System.out.println(Arrays.toString(chessBoard[i]));
		}
	}

}

class Node {
	int x;
	int y;

	Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
