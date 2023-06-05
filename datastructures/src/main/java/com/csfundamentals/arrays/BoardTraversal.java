package com.csfundamentals.arrays;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
Given a board and an end position for the player, write a function to determine if
it is possible to travel from every open cell on the board to the given end position.

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

		// ##################################

		int[][] a = { { 0, 1, 0, 1, 1 }, { 0, 0, 1, 1, 1 }, { 0, 1, 0, 1, 1 }, { 0, 1, 0, 1, 0 }, { 0, 0, 0, 1, 1 } };
		Cell[][] board = new Cell[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				board[i][j] = new Cell(i, j, a[i][j]);
			}
		}
		System.out.println(srcToDestPath(board, board[1][2], board[4][4], 5, 5));

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
			int x = cur.i;
			int y = cur.j;
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

	public static List<Cell> srcToDestPath(Cell[][] a, Cell s, Cell d, int rows, int cols) {
		LinkedList<Cell> queue = new LinkedList<Cell>();
		queue.add(s);
		s.setParent(new Cell(-1, -1, -1));
		Cell cur = null;
		while (!queue.isEmpty()) {
			cur = queue.poll();
			if (cur.i == d.i && cur.j == d.j) {
				d.parent = cur.parent;
				break;
			}
			List<Cell> adjCells = nextLegalMoves(a, cur, rows, cols, s);
			for (Cell nextCell : adjCells) {
				queue.add(nextCell);
				nextCell.setParent(cur);
			}
		}
		if (!d.equals(cur)) {
			return null;
		}
		LinkedList<Cell> path = new LinkedList<Cell>();
		while (cur != s) {
			path.addFirst(cur);
			cur = cur.parent;
		}
		path.addFirst(s);
		return path;

	}

	public static List<Cell> nextLegalMoves(Cell[][] a, Cell cur, int rows, int columns, Cell src) {
		ArrayList<Cell> cells = new ArrayList<Cell>();
		int x = cur.i;
		int y = cur.j;
		if (isSafe(a, x - 1, y, src)) {
			cells.add(a[x - 1][y]);
		}
		if (isSafe(a, x + 1, y, src)) {
			cells.add(a[x + 1][y]);
		}
		if (isSafe(a, x, y - 1, src)) {
			cells.add(a[x][y - 1]);
		}
		if (isSafe(a, x, y + 1, src)) {
			cells.add(a[x][y + 1]);
		}
		return cells;

	}

	private static boolean isSafe(Cell[][] a, int row, int col, Cell src) {
		int rows = a.length;
		int cols = a[0].length;
		if (row >= 0 && row < rows && col >= 0 && col < cols && a[row][col].parent == null && a[row][col].value == 1
				&& !src.equals(a[row][col])) {
			return true;
		}

		return false;
	}
}

class Cell {
	int i;
	int j;
	int value;
	Cell parent;
	boolean visited;

	public Cell(int i, int j) {
		super();
		this.i = i;
		this.j = j;
	}

	public Cell(int i, int j, int value) {
		super();
		this.i = i;
		this.j = j;
		this.value = value;
	}

	public Cell getParent() {
		return parent;
	}

	public void setParent(Cell parent) {
		this.parent = parent;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	@Override
	public boolean equals(Object o) {
		Cell o1 = (Cell) o;
		return (this.i == o1.i && this.j == o1.j);
	}

	@Override
	public String toString() {
		return "Cell [i=" + i + ", j=" + j + "]";
	}

}
