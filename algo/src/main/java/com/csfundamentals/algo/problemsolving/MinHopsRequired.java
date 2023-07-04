package com.csfundamentals.algo.problemsolving;

//https://www.geeksforgeeks.org/minimum-number-of-jumps-to-reach-end-of-a-given-array/
import java.util.ArrayList;
import java.util.List;

public class MinHopsRequired {
	public static int jump(int[] a) {
		int maxReachable = a[0];
		List<Integer> selectedJumps = new ArrayList<Integer>();
		selectedJumps.add(0);
		int ans = 0;
		int maxIndex = 0;
		for (int i = 1; i < a.length; i++) {
			if (maxReachable >= a.length - 1) {
				System.out.println(selectedJumps);
				return ans;
			}
			if (maxReachable < i) {
				System.out.println(selectedJumps);
				return -1;
			}
			if (maxIndex + a[maxIndex] < i + a[i]) {
				maxIndex = i;
			}
			if (maxReachable == i) {
				maxReachable = maxIndex + a[maxIndex];
				ans++;
				selectedJumps.add(maxIndex);
			}
		}
		return ans;
	}

	public static void main(String args[]) {
		int a3[] = { 1, 1, 3, 20, 1, 1, 1, 1, 1, 1, 1 };
		int a[] = { 5, 9, 3, 2, 1, 0, 2, 3, 3, 1, 0, 0 };
		int a1[] = { 1, 0, 2 };
		int a2[] = { 1, 3, 5, 8, 9, 2, 6, 7, 6, 8, 9 };

		System.out.println(jump(a2));
	}
}
