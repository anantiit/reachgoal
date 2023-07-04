package com.csfundamentals.algo.problemsolving.leetcode.top140;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeetCodeTopQuestions {
	public static void main(String args[]) {
		LeetCodeTopQuestions lc = new LeetCodeTopQuestions();
//		ListNode head = new ListNode(1);
//		head.next = new ListNode(2);
//		head.next.next = new ListNode(3);
//		System.out.println(lc.removeNthFromEnd(head, 2));
		String[] strs = { "eat", "tea", "tan", "ate", "nat", "bat" };
		lc.groupAnagrams(strs);
		int n = 00000000000000000000001000101011;
		System.out.println(lc.hammingWeight(n));

		int[] a = { -20, 10, 1, 2, 7, 4, -5, 50, -30 };
		System.out.println(Arrays.toString(findIndicesWithMinimumDifference(a, 0)));
	}

	// Given an array A and an integer T. find indices i,j such that ||Ai + Ai+1 +
	// .. Aj | - T | is minimum. A can contain both -ves, +ves, complexity: O(n)

	public static int[] findIndicesWithMinimumDifference(int[] a, int T) {

		int n = a.length;
		int sum = 0;
		int globalSum = Integer.MAX_VALUE;
		int x = 0;
		int y = 0;
		int globalX = 0;
		int globalY = 0;
		for (int i = 0; i < n; i++) {
			// if (sum + a[i] < a[i]) {//if absolute is needed,
			if (Math.abs(Math.abs(sum + a[i]) - T) < Math.abs(Math.abs(a[i]) - T)) {
				sum = sum + a[i];
				y = i;
			} else {
				sum = Math.abs(a[i]);
				x = i;
				y = i;
			}
			if (globalSum > Math.abs(Math.abs(sum) - T)) {
				globalSum = Math.abs(Math.abs(sum) - T);
				globalX = x;
				globalY = y;
			}
		}
		System.out.println("x:" + globalX + "y:" + globalY + "min:" + (globalSum));
		int[] result = new int[2];
		result[0] = globalX;
		result[1] = globalY;
		return result;

	}

	public static int getSum(int[] A, int i, int j) {
		int sum = 0;
		for (int k = i; k <= j; k++) {
			sum += A[k];
		}
		return sum;
	}

	// https://leetcode.com/problems/house-robber/?envType=featured-list&envId=top-interview-questions
	public int rob(int[] nums) {
		int n = nums.length;
		int[] dp = new int[n + 1];
		dp[0] = 0;
		dp[1] = nums[0];
		for (int i = 2; i < n + 1; i++) {
			dp[i] = Math.max(dp[i - 1], (dp[i - 2] + nums[i - 1]));
		}
		return dp[n];

	}

	// https://leetcode.com/problems/number-of-1-bits/?envType=featured-list&envId=top-interview-questions
	public int hammingWeight(int n) {
		int sum = 0;
		while (n != 0) {
			sum++;
			n &= (n - 1);
		}
		return sum;
	}

	/*
	 * https://leetcode.com/problems/longest-common-prefix/
	 * 
	 */
	public ListNode removeNthFromEnd(ListNode head, int n) {
		ListNode firstPointer = head;
		ListNode secondPointer = head;

		for (int i = 0; i < n + 1; i++) {
			firstPointer = firstPointer.next;
		}

		while (firstPointer != null) {
			firstPointer = firstPointer.next;
			secondPointer = secondPointer.next;
		}
		secondPointer.next = secondPointer.next.next;
		return head;
	}

	// https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/

	public int[] searchRange(int[] a, int target) {

		int left = 0;
		int right = a.length - 1;
		int mid = 0;
		boolean targetFound = false;
		while (left <= right) {
			mid = left + (right - left) / 2;
			if (a[mid] == target) {
				targetFound = true;
				break;
			} else if (a[mid] < target) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		int[] result = new int[2];
		if (targetFound) {
			int i = mid;
			int j = mid;
			while (i > 0 && a[i] == a[i - 1]) {
				i--;
			}
			result[0] = i;
			while (j < a.length - 1 && a[j] == a[j + 1]) {
				j++;
			}
			result[1] = j;
		} else {
			result[0] = -1;
			result[1] = -1;
		}
		return result;
	}

	public List<List<String>> groupAnagrams(String[] strs) {
		List<List<String>> groupedAnagrams = new ArrayList<List<String>>();
		Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		ArrayList<String> anagramList;
		for (int i = 0; i < strs.length; i++) {
			char[] charArr = strs[i].toCharArray();
			Arrays.sort(charArr);
			String str1 = new String(charArr);
			anagramList = map.getOrDefault(str1, new ArrayList<String>());
			anagramList.add(strs[i]);
			map.put(str1, anagramList);
		}
		groupedAnagrams.addAll(map.values());
		return groupedAnagrams;

	}

}

class ListNode {
	int val;
	ListNode next;

	ListNode() {
	}

	ListNode(int val) {
		this.val = val;
	}

	ListNode(int val, ListNode next) {
		this.val = val;
		this.next = next;
	}

	@Override
	public String toString() {
		return "ListNode [val=" + val + ", next=" + next + "]";
	}

}