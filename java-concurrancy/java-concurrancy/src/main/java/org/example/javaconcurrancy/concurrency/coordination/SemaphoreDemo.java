package org.example.javaconcurrancy.concurrency.coordination;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Demo 13: Semaphore - Resource Pool Management
 * 
 * KEY CONCEPTS:
 * - Controls access to a shared resource pool
 * - Permits limit number of concurrent accesses
 * - acquire() - takes a permit (blocks if none available)
 * - release() - returns a permit
 * - Perfect for connection pools, rate limiting
 */
public class SemaphoreDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Semaphore Demo ===\n");
        
        // Demo 1: Database connection pool
        demonstrateConnectionPool();
        
        // Demo 2: Parking lot simulation
        demonstrateParkingLot();
        
        System.out.println("\n=== Semaphore demo completed ===");
    }
    
    private static void demonstrateConnectionPool() throws InterruptedException {
        System.out.println("1. Database Connection Pool (3 connections):");
        
        // Only 3 concurrent connections allowed
        Semaphore connectionPool = new Semaphore(3);
        
        // 10 threads trying to get connections
        Thread[] threads = new Thread[10];
        for (int i = 1; i <= 10; i++) {
            final int threadNum = i;
            threads[i - 1] = new Thread(() -> {
                try {
                    System.out.println("  Thread-" + threadNum + " requesting connection...");
                    
                    connectionPool.acquire(); // Get a permit
                    
                    System.out.println("  ✓ Thread-" + threadNum + " acquired connection! " +
                                      "Available: " + connectionPool.availablePermits());
                    
                    // Use connection
                    Thread.sleep(1000);
                    
                    System.out.println("  Thread-" + threadNum + " releasing connection");
                    
                    connectionPool.release(); // Return permit
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "DBThread-" + i);
        }
        
        // Start all threads
        for (Thread thread : threads) {
            thread.start();
            Thread.sleep(100); // Stagger start times
        }
        
        // Wait for completion
        for (Thread thread : threads) {
            thread.join();
        }
        
        System.out.println("  ✅ All threads completed\n");
    }
    
    private static void demonstrateParkingLot() throws InterruptedException {
        System.out.println("2. Parking Lot (5 spots):");
        
        ParkingLot parkingLot = new ParkingLot(5);
        
        // 8 cars trying to park
        Thread[] cars = new Thread[8];
        for (int i = 1; i <= 8; i++) {
            final int carNum = i;
            cars[i - 1] = new Thread(() -> {
                parkingLot.park(carNum);
            }, "Car-" + i);
        }
        
        // Cars arrive at different times
        for (Thread car : cars) {
            car.start();
            Thread.sleep(200);
        }
        
        // Wait for all cars
        for (Thread car : cars) {
            car.join();
        }
        
        System.out.println("  ✅ All cars have parked and left\n");
    }
}

class ParkingLot {
    private final Semaphore spots;
    private final int totalSpots;
    
    public ParkingLot(int numberOfSpots) {
        this.spots = new Semaphore(numberOfSpots);
        this.totalSpots = numberOfSpots;
    }
    
    public void park(int carNumber) {
        try {
            System.out.println("  🚗 Car-" + carNumber + " arriving at parking lot");
            
            // Try to acquire spot with timeout
            boolean parked = spots.tryAcquire(2, TimeUnit.SECONDS);
            
            if (parked) {
                int occupiedSpots = totalSpots - spots.availablePermits();
                System.out.println("  ✓ Car-" + carNumber + " parked! " +
                                  "Occupied: " + occupiedSpots + "/" + totalSpots);
                
                // Stay parked for some time
                Thread.sleep((long) (Math.random() * 2000 + 1000));
                
                System.out.println("  Car-" + carNumber + " leaving");
                spots.release();
                
                occupiedSpots = totalSpots - spots.availablePermits();
                System.out.println("  Spot freed! Occupied: " + occupiedSpots + "/" + totalSpots);
            } else {
                System.out.println("  ❌ Car-" + carNumber + " couldn't find parking - leaving");
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
