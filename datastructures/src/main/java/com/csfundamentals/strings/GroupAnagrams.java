package com.csfundamentals.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class GroupAnagrams {

	public static void main(String args[]) {
		String s0 = "abd";
		String s5 = "ge";
		String s4 = "sfa";
		String s3 = "dab";
		String s2 = "fas";
		String s1 = "ge";
		List<String> list = new ArrayList<String>();
		list.add(s0);
		list.add(s1);
		list.add(s2);
		list.add(s3);
		list.add(s4);
		list.add(s5);
		System.out.println(list);
		System.out.println(groupAnagramsTogether(list));
	}

	public static List<String> groupAnagramsTogether(List<String> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		HashMap<Integer, List<String>> map = new HashMap<Integer, List<String>>();
		for (String s : list) {
			List<String> groupList = map.get(customHashCode(s));
			if (groupList != null && !groupList.isEmpty()) {
				groupList.add(s);
			} else {
				groupList = new ArrayList<String>();
				groupList.add(s);
				map.put(customHashCode(s), groupList);
			}
		}
		List<String> result = new ArrayList<String>();
		for (Entry<Integer, List<String>> entry : map.entrySet()) {
			result.addAll(entry.getValue());
		}
		return result;

	}

	private static ArrayList<ArrayList<String>> solver(ArrayList<String> list) {

		// Inner hashmap counts frequency
		// of characters in a string.
		// Outer hashmap for if same
		// frequency characters are present in
		// in a string then it will add it to
		// the arraylist.
		HashMap<HashMap<Character, Integer>, ArrayList<String>> map = new HashMap<HashMap<Character, Integer>, ArrayList<String>>();
		for (String str : list) {
			HashMap<Character, Integer> tempMap = new HashMap<Character, Integer>();

			// Counting the frequency of the
			// characters present in a string
			for (int i = 0; i < str.length(); i++) {
				if (tempMap.containsKey(str.charAt(i))) {
					int x = tempMap.get(str.charAt(i));
					tempMap.put(str.charAt(i), ++x);
				} else {
					tempMap.put(str.charAt(i), 1);
				}
			}

			// If the same frequency of characters
			// are already present then add that
			// string into that arraylist otherwise
			// created a new arraylist and add that string
			if (map.containsKey(tempMap))
				map.get(tempMap).add(str);
			else {
				ArrayList<String> tempList = new ArrayList<String>();
				tempList.add(str);
				map.put(tempMap, tempList);
			}
		}

		// Stores the result in a arraylist
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		for (HashMap<Character, Integer> temp : map.keySet())
			result.add(map.get(temp));
		return result;
	}

	public static int customHashCode(String s) {
		char[] chars = s.toCharArray();
		Arrays.sort(chars);
		String sortedStr = new String(chars);
		return sortedStr.hashCode();
	}

}
