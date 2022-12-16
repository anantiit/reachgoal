package com.csfundamentals.algo;

/*
 * Array of non negative integers i/p {2,3,7,0,0,0,2,1}  target number is 10 minimum length contiguous sub array which sums to target
 *
 * sumTillNow
 * startIndex
 * endIndex
 * windowLength
 * indexArray[]
 *
 *
 */
public class Test3 {

	public static void main(String[] args) {
		int[] arr = { 2, 3, 7, 0, 0, 0, 2, 1 };
		System.out.println(minSunbArray(arr, 10));
	}

	public static int minSunbArray(int[] arr, int targetSum) {
		int sumTillNow = 0;
		int startIndex = 0;
		int minSubArrayLength = 0;
		for (int endIndex = 0; endIndex < arr.length; endIndex++) {
			sumTillNow = sumTillNow + arr[endIndex];
			if (sumTillNow == targetSum && (minSubArrayLength < 1 || minSubArrayLength > (endIndex - startIndex + 1))) {
				System.out.println("endIndex " + endIndex + "startIndex" + startIndex);
				minSubArrayLength = endIndex - startIndex + 1;
			} else if (sumTillNow > targetSum) {
				sumTillNow = sumTillNow - arr[startIndex];
				startIndex++;
				if (sumTillNow == targetSum
						&& (minSubArrayLength < 1 || minSubArrayLength > (endIndex - startIndex + 1))) {
					System.out.println("endIndex " + endIndex + "startIndex" + startIndex);
					minSubArrayLength = endIndex - startIndex + 1;
				}
			}

		}
		return minSubArrayLength;
	}
}
