package org.example.javaconcurrancy.gc.tuning;

import java.util.ArrayList;
import java.util.List;

/**
 * Demo 19: GC Behavior - Observing Different Garbage Collectors
 * 
 * KEY CONCEPTS:
 * - Young Generation GC (Minor GC) - Fast, frequent
 * - Old Generation GC (Major GC) - Slower, less frequent
 * - Full GC - Entire heap, expensive
 * - Different GC algorithms have different trade-offs
 * 
 * RUN WITH DIFFERENT GC ALGORITHMS:
 * 
 * Serial GC (Single-threaded, simple):
 * java -Xms128m -Xmx256m -XX:+UseSerialGC -Xlog:gc* GCBehaviorDemo
 * 
 * Parallel GC (Multi-threaded, throughput-focused):
 * java -Xms128m -Xmx256m -XX:+UseParallelGC -Xlog:gc* GCBehaviorDemo
 * 
 * G1 GC (Low-latency, balanced):
 * java -Xms128m -Xmx256m -XX:+UseG1GC -Xlog:gc* GCBehaviorDemo
 * 
 * ZGC (Ultra-low latency, Java 15+):
 * java -Xms128m -Xmx256m -XX:+UseZGC -Xlog:gc* GCBehaviorDemo
 */
public class GCBehaviorDemo {
    
    private static final List<byte[]> youngGenObjects = new ArrayList<>();
    private static final List<byte[]> oldGenObjects = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=== GC Behavior Demo ===\n");
        
        printGCInfo();
        
        System.out.println("\nRunning memory allocation patterns...\n");
        
        // Demo 1: Young generation allocation
        demonstrateYoungGenAllocation();
        
        // Demo 2: Old generation promotion
        demonstrateOldGenPromotion();
        
        // Demo 3: Mixed allocation pattern
        demonstrateMixedPattern();
        
        System.out.println("\n=== GC behavior demo completed ===");
        System.out.println("\nCheck the GC logs above to see:");
        System.out.println("- Minor GC events (young generation)");
        System.out.println("- Major GC events (old generation)");
        System.out.println("- Pause times and throughput");
    }
    
    private static void demonstrateYoungGenAllocation() {
        System.out.println("1. Young Generation Allocation:");
        System.out.println("  Creating many short-lived objects...\n");
        
        for (int i = 0; i < 1000; i++) {
            // Create objects that become garbage immediately
            byte[] temp = new byte[1024 * 100]; // 100 KB
            // temp becomes unreachable - eligible for young gen GC
            
            if (i % 200 == 0) {
                printMemoryUsage("Iteration " + i);
            }
        }
        
        System.out.println("  ✓ Most objects collected by Minor GC (young gen)\n");
    }
    
    private static void demonstrateOldGenPromotion() {
        System.out.println("2. Old Generation Promotion:");
        System.out.println("  Creating long-lived objects...\n");
        
        for (int i = 0; i < 100; i++) {
            // Keep references - objects survive and get promoted to old gen
            oldGenObjects.add(new byte[1024 * 100]); // 100 KB
            
            if (i % 20 == 0) {
                printMemoryUsage("Allocated " + i + " long-lived objects");
            }
        }
        
        System.out.println("  ✓ Long-lived objects promoted to old generation\n");
    }
    
    private static void demonstrateMixedPattern() {
        System.out.println("3. Mixed Allocation Pattern:");
        System.out.println("  Mixing short-lived and long-lived objects...\n");
        
        for (int i = 0; i < 500; i++) {
            // Short-lived object
            byte[] temp = new byte[1024 * 50]; // 50 KB
            
            // Occasionally create long-lived object
            if (i % 10 == 0) {
                youngGenObjects.add(new byte[1024 * 50]);
            }
            
            // Occasionally clear some long-lived objects
            if (i % 50 == 0 && !youngGenObjects.isEmpty()) {
                youngGenObjects.remove(0);
            }
            
            if (i % 100 == 0) {
                printMemoryUsage("Iteration " + i);
            }
        }
        
        System.out.println("  ✓ Realistic pattern shows both minor and major GC\n");
    }
    
    private static void printGCInfo() {
        System.out.println("JVM GC Configuration:");
        
        // Get GC name
        List<String> gcArgs = java.lang.management.ManagementFactory.getRuntimeMXBean()
            .getInputArguments();
        
        String gcType = "Default";
        for (String arg : gcArgs) {
            if (arg.contains("UseSerialGC")) gcType = "Serial GC";
            if (arg.contains("UseParallelGC")) gcType = "Parallel GC";
            if (arg.contains("UseG1GC")) gcType = "G1 GC";
            if (arg.contains("UseZGC")) gcType = "Z GC";
            if (arg.contains("UseShenandoahGC")) gcType = "Shenandoah GC";
        }
        
        System.out.println("  GC Type: " + gcType);
        
        Runtime runtime = Runtime.getRuntime();
        System.out.println("  Max Heap: " + formatBytes(runtime.maxMemory()));
        System.out.println("  Initial Heap: " + formatBytes(runtime.totalMemory()));
        
        // Get memory pool information
        for (java.lang.management.MemoryPoolMXBean pool : 
             java.lang.management.ManagementFactory.getMemoryPoolMXBeans()) {
            if (pool.getType() == java.lang.management.MemoryType.HEAP) {
                System.out.println("  Pool: " + pool.getName() + 
                                  " - " + formatBytes(pool.getUsage().getMax()));
            }
        }
    }
    
    private static void printMemoryUsage(String label) {
        Runtime runtime = Runtime.getRuntime();
        long total = runtime.totalMemory();
        long free = runtime.freeMemory();
        long used = total - free;
        
        System.out.println("  [" + label + "] Used: " + formatBytes(used) + 
                          " / Total: " + formatBytes(total));
    }
    
    private static String formatBytes(long bytes) {
        return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
    }
}
