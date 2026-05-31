package org.example.javaconcurrancy.concurrency.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Demo 9: CompletableFuture - Modern Async Programming (Java 8+)
 * 
 * KEY CONCEPTS:
 * - Non-blocking async operations
 * - Chaining operations with thenApply, thenCompose, thenCombine
 * - Exception handling with exceptionally, handle
 * - Combining multiple futures
 * - Much more powerful than Future
 */
public class CompletableFutureDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("=== CompletableFuture Demo ===\n");
        
        // Demo 1: Basic CompletableFuture
        demonstrateBasicUsage();
        
        // Demo 2: Chaining operations
        demonstrateChaining();
        
        // Demo 3: Combining multiple futures
        demonstrateCombining();
        
        // Demo 4: Exception handling
        demonstrateExceptionHandling();
        
        // Demo 5: Real-world example
        demonstrateRealWorldExample();
        
        System.out.println("\n=== CompletableFuture demo completed ===");
    }
    
    private static void demonstrateBasicUsage() throws ExecutionException, InterruptedException {
        System.out.println("1. Basic CompletableFuture:");
        
        // Run async task
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("  Running in: " + Thread.currentThread().getName());
            sleep(500);
            return "Hello from async task";
        });
        
        System.out.println("  Main thread continues...");
        
        // Non-blocking callback
        future.thenAccept(result -> {
            System.out.println("  Result: " + result);
        });
        
        future.get(); // Wait for completion
        System.out.println("  ✓ Async task completed\n");
    }
    
    private static void demonstrateChaining() throws ExecutionException, InterruptedException {
        System.out.println("2. Chaining Operations:");
        
        CompletableFuture<String> result = CompletableFuture
            .supplyAsync(() -> {
                System.out.println("  Step 1: Fetching user ID...");
                sleep(300);
                return 12345;
            })
            .thenApply(userId -> {
                System.out.println("  Step 2: Fetching user details for ID: " + userId);
                sleep(300);
                return "User: John Doe (ID: " + userId + ")";
            })
            .thenApply(userDetails -> {
                System.out.println("  Step 3: Formatting user info...");
                sleep(200);
                return userDetails.toUpperCase();
            });
        
        System.out.println("  Final result: " + result.get());
        System.out.println("  ✓ Operations chained and executed sequentially\n");
    }
    
    private static void demonstrateCombining() throws ExecutionException, InterruptedException {
        System.out.println("3. Combining Multiple Futures:");
        
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("  Fetching weather data...");
            sleep(500);
            return "Sunny, 25°C";
        });
        
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("  Fetching stock price...");
            sleep(700);
            return "AAPL: $150.25";
        });
        
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("  Fetching news headlines...");
            sleep(400);
            return "Breaking: Tech stocks rally";
        });
        
        // Combine all three
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(future1, future2, future3);
        
        allFutures.thenRun(() -> {
            try {
                System.out.println("\n  Dashboard Data:");
                System.out.println("  Weather: " + future1.get());
                System.out.println("  Stocks: " + future2.get());
                System.out.println("  News: " + future3.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).get();
        
        System.out.println("  ✓ All futures completed in parallel!\n");
    }
    
    private static void demonstrateExceptionHandling() throws ExecutionException, InterruptedException {
        System.out.println("4. Exception Handling:");
        
        CompletableFuture<String> futureWithError = CompletableFuture
            .supplyAsync(() -> {
                System.out.println("  Processing payment...");
                sleep(300);
                if (Math.random() > 0.5) {
                    throw new RuntimeException("Payment gateway error!");
                }
                return "Payment successful";
            })
            .exceptionally(ex -> {
                System.out.println("  ❌ Error occurred: " + ex.getMessage());
                return "Payment failed - using backup method";
            })
            .thenApply(result -> {
                System.out.println("  Final status: " + result);
                return result;
            });
        
        futureWithError.get();
        System.out.println("  ✓ Exception handled gracefully\n");
    }
    
    private static void demonstrateRealWorldExample() throws ExecutionException, InterruptedException {
        System.out.println("5. Real-World Example - E-commerce Order Processing:");
        
        CompletableFuture<String> orderResult = CompletableFuture
            .supplyAsync(() -> {
                System.out.println("  [1] Validating order...");
                sleep(200);
                return "ORDER-12345";
            })
            .thenCompose(orderId -> CompletableFuture.supplyAsync(() -> {
                System.out.println("  [2] Processing payment for " + orderId + "...");
                sleep(400);
                return orderId + ":PAID";
            }))
            .thenCompose(paymentInfo -> CompletableFuture.supplyAsync(() -> {
                System.out.println("  [3] Updating inventory...");
                sleep(300);
                return paymentInfo + ":INVENTORY_UPDATED";
            }))
            .thenApply(status -> {
                System.out.println("  [4] Sending confirmation email...");
                sleep(200);
                return status + ":EMAIL_SENT";
            })
            .exceptionally(ex -> {
                System.out.println("  ❌ Order processing failed: " + ex.getMessage());
                return "ORDER_FAILED";
            });
        
        String finalStatus = orderResult.get();
        System.out.println("  ✅ Order status: " + finalStatus);
        System.out.println("  ✓ Complex workflow executed asynchronously\n");
    }
    
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
