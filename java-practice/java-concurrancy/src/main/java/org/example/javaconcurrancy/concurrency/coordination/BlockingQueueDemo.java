package org.example.javaconcurrancy.concurrency.coordination;

import java.util.concurrent.*;

/**
 * Demo 14: BlockingQueue - Producer-Consumer Pattern
 * 
 * KEY CONCEPTS:
 * - Thread-safe queue with blocking operations
 * - put() - blocks if queue is full
 * - take() - blocks if queue is empty
 * - Perfect for producer-consumer scenarios
 * - Different implementations: ArrayBlockingQueue, LinkedBlockingQueue, PriorityBlockingQueue
 */
public class BlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== BlockingQueue Demo ===\n");
        
        // Demo 1: Producer-Consumer pattern
        demonstrateProducerConsumer();
        
        // Demo 2: Task queue with multiple workers
        demonstrateTaskQueue();
        
        System.out.println("\n=== BlockingQueue demo completed ===");
    }
    
    private static void demonstrateProducerConsumer() throws InterruptedException {
        System.out.println("1. Producer-Consumer Pattern:");
        
        // Bounded queue with capacity of 5
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
        
        // Producer thread
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    String item = "Item-" + i;
                    System.out.println("  Producer: Creating " + item);
                    queue.put(item); // Blocks if queue is full
                    System.out.println("  Producer: Added " + item + 
                                      " (Queue size: " + queue.size() + ")");
                    Thread.sleep(300);
                }
                queue.put("DONE"); // Signal completion
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");
        
        // Consumer thread
        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    String item = queue.take(); // Blocks if queue is empty
                    
                    if ("DONE".equals(item)) {
                        System.out.println("  Consumer: Received completion signal");
                        break;
                    }
                    
                    System.out.println("  Consumer: Processing " + item);
                    Thread.sleep(500); // Slower than producer
                    System.out.println("  Consumer: Finished " + item);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");
        
        producer.start();
        consumer.start();
        
        producer.join();
        consumer.join();
        
        System.out.println("  ✅ Producer-Consumer completed\n");
    }
    
    private static void demonstrateTaskQueue() throws InterruptedException {
        System.out.println("2. Task Queue with Multiple Workers:");
        
        BlockingQueue<Task> taskQueue = new LinkedBlockingQueue<>();
        
        // Create 3 worker threads
        Thread[] workers = new Thread[3];
        for (int i = 1; i <= 3; i++) {
            final int workerId = i;
            workers[i - 1] = new Thread(() -> {
                try {
                    while (true) {
                        Task task = taskQueue.take();
                        
                        if (task.isPoison()) {
                            System.out.println("  Worker-" + workerId + " received poison pill - shutting down");
                            taskQueue.put(task); // Pass poison pill to other workers
                            break;
                        }
                        
                        System.out.println("  Worker-" + workerId + " processing: " + task.getName());
                        Thread.sleep(task.getDuration());
                        System.out.println("  Worker-" + workerId + " completed: " + task.getName());
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Worker-" + i);
            workers[i - 1].start();
        }
        
        // Submit tasks
        System.out.println("  Submitting 10 tasks...\n");
        for (int i = 1; i <= 10; i++) {
            taskQueue.put(new Task("Task-" + i, (long) (Math.random() * 1000 + 500)));
            Thread.sleep(100);
        }
        
        // Send poison pill to stop workers
        Thread.sleep(1000);
        taskQueue.put(Task.POISON_PILL);
        
        // Wait for workers to finish
        for (Thread worker : workers) {
            worker.join();
        }
        
        System.out.println("\n  ✅ All tasks processed by worker pool\n");
    }
}

class Task {
    private final String name;
    private final long duration;
    public static final Task POISON_PILL = new Task("POISON", 0);
    
    public Task(String name, long duration) {
        this.name = name;
        this.duration = duration;
    }
    
    public String getName() {
        return name;
    }
    
    public long getDuration() {
        return duration;
    }
    
    public boolean isPoison() {
        return this == POISON_PILL;
    }
}
