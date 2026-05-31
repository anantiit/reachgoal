package org.example.javaconcurrancy.concurrency.pitfalls;

/**
 * Demo 15: Deadlock - The Problem and Solution
 * 
 * KEY CONCEPTS:
 * - Deadlock occurs when threads wait for each other indefinitely
 * - Conditions: Mutual exclusion, Hold and wait, No preemption, Circular wait
 * - Prevention: Lock ordering, timeouts, deadlock detection
 * - Demonstrates WRONG approach and CORRECT solution
 */
public class DeadlockDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Deadlock Demo ===\n");
        
        // Demo 1: PROBLEM - Deadlock situation
        demonstrateDeadlock();
        
        Thread.sleep(3000);
        
        // Demo 2: SOLUTION - Fixed with lock ordering
        demonstrateDeadlockPrevention();
        
        System.out.println("\n=== Deadlock demo completed ===");
    }
    
    private static void demonstrateDeadlock() throws InterruptedException {
        System.out.println("1. PROBLEM: Deadlock Situation (WRONG)");
        System.out.println("  Creating deadlock scenario...\n");
        
        final Object lock1 = new Object();
        final Object lock2 = new Object();
        
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("  Thread-1: Acquired lock1");
                sleep(100);
                
                System.out.println("  Thread-1: Waiting for lock2...");
                synchronized (lock2) {
                    System.out.println("  Thread-1: Acquired lock2 (won't reach here)");
                }
            }
        }, "Thread-1");
        
        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("  Thread-2: Acquired lock2");
                sleep(100);
                
                System.out.println("  Thread-2: Waiting for lock1...");
                synchronized (lock1) {
                    System.out.println("  Thread-2: Acquired lock1 (won't reach here)");
                }
            }
        }, "Thread-2");
        
        thread1.start();
        thread2.start();
        
        // Wait a bit to see the deadlock
        Thread.sleep(1000);
        
        System.out.println("\n  ❌ DEADLOCK DETECTED!");
        System.out.println("  Thread-1 state: " + thread1.getState());
        System.out.println("  Thread-2 state: " + thread2.getState());
        System.out.println("  Both threads are BLOCKED, waiting for each other!\n");
        
        // Force stop the deadlocked threads (normally you can't do this)
        thread1.interrupt();
        thread2.interrupt();
    }
    
    private static void demonstrateDeadlockPrevention() throws InterruptedException {
        System.out.println("2. SOLUTION: Lock Ordering (CORRECT)");
        System.out.println("  Using consistent lock ordering...\n");
        
        final Account account1 = new Account("ACC-001", 1000);
        final Account account2 = new Account("ACC-002", 2000);
        
        // Transfer from account1 to account2
        Thread transfer1 = new Thread(() -> {
            BankService.transfer(account1, account2, 100);
        }, "Transfer-1");
        
        // Transfer from account2 to account1 (reverse direction)
        Thread transfer2 = new Thread(() -> {
            BankService.transfer(account2, account1, 150);
        }, "Transfer-2");
        
        transfer1.start();
        transfer2.start();
        
        transfer1.join();
        transfer2.join();
        
        System.out.println("\n  Final balances:");
        System.out.println("  " + account1);
        System.out.println("  " + account2);
        System.out.println("  ✅ No deadlock - lock ordering ensured!\n");
    }
    
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Account {
    private final String id;
    private double balance;
    
    public Account(String id, double balance) {
        this.id = id;
        this.balance = balance;
    }
    
    public String getId() {
        return id;
    }
    
    public void debit(double amount) {
        balance -= amount;
    }
    
    public void credit(double amount) {
        balance += amount;
    }
    
    @Override
    public String toString() {
        return id + ": $" + balance;
    }
}

class BankService {
    // SOLUTION: Always acquire locks in consistent order (by account ID)
    public static void transfer(Account from, Account to, double amount) {
        // Determine lock order to prevent deadlock
        Account first = from.getId().compareTo(to.getId()) < 0 ? from : to;
        Account second = first == from ? to : from;
        
        synchronized (first) {
            System.out.println("  " + Thread.currentThread().getName() + 
                              " acquired lock on " + first.getId());
            
            synchronized (second) {
                System.out.println("  " + Thread.currentThread().getName() + 
                                  " acquired lock on " + second.getId());
                
                // Perform transfer
                from.debit(amount);
                to.credit(amount);
                
                System.out.println("  " + Thread.currentThread().getName() + 
                                  " transferred $" + amount + " from " + 
                                  from.getId() + " to " + to.getId());
            }
        }
    }
}
