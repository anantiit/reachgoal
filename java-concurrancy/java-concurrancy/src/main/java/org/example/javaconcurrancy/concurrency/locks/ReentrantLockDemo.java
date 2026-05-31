package org.example.javaconcurrancy.concurrency.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

/**
 * Demo 5: ReentrantLock - Advanced Locking Mechanism
 * 
 * KEY CONCEPTS:
 * - More flexible than synchronized
 * - tryLock() - attempt to acquire without blocking
 * - tryLock(timeout) - attempt with timeout
 * - Fair vs Non-fair locks
 * - Must manually unlock in finally block
 */
public class ReentrantLockDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== ReentrantLock Demo ===\n");
        
        // Demo 1: Basic lock usage
        demonstrateBasicLock();
        
        // Demo 2: tryLock - non-blocking attempt
        demonstrateTryLock();
        
        // Demo 3: tryLock with timeout
        demonstrateTryLockWithTimeout();
        
        // Demo 4: Fair vs Non-fair locks
        demonstrateFairLock();
        
        System.out.println("\n=== ReentrantLock demo completed ===");
    }
    
    private static void demonstrateBasicLock() throws InterruptedException {
        System.out.println("1. Basic ReentrantLock Usage:");
        
        PrintQueue printQueue = new PrintQueue();
        
        Thread[] threads = new Thread[3];
        for (int i = 0; i < 3; i++) {
            final int docNum = i + 1;
            threads[i] = new Thread(() -> {
                printQueue.printDocument("Document-" + docNum);
            }, "Printer-" + (i + 1));
        }
        
        for (Thread thread : threads) {
            thread.start();
        }
        
        for (Thread thread : threads) {
            thread.join();
        }
        
        System.out.println();
    }
    
    private static void demonstrateTryLock() throws InterruptedException {
        System.out.println("2. tryLock() - Non-blocking Attempt:");
        
        ResourceManager resource = new ResourceManager();
        
        Thread worker1 = new Thread(() -> resource.accessResourceWithTryLock(), "Worker-1");
        Thread worker2 = new Thread(() -> resource.accessResourceWithTryLock(), "Worker-2");
        
        worker1.start();
        Thread.sleep(50); // Ensure worker1 acquires lock first
        worker2.start();
        
        worker1.join();
        worker2.join();
        
        System.out.println();
    }
    
    private static void demonstrateTryLockWithTimeout() throws InterruptedException {
        System.out.println("3. tryLock(timeout) - Wait with Timeout:");
        
        DatabaseConnection db = new DatabaseConnection();
        
        Thread query1 = new Thread(() -> db.executeQuery("SELECT * FROM users"), "Query-1");
        Thread query2 = new Thread(() -> db.executeQuery("UPDATE users SET ..."), "Query-2");
        
        query1.start();
        Thread.sleep(50);
        query2.start();
        
        query1.join();
        query2.join();
        
        System.out.println();
    }
    
    private static void demonstrateFairLock() throws InterruptedException {
        System.out.println("4. Fair Lock - FIFO Thread Access:");
        
        FairLockExample fairExample = new FairLockExample();
        
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            final int threadNum = i + 1;
            threads[i] = new Thread(() -> {
                fairExample.accessResource(threadNum);
            }, "Thread-" + threadNum);
        }
        
        // Start all threads with small delays
        for (Thread thread : threads) {
            thread.start();
            Thread.sleep(10);
        }
        
        for (Thread thread : threads) {
            thread.join();
        }
        
        System.out.println("  ✓ Fair lock ensures FIFO order\n");
    }
}

class PrintQueue {
    private final Lock lock = new ReentrantLock();
    
    public void printDocument(String document) {
        lock.lock(); // Acquire lock
        try {
            System.out.println("  " + Thread.currentThread().getName() + 
                              " printing " + document);
            Thread.sleep(500); // Simulate printing
            System.out.println("  " + Thread.currentThread().getName() + 
                              " finished printing " + document);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock(); // MUST unlock in finally block
        }
    }
}

class ResourceManager {
    private final Lock lock = new ReentrantLock();
    
    public void accessResourceWithTryLock() {
        String threadName = Thread.currentThread().getName();
        
        if (lock.tryLock()) { // Try to acquire without blocking
            try {
                System.out.println("  " + threadName + " acquired lock!");
                Thread.sleep(1000);
                System.out.println("  " + threadName + " releasing lock");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("  " + threadName + " could NOT acquire lock - doing alternative work");
        }
    }
}

class DatabaseConnection {
    private final Lock lock = new ReentrantLock();
    
    public void executeQuery(String query) {
        String threadName = Thread.currentThread().getName();
        
        try {
            // Try to acquire lock with 2-second timeout
            if (lock.tryLock(2, TimeUnit.SECONDS)) {
                try {
                    System.out.println("  " + threadName + " executing: " + query);
                    Thread.sleep(1500);
                    System.out.println("  " + threadName + " query completed");
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("  " + threadName + " timeout - query cancelled");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class FairLockExample {
    private final Lock fairLock = new ReentrantLock(true); // true = fair lock
    
    public void accessResource(int threadNum) {
        fairLock.lock();
        try {
            System.out.println("  Thread-" + threadNum + " accessing resource (in order)");
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            fairLock.unlock();
        }
    }
}
