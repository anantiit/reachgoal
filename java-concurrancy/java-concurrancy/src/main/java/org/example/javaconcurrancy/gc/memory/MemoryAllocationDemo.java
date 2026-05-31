package org.example.javaconcurrancy.gc.memory;

/**
 * Demo 16: Memory Allocation - Stack vs Heap
 * 
 * KEY CONCEPTS:
 * - Stack memory: Local variables, method calls (Thread-specific, fast, limited)
 * - Heap memory: Objects, instance variables (Shared, slower, larger, GC-managed)
 * - Object lifecycle: Creation -> Usage -> Unreachable -> GC eligible -> Collected
 * - Young Generation vs Old Generation
 * 
 * RUN WITH: java -Xms64m -Xmx128m -XX:+PrintGCDetails -verbose:gc MemoryAllocationDemo
 */
public class MemoryAllocationDemo {

    public static void main(String[] args) {
        System.out.println("=== Memory Allocation Demo ===\n");
        
        // Display memory info
        displayMemoryInfo("Initial state");
        
        // Demo 1: Stack memory
        demonstrateStackMemory();
        
        // Demo 2: Heap memory
        demonstrateHeapMemory();
        
        // Demo 3: Object lifecycle
        demonstrateObjectLifecycle();
        
        System.out.println("\n=== Memory allocation demo completed ===");
    }
    
    private static void demonstrateStackMemory() {
        System.out.println("\n1. Stack Memory (Method Call Stack):");
        
        // These are stored on stack
        int localVar = 10;
        double localDouble = 20.5;
        
        System.out.println("  Local variables (primitives) stored on stack");
        System.out.println("  Stack frame created for this method");
        System.out.println("  localVar: " + localVar + " (on stack)");
        System.out.println("  localDouble: " + localDouble + " (on stack)");
        
        recursiveMethod(3);
        
        System.out.println("  ✓ Stack frames removed after method returns");
    }
    
    private static void recursiveMethod(int depth) {
        if (depth == 0) {
            System.out.println("  Stack depth reached: 3 frames");
            return;
        }
        System.out.println("  Recursive call, depth: " + depth);
        recursiveMethod(depth - 1);
    }
    
    private static void demonstrateHeapMemory() {
        System.out.println("\n2. Heap Memory (Object Storage):");
        
        displayMemoryInfo("Before object creation");
        
        // Objects are created on heap
        Person person1 = new Person("Alice", 30);
        Person person2 = new Person("Bob", 25);
        
        System.out.println("  Object reference (person1) stored on stack");
        System.out.println("  Actual object data stored on heap");
        System.out.println("  person1: " + person1);
        System.out.println("  person2: " + person2);
        
        displayMemoryInfo("After object creation");
        
        System.out.println("  ✓ Objects remain on heap until GC collects them");
    }
    
    private static void demonstrateObjectLifecycle() {
        System.out.println("\n3. Object Lifecycle:");
        
        displayMemoryInfo("Before creating objects");
        
        // Create many objects
        for (int i = 0; i < 100000; i++) {
            Person temp = new Person("Person-" + i, i % 100);
            // temp becomes unreachable after each iteration
        }
        
        displayMemoryInfo("After creating 100K objects");
        
        // Suggest GC (not guaranteed to run immediately)
        System.out.println("\n  Suggesting garbage collection...");
        System.gc();
        
        // Give GC time to run
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        displayMemoryInfo("After GC suggestion");
        
        System.out.println("  ✓ Unreachable objects were collected by GC");
    }
    
    private static void displayMemoryInfo(String label) {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        long maxMemory = runtime.maxMemory();
        
        System.out.println("\n  📊 Memory Info [" + label + "]:");
        System.out.println("     Total Memory: " + formatBytes(totalMemory));
        System.out.println("     Used Memory:  " + formatBytes(usedMemory));
        System.out.println("     Free Memory:  " + formatBytes(freeMemory));
        System.out.println("     Max Memory:   " + formatBytes(maxMemory));
    }
    
    private static String formatBytes(long bytes) {
        return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
    }
}

class Person {
    private String name;
    private int age;
    private String[] hobbies = new String[10]; // Additional memory overhead
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}
