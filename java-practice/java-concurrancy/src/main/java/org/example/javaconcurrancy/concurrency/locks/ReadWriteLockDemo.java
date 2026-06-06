package org.example.javaconcurrancy.concurrency.locks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Demo 6: ReadWriteLock - Optimized for Read-Heavy Scenarios
 * 
 * KEY CONCEPTS:
 * - Read lock - multiple threads can hold simultaneously
 * - Write lock - exclusive, blocks all other reads and writes
 * - Perfect for caches and read-heavy data structures
 * - Significant performance improvement over regular locks
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== ReadWriteLock Demo ===\n");
        
        // Demo: Cache with read/write lock
        demonstrateReadWriteLock();
        
        System.out.println("\n=== ReadWriteLock demo completed ===");
    }
    
    private static void demonstrateReadWriteLock() throws InterruptedException {
        System.out.println("Comparing Regular Lock vs ReadWriteLock:\n");
        
        // Test with regular lock
        CacheWithRegularLock regularCache = new CacheWithRegularLock();
        long regularTime = testCache(regularCache, "Regular Lock");
        
        System.out.println();
        
        // Test with ReadWriteLock
        CacheWithReadWriteLock rwCache = new CacheWithReadWriteLock();
        long rwTime = testCache(rwCache, "ReadWrite Lock");
        
        System.out.println("\n=== Performance Comparison ===");
        System.out.println("Regular Lock time: " + regularTime + "ms");
        System.out.println("ReadWrite Lock time: " + rwTime + "ms");
        System.out.println("Improvement: " + ((regularTime - rwTime) * 100.0 / regularTime) + "%");
        System.out.println("\n✓ ReadWriteLock is much faster for read-heavy workloads!");
    }
    
    private static long testCache(Object cache, String lockType) throws InterruptedException {
        System.out.println("Testing " + lockType + ":");
        
        // Create 8 reader threads and 2 writer threads
        Thread[] threads = new Thread[10];
        
        long startTime = System.currentTimeMillis();
        
        // 8 readers
        for (int i = 0; i < 8; i++) {
            final int readerId = i + 1;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    if (cache instanceof CacheWithRegularLock) {
                        ((CacheWithRegularLock) cache).get("user" + (j % 3));
                    } else {
                        ((CacheWithReadWriteLock) cache).get("user" + (j % 3));
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }, "Reader-" + readerId);
        }
        
        // 2 writers
        for (int i = 8; i < 10; i++) {
            final int writerId = i - 7;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 2; j++) {
                    if (cache instanceof CacheWithRegularLock) {
                        ((CacheWithRegularLock) cache).put("user" + j, "Data-" + j);
                    } else {
                        ((CacheWithReadWriteLock) cache).put("user" + j, "Data-" + j);
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }, "Writer-" + writerId);
        }
        
        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Wait for completion
        for (Thread thread : threads) {
            thread.join();
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("  Completed in " + (endTime - startTime) + "ms");
        
        return endTime - startTime;
    }
}

// Cache with regular ReentrantLock - slower for read-heavy workloads
class CacheWithRegularLock {
    private final Map<String, String> cache = new HashMap<>();
    private final java.util.concurrent.locks.Lock lock = new java.util.concurrent.locks.ReentrantLock();
    
    public String get(String key) {
        lock.lock();
        try {
            // Simulate some processing
            Thread.sleep(50);
            return cache.get(key);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            lock.unlock();
        }
    }
    
    public void put(String key, String value) {
        lock.lock();
        try {
            System.out.println("  [Regular] " + Thread.currentThread().getName() + 
                              " writing: " + key);
            Thread.sleep(100);
            cache.put(key, value);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}

// Cache with ReadWriteLock - much faster for read-heavy workloads
class CacheWithReadWriteLock {
    private final Map<String, String> cache = new HashMap<>();
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    
    public String get(String key) {
        rwLock.readLock().lock(); // Multiple readers can acquire this simultaneously
        try {
            // Simulate some processing
            Thread.sleep(50);
            return cache.get(key);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            rwLock.readLock().unlock();
        }
    }
    
    public void put(String key, String value) {
        rwLock.writeLock().lock(); // Exclusive write access
        try {
            System.out.println("  [ReadWrite] " + Thread.currentThread().getName() + 
                              " writing: " + key);
            Thread.sleep(100);
            cache.put(key, value);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}
