package com.example.java_collections.collections;

import java.util.*;
import java.util.concurrent.*;

/**
 * Queue and Deque implementations: PriorityQueue, ArrayDeque, LinkedList
 * Blocking queues for concurrent programming
 */
public class QueueExamples {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== QUEUE & DEQUE IMPLEMENTATIONS ===\n");
        
        demonstratePriorityQueue();
        demonstrateArrayDeque();
        demonstrateLinkedListAsDeque();
        compareDequePerformance();
        demonstrateBlockingQueue();
        demonstratePriorityBlockingQueue();
        demonstrateDelayQueue();
    }
    
    private static void demonstratePriorityQueue() {
        System.out.println("1. PriorityQueue - Min Heap by Default");
        System.out.println("   - Binary heap implementation");
        System.out.println("   - O(log n) insertion, O(1) peek, O(log n) poll");
        System.out.println("   - Not thread-safe, no null elements");
        
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.addAll(Arrays.asList(50, 20, 80, 10, 30, 60));
        
        System.out.println("   - Added: 50, 20, 80, 10, 30, 60");
        System.out.print("   - Poll order (ascending): ");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.poll() + " ");
        }
        System.out.println();
        
        // Max heap with custom comparator
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        maxHeap.addAll(Arrays.asList(50, 20, 80, 10, 30, 60));
        
        System.out.print("   - Max heap order (descending): ");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.poll() + " ");
        }
        System.out.println("\n");
    }
    
    private static void demonstrateArrayDeque() {
        System.out.println("2. ArrayDeque - Resizable Array Double-Ended Queue");
        System.out.println("   - Faster than LinkedList for stack and queue operations");
        System.out.println("   - No capacity restrictions, grows dynamically");
        System.out.println("   - Not thread-safe, no null elements");
        
        Deque<String> deque = new ArrayDeque<>();
        
        // As a Stack (LIFO)
        deque.push("First");
        deque.push("Second");
        deque.push("Third");
        System.out.println("   - Stack operations: " + deque);
        System.out.println("   - Pop: " + deque.pop());
        
        // As a Queue (FIFO)
        deque.clear();
        deque.offer("A");
        deque.offer("B");
        deque.offer("C");
        System.out.println("   - Queue operations: " + deque);
        System.out.println("   - Poll: " + deque.poll());
        
        // Deque specific operations
        deque.clear();
        deque.addFirst("Start");
        deque.addLast("End");
        deque.addFirst("New Start");
        deque.addLast("New End");
        System.out.println("   - Deque: " + deque);
        System.out.println("   - peekFirst: " + deque.peekFirst());
        System.out.println("   - peekLast: " + deque.peekLast() + "\n");
    }
    
    private static void demonstrateLinkedListAsDeque() {
        System.out.println("3. LinkedList as Deque");
        System.out.println("   - Implements both List and Deque");
        System.out.println("   - Better for frequent insertion/removal at both ends");
        System.out.println("   - Allows null elements (unlike ArrayDeque)");
        
        Deque<Integer> linkedDeque = new LinkedList<>();
        linkedDeque.offerFirst(2);
        linkedDeque.offerFirst(1);
        linkedDeque.offerLast(3);
        linkedDeque.offerLast(4);
        
        System.out.println("   - Deque: " + linkedDeque);
        System.out.println("   - Remove first: " + linkedDeque.pollFirst());
        System.out.println("   - Remove last: " + linkedDeque.pollLast());
        System.out.println("   - Remaining: " + linkedDeque + "\n");
    }
    
    private static void compareDequePerformance() {
        System.out.println("4. ArrayDeque vs LinkedList Performance");
        
        int iterations = 100_000;
        
        Deque<Integer> arrayDeque = new ArrayDeque<>();
        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            arrayDeque.addFirst(i);
            arrayDeque.addLast(i);
        }
        long arrayDequeTime = System.nanoTime() - start;
        
        Deque<Integer> linkedDeque = new LinkedList<>();
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            linkedDeque.addFirst(i);
            linkedDeque.addLast(i);
        }
        long linkedDequeTime = System.nanoTime() - start;
        
        System.out.printf("   ArrayDeque:  %,d ms%n", TimeUnit.NANOSECONDS.toMillis(arrayDequeTime));
        System.out.printf("   LinkedList:  %,d ms%n", TimeUnit.NANOSECONDS.toMillis(linkedDequeTime));
        System.out.printf("   ArrayDeque is %.2fx faster%n%n", 
            (double) linkedDequeTime / arrayDequeTime);
    }
    
    private static void demonstrateBlockingQueue() throws InterruptedException {
        System.out.println("5. BlockingQueue - Thread-Safe Producer-Consumer");
        System.out.println("   - put() blocks when queue is full");
        System.out.println("   - take() blocks when queue is empty");
        
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        
        // Producer thread
        Thread producer = new Thread(() -> {
            try {
                queue.put("Item1");
                queue.put("Item2");
                queue.put("Item3");
                System.out.println("   - Producer: Added 3 items");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        // Consumer thread
        Thread consumer = new Thread(() -> {
            try {
                Thread.sleep(100);
                System.out.println("   - Consumer: " + queue.take());
                System.out.println("   - Consumer: " + queue.take());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        
        System.out.println("   - Remaining in queue: " + queue.size() + "\n");
    }
    
    private static void demonstratePriorityBlockingQueue() {
        System.out.println("6. PriorityBlockingQueue - Thread-Safe Priority Queue");
        System.out.println("   - Unbounded, blocking queue");
        System.out.println("   - Orders elements by natural ordering or Comparator");
        
        PriorityBlockingQueue<Task> taskQueue = new PriorityBlockingQueue<>();
        taskQueue.add(new Task("Low priority", 3));
        taskQueue.add(new Task("High priority", 1));
        taskQueue.add(new Task("Medium priority", 2));
        
        System.out.println("   - Tasks processed by priority:");
        while (!taskQueue.isEmpty()) {
            System.out.println("     " + taskQueue.poll());
        }
        System.out.println();
    }
    
    private static void demonstrateDelayQueue() throws InterruptedException {
        System.out.println("7. DelayQueue - Elements Available After Delay");
        System.out.println("   - Unbounded blocking queue of Delayed elements");
        System.out.println("   - Elements can only be taken when their delay has expired");
        
        DelayQueue<DelayedTask> delayQueue = new DelayQueue<>();
        long now = System.currentTimeMillis();
        
        delayQueue.put(new DelayedTask("Task1", now + 200));
        delayQueue.put(new DelayedTask("Task2", now + 100));
        delayQueue.put(new DelayedTask("Task3", now + 300));
        
        System.out.println("   - Added tasks with delays: 200ms, 100ms, 300ms");
        System.out.println("   - Taking tasks (blocks until delay expires):");
        
        for (int i = 0; i < 3; i++) {
            DelayedTask task = delayQueue.take();
            System.out.println("     " + task.name + " executed");
        }
        
        System.out.println("\n=== KEY TAKEAWAY ===");
        System.out.println("PriorityQueue: Heap-based, priority ordering, not thread-safe");
        System.out.println("ArrayDeque: Fast stack/queue, better than LinkedList for most uses");
        System.out.println("BlockingQueue: Thread-safe, producer-consumer patterns");
        System.out.println("DelayQueue: Scheduled tasks, elements available after delay");
    }
    
    static class Task implements Comparable<Task> {
        String name;
        int priority;
        
        Task(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }
        
        @Override
        public int compareTo(Task other) {
            return Integer.compare(this.priority, other.priority);
        }
        
        @Override
        public String toString() {
            return name + " (priority: " + priority + ")";
        }
    }
    
    static class DelayedTask implements Delayed {
        String name;
        long executeTime;
        
        DelayedTask(String name, long executeTime) {
            this.name = name;
            this.executeTime = executeTime;
        }
        
        @Override
        public long getDelay(TimeUnit unit) {
            long diff = executeTime - System.currentTimeMillis();
            return unit.convert(diff, TimeUnit.MILLISECONDS);
        }
        
        @Override
        public int compareTo(Delayed other) {
            return Long.compare(this.executeTime, ((DelayedTask) other).executeTime);
        }
    }
}
