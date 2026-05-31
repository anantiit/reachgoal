package org.example.javaconcurrancy.concurrency.synchronization;

/**
 * Demo 4: Synchronization - Method vs Block Level
 * 
 * KEY CONCEPTS:
 * - synchronized method - locks entire object
 * - synchronized block - locks specific object (more granular)
 * - Different lock objects for better concurrency
 * - Static synchronization - locks on Class object
 */
public class SynchronizationDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Synchronization Demo ===\n");
        
        // Demo 1: Method-level synchronization
        demonstrateMethodSync();
        
        // Demo 2: Block-level synchronization (more efficient)
        demonstrateBlockSync();
        
        // Demo 3: Static synchronization
        demonstrateStaticSync();
        
        System.out.println("\n=== Synchronization demo completed ===");
    }
    
    private static void demonstrateMethodSync() throws InterruptedException {
        System.out.println("1. Method-level Synchronization:");
        
        BankAccount account = new BankAccount(1000);
        
        Thread withdrawal1 = new Thread(() -> {
            account.withdraw(600);
        }, "ATM-1");
        
        Thread withdrawal2 = new Thread(() -> {
            account.withdraw(600);
        }, "ATM-2");
        
        withdrawal1.start();
        withdrawal2.start();
        
        withdrawal1.join();
        withdrawal2.join();
        
        System.out.println("  Final balance: $" + account.getBalance());
        System.out.println("  ✓ One withdrawal was prevented due to synchronization\n");
    }
    
    private static void demonstrateBlockSync() throws InterruptedException {
        System.out.println("2. Block-level Synchronization (Fine-grained):");
        
        InventorySystem inventory = new InventorySystem();
        
        // Multiple threads trying to update different products simultaneously
        Thread[] threads = new Thread[4];
        threads[0] = new Thread(() -> inventory.updateProduct("Laptop", 5), "Thread-1");
        threads[1] = new Thread(() -> inventory.updateProduct("Mouse", 10), "Thread-2");
        threads[2] = new Thread(() -> inventory.updateProduct("Laptop", 3), "Thread-3");
        threads[3] = new Thread(() -> inventory.updateProduct("Keyboard", 7), "Thread-4");
        
        for (Thread thread : threads) {
            thread.start();
        }
        
        for (Thread thread : threads) {
            thread.join();
        }
        
        System.out.println("  ✓ Block-level sync allows better concurrency\n");
    }
    
    private static void demonstrateStaticSync() throws InterruptedException {
        System.out.println("3. Static Synchronization (Class-level lock):");
        
        Thread[] threads = new Thread[3];
        for (int i = 0; i < 3; i++) {
            final int id = i + 1;
            threads[i] = new Thread(() -> {
                IdGenerator.generateId(id);
            }, "Generator-" + id);
        }
        
        for (Thread thread : threads) {
            thread.start();
        }
        
        for (Thread thread : threads) {
            thread.join();
        }
        
        System.out.println("  ✓ Static synchronization locks on class object\n");
    }
}

class BankAccount {
    private double balance;
    
    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }
    
    // synchronized method - locks entire object
    public synchronized void withdraw(double amount) {
        System.out.println("  " + Thread.currentThread().getName() + 
                          " attempting to withdraw $" + amount);
        
        if (balance >= amount) {
            System.out.println("  " + Thread.currentThread().getName() + 
                              " - Sufficient balance. Processing...");
            
            // Simulate processing time
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            balance -= amount;
            System.out.println("  " + Thread.currentThread().getName() + 
                              " - Withdrawal successful. New balance: $" + balance);
        } else {
            System.out.println("  " + Thread.currentThread().getName() + 
                              " - Insufficient balance. Current: $" + balance);
        }
    }
    
    public synchronized double getBalance() {
        return balance;
    }
}

class InventorySystem {
    private final Object laptopLock = new Object();
    private final Object mouseLock = new Object();
    private final Object keyboardLock = new Object();
    
    public void updateProduct(String product, int quantity) {
        // Use different locks for different products - better concurrency
        Object lock = getLockForProduct(product);
        
        synchronized (lock) {
            System.out.println("  " + Thread.currentThread().getName() + 
                              " updating " + product + " by " + quantity);
            try {
                Thread.sleep(100); // Simulate database update
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("  " + Thread.currentThread().getName() + 
                              " completed updating " + product);
        }
    }
    
    private Object getLockForProduct(String product) {
        return switch (product) {
            case "Laptop" -> laptopLock;
            case "Mouse" -> mouseLock;
            case "Keyboard" -> keyboardLock;
            default -> this;
        };
    }
}

class IdGenerator {
    private static int counter = 0;
    
    // Static synchronized - locks on IdGenerator.class object
    public static synchronized void generateId(int threadId) {
        System.out.println("  Thread-" + threadId + " generating ID...");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        counter++;
        System.out.println("  Thread-" + threadId + " generated ID: " + counter);
    }
}
