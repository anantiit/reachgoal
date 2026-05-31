package org.example.javaconcurrancy.concurrency.coordination;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Demo 11: CountDownLatch - Wait for Multiple Threads
 * 
 * KEY CONCEPTS:
 * - Allows one or more threads to wait until operations in other threads complete
 * - Count is decremented by threads calling countDown()
 * - Waiting threads proceed when count reaches zero
 * - One-time use only (cannot be reset)
 * - Perfect for parallel initialization or startup coordination
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== CountDownLatch Demo ===\n");
        
        // Demo 1: Service startup coordination
        demonstrateServiceStartup();
        
        // Demo 2: Parallel task completion
        demonstrateParallelTasks();
        
        System.out.println("\n=== CountDownLatch demo completed ===");
    }
    
    private static void demonstrateServiceStartup() throws InterruptedException {
        System.out.println("1. Service Startup Coordination:");
        System.out.println("  Waiting for all services to start...\n");
        
        // Create latch with count = 3 (3 services)
        CountDownLatch startupLatch = new CountDownLatch(3);
        
        // Database service
        new Thread(new Service("Database", 2000, startupLatch)).start();
        
        // Cache service
        new Thread(new Service("Cache", 1500, startupLatch)).start();
        
        // API service
        new Thread(new Service("API", 1000, startupLatch)).start();
        
        // Main thread waits for all services
        System.out.println("  Main: Waiting for all services to be ready...");
        startupLatch.await(); // Blocks until count reaches 0
        
        System.out.println("\n  ✅ All services started! Application is ready.\n");
    }
    
    private static void demonstrateParallelTasks() throws InterruptedException {
        System.out.println("2. Parallel Task Completion:");
        System.out.println("  Processing multiple files in parallel...\n");
        
        int numberOfFiles = 5;
        CountDownLatch completionLatch = new CountDownLatch(numberOfFiles);
        
        // Start all file processing tasks
        for (int i = 1; i <= numberOfFiles; i++) {
            final int fileNum = i;
            new Thread(() -> {
                processFile(fileNum);
                completionLatch.countDown(); // Signal completion
            }, "FileProcessor-" + i).start();
        }
        
        System.out.println("  Main thread doing other work...");
        
        // Wait with timeout
        boolean completed = completionLatch.await(5, TimeUnit.SECONDS);
        
        if (completed) {
            System.out.println("\n  ✅ All files processed successfully!");
        } else {
            System.out.println("\n  ⚠️ Timeout - some files still processing");
        }
        
        System.out.println("  Remaining count: " + completionLatch.getCount() + "\n");
    }
    
    private static void processFile(int fileNum) {
        try {
            System.out.println("  Processing file-" + fileNum + "...");
            Thread.sleep(500 + (fileNum * 200)); // Variable processing time
            System.out.println("  File-" + fileNum + " completed!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Service implements Runnable {
    private final String name;
    private final long startupTime;
    private final CountDownLatch latch;
    
    public Service(String name, long startupTime, CountDownLatch latch) {
        this.name = name;
        this.startupTime = startupTime;
        this.latch = latch;
    }
    
    @Override
    public void run() {
        try {
            System.out.println("  " + name + " service starting...");
            Thread.sleep(startupTime); // Simulate startup time
            System.out.println("  " + name + " service ready!");
            latch.countDown(); // Signal that this service is ready
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
