import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AngelOne {

	public static void main(String[] args) {
		int[][] a = { { 0, 1, 0, 1, 1 }, { 0, 0, 1, 1, 1 }, { 0, 1, 0, 1, 1 }, { 0, 1, 0, 1, 0 }, { 0, 0, 0, 1, 1 } };
		Cell[][] board = new Cell[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				board[i][j] = new Cell(i, j, a[i][j]);
			}
		}
		System.out.println(srcToDestPath(board, board[1][2], board[4][4], 5, 5));
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
