package com.example.java_collections.collections;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Deep dive into Set implementations: HashSet, LinkedHashSet, TreeSet
 * Performance characteristics, ordering guarantees, and use cases
 */
public class SetExamples {
    
    private static final int ITERATIONS = 100_000;
    
    public static void main(String[] args) {
        System.out.println("=== SET IMPLEMENTATIONS: CHARACTERISTICS & PERFORMANCE ===\n");
        
        demonstrateHashSetInternals();
        demonstrateLinkedHashSetOrdering();
        demonstrateTreeSetOrdering();
        compareAddPerformance();
        compareContainsPerformance();
        demonstrateSetOperations();
        demonstrateCustomObjects();
        demonstrateEnumSet();
    }
    
    private static void demonstrateHashSetInternals() {
        System.out.println("1. HashSet Internal Mechanics");
        System.out.println("   - Backed by HashMap (keys stored, values are dummy PRESENT object)");
        System.out.println("   - Default capacity: 16, load factor: 0.75");
        System.out.println("   - O(1) add, remove, contains (assuming good hash function)");
        
        Set<String> hashSet = new HashSet<>();
        hashSet.add("Apple");
        hashSet.add("Banana");
        hashSet.add("Cherry");
        hashSet.add("Apple"); // Duplicate, ignored
        
        System.out.println("   - Elements: " + hashSet);
        System.out.println("   - Size: " + hashSet.size() + " (duplicate ignored)");
        System.out.println("   - Order: Unpredictable (based on hash codes)\n");
    }
    
    private static void demonstrateLinkedHashSetOrdering() {
        System.out.println("2. LinkedHashSet - Insertion Order Preservation");
        System.out.println("   - Extends HashSet with doubly-linked list");
        System.out.println("   - Maintains insertion order");
        System.out.println("   - Slightly slower than HashSet due to linked list overhead");
        
        Set<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("Zebra");
        linkedHashSet.add("Apple");
        linkedHashSet.add("Mango");
        linkedHashSet.add("Banana");
        
        System.out.println("   - Insertion order: Zebra, Apple, Mango, Banana");
        System.out.println("   - Iteration order: " + linkedHashSet);
        System.out.println("   - Use case: Cache with predictable iteration (LRU implementation)\n");
    }
    
    private static void demonstrateTreeSetOrdering() {
        System.out.println("3. TreeSet - Sorted Order");
        System.out.println("   - Backed by TreeMap (Red-Black tree)");
        System.out.println("   - O(log n) add, remove, contains");
        System.out.println("   - Elements must implement Comparable or provide Comparator");
        
        Set<Integer> treeSet = new TreeSet<>();
        treeSet.addAll(Arrays.asList(50, 20, 80, 10, 30));
        
        System.out.println("   - Inserted: 50, 20, 80, 10, 30");
        System.out.println("   - Sorted order: " + treeSet);
        
        TreeSet<Integer> navigableSet = (TreeSet<Integer>) treeSet;
        System.out.println("   - First: " + navigableSet.first());
        System.out.println("   - Last: " + navigableSet.last());
        System.out.println("   - Higher than 30: " + navigableSet.higher(30));
        System.out.println("   - Lower than 30: " + navigableSet.lower(30));
        System.out.println("   - SubSet [20, 80): " + navigableSet.subSet(20, 80) + "\n");
    }
    
    private static void compareAddPerformance() {
        System.out.println("4. Add Performance Comparison");
        
        Set<Integer> hashSet = new HashSet<>();
        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            hashSet.add(i);
        }
        long hashSetTime = System.nanoTime() - start;
        
        Set<Integer> linkedHashSet = new LinkedHashSet<>();
        start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            linkedHashSet.add(i);
        }
        long linkedHashSetTime = System.nanoTime() - start;
        
        Set<Integer> treeSet = new TreeSet<>();
        start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            treeSet.add(i);
        }
        long treeSetTime = System.nanoTime() - start;
        
        System.out.printf("   HashSet:       %,d ms (O(1) - fastest)%n", 
            TimeUnit.NANOSECONDS.toMillis(hashSetTime));
        System.out.printf("   LinkedHashSet: %,d ms (O(1) + linked list overhead)%n", 
            TimeUnit.NANOSECONDS.toMillis(linkedHashSetTime));
        System.out.printf("   TreeSet:       %,d ms (O(log n) - tree balancing)%n%n", 
            TimeUnit.NANOSECONDS.toMillis(treeSetTime));
    }
    
    private static void compareContainsPerformance() {
        System.out.println("5. Contains Performance Comparison");
        
        Set<Integer> hashSet = new HashSet<>();
        Set<Integer> treeSet = new TreeSet<>();
        for (int i = 0; i < ITERATIONS; i++) {
            hashSet.add(i);
            treeSet.add(i);
        }
        
        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            hashSet.contains(i);
        }
        long hashSetTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            treeSet.contains(i);
        }
        long treeSetTime = System.nanoTime() - start;
        
        System.out.printf("   HashSet: %,d ms (O(1))%n", TimeUnit.NANOSECONDS.toMillis(hashSetTime));
        System.out.printf("   TreeSet: %,d ms (O(log n))%n", TimeUnit.NANOSECONDS.toMillis(treeSetTime));
        System.out.printf("   HashSet is %.2fx faster%n%n", (double) treeSetTime / hashSetTime);
    }
    
    private static void demonstrateSetOperations() {
        System.out.println("6. Set Theory Operations");
        
        Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        Set<Integer> set2 = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8));
        
        Set<Integer> union = new HashSet<>(set1);
        union.addAll(set2);
        System.out.println("   Union: " + union);
        
        Set<Integer> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        System.out.println("   Intersection: " + intersection);
        
        Set<Integer> difference = new HashSet<>(set1);
        difference.removeAll(set2);
        System.out.println("   Difference (set1 - set2): " + difference);
        
        Set<Integer> symmetricDiff = new HashSet<>(union);
        symmetricDiff.removeAll(intersection);
        System.out.println("   Symmetric Difference: " + symmetricDiff + "\n");
    }
    
    private static void demonstrateCustomObjects() {
        System.out.println("7. Custom Objects in Sets (hashCode & equals contract)");
        
        Set<Employee> employees = new HashSet<>();
        employees.add(new Employee("John", 101));
        employees.add(new Employee("Jane", 102));
        employees.add(new Employee("John", 101)); // Duplicate based on equals/hashCode
        
        System.out.println("   Employees added: 3 (one duplicate)");
        System.out.println("   Actual size: " + employees.size());
        System.out.println("   Elements: " + employees);
        System.out.println("   Key: Override hashCode() and equals() consistently\n");
    }
    
    private static void demonstrateEnumSet() {
        System.out.println("8. EnumSet - Specialized Set for Enums");
        System.out.println("   - Bit vector implementation (extremely efficient)");
        System.out.println("   - All operations O(1)");
        System.out.println("   - Type-safe, memory efficient");
        
        EnumSet<Day> weekend = EnumSet.of(Day.SATURDAY, Day.SUNDAY);
        EnumSet<Day> weekdays = EnumSet.range(Day.MONDAY, Day.FRIDAY);
        EnumSet<Day> allDays = EnumSet.allOf(Day.class);
        
        System.out.println("   Weekend: " + weekend);
        System.out.println("   Weekdays: " + weekdays);
        System.out.println("   All days: " + allDays);
        
        System.out.println("\n=== KEY TAKEAWAY ===");
        System.out.println("HashSet: General purpose, best performance, no ordering");
        System.out.println("LinkedHashSet: Insertion order, predictable iteration");
        System.out.println("TreeSet: Sorted order, range operations, O(log n)");
        System.out.println("EnumSet: Enum types only, highest performance");
    }
    
    static class Employee {
        String name;
        int id;
        
        Employee(String name, int id) {
            this.name = name;
            this.id = id;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Employee)) return false;
            Employee employee = (Employee) o;
            return id == employee.id && Objects.equals(name, employee.name);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(name, id);
        }
        
        @Override
        public String toString() {
            return name + "(" + id + ")";
        }
    }
    
    enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}
