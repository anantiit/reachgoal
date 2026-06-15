package com.example.java_collections.collections;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Deep dive into List implementations: ArrayList vs LinkedList
 * Performance characteristics, use cases, and internal mechanics
 */
public class ListExamples {
    
    private static final int ITERATIONS = 100_000;
    private static final int SEARCH_ITERATIONS = 10_000;
    
    public static void main(String[] args) {
        System.out.println("=== LIST IMPLEMENTATIONS: PERFORMANCE & CHARACTERISTICS ===\n");
        
        demonstrateArrayListInternals();
        demonstrateLinkedListInternals();
        compareRandomAccess();
        compareSequentialAccess();
        compareInsertionPerformance();
        compareDeletionPerformance();
        demonstrateMemoryFootprint();
        demonstrateFailFastBehavior();
    }
    
    private static void demonstrateArrayListInternals() {
        System.out.println("1. ArrayList Internal Mechanics");
        System.out.println("   - Backed by dynamic array (default capacity: 10)");
        System.out.println("   - Growth strategy: newCapacity = oldCapacity + (oldCapacity >> 1) // 1.5x");
        
        List<Integer> arrayList = new ArrayList<>(5);
        for (int i = 0; i < 12; i++) {
            arrayList.add(i);
            // Capacity grows: 5 -> 7 -> 10 -> 15
        }
        System.out.println("   - Added 12 elements (initial capacity: 5)");
        System.out.println("   - Resizing occurred at elements: 6 (to 7), 8 (to 10), 11 (to 15)\n");
    }
    
    private static void demonstrateLinkedListInternals() {
        System.out.println("2. LinkedList Internal Mechanics");
        System.out.println("   - Doubly-linked list (each node: prev, data, next)");
        System.out.println("   - Implements Deque interface (double-ended queue)");
        System.out.println("   - No capacity, no resizing, O(1) insertion at both ends");
        
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.addFirst("Middle");
        linkedList.addFirst("First");
        linkedList.addLast("Last");
        System.out.println("   - Structure: " + linkedList);
        System.out.println("   - Memory: 3 objects + 3 nodes (24 bytes overhead per node)\n");
    }
    
    private static void compareRandomAccess() {
        System.out.println("3. Random Access Performance: get(index)");
        
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        
        for (int i = 0; i < ITERATIONS; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }
        
        long start = System.nanoTime();
        for (int i = 0; i < SEARCH_ITERATIONS; i++) {
            arrayList.get(ITERATIONS / 2);
        }
        long arrayListTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        for (int i = 0; i < SEARCH_ITERATIONS; i++) {
            linkedList.get(ITERATIONS / 2);
        }
        long linkedListTime = System.nanoTime() - start;
        
        System.out.printf("   - ArrayList:  %,d ns (O(1) - direct array access)%n", arrayListTime);
        System.out.printf("   - LinkedList: %,d ns (O(n) - node traversal)%n", linkedListTime);
        System.out.printf("   - ArrayList is %.2fx faster%n%n", (double) linkedListTime / arrayListTime);
    }
    
    private static void compareSequentialAccess() {
        System.out.println("4. Sequential Access Performance: Iterator");
        
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        
        for (int i = 0; i < ITERATIONS; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }
        
        long start = System.nanoTime();
        int sum = 0;
        for (Integer num : arrayList) {
            sum += num;
        }
        long arrayListTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        sum = 0;
        for (Integer num : linkedList) {
            sum += num;
        }
        long linkedListTime = System.nanoTime() - start;
        
        System.out.printf("   - ArrayList:  %,d ns%n", arrayListTime);
        System.out.printf("   - LinkedList: %,d ns%n", linkedListTime);
        System.out.println("   - Both O(n), but ArrayList has better cache locality\n");
    }
    
    private static void compareInsertionPerformance() {
        System.out.println("5. Insertion Performance Analysis");
        
        // Insert at beginning
        List<Integer> arrayList = new ArrayList<>();
        long start = System.nanoTime();
        for (int i = 0; i < 10_000; i++) {
            arrayList.add(0, i); // Insert at beginning
        }
        long arrayListBeginning = System.nanoTime() - start;
        
        List<Integer> linkedList = new LinkedList<>();
        start = System.nanoTime();
        for (int i = 0; i < 10_000; i++) {
            linkedList.add(0, i);
        }
        long linkedListBeginning = System.nanoTime() - start;
        
        System.out.println("   Insert at Beginning (10k elements):");
        System.out.printf("   - ArrayList:  %,d ms (O(n) - shift all elements)%n", 
            TimeUnit.NANOSECONDS.toMillis(arrayListBeginning));
        System.out.printf("   - LinkedList: %,d ms (O(1) - update head reference)%n", 
            TimeUnit.NANOSECONDS.toMillis(linkedListBeginning));
        System.out.printf("   - LinkedList is %.2fx faster%n%n", 
            (double) arrayListBeginning / linkedListBeginning);
    }
    
    private static void compareDeletionPerformance() {
        System.out.println("6. Deletion Performance Analysis");
        
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 50_000; i++) arrayList.add(i);
        
        long start = System.nanoTime();
        while (arrayList.size() > 25_000) {
            arrayList.remove(0); // Remove from beginning
        }
        long arrayListTime = System.nanoTime() - start;
        
        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < 50_000; i++) linkedList.add(i);
        
        start = System.nanoTime();
        while (linkedList.size() > 25_000) {
            linkedList.remove(0);
        }
        long linkedListTime = System.nanoTime() - start;
        
        System.out.println("   Remove from Beginning (25k deletions):");
        System.out.printf("   - ArrayList:  %,d ms%n", TimeUnit.NANOSECONDS.toMillis(arrayListTime));
        System.out.printf("   - LinkedList: %,d ms%n", TimeUnit.NANOSECONDS.toMillis(linkedListTime));
    }
    
    private static void demonstrateMemoryFootprint() {
        System.out.println("\n7. Memory Footprint Comparison");
        System.out.println("   ArrayList (1000 integers):");
        System.out.println("   - Array: 4KB (1000 * 4 bytes)");
        System.out.println("   - Object overhead: ~24 bytes");
        System.out.println("   - Total: ~4KB");
        System.out.println();
        System.out.println("   LinkedList (1000 integers):");
        System.out.println("   - Nodes: 24KB (1000 * 24 bytes per node)");
        System.out.println("   - Integer objects: 16KB (1000 * 16 bytes)");
        System.out.println("   - Total: ~40KB (10x more memory)\n");
    }
    
    private static void demonstrateFailFastBehavior() {
        System.out.println("8. Fail-Fast Iterator Behavior");
        List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        
        try {
            for (String item : list) {
                if (item.equals("B")) {
                    list.remove(item);
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("   - ConcurrentModificationException caught!");
            System.out.println("   - modCount changed during iteration");
        }
        
        System.out.println("\n   Correct approach using Iterator.remove():");
        list = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if (item.equals("B")) {
                iterator.remove();
            }
        }
        System.out.println("   - Result: " + list);
        System.out.println("\n=== KEY TAKEAWAY ===");
        System.out.println("ArrayList: Random access, append operations, low memory");
        System.out.println("LinkedList: Frequent insertion/deletion at both ends, implements Deque");
    }
}
