package org.example.javaconcurrancy.concurrency.coordination;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Demo 12: CyclicBarrier - Synchronization Point for Threads
 * 
 * KEY CONCEPTS:
 * - Threads wait at barrier until all parties arrive
 * - Can be reused (cyclic) unlike CountDownLatch
 * - Optional barrier action executes when all threads arrive
 * - Perfect for iterative parallel algorithms
 * - Used in multi-phase computations
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== CyclicBarrier Demo ===\n");
        
        // Demo 1: Basic barrier synchronization
        demonstrateBasicBarrier();
        
        // Demo 2: Multi-phase computation
        demonstrateMultiPhase();
        
        System.out.println("\n=== CyclicBarrier demo completed ===");
    }
    
    private static void demonstrateBasicBarrier() throws InterruptedException {
        System.out.println("1. Basic CyclicBarrier - Team Synchronization:");
        
        int numberOfPlayers = 4;
        
        // Barrier action runs when all threads reach the barrier
        CyclicBarrier barrier = new CyclicBarrier(numberOfPlayers, () -> {
            System.out.println("\n  🎮 All players ready! Starting game...\n");
        });
        
        // Create player threads
        for (int i = 1; i <= numberOfPlayers; i++) {
            new Thread(new Player("Player-" + i, barrier)).start();
        }
        
        Thread.sleep(4000); // Wait for demo to complete
        System.out.println();
    }
    
    private static void demonstrateMultiPhase() throws InterruptedException {
        System.out.println("2. Multi-Phase Computation (Reusable Barrier):");
        System.out.println("  Simulating parallel matrix computation in phases\n");
        
        int numberOfWorkers = 3;
        int numberOfPhases = 3;
        
        CyclicBarrier barrier = new CyclicBarrier(numberOfWorkers, () -> {
            System.out.println("  --- Phase completed! All workers synchronized ---");
        });
        
        // Create worker threads
        for (int i = 1; i <= numberOfWorkers; i++) {
            new Thread(new ComputationWorker("Worker-" + i, barrier, numberOfPhases)).start();
        }
        
        Thread.sleep(5000); // Wait for all phases to complete
        System.out.println("\n  ✅ All phases completed!\n");
    }
}

class Player implements Runnable {
    private final String name;
    private final CyclicBarrier barrier;
    
    public Player(String name, CyclicBarrier barrier) {
        this.name = name;
        this.barrier = barrier;
    }
    
    @Override
    public void run() {
        try {
            // Simulate player loading time
            System.out.println("  " + name + " is loading...");
            Thread.sleep((long) (Math.random() * 2000 + 500));
            
            System.out.println("  " + name + " ready and waiting for others...");
            
            // Wait at barrier for all players
            barrier.await();
            
            // After barrier - all players proceed together
            System.out.println("  " + name + " started playing!");
            
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class ComputationWorker implements Runnable {
    private final String name;
    private final CyclicBarrier barrier;
    private final int numberOfPhases;
    
    public ComputationWorker(String name, CyclicBarrier barrier, int numberOfPhases) {
        this.name = name;
        this.barrier = barrier;
        this.numberOfPhases = numberOfPhases;
    }
    
    @Override
    public void run() {
        try {
            for (int phase = 1; phase <= numberOfPhases; phase++) {
                // Do computation for this phase
                System.out.println("  " + name + " working on phase " + phase);
                Thread.sleep((long) (Math.random() * 1000 + 500));
                
                System.out.println("  " + name + " finished phase " + phase + ", waiting...");
                
                // Wait for all workers to complete this phase
                barrier.await(); // Barrier is reused for each phase
                
                // Small delay before next phase
                Thread.sleep(100);
            }
            
            System.out.println("  ✓ " + name + " completed all phases");
            
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
        }
    }
}
