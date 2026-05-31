# 🚀 Quick Start Guide
## Run Your First Demo in 2 Minutes

---

## Prerequisites Check

✅ **Java 17+** installed
```bash
java -version
# Should show version 17 or higher
```

✅ **In the correct directory**
```bash
cd java-concurrancy/java-concurrancy
```

---

## Step 1: Build the Project (30 seconds)

```bash
./gradlew build
```

Expected output: `BUILD SUCCESSFUL`

---

## Step 2: Run Your First Concurrency Demo (30 seconds)

```bash
java -cp build/classes/java/main org.example.javaconcurrancy.concurrency.basics.ThreadCreationDemo
```

**What you'll see**: Thread creation in 3 different ways, thread states demonstration

---

## Step 3: Run a Race Condition Demo (30 seconds)

```bash
java -cp build/classes/java/main org.example.javaconcurrancy.concurrency.synchronization.RaceConditionDemo
```

**What you'll see**: Data loss from race condition, then the fix with synchronization

---

## Step 4: Run a Memory/GC Demo (30 seconds)

```bash
java -Xms64m -Xmx128m -cp build/classes/java/main org.example.javaconcurrancy.gc.memory.MemoryAllocationDemo
```

**What you'll see**: Stack vs Heap allocation, object lifecycle, garbage collection in action

---

## 📋 All Available Demos

### Concurrency Demos (14 total)

```bash
# Basics
java -cp build/classes/java/main org.example.javaconcurrancy.concurrency.basics.ThreadCreationDemo
java -cp build/classes/java/main org.example.javaconcurrancy.concurrency.basics.ThreadCoordinationDemo

# Synchronization
java -cp build/classes/java/main org.example.javaconcurrancy.concurrency.synchronization.RaceConditionDemo
java -cp build/classes/java/main org.example.javaconcurrancy.concurrency.synchronization.SynchronizationDemo

# Locks
java -cp build/classes/java/main org.example.javaconcurrancy.concurrency.locks.ReentrantLockDemo
java -cp build/classes/java/main org.example.javaconcurrancy.concurrency.locks.ReadWriteLockDemo

# Executors
java -cp build/classes/java/main org.example.javaconcurrancy.concurrency.executors.ExecutorServiceDemo

# Async
java -cp build/classes/java/main org.example.javaconcurrancy.concurrency.async.CallableFutureDemo
java -cp build/classes/java/main org.example.javaconcurrancy.concurrency.async.CompletableFutureDemo

# Atomic
java -cp build/classes/java/main org.example.javaconcurrancy.concurrency.atomic.AtomicVariablesDemo

# Coordination
java -cp build/classes/java/main org.example.javaconcurrancy.concurrency.coordination.CountDownLatchDemo
java -cp build/classes/java/main org.example.javaconcurrancy.concurrency.coordination.CyclicBarrierDemo
java -cp build/classes/java/main org.example.javaconcurrancy.concurrency.coordination.SemaphoreDemo
java -cp build/classes/java/main org.example.javaconcurrancy.concurrency.coordination.BlockingQueueDemo

# Pitfalls
java -cp build/classes/java/main org.example.javaconcurrancy.concurrency.pitfalls.DeadlockDemo
```

### GC Demos (4 total)

```bash
# Memory Basics
java -Xms64m -Xmx128m -verbose:gc -cp build/classes/java/main \
  org.example.javaconcurrancy.gc.memory.MemoryAllocationDemo

# Reference Types
java -Xmx64m -verbose:gc -cp build/classes/java/main \
  org.example.javaconcurrancy.gc.references.ReferenceTypesDemo

# Memory Leaks
java -Xmx128m -cp build/classes/java/main \
  org.example.javaconcurrancy.gc.leaks.MemoryLeakDemo

# GC Comparison (try different GC algorithms)
# G1 GC (default)
java -Xms128m -Xmx256m -XX:+UseG1GC -Xlog:gc* -cp build/classes/java/main \
  org.example.javaconcurrancy.gc.tuning.GCBehaviorDemo

# Parallel GC
java -Xms128m -Xmx256m -XX:+UseParallelGC -Xlog:gc* -cp build/classes/java/main \
  org.example.javaconcurrancy.gc.tuning.GCBehaviorDemo

# Serial GC
java -Xms128m -Xmx256m -XX:+UseSerialGC -Xlog:gc* -cp build/classes/java/main \
  org.example.javaconcurrancy.gc.tuning.GCBehaviorDemo
```

---

## 💡 Pro Tips

### Tip 1: Copy-Paste Helper
Create this alias in your terminal:
```bash
alias runjava="java -cp build/classes/java/main"

# Then run demos easily:
runjava org.example.javaconcurrancy.concurrency.basics.ThreadCreationDemo
```

### Tip 2: Watch GC Activity
Add `-verbose:gc` to any demo to see garbage collection:
```bash
java -verbose:gc -cp build/classes/java/main org.example.javaconcurrancy.gc.memory.MemoryAllocationDemo
```

### Tip 3: Increase Memory for Demos
Some demos work better with more memory:
```bash
java -Xms256m -Xmx512m -cp build/classes/java/main <ClassName>
```

---

## 🎯 Recommended Learning Path

**For Presentation Prep (1-2 hours)**:

1. **Start here** (understand the problem):
   - RaceConditionDemo - See why we need concurrency control
   - DeadlockDemo - See what can go wrong

2. **Learn the solutions**:
   - ExecutorServiceDemo - Modern thread management
   - CompletableFutureDemo - Modern async programming
   - AtomicVariablesDemo - Lock-free operations

3. **Understand memory**:
   - MemoryAllocationDemo - Stack vs Heap
   - ReferenceTypesDemo - Different reference types
   - GCBehaviorDemo - How GC works

4. **Review documentation**:
   - Read PRESENTATION_GUIDE.md (20 min)
   - Skim STUDY_GUIDE.md (30 min)
   - Review INTERVIEW_QUESTIONS.md (15 min)

---

## 🆘 Troubleshooting

**Problem**: `java: command not found`
**Solution**: Install Java 17+ from https://adoptium.net/

**Problem**: `ClassNotFoundException`
**Solution**: Make sure you ran `./gradlew build` first

**Problem**: `OutOfMemoryError`
**Solution**: This is expected for some memory leak demos

**Problem**: Demo hangs
**Solution**: Deadlock demo intentionally hangs. Press Ctrl+C to stop.

---

## 📚 Next Steps

1. ✅ Run all demos once to familiarize yourself
2. ✅ Read PRESENTATION_GUIDE.md for your session structure
3. ✅ Study STUDY_GUIDE.md for deep understanding
4. ✅ Practice explaining demos out loud
5. ✅ Prepare 2-3 real-world examples from your experience

---

**You're ready! Start with the first demo above. Good luck! 🚀**
