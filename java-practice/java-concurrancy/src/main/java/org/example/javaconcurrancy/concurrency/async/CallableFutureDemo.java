package org.example.javaconcurrancy.concurrency.async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Demo 8: Callable and Future - Async Tasks with Return Values
 * 
 * KEY CONCEPTS:
 * - Callable - like Runnable but can return value and throw exceptions
 * - Future - represents result of async computation
 * - get() - blocks until result is available
 * - get(timeout) - blocks with timeout
 * - isDone() and cancel() for task management
 */
public class CallableFutureDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("=== Callable and Future Demo ===\n");
        
        // Demo 1: Single Callable task
        demonstrateSingleCallable();
        
        // Demo 2: Multiple Callable tasks
        demonstrateMultipleCallables();
        
        // Demo 3: Future timeout and cancellation
        demonstrateFutureControl();
        
        System.out.println("\n=== Callable/Future demo completed ===");
    }
    
    private static void demonstrateSingleCallable() throws InterruptedException, ExecutionException {
        System.out.println("1. Single Callable Task:");
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        
        // Callable returns a value (unlike Runnable)
        Callable<Integer> sumTask = () -> {
            System.out.println("  Computing sum of 1 to 100...");
            Thread.sleep(1000); // Simulate computation
            int sum = 0;
            for (int i = 1; i <= 100; i++) {
                sum += i;
            }
            return sum;
        };
        
        System.out.println("  Submitting task...");
        Future<Integer> future = executor.submit(sumTask);
        
        System.out.println("  Task submitted. Doing other work...");
        Thread.sleep(500);
        
        System.out.println("  Now waiting for result...");
        Integer result = future.get(); // Blocks until result is available
        
        System.out.println("  Result: " + result);
        System.out.println("  ✓ Callable returned a value!\n");
        
        executor.shutdown();
    }
    
    private static void demonstrateMultipleCallables() throws InterruptedException {
        System.out.println("2. Multiple Callable Tasks (Parallel Computation):");
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        List<Callable<String>> tasks = new ArrayList<>();
        
        // Create multiple tasks
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            tasks.add(() -> {
                System.out.println("  Task " + taskId + " processing...");
                Thread.sleep(500 + (taskId * 100)); // Variable processing time
                return "Result from Task " + taskId;
            });
        }
        
        System.out.println("  Submitting 5 tasks in parallel...");
        long startTime = System.currentTimeMillis();
        
        // invokeAll executes all tasks and waits for all to complete
        List<Future<String>> futures = executor.invokeAll(tasks);
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("\n  All tasks completed in " + (endTime - startTime) + "ms");
        System.out.println("  Results:");
        
        for (Future<String> future : futures) {
            try {
                System.out.println("    - " + future.get());
            } catch (ExecutionException e) {
                System.out.println("    - Task failed: " + e.getMessage());
            }
        }
        
        System.out.println("  ✓ Multiple tasks executed in parallel!\n");
        
        executor.shutdown();
    }
    
    private static void demonstrateFutureControl() throws InterruptedException {
        System.out.println("3. Future Control - Timeout and Cancellation:");
        
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        // Task that takes long time
        Future<String> longTask = executor.submit(() -> {
            System.out.println("  Long task started...");
            Thread.sleep(5000);
            return "Long task completed";
        });
        
        // Task that will be cancelled
        Future<String> cancellableTask = executor.submit(() -> {
            System.out.println("  Cancellable task started...");
            Thread.sleep(10000);
            return "This won't be returned";
        });
        
        // Check if done
        System.out.println("  Is long task done? " + longTask.isDone());
        
        // Try to get result with timeout
        try {
            String result = longTask.get(2, TimeUnit.SECONDS);
            System.out.println("  Result: " + result);
        } catch (TimeoutException e) {
            System.out.println("  ❌ Timeout! Task took too long.");
        } catch (ExecutionException e) {
            System.out.println("  ❌ Task failed: " + e.getMessage());
        }
        
        // Cancel a task
        System.out.println("\n  Cancelling the cancellable task...");
        boolean cancelled = cancellableTask.cancel(true); // true = interrupt if running
        System.out.println("  Task cancelled? " + cancelled);
        System.out.println("  Is task cancelled? " + cancellableTask.isCancelled());
        
        try {
            cancellableTask.get();
        } catch (CancellationException e) {
            System.out.println("  ✓ Confirmed: Task was cancelled\n");
        } catch (ExecutionException e) {
            // Won't reach here
        }
        
        executor.shutdownNow(); // Force shutdown
    }
}
