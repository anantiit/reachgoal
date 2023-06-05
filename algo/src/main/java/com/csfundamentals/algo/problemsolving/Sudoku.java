package com.csfundamentals.algo.problemsolving;

import java.util.HashSet;
import java.util.Set;

public class Sudoku {
	/*
	 * 4 in row 7 is encoded as 7(4) 4 in column is encoded as (4)7 4 in top right
	 * block is encoded as 0(4)2
	 */
	public boolean isValidSudoku(char[][] board) {
		Set<String> seen = new HashSet<String>();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j] != '.') {
					if (!seen.add(i + "(" + board[i][j] + ")") || !seen.add("(" + board[i][j] + ")" + j)
							|| !seen.add(i / 3 + "(" + board[i][j] + ")" + j / 3)) {
						return false;
					}
				}

			}
		}
		return true;

	}
}
