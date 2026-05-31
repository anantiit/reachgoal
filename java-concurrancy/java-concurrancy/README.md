# Java Concurrency & Garbage Collection Demo Project

Complete hands-on demonstrations of Java concurrency and garbage collection concepts for intermediate developers.

## 🎯 Project Overview

This project contains **19 comprehensive demos** covering:
- **Java Concurrency** (14 demos): Threading, synchronization, executors, async programming, coordination utilities
- **Garbage Collection** (5 demos): Memory management, reference types, GC tuning, memory leaks

## 📁 Project Structure

```
src/main/java/org/example/javaconcurrancy/
├── concurrency/
│   ├── basics/
│   │   ├── ThreadCreationDemo.java         # Demo 1: Thread creation (3 ways)
│   │   └── ThreadCoordinationDemo.java     # Demo 2: join(), sleep(), daemon threads
│   ├── synchronization/
│   │   ├── RaceConditionDemo.java          # Demo 3: Race conditions & solutions
│   │   └── SynchronizationDemo.java        # Demo 4: Method vs block synchronization
│   ├── locks/
│   │   ├── ReentrantLockDemo.java          # Demo 5: Advanced locking
│   │   └── ReadWriteLockDemo.java          # Demo 6: Read-heavy optimization
│   ├── executors/
│   │   └── ExecutorServiceDemo.java        # Demo 7: Thread pools
│   ├── async/
│   │   ├── CallableFutureDemo.java         # Demo 8: Async with return values
│   │   └── CompletableFutureDemo.java      # Demo 9: Modern async programming
│   ├── atomic/
│   │   └── AtomicVariablesDemo.java        # Demo 10: Lock-free thread safety
│   ├── coordination/
│   │   ├── CountDownLatchDemo.java         # Demo 11: Wait for multiple threads
│   │   ├── CyclicBarrierDemo.java          # Demo 12: Synchronization points
│   │   ├── SemaphoreDemo.java              # Demo 13: Resource pools
│   │   └── BlockingQueueDemo.java          # Demo 14: Producer-consumer
│   └── pitfalls/
│       └── DeadlockDemo.java               # Demo 15: Deadlock problem & solution
└── gc/
    ├── memory/
    │   └── MemoryAllocationDemo.java       # Demo 16: Stack vs Heap
    ├── references/
    │   └── ReferenceTypesDemo.java         # Demo 17: Strong, Weak, Soft, Phantom
    ├── leaks/
    │   └── MemoryLeakDemo.java             # Demo 18: Common memory leaks
    └── tuning/
        └── GCBehaviorDemo.java             # Demo 19: GC algorithms comparison
```

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Gradle (included via wrapper)

### Build the Project
```bash
cd java-concurrancy/java-concurrancy
./gradlew build
```

### Run Individual Demos

#### Basic Execution
```bash
# Run any demo
./gradlew run --args="org.example.javaconcurrancy.concurrency.basics.ThreadCreationDemo"
```

Or compile and run directly:
```bash
# Compile
./gradlew compileJava

# Run a specific demo
java -cp build/classes/java/main org.example.javaconcurrancy.concurrency.basics.ThreadCreationDemo
```

## 📖 Demo Categories

### 🔄 Concurrency Demos

| Demo | File | Key Concepts |
|------|------|--------------|
| 1 | ThreadCreationDemo | Thread creation, lifecycle, states |
| 2 | ThreadCoordinationDemo | join(), daemon threads, priorities |
| 3 | RaceConditionDemo | Race conditions, synchronized |
| 4 | SynchronizationDemo | Method vs block sync |
| 5 | ReentrantLockDemo | tryLock(), fair locks |
| 6 | ReadWriteLockDemo | Read-heavy optimization |
| 7 | ExecutorServiceDemo | Thread pools, scheduling |
| 8 | CallableFutureDemo | Future, timeout, cancellation |
| 9 | CompletableFutureDemo | Chaining, combining, async |
| 10 | AtomicVariablesDemo | Lock-free, CAS operations |
| 11 | CountDownLatchDemo | Await multiple completions |
| 12 | CyclicBarrierDemo | Reusable barriers |
| 13 | SemaphoreDemo | Resource pools, permits |
| 14 | BlockingQueueDemo | Producer-consumer pattern |
| 15 | DeadlockDemo | Deadlock & prevention |

### 🗑️ Garbage Collection Demos

| Demo | File | Run With | Purpose |
|------|------|----------|---------|
| 16 | MemoryAllocationDemo | `-Xms64m -Xmx128m -verbose:gc` | Stack vs Heap |
| 17 | ReferenceTypesDemo | `-Xmx64m -verbose:gc` | Weak, Soft, Phantom refs |
| 18 | MemoryLeakDemo | `-Xmx128m -XX:+HeapDumpOnOutOfMemoryError` | Common leaks |
| 19 | GCBehaviorDemo | See below | GC algorithms |

### GC Algorithm Comparison (Demo 19)

Run with different GC algorithms to compare:

```bash
# Serial GC
java -Xms128m -Xmx256m -XX:+UseSerialGC -Xlog:gc* \
  -cp build/classes/java/main org.example.javaconcurrancy.gc.tuning.GCBehaviorDemo

# Parallel GC (Throughput)
java -Xms128m -Xmx256m -XX:+UseParallelGC -Xlog:gc* \
  -cp build/classes/java/main org.example.javaconcurrancy.gc.tuning.GCBehaviorDemo

# G1 GC (Balanced)
java -Xms128m -Xmx256m -XX:+UseG1GC -Xlog:gc* \
  -cp build/classes/java/main org.example.javaconcurrancy.gc.tuning.GCBehaviorDemo

# ZGC (Low Latency, Java 15+)
java -Xms128m -Xmx256m -XX:+UseZGC -Xlog:gc* \
  -cp build/classes/java/main org.example.javaconcurrancy.gc.tuning.GCBehaviorDemo
```

## 🎓 For Presentation

See companion guides:
- **PRESENTATION_GUIDE.md** - Quick reference with talking points for your 1-hour session
- **STUDY_GUIDE.md** - Comprehensive preparation material with theory and best practices

## 📊 Useful JVM Flags Reference

### Memory Configuration
- `-Xms<size>` - Initial heap size (e.g., `-Xms128m`)
- `-Xmx<size>` - Maximum heap size (e.g., `-Xmx256m`)
- `-Xss<size>` - Thread stack size (e.g., `-Xss512k`)

### GC Logging (Java 9+)
- `-Xlog:gc` - Basic GC logging
- `-Xlog:gc*` - Detailed GC logging
- `-Xlog:gc*:file=gc.log` - Log to file

### GC Selection
- `-XX:+UseSerialGC` - Single-threaded GC
- `-XX:+UseParallelGC` - Multi-threaded throughput GC
- `-XX:+UseG1GC` - G1 garbage collector (default in Java 9+)
- `-XX:+UseZGC` - Z garbage collector (Java 15+)

### Debugging
- `-XX:+HeapDumpOnOutOfMemoryError` - Create heap dump on OOM
- `-XX:HeapDumpPath=<path>` - Heap dump location
- `-verbose:gc` - Print GC details

## 💡 Tips for Demo

1. **Run demos one at a time** - Each is standalone
2. **Watch console output** - All demos print explanatory messages
3. **Compare results** - Some demos show before/after comparisons
4. **Use GC logs** - Enable verbose GC to see collector behavior
5. **Adjust timing** - Modify sleep() durations if needed for your pace

## 🔧 Troubleshooting

**Issue**: `OutOfMemoryError`
**Solution**: Some demos intentionally stress memory. This is expected for memory leak demos.

**Issue**: Deadlock demo hangs
**Solution**: This is intentional to show deadlock. The demo will forcefully interrupt.

**Issue**: GC logs not showing
**Solution**: Use Java 9+ syntax: `-Xlog:gc*` instead of older flags

## 📚 Additional Resources

- Java Concurrency in Practice (Book)
- Java Memory Management (Oracle Docs)
- JVM GC Tuning Guide

---

**Author**: Created for 1-hour Java Concurrency & GC presentation
**Java Version**: 17+
**Last Updated**: 2026-05-30
