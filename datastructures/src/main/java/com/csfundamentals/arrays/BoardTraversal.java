package com.csfundamentals.arrays;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
Given a board and an end position for the player, write a function to determine if it is possible to travel from every open cell on the board to the given end position.

board1 = [
    [ 0,  0,  0, 0, -1 ],
    [ 0, -1, -1, 0,  0 ],
    [ 0,  0,  0, 0,  0 ],
    [ 0, -1,  0, 0,  0 ],
    [ 0,  0,  0, 0,  0 ],
    [ 0,  0,  0, 0,  0 ],
]

board2 = [
    [  0,  0,  0, 0, -1 ],
    [  0, -1, -1, 0,  0 ],
    [  0,  0,  0, 0,  0 ],
    [ -1, -1,  0, 0,  0 ],
    [  0, -1,  0, 0,  0 ],
    [  0, -1,  0, 0,  0 ],
]

board3 = [
    [ 0,  0,  0,  0,  0,  0, 0 ],
    [ 0, -1, -1, -1, -1, -1, 0 ],
    [ 0, -1,  0,  0,  0, -1, 0 ],
    [ 0, -1,  0,  0,  0, -1, 0 ],
    [ 0, -1,  0,  0,  0, -1, 0 ],
    [ 0, -1, -1, -1, -1, -1, 0 ],
    [ 0,  0,  0,  0,  0,  0, 0 ],
]

board4 = [
    [0,  0,  0,  0, 0],
    [0, -1, -1, -1, 0],
    [0, -1, -1, -1, 0],
    [0, -1, -1, -1, 0],
    [0,  0,  0,  0, 0],
]

board5 = [
    [0],
]

end1 = (0, 0)
end2 = (5, 0)

Expected output:

isReachable(board1, end1) -> True
isReachable(board1, end2) -> True
isReachable(board2, end1) -> False
isReachable(board2, end2) -> False
isReachable(board3, end1) -> False
isReachable(board4, end1) -> True
isReachable(board5, end1) -> True


n: width of the input board
m: height of the input board
*/

public class BoardTraversal {
	public static void main(String[] argv) {
		int[][] board1 = new int[][] { { 0, 0, 0, 0, -1 }, { 0, -1, -1, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, -1, 0, 0, 0 },
				{ 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, };

		int[][] board2 = new int[][] { { 0, 0, 0, 0, -1 }, { 0, -1, -1, 0, 0 }, { 0, 0, 0, 0, 0 }, { -1, -1, 0, 0, 0 },
				{ 0, -1, 0, 0, 0 }, { 0, -1, 0, 0, 0 }, };

		int[][] board3 = new int[][] { { 0, 0, 0, 0, 0, 0, 0 }, { 0, -1, -1, -1, -1, -1, 0 }, { 0, -1, 0, 0, 0, -1, 0 },
				{ 0, -1, 0, 0, 0, -1, 0 }, { 0, -1, 0, 0, 0, -1, 0 }, { 0, -1, -1, -1, -1, -1, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, };

		int[][] board4 = new int[][] { { 0, 0, 0, 0, 0 }, { 0, -1, -1, -1, 0 }, { 0, -1, -1, -1, 0 },
				{ 0, -1, -1, -1, 0 }, { 0, 0, 0, 0, 0 }, };

		int[][] board5 = new int[][] { { 0 }, };

		int[] end1 = new int[] { 0, 0 };
		int[] end2 = new int[] { 5, 0 };

	}

	private static boolean isSafe(int[][] grid, int rows, int columns, int x, int y) {
		return x < rows && x >= 0 && y < columns && y >= 0 && grid[x][y] != -1;
	}

	public static List<Cell> findLegalMoves(int[][] grid, int[] start) {
		ArrayList<Cell> cells = new ArrayList<Cell>();
		int rows = grid.length;
		int columns = grid[0].length;
		int x = start[0];
		int y = start[1];
		if (isSafe(grid, rows, columns, x - 1, y)) {
			cells.add(new Cell(x - 1, y));
		}
		if (isSafe(grid, rows, columns, x + 1, y)) {
			cells.add(new Cell(x + 1, y));
		}
		if (isSafe(grid, rows, columns, x, y - 1)) {
			cells.add(new Cell(x, y - 1));
		}
		if (isSafe(grid, rows, columns, x, y + 1)) {
			cells.add(new Cell(x, y + 1));
		}
		return cells;

	}

	private static boolean isSafe(int[][] grid, int rows, int columns, int x, int y, int[][] visited) {
		return x < rows && x >= 0 && y < columns && y >= 0 && grid[x][y] != -1 && visited[x][y] == 0;
	}

	public static boolean isReachable(int[][] grid, int[] dest) {
		int[][] visited = new int[grid.length][grid[0].length];
		int rows = grid.length;
		int columns = grid[0].length;
		if (grid[dest[0]][dest[1]] == -1) {
			return false;
		}
		Queue<Cell> q = new LinkedList<Cell>();
		while (!q.isEmpty()) {
			Cell cur = q.poll();
			int x = cur.x;
			int y = cur.y;
			visited[x][y] = 1;
			if (grid[x][y] == -1) {
				continue;
			}
			if (isSafe(grid, rows, columns, x - 1, y)) {
				q.add(new Cell(x - 1, y));
			}
			if (isSafe(grid, rows, columns, x + 1, y)) {
				q.add(new Cell(x + 1, y));
			}
			if (isSafe(grid, rows, columns, x, y - 1)) {
				q.add(new Cell(x, y - 1));
			}
			if (isSafe(grid, rows, columns, x, y + 1)) {
				q.add(new Cell(x, y + 1));
			}
		}
		if (checkAllVisited(visited)) {
			return true;
		}
		return false;

	}

	public static boolean checkAllVisited(int[][] visited) {
		for (int i = 0; i < visited.length; i++) {
			for (int j = 0; j < visited[0].length; j++) {
				if (visited[i][j] == 0)
					return false;
			}
		}
		return true;
	}
}

class Cell {
	Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	int x;
	int y;

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
