package com.reachgoal.problemsolving;

/*
 * given array of heights of histograms find the water valume that can be trapped between histograms\
 * Eg: a= {4,2,0,1,3} Ans: 6
 * Clue: Find max water can be trapped at each index. Find left max (not greater than a[i] it is max on left side) , right max for each index i and the max water can be trapped there is min(leftMax, rightMax)-a[i] 
 */
public class WaterHistogram {

	public static int trappedBetweenHistograms(int a[]) {
		int leftMax = -1;
		int rightMaxIndex = findRightMaxIndexForGivenIndex(a, 1);
		int trappedWaterVolume = 0;
		for (int i = 0; i < a.length; i++) {
			if (leftMax < a[i]) {
				leftMax = a[i];
			}
			if (rightMaxIndex < i) {
				rightMaxIndex = findRightMaxIndexForGivenIndex(a, i);
			}
			int currtrappedWaterVolume = Math.min(leftMax, a[rightMaxIndex]) - a[i];
			System.out.println("trappedWaterVolume =" + trappedWaterVolume + "currtrappedWaterVolume:"
					+ currtrappedWaterVolume + "leftMax:" + leftMax + " rightMax:" + rightMaxIndex + " a[i]:" + a[i]);

			trappedWaterVolume = trappedWaterVolume + ((currtrappedWaterVolume > 0) ? currtrappedWaterVolume : 0);
		}
		return trappedWaterVolume;
	}

	public static int findRightMaxIndexForGivenIndex(int a[], int j) {
		int rightMaxIndex = -1;
		int rightMax = Integer.MIN_VALUE;
		for (int i = j; i < a.length; i++) {
			if (rightMax < a[i]) {
				rightMaxIndex = i;
				rightMax = a[i];
			}
		}
		return rightMaxIndex;
	}

	public static void main(String args[]) {
		int a[] = { 4, 2, 0, 1, 3 };
		int a1[] = { 3, 0, 2, 0, 4 };
		int a2[] = { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };
		int a3[] = { 8, 8, 2, 4, 5, 5, 1 };
		System.out.println(trappedBetweenHistograms(a3));
	}
}
