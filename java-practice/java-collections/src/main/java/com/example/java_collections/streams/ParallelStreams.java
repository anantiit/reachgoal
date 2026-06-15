package com.example.java_collections.streams;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

/**
 * Parallel Streams: Performance, Pitfalls, and Best Practices
 */
public class ParallelStreams {
    
    private static final int DATA_SIZE = 10_000_000;
    
    public static void main(String[] args) {
        System.out.println("=== PARALLEL STREAMS ===\n");
        
        demonstrateParallelStreamCreation();
        compareSequentialVsParallel();
        demonstrateCommonPitfalls();
        demonstrateThreadSafety();
        demonstrateWhenToUseParallel();
        demonstrateForkJoinPool();
    }
    
    private static void demonstrateParallelStreamCreation() {
        System.out.println("1. Creating Parallel Streams");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        Stream<Integer> parallel1 = numbers.parallelStream();
        System.out.println("   parallelStream(): " + parallel1.isParallel());
        
        Stream<Integer> parallel2 = numbers.stream().parallel();
        System.out.println("   stream().parallel(): " + parallel2.isParallel());
        
        Stream<Integer> sequential = numbers.parallelStream().sequential();
        System.out.println("   parallel().sequential(): " + sequential.isParallel());
        
        System.out.println("   Default parallelism: " + 
            ForkJoinPool.commonPool().getParallelism() + " (CPU cores - 1)\n");
    }
    
    private static void compareSequentialVsParallel() {
        System.out.println("2. Performance Comparison (Large Dataset)");
        
        List<Integer> data = IntStream.range(0, DATA_SIZE)
            .boxed()
            .collect(Collectors.toList());
        
        long start = System.currentTimeMillis();
        long sumSequential = data.stream()
            .mapToLong(i -> i * 2)
            .sum();
        long sequentialTime = System.currentTimeMillis() - start;
        
        start = System.currentTimeMillis();
        long sumParallel = data.parallelStream()
            .mapToLong(i -> i * 2)
            .sum();
        long parallelTime = System.currentTimeMillis() - start;
        
        System.out.printf("   Sequential: %d ms (sum=%d)%n", sequentialTime, sumSequential);
        System.out.printf("   Parallel:   %d ms (sum=%d)%n", parallelTime, sumParallel);
        System.out.printf("   Speedup: %.2fx%n%n", (double) sequentialTime / parallelTime);
    }
    
    private static void demonstrateCommonPitfalls() {
        System.out.println("3. Common Pitfalls");
        
        System.out.println("   Pitfall 1: Non-thread-safe collection");
        List<Integer> unsafeList = new ArrayList<>();
        IntStream.range(0, 1000).parallel().forEach(unsafeList::add);
        System.out.println("   Expected: 1000, Actual: " + unsafeList.size() + " (race condition!)");
        
        System.out.println("\n   Pitfall 2: Shared mutable state");
        int[] sum = {0};
        IntStream.range(0, 1000).parallel().forEach(i -> sum[0] += i);
        int expected = IntStream.range(0, 1000).sum();
        System.out.println("   Expected: " + expected + ", Actual: " + sum[0] + " (wrong!)");
        
        System.out.println("\n   Pitfall 3: Order dependency");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.print("   Parallel forEach (unordered): ");
        numbers.parallelStream().forEach(n -> System.out.print(n + " "));
        
        System.out.print("\n   forEachOrdered (ordered): ");
        numbers.parallelStream().forEachOrdered(n -> System.out.print(n + " "));
        System.out.println("\n");
    }
    
    private static void demonstrateThreadSafety() {
        System.out.println("4. Thread-Safe Solutions");
        
        System.out.println("   Solution 1: Use collect() instead of forEach()");
        List<Integer> safeList = IntStream.range(0, 1000)
            .parallel()
            .boxed()
            .collect(Collectors.toList());
        System.out.println("   Size: " + safeList.size() + " ✓");
        
        System.out.println("\n   Solution 2: Use thread-safe collections");
        List<Integer> syncList = Collections.synchronizedList(new ArrayList<>());
        IntStream.range(0, 1000).parallel().forEach(syncList::add);
        System.out.println("   Size: " + syncList.size() + " ✓");
        
        System.out.println("\n   Solution 3: Use reduce for aggregation");
        int correctSum = IntStream.range(0, 1000)
            .parallel()
            .reduce(0, Integer::sum);
        System.out.println("   Sum: " + correctSum + " ✓\n");
    }
    
    private static void demonstrateWhenToUseParallel() {
        System.out.println("5. When to Use Parallel Streams");
        
        System.out.println("   ✓ Large datasets (>10,000 elements)");
        System.out.println("   ✓ CPU-intensive operations");
        System.out.println("   ✓ Independent operations (no shared state)");
        System.out.println("   ✓ Stateless operations");
        System.out.println();
        System.out.println("   ✗ Small datasets (overhead > benefit)");
        System.out.println("   ✗ I/O-bound operations (blocking)");
        System.out.println("   ✗ Ordered operations required");
        System.out.println("   ✗ Shared mutable state");
        
        int smallSize = 100;
        List<Integer> smallData = IntStream.range(0, smallSize)
            .boxed()
            .collect(Collectors.toList());
        
        long start = System.nanoTime();
        smallData.stream().mapToInt(i -> i * 2).sum();
        long seqTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        smallData.parallelStream().mapToInt(i -> i * 2).sum();
        long parTime = System.nanoTime() - start;
        
        System.out.printf("\n   Small dataset (%d elements):%n", smallSize);
        System.out.printf("   Sequential: %,d ns%n", seqTime);
        System.out.printf("   Parallel:   %,d ns (slower due to overhead!)%n%n", parTime);
    }
    
    private static void demonstrateForkJoinPool() {
        System.out.println("6. Custom ForkJoinPool");
        
        System.out.println("   Default common pool parallelism: " + 
            ForkJoinPool.commonPool().getParallelism());
        
        ForkJoinPool customPool = new ForkJoinPool(2);
        
        try {
            long sum = customPool.submit(() ->
                IntStream.range(0, 1000)
                    .parallel()
                    .mapToLong(i -> {
                        return i * 2;
                    })
                    .sum()
            ).get();
            
            System.out.println("   Custom pool (parallelism=2) sum: " + sum);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            customPool.shutdown();
        }
        
        System.out.println("\n=== KEY TAKEAWAY ===");
        System.out.println("Parallel streams use ForkJoinPool.commonPool()");
        System.out.println("Avoid shared mutable state - use collect(), reduce()");
        System.out.println("Best for large datasets + CPU-intensive operations");
        System.out.println("Measure performance - parallel is not always faster!");
    }
}
