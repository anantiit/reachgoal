package org.example.javaconcurrancy.concurrency.basics;

/**
 * Demo 2: Thread Coordination - join(), sleep(), yield()
 * 
 * KEY CONCEPTS:
 * - Thread.join() - waiting for thread completion
 * - Thread.sleep() - pausing execution
 * - Thread.yield() - giving up CPU time
 * - Daemon vs User threads
 */
public class ThreadCoordinationDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Thread Coordination Demo ===\n");
        
        // Demo 1: join() - Wait for thread completion
        demonstrateJoin();
        
        // Demo 2: Daemon threads
        demonstrateDaemonThreads();
        
        // Demo 3: Thread priorities
        demonstratePriorities();
        
        System.out.println("\n=== Coordination demo completed ===");
    }
    
    private static void demonstrateJoin() throws InterruptedException {
        System.out.println("1. Demonstrating join() - Sequential execution:");
        
        Thread task1 = new Thread(() -> {
            System.out.println("  Task 1: Starting data download...");
            sleep(1000);
            System.out.println("  Task 1: Download completed!");
        }, "Downloader");
        
        Thread task2 = new Thread(() -> {
            System.out.println("  Task 2: Processing downloaded data...");
            sleep(500);
            System.out.println("  Task 2: Processing completed!");
        }, "Processor");
        
        task1.start();
        task1.join(); // Wait for task1 to complete before starting task2
        
        task2.start();
        task2.join(); // Wait for task2 to complete
        
        System.out.println("  All tasks completed in sequence!\n");
    }
    
    private static void demonstrateDaemonThreads() throws InterruptedException {
        System.out.println("2. Demonstrating Daemon vs User Threads:");
        
        // User thread - JVM waits for it to complete
        Thread userThread = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println("  User thread working... " + i);
                sleep(300);
            }
            System.out.println("  User thread completed!");
        }, "UserThread");
        
        // Daemon thread - JVM doesn't wait for it
        Thread daemonThread = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("  Daemon thread working... " + i);
                sleep(300);
            }
            System.out.println("  Daemon thread completed (this may not print)!");
        }, "DaemonThread");
        
        daemonThread.setDaemon(true); // Must be set before start()
        
        System.out.println("  Starting both threads...");
        userThread.start();
        daemonThread.start();
        
        userThread.join();
        System.out.println("  Main thread exiting (daemon thread will be terminated)\n");
        Thread.sleep(100); // Small delay to see daemon thread behavior
    }
    
    private static void demonstratePriorities() throws InterruptedException {
        System.out.println("3. Demonstrating Thread Priorities:");
        System.out.println("  Priority Range: MIN=" + Thread.MIN_PRIORITY + 
                          ", NORM=" + Thread.NORM_PRIORITY + 
                          ", MAX=" + Thread.MAX_PRIORITY);
        
        Thread lowPriority = new Thread(() -> {
            long count = 0;
            while (count < 100_000_000) count++;
            System.out.println("  Low priority thread finished. Count: " + count);
        }, "LowPriority");
        lowPriority.setPriority(Thread.MIN_PRIORITY);
        
        Thread highPriority = new Thread(() -> {
            long count = 0;
            while (count < 100_000_000) count++;
            System.out.println("  High priority thread finished. Count: " + count);
        }, "HighPriority");
        highPriority.setPriority(Thread.MAX_PRIORITY);
        
        long startTime = System.currentTimeMillis();
        lowPriority.start();
        highPriority.start();
        
        lowPriority.join();
        highPriority.join();
        
        long endTime = System.currentTimeMillis();
        System.out.println("  Total execution time: " + (endTime - startTime) + "ms");
        System.out.println("  NOTE: Priority is just a hint to OS scheduler\n");
    }
    
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
