package org.example.javaconcurrancy.concurrency.basics;

/**
 * Demo 1: Thread Creation - Three Ways to Create Threads
 * 
 * KEY CONCEPTS:
 * - Thread class extension
 * - Runnable interface implementation
 * - Lambda expressions (Java 8+)
 * - Thread lifecycle states
 */
public class ThreadCreationDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Thread Creation Demo ===\n");
        
        // Method 1: Extending Thread class
        System.out.println("1. Creating thread by extending Thread class:");
        MyThread thread1 = new MyThread("Thread-1");
        thread1.start();
        
        // Method 2: Implementing Runnable interface
        System.out.println("\n2. Creating thread using Runnable interface:");
        Thread thread2 = new Thread(new MyRunnable("Thread-2"));
        thread2.start();
        
        // Method 3: Using Lambda (Modern approach - RECOMMENDED)
        System.out.println("\n3. Creating thread using Lambda:");
        Thread thread3 = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println("Lambda-Thread executing step " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Lambda-Thread");
        thread3.start();
        
        // Demonstrate thread states
        demonstrateThreadStates();
        
        // Wait for all threads to complete
        thread1.join();
        thread2.join();
        thread3.join();
        
        System.out.println("\n=== All threads completed ===");
    }
    
    private static void demonstrateThreadStates() throws InterruptedException {
        System.out.println("\n4. Thread States Demonstration:");
        
        Thread demoThread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "StateDemo");
        
        System.out.println("State after creation: " + demoThread.getState()); // NEW
        
        demoThread.start();
        System.out.println("State after start: " + demoThread.getState()); // RUNNABLE
        
        Thread.sleep(100);
        System.out.println("State while sleeping: " + demoThread.getState()); // TIMED_WAITING
        
        demoThread.join();
        System.out.println("State after completion: " + demoThread.getState()); // TERMINATED
    }
}

// Method 1: Extending Thread class (NOT RECOMMENDED - less flexible)
class MyThread extends Thread {
    private String name;
    
    public MyThread(String name) {
        super(name);
        this.name = name;
    }
    
    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(name + " executing step " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

// Method 2: Implementing Runnable (RECOMMENDED - more flexible)
class MyRunnable implements Runnable {
    private String name;
    
    public MyRunnable(String name) {
        this.name = name;
    }
    
    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(name + " executing step " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
