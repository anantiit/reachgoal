package com.csfundamentals.algo.problemsolving.leetcode.top140;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeetCodeTopQuestions {
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

	public static void main(String args[]) {
		LeetCodeTopQuestions lc = new LeetCodeTopQuestions();
//		ListNode head = new ListNode(1);
//		head.next = new ListNode(2);
//		head.next.next = new ListNode(3);
//		System.out.println(lc.removeNthFromEnd(head, 2));
		String[] strs = { "eat", "tea", "tan", "ate", "nat", "bat" };
		lc.groupAnagrams(strs);

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