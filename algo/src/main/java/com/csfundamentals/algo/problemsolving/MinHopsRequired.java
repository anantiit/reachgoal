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
		if (maxReachable <= 0) {
			return -1;
		}
		for (int i = 0; i < a.length; i++) {
			if (maxReachable < a.length && maxReachable < i + a[i]) {
				maxReachable = i + a[i];
				ans++;
				selectedJumps.add(i);
			}
			if (maxReachable <= i && i != a.length - 1) {
				return -1;
			}
		}
		if (a.length - 1 > maxReachable) {
			return -1;
		}
		System.out.println(selectedJumps);
		return ans;
	}

	public static void main(String args[]) {
		int arr[] = { 1, 1, 16, 20, 21, 2, 6, 7, 6, 8, 9 };
		int a[] = { 5, 9, 3, 2, 1, 0, 2, 3, 3, 1, 0, 0 };
		int a1[] = { 1, 0, 2 };

		System.out.println(jump(a));
	}
}
