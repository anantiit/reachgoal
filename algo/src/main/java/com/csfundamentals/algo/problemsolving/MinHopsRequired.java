package com.csfundamentals.algo.problemsolving;

import java.util.ArrayList;
import java.util.List;

public class MinHopsRequired {
	public int jump(int[] a) {
		int maxReachable = a[0];
		int limit = a[0];
		List<Integer> selectedJumps = new ArrayList<Integer>();
		int lastJumConsidered = a[0];
		int ans = 0;
		for (int i = 0; i < a.length; i++) {
			if (i > limit) {
				limit = maxReachable;
				ans++;
				selectedJumps.add(lastJumConsidered);
			}
			if (maxReachable < i + a[i]) {
				maxReachable = i + a[i];
				lastJumConsidered = i;
			}

		}
		if (a.length > 1) {
			ans++;
			selectedJumps.add(lastJumConsidered);
		}
		System.out.println(selectedJumps);
		return ans;
	}

	public static void main() {

	}
}
