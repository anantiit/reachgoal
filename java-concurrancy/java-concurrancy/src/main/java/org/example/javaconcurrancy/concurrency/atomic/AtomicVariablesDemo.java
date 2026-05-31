package org.example.javaconcurrancy.concurrency.atomic;

import java.util.concurrent.atomic.*;

/**
 * Demo 10: Atomic Variables - Lock-free Thread Safety
 * 
 * KEY CONCEPTS:
 * - Lock-free thread-safe operations
 * - Better performance than synchronized for simple operations
 * - AtomicInteger, AtomicLong, AtomicBoolean, AtomicReference
 * - Compare-and-swap (CAS) operations
 * - Perfect for counters, flags, and simple state management
 */
public class AtomicVariablesDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Atomic Variables Demo ===\n");
        
        // Demo 1: AtomicInteger vs regular int
        demonstrateAtomicInteger();
        
        // Demo 2: Various atomic operations
        demonstrateAtomicOperations();
        
        // Demo 3: AtomicReference
        demonstrateAtomicReference();
        
        // Demo 4: Performance comparison
        demonstratePerformance();
        
        System.out.println("\n=== Atomic variables demo completed ===");
    }
    
    private static void demonstrateAtomicInteger() throws InterruptedException {
        System.out.println("1. AtomicInteger - Thread-safe Counter:");
        
        AtomicInteger atomicCounter = new AtomicInteger(0);
        
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    atomicCounter.incrementAndGet(); // Thread-safe increment
                }
            });
        }
        
        for (Thread thread : threads) {
            thread.start();
        }
        
        for (Thread thread : threads) {
            thread.join();
        }
        
        System.out.println("  Expected: 10,000");
        System.out.println("  Actual: " + atomicCounter.get());
        System.out.println("  ✓ No race condition - lock-free thread safety!\n");
    }
    
    private static void demonstrateAtomicOperations() {
        System.out.println("2. Various Atomic Operations:");
        
        AtomicInteger value = new AtomicInteger(10);
        
        System.out.println("  Initial value: " + value.get());
        
        // Increment and get
        System.out.println("  incrementAndGet(): " + value.incrementAndGet()); // 11
        
        // Get and increment
        System.out.println("  getAndIncrement(): " + value.getAndIncrement()); // 11, then becomes 12
        System.out.println("  Current value: " + value.get()); // 12
        
        // Add and get
        System.out.println("  addAndGet(5): " + value.addAndGet(5)); // 17
        
        // Compare and set (CAS operation)
        boolean updated = value.compareAndSet(17, 100);
        System.out.println("  compareAndSet(17, 100): " + updated); // true
        System.out.println("  Current value: " + value.get()); // 100
        
        // Try to update with wrong expected value
        updated = value.compareAndSet(50, 200);
        System.out.println("  compareAndSet(50, 200): " + updated); // false
        System.out.println("  Current value: " + value.get()); // Still 100
        
        System.out.println("  ✓ CAS ensures atomic update only if value hasn't changed\n");
    }
    
    private static void demonstrateAtomicReference() throws InterruptedException {
        System.out.println("3. AtomicReference - Thread-safe Object Reference:");
        
        AtomicReference<UserStatus> statusRef = new AtomicReference<>(
            new UserStatus("John", "OFFLINE")
        );
        
        System.out.println("  Initial: " + statusRef.get());
        
        // Multiple threads trying to update status
        Thread loginThread = new Thread(() -> {
            UserStatus current = statusRef.get();
            UserStatus updated = new UserStatus(current.username, "ONLINE");
            if (statusRef.compareAndSet(current, updated)) {
                System.out.println("  Login thread: Updated status to ONLINE");
            }
        });
        
        Thread logoutThread = new Thread(() -> {
            sleep(50); // Slight delay
            UserStatus current = statusRef.get();
            UserStatus updated = new UserStatus(current.username, "OFFLINE");
            if (statusRef.compareAndSet(current, updated)) {
                System.out.println("  Logout thread: Updated status to OFFLINE");
            }
        });
        
        loginThread.start();
        logoutThread.start();
        
        loginThread.join();
        logoutThread.join();
        
        System.out.println("  Final: " + statusRef.get());
        System.out.println("  ✓ AtomicReference ensures thread-safe object updates\n");
    }
    
    private static void demonstratePerformance() throws InterruptedException {
        System.out.println("4. Performance Comparison - Atomic vs Synchronized:");
        
        // Test with AtomicInteger
        AtomicInteger atomicCounter = new AtomicInteger(0);
        long atomicTime = testCounter(() -> {
            for (int i = 0; i < 100000; i++) {
                atomicCounter.incrementAndGet();
            }
        }, "Atomic");
        
        // Test with synchronized
        SynchronizedCounter syncCounter = new SynchronizedCounter();
        long syncTime = testCounter(() -> {
            for (int i = 0; i < 100000; i++) {
                syncCounter.increment();
            }
        }, "Synchronized");
        
        System.out.println("\n  Performance Results:");
        System.out.println("  Atomic time: " + atomicTime + "ms");
        System.out.println("  Synchronized time: " + syncTime + "ms");
        System.out.println("  Speedup: " + (syncTime * 100.0 / atomicTime - 100) + "%");
        System.out.println("  ✓ Atomic is faster for simple operations!\n");
    }
    
    private static long testCounter(Runnable task, String name) throws InterruptedException {
        Thread[] threads = new Thread[10];
        
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(task);
            threads[i].start();
        }
        
        for (Thread thread : threads) {
            thread.join();
        }
        
        return System.currentTimeMillis() - startTime;
    }
    
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class UserStatus {
    final String username;
    final String status;
    
    UserStatus(String username, String status) {
        this.username = username;
        this.status = status;
    }
    
    @Override
    public String toString() {
        return username + " is " + status;
    }
}

class SynchronizedCounter {
    private int count = 0;
    
    public synchronized void increment() {
        count++;
    }
}
