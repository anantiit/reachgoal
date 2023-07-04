package com.csfundamentals.maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RotatedStringsTogether {

	public static List<Set<String>> findSimilarSets(String[] strings) {
		Map<String, Set<String>> similarityMap = new HashMap<>();

		for (String str : strings) {
			String key = generateKey(str);
			similarityMap.putIfAbsent(key, new HashSet<>());
			similarityMap.get(key).add(str);
		}
		System.out.println(similarityMap.toString());

		List<Set<String>> similarSets = new ArrayList<>();
		for (Set<String> set : similarityMap.values()) {
			similarSets.add(set);
		}

		return similarSets;
	}

	private static String generateKey(String str) {
		StringBuilder keyBuilder = new StringBuilder();
		int distance = str.charAt(0) - 'a';
		for (char c : str.toCharArray()) {
			char normalizedChar = (char) ((c - distance - 'a') < 0 ? ('a' + 26 - distance + 1) : (c - distance)); // Apply
			// the
			// equidistant
			// similarity
			// rule
			keyBuilder.append(normalizedChar);
		}

		return keyBuilder.toString();
	}

	public static void main(String[] args) {
		String[] strings = { "zb", "abc", "def", "ghi", "xz" };

		List<Set<String>> similarSets = findSimilarSets(strings);

		// Print the similar sets
		for (Set<String> set : similarSets) {
			System.out.println(set);
		}
	}
}
