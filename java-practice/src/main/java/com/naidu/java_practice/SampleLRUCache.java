package com.naidu.java_practice;

import java.util.HashMap;
import java.util.LinkedList;

class CacheObject {
	Integer EmpId;
	String Name;
}

class CacheEntry {
	CacheObject entry;
	CacheEntry next;
	CacheEntry prev;
}
public class SampleLRUCache {
	HashMap<Integer, CacheEntry> cacheMap = new HashMap<Integer, CacheEntry>();
	LinkedList<CacheEntry> queue = new LinkedList<CacheEntry>();

	public static void lruCacheAdd() {

	}

	public static void lruCacheFind() {

	}

}
