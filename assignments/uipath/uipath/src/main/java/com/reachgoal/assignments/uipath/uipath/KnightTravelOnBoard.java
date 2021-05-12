package com.reachgoal.assignments.uipath.uipath;

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
			if (nextX > n - 1 || nextY > n - 1 || nextX < 0 || nextY < 0 || chessBoard[nextX][nextY] == 1) {
				continue;
			}
			chessBoard[nextX][nextY] = 1;
			if (knightTravel(chessBoard, nextX, nextY, destRow, destCol, hopCount + 1, n, xMove, yMove)) {
				solution = true;
			} else {
				chessBoard[nextX][nextY] = 0;
			}
		}
		return solution;

	}

	public static boolean knightTourUsingBFS(int[][] chessBoard, int srcRow, int srcCol, int destRow, int destCol,
			int hopCount, int n, int xMove[], int yMove[]) {

		return false;

	}

	public static void main(String[] args) {
		int[][] chessBoard = new int[8][8];
		int srcRow = 0;
		int srcCol = 0;
		int destRow = 0;
		int destCol = 7;
		int n = 8;
		int hopCount = 0;
		if (knightTravel(chessBoard, srcRow, srcCol, destRow, destCol, hopCount, n, xMove, yMove)) {
			System.out.println("minTraversalPath : " + minTraversalPath);
		}
	}

}
