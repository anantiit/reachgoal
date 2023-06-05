package com.csfundamentals.arrays;

public class MaxTubHistogram {
	public static int maxArea(int[] height) {
		int left = 0;
		int n = height.length;
		int right = n - 1;
		int maxArea = (right - left) * Math.min(height[left], height[right]);
		while (left < right) {
			int tempArea1 = 0;
			if (height[left] < height[right]) {
				left++;
			} else {
				right--;
			}
			maxArea = Math.max(maxArea, (right - left) * Math.min(height[left], height[right]));

		}
		return maxArea;
	}

	public static void main(String[] args) {
		int[] height = { 2, 3, 10, 5, 7, 8, 9 };
		System.out.println(maxArea(height));

	}
}