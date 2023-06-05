package com.csfundamentals.queues;

import java.util.LinkedList;

/*
Q1) Imagine there is a mxn grid of integers, where some of the cells are occupied by monsters. An empty cell is 0, a cell with monster is 1.
We need to find the position in the grid where we need to place a player character such that it is at maximum distance from the nearest monster.

1 0 0 0 0
0 0 0 0 0
1 0 0 0 0
0 0 0 0 1


1 1 0 0 0
1 0 0 0 0
1 1 0 0 0
0 0 0 0 1

Q 1,1,1 

Time : O(mxn)
Space complexity = O(mxn)

Public static 






10
01


Q 00, 1,1
Q 11,01,10






Q2) Now return an empty position in the grid in which if a monster is added, the number of hordes of monster are minimized.

Horde is a group of monsters standing together, vertically or horizontally to any of the monsters present in the same Horde.
1 0 0 0 0
0 0 0 0 0
1 0 0 0 0
0 0 0 0 1  -> 3   -> [1,0] -> 2

1 0 0 0 0
0 1 0 0 0
1 0 0 0 0
0 0 0 0 1 -> 4 -> [1,0] -> 2


1 0 0 0 0
1 1 0 0 0
1 0 0 0 0
0 0 0 0 1 -> 2 -> [1,0] -> 2






*/
public class MonsterPlayer {
	public static void main(String[] args) {
		int[][] grid = { { 1, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0 }, { 0, 0, 0, 0, 1 } };
		System.out.println(getOptimalPosition(grid, 4, 5));

	}

	public static Position getOptimalPosition(int[][] grid, int row, int col) {
		boolean[][] visited = new boolean[row][col];
		LinkedList<Position> q = new LinkedList<Position>();
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (grid[i][j] == 1) {
					q.add(new Position(i, j));
					visited[i][j] = true;
				}
			}
		}
		if (q.size() == 0) {
			return new Position(0, 0);
		}
		if (q.size() == row * col) {
			return null;
		}
		Position cur = null;
		while (!q.isEmpty()) {
			cur = q.poll();
			if (cur.x + 1 < row && !visited[cur.x + 1][cur.y]) {
				q.add(new Position(cur.x + 1, cur.y));
				visited[cur.x + 1][cur.y] = true;
			}
			if (cur.y + 1 < col && !visited[cur.x][cur.y + 1]) {
				q.add(new Position(cur.x, cur.y + 1));
				visited[cur.x][cur.y + 1] = true;
			}
			if (cur.x - 1 >= 0 && !visited[cur.x - 1][cur.y]) {
				q.add(new Position(cur.x - 1, cur.y));
				visited[cur.x - 1][cur.y] = true;
			}
			if (cur.y - 1 >= 0 && !visited[cur.x][cur.y - 1]) {
				q.add(new Position(cur.x, cur.y - 1));
				visited[cur.x - 1][cur.y] = true;
			}
		}
		return cur;
	}

}

class Position {
	int x;
	int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}

}
