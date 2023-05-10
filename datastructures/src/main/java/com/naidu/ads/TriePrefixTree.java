package com.naidu.ads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TriePrefixTree {
	TrieNode root;

	TriePrefixTree(TrieNode root) {
		this.root = root;
	}

	public static void insert(TrieNode root, String word) {
		int n = word.length();
		if (root == null) {
			root = new TrieNode(null);
		}
		TrieNode cur = root;
		TrieNode temp;
		for (int i = 0; i < n; i++) {
			char character = word.charAt(i);
			if (cur.children.get(character) == null) {
				temp = new TrieNode(character);
				cur.children.put(character, temp);
			}
			cur = cur.children.get(character);
			if (i == n - 1) {
				cur.setEndOfWord(true);
			}
		}
	}

	public static TrieNode buildTrie(List<String> words) {
		TrieNode root = new TrieNode(null);
		words.stream().forEach(word -> TriePrefixTree.insert(root, word));
		return root;
	}

	public static boolean search(TrieNode root, String word, boolean exact) {
		int n = word.length();
		TrieNode cur = root;
		for (int i = 0; i < n; i++) {
			char character = word.charAt(i);
			cur = cur.children.get(character);
			if (cur == null)
				return false;
		}
		return !exact || cur.isEndOfWord;
	}

	public static List<String> suggest(TrieNode root, String word) {
		int n = word.length();
		TrieNode cur = root;
		StringBuilder str = new StringBuilder();
		List<String> suggestions = new ArrayList<String>();
		for (int i = 0; i < n; i++) {
			char character = word.charAt(i);
			str.append(character);
			if (cur.children.containsKey(character)) {
				cur = cur.children.get(character);
			} else {
				return suggestions;
			}
		}
		suggestHelper(cur, str, suggestions);
		return suggestions;
	}

	private static void suggestHelper(TrieNode cur, StringBuilder str, List<String> suggestions) {
		if (cur.isEndOfWord) {
			suggestions.add(str.toString());
		}
		if (cur.children == null || cur.children.isEmpty())
			return;

		for (TrieNode next : cur.children.values()) {
			str = str.append(next.c);
			suggestHelper(next, str, suggestions);
		}
	}

	public static void main(String[] args) {
		TrieNode root = buildTrie(List.of("geeks", "for", "geeks", "naidu", "dakshi", "naidu123", "dakshi123"));
		System.out.println(search(root, "naidu", true));
		System.out.println(search(root, "naid", false));
		System.out.println(search(root, "naid", true));
		System.out.println(suggest(root, "naid"));
		System.out.println(suggest(root, "naid4"));
	}
}

class TrieNode {
	Map<Character, TrieNode> children; // To optimize memory we can use hashmap instead of fixed array. but hashmap
	// default size is 16 we can reduce the default size so that some more memory is
	// optimized
	Character c;
	boolean isEndOfWord;

	TrieNode(Character c) {
		this.children = new HashMap<Character, TrieNode>(4);
		this.c = c;
		this.isEndOfWord = false;
	}

	public Map<Character, TrieNode> getMap() {
		return children;
	}

	public void setMap(Map<Character, TrieNode> map) {
		this.children = map;
	}

	public boolean isEndOfWord() {
		return isEndOfWord;
	}

	public void setEndOfWord(boolean isEndOfWord) {
		this.isEndOfWord = isEndOfWord;
	}

}