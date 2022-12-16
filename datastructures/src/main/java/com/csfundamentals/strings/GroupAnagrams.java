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

	public static int customHashCode(String s) {
		char[] chars = s.toCharArray();
		Arrays.sort(chars);
		String sortedStr = new String(chars);
		return sortedStr.hashCode();
	}

}
