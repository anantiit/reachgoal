package com.csfundamentals.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupAnagrams {

	public static void main(String args[]) {
		String[] strs = { "abd", "ge", "sfa", "dab", "fas", "ge" };
		System.out.println(groupAnagrams(strs));
	}

	public static List<List<String>> groupAnagrams(String[] strs) {
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

}
