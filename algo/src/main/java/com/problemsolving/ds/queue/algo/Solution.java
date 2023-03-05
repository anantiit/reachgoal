package com.problemsolving.ds.queue.algo;
//Java program to find minimum time required to make all 

import java.util.Arrays;

//oranges rotten 

/*
 * Question:
 * Given an office with cubicles represented in matrix form, where each cubicle represent a cell in matrix the cell can contain one of the fallowing values
 * 0 : empty cell/cubicle
 * 2 : cubicle with employee who does not have common cold
 * 1 : cell/cubicle with employee who got common cold
 * Find minimum number of days in which all the employees in the office gets common cold given that infected person in cubicle can spread to [i-1,j], [i+1,j], [i,j-1], [i,j+1]  cubicles in single day. Assume the employees do not move to other cubicles. output contains to integers, where first value represents whether all the employees gets infected with common cold or not (1 for Yes,  -1 for No ), The second represents how many minimum number of days required
 */
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Solution {

	// structure for storing coordinates of the cell
	static class Ele {
		int x = 0;
		int y = 0;

		Ele(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	// function to check whether a cell is valid / invalid
	static boolean isValid(int i, int j, int R, int C) {
		return (i >= 0 && j >= 0 && i < R && j < C);
	}

	// Function to check whether the cell is delimiter
	// which is (-1, -1)
	static boolean isDelim(Ele temp) {
		return (temp.x == -1 && temp.y == -1);
	}

	// Function to check whether there is still a fresh
	// orange remaining
	static boolean checkAll(int arr[][], int R, int C) {
		for (int i = 0; i < R; i++)
			for (int j = 0; j < C; j++)
				if (arr[i][j] == 1)
					return true;
		return false;
	}

	// This function finds if it is possible to rot all oranges or not.
	// If possible, then it returns minimum time required to rot all,
	// otherwise returns -1
	static int rotOranges(int arr[][], int R, int C) {

		// Create a queue of cells
		Queue<Ele> Q = new LinkedList<Ele>();
		Ele temp;
		int ans = 0;
		// Store all the cells having rotten orange in first time frame
		for (int i = 0; i < R; i++)
			for (int j = 0; j < C; j++)
				if (arr[i][j] == 2)
					Q.add(new Ele(i, j));

		// Separate these rotten oranges from the oranges which will rotten
		// due the oranges in first time frame using delimiter which is (-1, -1)
		Q.add(new Ele(-1, -1));

		// Process the grid while there are rotten oranges in the Queue
		while (!Q.isEmpty()) {
			// This flag is used to determine whether even a single fresh
			// orange gets rotten due to rotten oranges in the current time
			// frame so we can increase the count of the required time.
			boolean flag = false;

			// Process all the rotten oranges in current time frame.
			while (!isDelim(Q.peek())) {
				temp = Q.peek();

				// Check right adjacent cell that if it can be rotten
				if (isValid(temp.x + 1, temp.y, R, C) && arr[temp.x + 1][temp.y] == 1) {
					if (!flag) {
						// if this is the first orange to get rotten, increase
						// count and set the flag.
						ans++;
						flag = true;
						System.out.println(Arrays.toString(arr));
					}
					// Make the orange rotten
					arr[temp.x + 1][temp.y] = 2;

					// push the adjacent orange to Queue
					temp.x++;
					Q.add(new Ele(temp.x, temp.y));

					// Move back to current cell
					temp.x--;
				}

				// Check left adjacent cell that if it can be rotten
				if (isValid(temp.x - 1, temp.y, R, C) && arr[temp.x - 1][temp.y] == 1) {
					if (!flag) {
						ans++;
						flag = true;
						System.out.println(Arrays.toString(arr));
					}
					arr[temp.x - 1][temp.y] = 2;
					temp.x--;
					Q.add(new Ele(temp.x, temp.y)); // push this cell to Queue
					temp.x++;
				}

				// Check top adjacent cell that if it can be rotten
				if (isValid(temp.x, temp.y + 1, R, C) && arr[temp.x][temp.y + 1] == 1) {
					if (!flag) {
						ans++;
						flag = true;
						System.out.println(Arrays.toString(arr));
					}
					arr[temp.x][temp.y + 1] = 2;
					temp.y++;
					Q.add(new Ele(temp.x, temp.y)); // Push this cell to Queue
					temp.y--;
				}

				// Check bottom adjacent cell if it can be rotten
				if (isValid(temp.x, temp.y - 1, R, C) && arr[temp.x][temp.y - 1] == 1) {
					if (!flag) {
						ans++;
						flag = true;
						System.out.println(Arrays.toString(arr));
					}
					arr[temp.x][temp.y - 1] = 2;
					temp.y--;
					Q.add(new Ele(temp.x, temp.y)); // push this cell to Queue
				}
				Q.remove();

			}
			// Pop the delimiter
			Q.remove();

			// If oranges were rotten in current frame than separate the
			// rotten oranges using delimiter for the next frame for processing.
			if (!Q.isEmpty()) {
				Q.add(new Ele(-1, -1));
			}

			// If Queue was empty than no rotten oranges left to process so exit
		}

		// Return -1 if all arranges could not rot, otherwise -1.s
		return (checkAll(arr, R, C)) ? -1 : ans;

	}

	private static int[] lineToIntArray(String line) {
		String[] cols = line.split(",");
		int[] ints = new int[cols.length];
		for (int i = 0; i < cols.length; ++i) {
			ints[i] = Integer.parseInt(cols[i]);
		}
		return ints;
	}

	// Drive program
	public static void main(String[] args) {
		// Scanner scanner = new Scanner(System.in);
		// String line1 = scanner.nextLine();
		// String[] linearr = line1.split(",");
		// int rows = Integer.parseInt(linearr[0]);
		// int columns = Integer.parseInt(linearr[0]);
		// List<List<Integer>> cubicles = null;
		// int R = cubicles.size();
		// int C = cubicles.get(0).size();
		// int[][] arr = new int[rows][];
		//
		// for (int i = 0; i < rows; ++i) {
		// String line = scanner.nextLine();
		// arr[i] = lineToIntArray(line);
		// }

		// int arr[][] = { { 2, 1, 0, 2, 1 }, { 1, 0, 1, 2, 1 }, { 1, 0, 0, 2, 1
		// } };
		// //Ans: 1,2
		// int arr1[][] = { { 2, 1, 0, 2, 1 }, { 0, 0, 1, 2, 1 }, { 1, 0, 0, 2,
		// 1 } };
		// Ans: -1,-1
		int arr2[][] = { { 2, 1, 0, 2, 1, 1 }, { 1, 0, 2, 0, 1, 2 }, { 1, 0, 0, 1, 1, 0 }, { 1, 0, 0, 1, 1, 1 },
				{ 1, 0, 0, 0, 1, 2 }, { 1, 0, 0, 1, 1, 1 } };
		// // Ans: 1,4
		// int arr3[][] = { { 2, 1, 0, 2, 1, 1 }, { 1, 0, 1, 0, 1, 2 }, { 1, 0,
		// 0, 1, 1, 0 }, { 1, 0, 0, 1, 1, 1 },
		// { 1, 0, 0, 0, 1, 2 }, { 1, 0, 0, 1, 1, 1 } };
		// Ans: -1,-1
		int rows = arr2.length;
		int columns = arr2[0].length;
		int ans = rotOranges(arr2, rows, columns);
		if (ans == -1)
			System.out.println("-1,-1");
		else
			System.out.println("1," + ans);
	}

}
