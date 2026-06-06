package org.example.javaconcurrancy.gc.references;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo 17: Reference Types - Strong, Weak, Soft, Phantom
 * 
 * KEY CONCEPTS:
 * - Strong Reference: Normal references, never GC'd while reachable
 * - Weak Reference: GC'd in next GC cycle even if memory available
 * - Soft Reference: GC'd only when memory is low
 * - Phantom Reference: Used for cleanup actions before finalization
 * 
 * RUN WITH: java -Xmx64m -verbose:gc ReferenceTypesDemo
 */
public class ReferenceTypesDemo {

    public static void main(String[] args) {
        System.out.println("=== Reference Types Demo ===\n");
        
        // Demo 1: Strong Reference
        demonstrateStrongReference();
        
        // Demo 2: Weak Reference
        demonstrateWeakReference();
        
        // Demo 3: Soft Reference
        demonstrateSoftReference();
        
        // Demo 4: Phantom Reference
        demonstratePhantomReference();
        
        System.out.println("\n=== Reference types demo completed ===");
    }
    
    private static void demonstrateStrongReference() {
        System.out.println("1. Strong Reference (Normal Reference):");
        
        LargeObject obj = new LargeObject("Strong-1");
        System.out.println("  Created: " + obj.getName());
        
        // Try to force GC
        System.out.println("  Attempting GC...");
        System.gc();
        sleep(100);
        
        System.out.println("  Object still exists: " + obj.getName());
        System.out.println("  ✓ Strong references are NEVER GC'd while reachable\n");
    }
    
    private static void demonstrateWeakReference() {
        System.out.println("2. Weak Reference (WeakReference):");
        
        LargeObject obj = new LargeObject("Weak-1");
        WeakReference<LargeObject> weakRef = new WeakReference<>(obj);
        
        System.out.println("  Created weak reference to: " + weakRef.get().getName());
        System.out.println("  Strong reference still exists: " + (obj != null));
        
        // Remove strong reference
        obj = null;
        System.out.println("  Strong reference removed");
        
        System.out.println("  Before GC - weak ref: " + weakRef.get());
        
        // Force GC
        System.out.println("  Forcing GC...");
        System.gc();
        sleep(100);
        
        System.out.println("  After GC - weak ref: " + weakRef.get());
        System.out.println("  ✓ Weak references are GC'd even if memory is available\n");
    }
    
    private static void demonstrateSoftReference() {
        System.out.println("3. Soft Reference (SoftReference) - Used for Caches:");
        
        List<SoftReference<LargeObject>> cache = new ArrayList<>();
        
        System.out.println("  Creating soft references (cache)...");
        for (int i = 0; i < 10; i++) {
            cache.add(new SoftReference<>(new LargeObject("Cached-" + i)));
        }
        
        System.out.println("  Cache size: " + cache.size());
        
        // Check cache before GC
        long validRefs = cache.stream().filter(ref -> ref.get() != null).count();
        System.out.println("  Valid references before GC: " + validRefs);
        
        // Force GC (soft refs usually survive unless memory pressure)
        System.out.println("  Forcing GC...");
        System.gc();
        sleep(100);
        
        validRefs = cache.stream().filter(ref -> ref.get() != null).count();
        System.out.println("  Valid references after GC: " + validRefs);
        System.out.println("  ✓ Soft references survive GC until memory is needed\n");
    }
    
    private static void demonstratePhantomReference() {
        System.out.println("4. Phantom Reference (PhantomReference) - Cleanup Actions:");
        
        ReferenceQueue<LargeObject> refQueue = new ReferenceQueue<>();
        
        LargeObject obj = new LargeObject("Phantom-1");
        PhantomReference<LargeObject> phantomRef = new PhantomReference<>(obj, refQueue);
        
        System.out.println("  Created phantom reference");
        System.out.println("  Phantom ref.get() always returns: " + phantomRef.get());
        System.out.println("  (Phantom references cannot be used to access object)");
        
        // Remove strong reference
        obj = null;
        
        System.out.println("  Strong reference removed, forcing GC...");
        System.gc();
        sleep(100);
        
        // Check if reference is enqueued
        Reference<?> polled = refQueue.poll();
        if (polled != null) {
            System.out.println("  ✓ Object was GC'd, phantom ref enqueued");
            System.out.println("  Cleanup actions can be performed here");
            polled.clear();
        } else {
            System.out.println("  Reference not yet enqueued");
        }
        
        System.out.println("  ✓ Phantom references used for post-mortem cleanup\n");
    }
    
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class LargeObject {
    private final String name;
    private final byte[] data = new byte[1024 * 100]; // 100 KB
    
    public LargeObject(String name) {
        this.name = name;
        System.out.println("    [Allocated] " + name + " (100 KB)");
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    protected void finalize() throws Throwable {
        System.out.println("    [Finalized] " + name);
        super.finalize();
    }
}
