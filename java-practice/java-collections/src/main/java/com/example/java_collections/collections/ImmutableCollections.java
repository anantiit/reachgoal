package com.example.java_collections.collections;

import java.util.*;

/**
 * Immutable Collections in Java 9+ and best practices
 */
public class ImmutableCollections {
    
    public static void main(String[] args) {
        System.out.println("=== IMMUTABLE COLLECTIONS (Java 9+) ===\n");
        
        demonstrateListOf();
        demonstrateSetOf();
        demonstrateMapOf();
        demonstrateCopyOf();
        compareUnmodifiableVsImmutable();
    }
    
    private static void demonstrateListOf() {
        System.out.println("1. List.of() - Immutable Lists");
        
        List<String> immutableList = List.of("Apple", "Banana", "Cherry");
        System.out.println("   - List: " + immutableList);
        
        try {
            immutableList.add("Date");
        } catch (UnsupportedOperationException e) {
            System.out.println("   - add() throws UnsupportedOperationException ✓");
        }
        
        try {
            List<String> nullList = List.of("A", null, "C");
        } catch (NullPointerException e) {
            System.out.println("   - null elements throw NullPointerException ✓\n");
        }
    }
    
    private static void demonstrateSetOf() {
        System.out.println("2. Set.of() - Immutable Sets");
        
        Set<Integer> immutableSet = Set.of(1, 2, 3, 4, 5);
        System.out.println("   - Set: " + immutableSet);
        
        try {
            Set<Integer> duplicateSet = Set.of(1, 2, 3, 2);
        } catch (IllegalArgumentException e) {
            System.out.println("   - Duplicates throw IllegalArgumentException ✓\n");
        }
    }
    
    private static void demonstrateMapOf() {
        System.out.println("3. Map.of() - Immutable Maps");
        
        Map<String, Integer> map = Map.of("One", 1, "Two", 2, "Three", 3);
        System.out.println("   - Map: " + map);
        
        Map<String, Integer> largeMap = Map.ofEntries(
            Map.entry("A", 1),
            Map.entry("B", 2),
            Map.entry("C", 3)
        );
        System.out.println("   - Map.ofEntries(): " + largeMap + "\n");
    }
    
    private static void demonstrateCopyOf() {
        System.out.println("4. copyOf() - Immutable Copies");
        
        List<String> mutableList = new ArrayList<>(Arrays.asList("A", "B", "C"));
        List<String> immutableCopy = List.copyOf(mutableList);
        
        mutableList.add("D");
        System.out.println("   - Original modified: " + mutableList);
        System.out.println("   - Copy unchanged: " + immutableCopy + "\n");
    }
    
    private static void compareUnmodifiableVsImmutable() {
        System.out.println("5. Collections.unmodifiableList() vs List.of()");
        
        List<String> original = new ArrayList<>(Arrays.asList("A", "B", "C"));
        List<String> unmodifiableView = Collections.unmodifiableList(original);
        
        original.add("D");
        System.out.println("   - Unmodifiable view affected: " + unmodifiableView);
        
        original = new ArrayList<>(Arrays.asList("A", "B", "C"));
        List<String> immutableCopy = List.copyOf(original);
        original.add("D");
        System.out.println("   - Immutable copy unaffected: " + immutableCopy);
        
        System.out.println("\n=== KEY TAKEAWAY ===");
        System.out.println("List.of(), Set.of(), Map.of(): Truly immutable, no nulls");
        System.out.println("Collections.unmodifiable*(): View, reflects source changes");
        System.out.println("copyOf(): Immutable copy, unaffected by source changes");
    }
}
