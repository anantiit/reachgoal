package com.algo.dynamicprogramming;

public class OptimalCopyPaste {
	// A Dynamic Programming based Java program
	// to find maximum number of A's that
	// can be printed using four keys
	// This function returns the optimal length
	// string for N keystrokes
	static int findoptimal(int N) {
		// The optimal string length is N
		// when N is smaller than 7
		if (N <= 6)
			return N;

		// An array to store result of subproblems
		int[] screen = new int[N];

		int b; // To pick a breakpoint

		// Initializing the optimal lengths array
		// for uptil 6 input strokes.
		int n;
		for (n = 1; n <= 6; n++)
			screen[n - 1] = n;

		// Solve all subproblems in bottom-up manner
		for (n = 7; n <= N; n++) {

			// for any keystroke n, we will need to choose between:-
			// 1. pressing Ctrl-V once after copying the
			// A's obtained by n-3 keystrokes.

			// 2. pressing Ctrl-V twice after copying the A's
			// obtained by n-4 keystrokes.

			// 3. pressing Ctrl-V thrice after copying the A's
			// obtained by n-5 keystrokes.
			// 4. pressing ctrl-v 5 times is not optimal < 6 * screen[n - 7] which is less
			// than 6x where
			// x=screen[n-7] but we can get better choice by doing the following steps
			// out of 5 strokes : 1. Ctrl+V on existing x will give 2x As , 2 Ctrl+A , 3
			// CtrlC, 4Ctrl+V +5. ctrlV will give 6x
			screen[n - 1] = Math.max(2 * screen[n - 4],
					Math.max(3 * screen[n - 5], Math.max(4 * screen[n - 6], 5 * screen[n - 7])));
		}
		return screen[N - 1];
	}

	// Driver Code
	public static void main(String[] args) {
		int N;

		// for the rest of the array we will rely
		// on the previous entries to compute new ones
		for (N = 1; N <= 20; N++)
			System.out.printf("Maximum Number of A's with" + " %d keystrokes is %d\n", N, findoptimal(N));
	}
}
