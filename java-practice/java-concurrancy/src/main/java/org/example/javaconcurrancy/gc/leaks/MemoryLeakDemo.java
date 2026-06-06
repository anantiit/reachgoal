package org.example.javaconcurrancy.gc.leaks;

import java.util.*;

/**
 * Demo 18: Memory Leaks - Common Causes and Detection
 * 
 * KEY CONCEPTS:
 * - Memory leak: Objects that are no longer needed but still referenced
 * - Common causes: Unclosed resources, static collections, listeners
 * - Detection: Heap dumps, profilers, monitoring memory growth
 * - Prevention: Proper resource management, weak references
 * 
 * RUN WITH: java -Xmx128m -XX:+HeapDumpOnOutOfMemoryError MemoryLeakDemo
 */
public class MemoryLeakDemo {

    public static void main(String[] args) {
        System.out.println("=== Memory Leak Demo ===\n");
        
        System.out.println("Choose a demo:");
        System.out.println("1. Static Collection Leak (PROBLEM)");
        System.out.println("2. Listener Leak (PROBLEM)");
        System.out.println("3. Unclosed Resources Leak (PROBLEM)");
        System.out.println("4. Fixed Version (SOLUTION)\n");
        
        // Uncomment ONE demo at a time to see memory leak
        
        // demonstrateStaticCollectionLeak(); // WILL CAUSE OOM
        // demonstrateListenerLeak(); // WILL CAUSE OOM
        // demonstrateUnclosedResourceLeak(); // WILL CAUSE OOM
        demonstrateProperMemoryManagement(); // CORRECT
        
        System.out.println("\n=== Memory leak demo completed ===");
    }
    
    private static void demonstrateStaticCollectionLeak() {
        System.out.println("1. Static Collection Leak (PROBLEM):");
        System.out.println("  Adding objects to static collection without clearing...\n");
        
        for (int i = 1; i <= 1000; i++) {
            LeakyCache.addUser(new User("User-" + i, i));
            
            if (i % 100 == 0) {
                displayMemory("Iteration " + i);
            }
        }
        
        System.out.println("  ❌ Objects remain in static cache forever!");
        System.out.println("  Cache size: " + LeakyCache.size());
        System.out.println("  Even if references are removed locally, static collection keeps them alive\n");
    }
    
    private static void demonstrateListenerLeak() {
        System.out.println("2. Listener/Observer Leak (PROBLEM):");
        System.out.println("  Registering listeners without unregistering...\n");
        
        EventSource eventSource = new EventSource();
        
        for (int i = 1; i <= 1000; i++) {
            EventListener listener = new EventListener("Listener-" + i);
            eventSource.addListener(listener);
            // listener goes out of scope but still referenced by eventSource
            
            if (i % 100 == 0) {
                displayMemory("Registered " + i + " listeners");
            }
        }
        
        System.out.println("  ❌ " + eventSource.getListenerCount() + " listeners still registered!");
        System.out.println("  Memory leaked because listeners were never removed\n");
    }
    
    private static void demonstrateUnclosedResourceLeak() {
        System.out.println("3. Unclosed Resources Leak (PROBLEM):");
        System.out.println("  Opening connections without closing...\n");
        
        for (int i = 1; i <= 100; i++) {
            // Creating connection but not closing it
            DatabaseConnection conn = new DatabaseConnection("DB-" + i);
            conn.connect();
            // conn is not closed - resource leak!
            
            if (i % 20 == 0) {
                displayMemory("Opened " + i + " connections");
            }
        }
        
        System.out.println("  ❌ Resources not properly closed!");
        System.out.println("  Active connections: " + DatabaseConnection.getActiveConnections() + "\n");
    }
    
    private static void demonstrateProperMemoryManagement() {
        System.out.println("4. Proper Memory Management (SOLUTION):");
        
        // Use try-with-resources
        System.out.println("  Using try-with-resources for auto-cleanup:");
        for (int i = 1; i <= 100; i++) {
            try (ProperDatabaseConnection conn = new ProperDatabaseConnection("DB-" + i)) {
                conn.connect();
                // Automatically closed when exiting try block
            }
            
            if (i % 20 == 0) {
                displayMemory("Processed " + i + " connections");
            }
        }
        
        System.out.println("  ✅ All resources properly closed!");
        System.out.println("  Active connections: " + ProperDatabaseConnection.getActiveConnections());
        
        // Clear collections when no longer needed
        System.out.println("\n  Clearing cache when done:");
        LeakyCache.clear();
        System.gc();
        
        displayMemory("After cleanup");
        System.out.println("  ✅ Memory properly managed!\n");
    }
    
    private static void displayMemory(String label) {
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
        System.out.println("  [" + label + "] Used Memory: " + usedMemory + " MB");
    }
}

// PROBLEM: Static collection keeps references forever
class LeakyCache {
    private static final List<User> cache = new ArrayList<>();
    
    public static void addUser(User user) {
        cache.add(user);
    }
    
    public static int size() {
        return cache.size();
    }
    
    public static void clear() {
        cache.clear();
    }
}

class User {
    private final String name;
    private final int id;
    private final byte[] data = new byte[1024 * 10]; // 10 KB each
    
    public User(String name, int id) {
        this.name = name;
        this.id = id;
    }
}

// PROBLEM: Listeners not removed
class EventSource {
    private final List<EventListener> listeners = new ArrayList<>();
    
    public void addListener(EventListener listener) {
        listeners.add(listener);
    }
    
    public int getListenerCount() {
        return listeners.size();
    }
}

class EventListener {
    private final String name;
    private final byte[] data = new byte[1024 * 5]; // 5 KB each
    
    public EventListener(String name) {
        this.name = name;
    }
}

// PROBLEM: Resources not closed
class DatabaseConnection {
    private static int activeConnections = 0;
    private final String name;
    
    public DatabaseConnection(String name) {
        this.name = name;
    }
    
    public void connect() {
        activeConnections++;
    }
    
    public static int getActiveConnections() {
        return activeConnections;
    }
}

// SOLUTION: Implements AutoCloseable
class ProperDatabaseConnection implements AutoCloseable {
    private static int activeConnections = 0;
    private final String name;
    
    public ProperDatabaseConnection(String name) {
        this.name = name;
    }
    
    public void connect() {
        activeConnections++;
    }
    
    @Override
    public void close() {
        activeConnections--;
    }
    
    public static int getActiveConnections() {
        return activeConnections;
    }
}
