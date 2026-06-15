package com.example.java_collections.collections;

import java.util.*;
import java.util.concurrent.*;

/**
 * Deep dive into Map implementations: HashMap, LinkedHashMap, TreeMap, ConcurrentHashMap
 * Performance, ordering, thread-safety, and advanced features
 */
public class MapExamples {
    
    private static final int ITERATIONS = 100_000;
    
    public static void main(String[] args) {
        System.out.println("=== MAP IMPLEMENTATIONS: DEEP DIVE ===\n");
        
        demonstrateHashMapInternals();
        demonstrateLinkedHashMapOrdering();
        demonstrateTreeMapNavigable();
        demonstrateConcurrentHashMap();
        comparePerformance();
        demonstrateHashCollisions();
        demonstrateComputeOperations();
        demonstrateMergeOperation();
    }
    
    private static void demonstrateHashMapInternals() {
        System.out.println("1. HashMap Internal Mechanics");
        System.out.println("   - Array of Node<K,V>[] (buckets)");
        System.out.println("   - Default capacity: 16, load factor: 0.75");
        System.out.println("   - Java 8+: Bucket converts to TreeNode when size > 8 (TREEIFY_THRESHOLD)");
        System.out.println("   - Hash calculation: (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16)");
        System.out.println("   - Index: (n - 1) & hash  // n must be power of 2");
        
        Map<String, Integer> map = new HashMap<>();
        map.put("Apple", 100);
        map.put("Banana", 200);
        map.put(null, 300); // HashMap allows one null key
        
        System.out.println("   - Map: " + map);
        System.out.println("   - Null key allowed: " + map.containsKey(null));
        System.out.println("   - Get null key: " + map.get(null) + "\n");
    }
    
    private static void demonstrateLinkedHashMapOrdering() {
        System.out.println("2. LinkedHashMap - Insertion & Access Order");
        
        System.out.println("   Insertion Order (default):");
        Map<String, Integer> insertionOrder = new LinkedHashMap<>();
        insertionOrder.put("Third", 3);
        insertionOrder.put("First", 1);
        insertionOrder.put("Second", 2);
        System.out.println("   - " + insertionOrder);
        
        System.out.println("\n   Access Order (LRU Cache implementation):");
        Map<String, Integer> accessOrder = new LinkedHashMap<>(16, 0.75f, true);
        accessOrder.put("A", 1);
        accessOrder.put("B", 2);
        accessOrder.put("C", 3);
        accessOrder.get("A"); // Access A, moves to end
        System.out.println("   - After accessing 'A': " + accessOrder);
        
        System.out.println("\n   LRU Cache with removeEldestEntry override:");
        LRUCache<String, Integer> lruCache = new LRUCache<>(3);
        lruCache.put("1", 1);
        lruCache.put("2", 2);
        lruCache.put("3", 3);
        System.out.println("   - Cache (capacity 3): " + lruCache);
        lruCache.put("4", 4); // Evicts eldest
        System.out.println("   - After adding 4th: " + lruCache + " (eldest removed)\n");
    }
    
    private static void demonstrateTreeMapNavigable() {
        System.out.println("3. TreeMap - Sorted & Navigable");
        System.out.println("   - Red-Black tree implementation");
        System.out.println("   - O(log n) for get, put, remove");
        System.out.println("   - Implements NavigableMap (ceiling, floor, subMap, etc.)");
        
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(50, "Fifty");
        treeMap.put(20, "Twenty");
        treeMap.put(80, "Eighty");
        treeMap.put(10, "Ten");
        treeMap.put(30, "Thirty");
        
        System.out.println("   - Sorted keys: " + treeMap);
        System.out.println("   - firstKey: " + treeMap.firstKey());
        System.out.println("   - lastKey: " + treeMap.lastKey());
        System.out.println("   - ceilingKey(25): " + treeMap.ceilingKey(25));
        System.out.println("   - floorKey(25): " + treeMap.floorKey(25));
        System.out.println("   - headMap(50): " + treeMap.headMap(50));
        System.out.println("   - tailMap(50): " + treeMap.tailMap(50));
        System.out.println("   - subMap(20, 80): " + treeMap.subMap(20, 80) + "\n");
    }
    
    private static void demonstrateConcurrentHashMap() {
        System.out.println("4. ConcurrentHashMap - Thread-Safe without full locking");
        System.out.println("   - Segment-based locking (Java 7) → CAS + synchronized (Java 8+)");
        System.out.println("   - No null keys or values");
        System.out.println("   - Atomic operations: putIfAbsent, compute, merge");
        System.out.println("   - Weakly consistent iterators (no ConcurrentModificationException)");
        
        ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put("Counter", 0);
        
        // Atomic increment
        concurrentMap.compute("Counter", (k, v) -> v + 1);
        System.out.println("   - After compute: " + concurrentMap.get("Counter"));
        
        // putIfAbsent
        concurrentMap.putIfAbsent("Counter", 100); // Won't replace
        System.out.println("   - After putIfAbsent(100): " + concurrentMap.get("Counter"));
        
        // Parallel operations
        concurrentMap.put("A", 1);
        concurrentMap.put("B", 2);
        concurrentMap.put("C", 3);
        
        int sum = concurrentMap.reduceValues(1, Integer::sum);
        System.out.println("   - Sum of all values (parallel): " + sum + "\n");
    }
    
    private static void comparePerformance() {
        System.out.println("5. Performance Comparison (100k operations)");
        
        Map<Integer, String> hashMap = new HashMap<>();
        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            hashMap.put(i, "Value" + i);
        }
        long hashMapPut = System.nanoTime() - start;
        
        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            linkedHashMap.put(i, "Value" + i);
        }
        long linkedHashMapPut = System.nanoTime() - start;
        
        Map<Integer, String> treeMap = new TreeMap<>();
        start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            treeMap.put(i, "Value" + i);
        }
        long treeMapPut = System.nanoTime() - start;
        
        Map<Integer, String> concurrentMap = new ConcurrentHashMap<>();
        start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            concurrentMap.put(i, "Value" + i);
        }
        long concurrentMapPut = System.nanoTime() - start;
        
        System.out.printf("   HashMap:           %,d ms%n", TimeUnit.NANOSECONDS.toMillis(hashMapPut));
        System.out.printf("   LinkedHashMap:     %,d ms%n", TimeUnit.NANOSECONDS.toMillis(linkedHashMapPut));
        System.out.printf("   TreeMap:           %,d ms%n", TimeUnit.NANOSECONDS.toMillis(treeMapPut));
        System.out.printf("   ConcurrentHashMap: %,d ms%n%n", TimeUnit.NANOSECONDS.toMillis(concurrentMapPut));
    }
    
    private static void demonstrateHashCollisions() {
        System.out.println("6. Hash Collision Handling");
        System.out.println("   - Separate chaining: LinkedList (Java 7)");
        System.out.println("   - Java 8+: LinkedList → Red-Black Tree when bin size > 8");
        
        // Force collisions with poor hash function
        Map<BadHashKey, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(new BadHashKey(i), "Value" + i);
        }
        
        System.out.println("   - 10 keys with same hashCode → same bucket → treeified");
        System.out.println("   - Search degrades from O(1) → O(n) → O(log n) after treeify\n");
    }
    
    private static void demonstrateComputeOperations() {
        System.out.println("7. Compute Operations (Java 8+)");
        
        Map<String, Integer> map = new HashMap<>();
        
        map.compute("A", (k, v) -> v == null ? 1 : v + 1);
        System.out.println("   - compute('A'): " + map.get("A"));
        
        map.computeIfAbsent("B", k -> 100);
        System.out.println("   - computeIfAbsent('B'): " + map.get("B"));
        
        map.put("C", 50);
        map.computeIfPresent("C", (k, v) -> v * 2);
        System.out.println("   - computeIfPresent('C'): " + map.get("C"));
        
        // Word frequency counter
        String text = "the quick brown fox jumps over the lazy dog the fox";
        Map<String, Integer> wordCount = new HashMap<>();
        for (String word : text.split(" ")) {
            wordCount.compute(word, (k, v) -> v == null ? 1 : v + 1);
        }
        System.out.println("   - Word frequency: " + wordCount + "\n");
    }
    
    private static void demonstrateMergeOperation() {
        System.out.println("8. Merge Operation");
        
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("A", 10);
        map1.put("B", 20);
        
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("B", 30);
        map2.put("C", 40);
        
        // Merge map2 into map1, summing values for duplicate keys
        map2.forEach((k, v) -> map1.merge(k, v, Integer::sum));
        
        System.out.println("   - Merged map: " + map1);
        System.out.println("   - 'B' summed: 20 + 30 = " + map1.get("B"));
        
        System.out.println("\n=== KEY TAKEAWAY ===");
        System.out.println("HashMap: General purpose, O(1), allows null key");
        System.out.println("LinkedHashMap: Insertion/access order, LRU cache");
        System.out.println("TreeMap: Sorted, range operations, O(log n)");
        System.out.println("ConcurrentHashMap: Thread-safe, no locking on reads, atomic operations");
    }
    
    static class LRUCache<K, V> extends LinkedHashMap<K, V> {
        private final int capacity;
        
        LRUCache(int capacity) {
            super(capacity, 0.75f, true);
            this.capacity = capacity;
        }
        
        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > capacity;
        }
    }
    
    static class BadHashKey {
        int value;
        
        BadHashKey(int value) {
            this.value = value;
        }
        
        @Override
        public int hashCode() {
            return 42; // Terrible hash function - all keys collide
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BadHashKey)) return false;
            return value == ((BadHashKey) o).value;
        }
    }
}
