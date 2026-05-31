package org.example.javaconcurrancy.concurrency.synchronization;

/**
 * Demo 3: Race Condition - The Problem and Solution
 * 
 * KEY CONCEPTS:
 * - Race condition occurs when multiple threads access shared data
 * - Non-atomic operations can lead to incorrect results
 * - synchronized keyword ensures thread safety
 * - Demonstrates both WRONG and CORRECT approaches
 */
public class RaceConditionDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Race Condition Demo ===\n");
        
        // Demo 1: PROBLEM - Race condition without synchronization
        demonstrateRaceCondition();
        
        // Demo 2: SOLUTION - Fixed with synchronization
        demonstrateSynchronizedSolution();
        
        System.out.println("\n=== Race condition demo completed ===");
    }
    
    private static void demonstrateRaceCondition() throws InterruptedException {
        System.out.println("1. PROBLEM: Race Condition (WRONG approach)");
        
        UnsafeCounter unsafeCounter = new UnsafeCounter();
        
        // Create 10 threads, each incrementing counter 1000 times
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    unsafeCounter.increment();
                }
            }, "UnsafeThread-" + i);
        }
        
        // Start all threads
        long startTime = System.currentTimeMillis();
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }
        long endTime = System.currentTimeMillis();
        
        System.out.println("  Expected count: 10,000");
        System.out.println("  Actual count: " + unsafeCounter.getCount());
        System.out.println("  Data lost: " + (10000 - unsafeCounter.getCount()));
        System.out.println("  Time taken: " + (endTime - startTime) + "ms");
        System.out.println("  ❌ THIS IS WRONG! Race condition caused data loss.\n");
    }
    
    private static void demonstrateSynchronizedSolution() throws InterruptedException {
        System.out.println("2. SOLUTION: Synchronized Counter (CORRECT approach)");
        
        SafeCounter safeCounter = new SafeCounter();
        
        // Create 10 threads, each incrementing counter 1000 times
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    safeCounter.increment();
                }
            }, "SafeThread-" + i);
        }
        
        // Start all threads
        long startTime = System.currentTimeMillis();
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }
        long endTime = System.currentTimeMillis();
        
        System.out.println("  Expected count: 10,000");
        System.out.println("  Actual count: " + safeCounter.getCount());
        System.out.println("  Data lost: " + (10000 - safeCounter.getCount()));
        System.out.println("  Time taken: " + (endTime - startTime) + "ms");
        System.out.println("  ✓ CORRECT! Synchronization prevented race condition.\n");
    }
}

// UNSAFE - Race condition will occur
class UnsafeCounter {
    private int count = 0;
    
    // NOT thread-safe: increment is not atomic (read-modify-write)
    public void increment() {
        count++; // This is actually 3 operations: read, increment, write
    }
    
    public int getCount() {
        return count;
    }
}

// SAFE - Synchronized to prevent race condition
class SafeCounter {
    private int count = 0;
    
    // Thread-safe: synchronized ensures only one thread at a time
    public synchronized void increment() {
        count++;
    }
    
    public synchronized int getCount() {
        return count;
    }
}
