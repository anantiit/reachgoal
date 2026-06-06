package org.example.javaconcurrancy.concurrency.executors;

import java.util.concurrent.*;

/**
 * Demo 7: ExecutorService - Modern Thread Management
 * 
 * KEY CONCEPTS:
 * - Thread pools - reuse threads instead of creating new ones
 * - Different types: FixedThreadPool, CachedThreadPool, SingleThreadExecutor
 * - Much more efficient than manual thread creation
 * - Automatic resource management
 */
public class ExecutorServiceDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== ExecutorService Demo ===\n");
        
        // Demo 1: FixedThreadPool
        demonstrateFixedThreadPool();
        
        // Demo 2: CachedThreadPool
        demonstrateCachedThreadPool();
        
        // Demo 3: SingleThreadExecutor
        demonstrateSingleThreadExecutor();
        
        // Demo 4: ScheduledExecutorService
        demonstrateScheduledExecutor();
        
        System.out.println("\n=== ExecutorService demo completed ===");
    }
    
    private static void demonstrateFixedThreadPool() throws InterruptedException {
        System.out.println("1. FixedThreadPool - Fixed number of threads:");
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        System.out.println("  Submitting 10 tasks to pool of 3 threads...");
        
        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("  Task " + taskId + " started by " + 
                                  Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("  Task " + taskId + " completed");
            });
        }
        
        executor.shutdown(); // No new tasks accepted
        executor.awaitTermination(1, TimeUnit.MINUTES);
        
        System.out.println("  ✓ All tasks completed. Only 3 threads were used.\n");
    }
    
    private static void demonstrateCachedThreadPool() throws InterruptedException {
        System.out.println("2. CachedThreadPool - Creates threads as needed:");
        
        ExecutorService executor = Executors.newCachedThreadPool();
        
        System.out.println("  Submitting 5 quick tasks...");
        
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("  Quick task " + taskId + " by " + 
                                  Thread.currentThread().getName());
            });
        }
        
        Thread.sleep(100);
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        
        System.out.println("  ✓ CachedThreadPool creates threads on demand and reuses them.\n");
    }
    
    private static void demonstrateSingleThreadExecutor() throws InterruptedException {
        System.out.println("3. SingleThreadExecutor - Sequential execution:");
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        
        System.out.println("  Submitting tasks (will execute one at a time)...");
        
        for (int i = 1; i <= 4; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("  Task " + taskId + " executing...");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        
        System.out.println("  ✓ All tasks executed sequentially by single thread.\n");
    }
    
    private static void demonstrateScheduledExecutor() throws InterruptedException {
        System.out.println("4. ScheduledExecutorService - Delayed and periodic tasks:");
        
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        
        // One-time delayed task
        System.out.println("  Scheduling task to run after 1 second...");
        scheduler.schedule(() -> {
            System.out.println("  Delayed task executed!");
        }, 1, TimeUnit.SECONDS);
        
        // Periodic task
        System.out.println("  Scheduling periodic task (every 500ms)...");
        ScheduledFuture<?> periodicTask = scheduler.scheduleAtFixedRate(() -> {
            System.out.println("  Periodic task tick - " + System.currentTimeMillis());
        }, 0, 500, TimeUnit.MILLISECONDS);
        
        // Let it run for a while
        Thread.sleep(2500);
        
        // Cancel periodic task
        periodicTask.cancel(false);
        
        scheduler.shutdown();
        scheduler.awaitTermination(5, TimeUnit.SECONDS);
        
        System.out.println("  ✓ Scheduled tasks demonstrate delayed and periodic execution.\n");
    }
}
