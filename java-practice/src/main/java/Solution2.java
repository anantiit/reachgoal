import java.util.Arrays;

/*
 * 
Given an array A[] of N distinct integers. The task is to return the lexicographically greater permutation than the given arrangement. If no such arrangement is possible, return the array sorted in non-decreasing order.

Examples:

Input: A[] = [1, 2, 3]
Output: [1, 3, 2]
Explanation: The lexicographically greater permutation than A[] is [1, 3, 2]

Input:  A[] = [4, 3, 2, 1]

Output: [1, 2, 3, 4]


 */
public class Solution2 {

	public static void nextPermutation(int[] A) {
		if (A == null || A.length <= 1)
			return;
		int i = A.length - 2;
		while (i >= 0 && A[i] >= A[i + 1])
			i--;
		if (i >= 0) {
			int j = A.length - 1;
			while (A[j] <= A[i])
				j--;
			swap(A, i, j);
		}
		reverse(A, i + 1, A.length - 1);
	}

	public static void swap(int[] A, int i, int j) {
		int tmp = A[i];
		A[i] = A[j];
		A[j] = tmp;
	}

	public static void reverse(int[] A, int i, int j) {
		while (i < j)
			swap(A, i++, j--);
	}

	public static void main(String args[]) {
		int[] a = { 2, 3, 1, 4 };
		nextPermutation(a);
		System.out.println(Arrays.toString(a));
	}
}
