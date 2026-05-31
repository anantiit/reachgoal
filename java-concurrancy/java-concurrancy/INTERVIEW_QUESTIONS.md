# 💼 Common Interview Questions
## Java Concurrency & Garbage Collection

---

## 🔄 CONCURRENCY QUESTIONS

### Q1: What is the difference between Thread and Runnable?

**Answer**: 
- **Thread**: A class you extend. Limits inheritance (can't extend another class).
- **Runnable**: An interface you implement. More flexible, allows extending other classes.
- **Best Practice**: Use Runnable (or lambda) for better design flexibility.

```java
// Not recommended
class MyThread extends Thread { }

// Recommended
class MyTask implements Runnable { }
Thread t = new Thread(new MyTask());

// Best (Java 8+)
Thread t = new Thread(() -> { /* work */ });
```

---

### Q2: What is a race condition? How do you prevent it?

**Answer**:
Race condition occurs when multiple threads access shared mutable data concurrently, and at least one modifies it, leading to unpredictable results.

**Prevention**:
1. **synchronized** keyword
2. **Locks** (ReentrantLock)
3. **Atomic variables** (AtomicInteger)
4. **Immutable objects**
5. **Thread-safe collections**

```java
// Problem
counter++; // NOT atomic!

// Solution 1: synchronized
public synchronized void increment() { counter++; }

// Solution 2: Atomic
AtomicInteger counter = new AtomicInteger();
counter.incrementAndGet();
```

---

### Q3: Explain synchronized vs ReentrantLock

**Answer**:

| Feature | synchronized | ReentrantLock |
|---------|--------------|---------------|
| Type | Keyword | Class |
| Fairness | No | Yes (optional) |
| Try lock | No | Yes (tryLock) |
| Timeout | No | Yes (tryLock with timeout) |
| Interruptible | No | Yes |
| Must unlock in finally | No | Yes |
| Multiple conditions | No | Yes |

**When to use**:
- **synchronized**: Simple cases, less code
- **ReentrantLock**: Need advanced features (tryLock, fairness, multiple conditions)

---

### Q4: What is a deadlock? How do you prevent it?

**Answer**:
Deadlock occurs when two or more threads wait for each other indefinitely.

**Four Conditions (all must be true)**:
1. Mutual exclusion
2. Hold and wait
3. No preemption
4. Circular wait

**Prevention**:
1. **Lock ordering**: Always acquire locks in same order
2. **Timeouts**: Use tryLock(timeout)
3. **Avoid nested locks**: Minimize lock nesting
4. **Deadlock detection**: Monitor thread dumps

```java
// Problem: Deadlock
Thread1: lock(A) -> lock(B)
Thread2: lock(B) -> lock(A)

// Solution: Lock ordering
Both threads: lock(A) -> lock(B)
```

---

### Q5: What is the difference between wait() and sleep()?

**Answer**:

| wait() | sleep() |
|--------|---------|
| From Object class | From Thread class |
| Releases lock | Keeps lock |
| Must be in synchronized block | Can be anywhere |
| Woken by notify()/notifyAll() | Woken after timeout |
| For inter-thread communication | For pausing execution |

```java
synchronized(lock) {
    lock.wait(); // Releases lock, waits for notify
}

Thread.sleep(1000); // Keeps locks, just pauses
```

---

### Q6: Explain volatile keyword

**Answer**:
`volatile` ensures visibility of changes to variables across threads.

**Guarantees**:
- Read from main memory (not CPU cache)
- Write to main memory immediately
- No reordering around volatile reads/writes

**Use cases**:
- Flags (boolean done = false)
- State variables
- Double-checked locking

**Limitations**:
- NOT for compound operations (counter++)
- Use Atomic for that

```java
private volatile boolean running = true;

// Thread 1
running = false; // Visible to all threads immediately

// Thread 2
while (running) { } // Sees latest value
```

---

### Q7: What is ThreadLocal? When to use it?

**Answer**:
ThreadLocal provides thread-local variables. Each thread has its own independent copy.

**Use cases**:
- User session context (in web apps)
- Database connections (per-thread)
- SimpleDateFormat (not thread-safe)

**Caution**: Can cause memory leaks in thread pools if not cleaned up.

```java
ThreadLocal<User> userContext = new ThreadLocal<>();

// Set for current thread
userContext.set(user);

// Get for current thread
User user = userContext.get();

// IMPORTANT: Clean up
userContext.remove();
```

---

### Q8: Explain ExecutorService

**Answer**:
Thread pool framework for managing worker threads.

**Benefits**:
- Reuses threads (no overhead of creating new threads)
- Limits resource usage
- Built-in task queue
- Lifecycle management

**Types**:
- `newFixedThreadPool(n)` - Fixed size pool
- `newCachedThreadPool()` - Grows/shrinks as needed
- `newSingleThreadExecutor()` - Single worker thread
- `newScheduledThreadPool(n)` - Scheduled tasks

```java
ExecutorService executor = Executors.newFixedThreadPool(10);
executor.submit(() -> { /* task */ });
executor.shutdown();
executor.awaitTermination(1, TimeUnit.MINUTES);
```

---

### Q9: Difference between Callable and Runnable?

**Answer**:

| Runnable | Callable |
|----------|----------|
| No return value | Returns value |
| Can't throw checked exception | Can throw exception |
| run() method | call() method |
| Older (Java 1.0) | Newer (Java 5) |

```java
Runnable r = () -> { /* no return */ };

Callable<Integer> c = () -> {
    return 42; // Returns value
};

Future<Integer> future = executor.submit(c);
Integer result = future.get();
```

---

### Q10: What is CompletableFuture?

**Answer**:
Modern async programming API (Java 8+). Improvement over Future.

**Features**:
- Non-blocking callbacks
- Chaining operations
- Combining multiple futures
- Exception handling

**Key Methods**:
- `supplyAsync()` - Start async task
- `thenApply()` - Transform result
- `thenCompose()` - Chain dependent futures
- `thenCombine()` - Combine two futures
- `allOf()`, `anyOf()` - Wait for multiple
- `exceptionally()` - Handle errors

```java
CompletableFuture.supplyAsync(() -> fetchUser())
    .thenApply(user -> user.getName())
    .thenAccept(name -> System.out.println(name))
    .exceptionally(ex -> { /* handle error */ return null; });
```

---

## 🗑️ GARBAGE COLLECTION QUESTIONS

### Q11: Explain Stack vs Heap memory

**Answer**:

| Stack | Heap |
|-------|------|
| Stores local variables, method calls | Stores objects |
| Fast, LIFO | Slower, GC-managed |
| Thread-specific | Shared across threads |
| Limited size (~1MB) | Large (configurable GB) |
| Auto-managed | GC-managed |

```java
void method() {
    int x = 10;        // Stack
    String s = "Hi";   // "s" on stack, "Hi" object on heap
    Person p = new Person(); // "p" on stack, Person object on heap
}
```

---

### Q12: What is Garbage Collection?

**Answer**:
Automatic memory management. JVM identifies and reclaims memory occupied by unreachable objects.

**Benefits**:
- No manual memory management
- Prevents memory leaks (mostly)
- Prevents dangling pointers

**How it works**:
1. Mark: Identify reachable objects
2. Sweep: Reclaim unreachable objects
3. Compact: Reduce fragmentation (optional)

---

### Q13: Explain Generational Garbage Collection

**Answer**:
Based on **Weak Generational Hypothesis**: Most objects die young.

**Heap Structure**:
- **Young Generation**: New objects (Eden + Survivor spaces)
  - Minor GC: Frequent, fast
- **Old Generation**: Long-lived objects
  - Major GC: Infrequent, slower

**Process**:
1. Objects created in Eden
2. Survive 1 GC → Survivor space
3. Survive many GCs → Promoted to Old Gen

**Why**: Optimizes for common case (short-lived objects)

---

### Q14: Different types of GC algorithms?

**Answer**:

| GC | Characteristics | Use Case |
|----|-----------------|----------|
| Serial | Single-threaded, simple | Small apps, single CPU |
| Parallel | Multi-threaded, throughput | Batch jobs |
| G1 | Balanced, predictable pauses | General purpose (default) |
| ZGC | Ultra-low latency (<10ms) | Low-latency apps |

**Commands**:
```bash
-XX:+UseSerialGC
-XX:+UseParallelGC
-XX:+UseG1GC  # Default Java 9+
-XX:+UseZGC   # Java 15+
```

---

### Q15: What are reference types in Java?

**Answer**:

1. **Strong Reference** (default): Never GC'd while reachable
   ```java
   Object obj = new Object();
   ```

2. **Weak Reference**: GC'd in next GC even if memory available
   ```java
   WeakReference<Object> weak = new WeakReference<>(obj);
   ```
   Use: Caches that shouldn't prevent GC

3. **Soft Reference**: GC'd only when memory low
   ```java
   SoftReference<Object> soft = new SoftReference<>(obj);
   ```
   Use: Memory-sensitive caches

4. **Phantom Reference**: For cleanup actions
   ```java
   PhantomReference<Object> phantom = new PhantomReference<>(obj, queue);
   ```
   Use: Pre-finalization cleanup

---

### Q16: Can Java have memory leaks?

**Answer**: **YES!** Despite GC, memory leaks occur when:

**Common Causes**:
1. **Static collections** never cleared
2. **Unclosed resources** (connections, files)
3. **Listeners** never unregistered
4. **ThreadLocal** not removed in thread pools
5. **Inner classes** holding outer references

**Example**:
```java
// LEAK
private static List<Object> cache = new ArrayList<>();
cache.add(obj); // Never removed!

// FIX
cache.clear(); // When done
```

---

### Q17: How to tune GC?

**Answer**:

**Heap Sizing**:
```bash
-Xms2g -Xmx2g  # Set min=max (avoid resizing)
```

**GC Selection**:
```bash
-XX:+UseG1GC  # Usually good default
```

**GC Logging**:
```bash
-Xlog:gc*:file=gc.log
```

**Monitoring**:
```bash
-XX:+HeapDumpOnOutOfMemoryError
```

**Methodology**:
1. Measure with GC logs
2. Identify problem (pause times, frequency, OOM)
3. Adjust ONE parameter
4. Measure again
5. Iterate

**Red Flags**:
- Frequent Full GCs → Memory leak or too small heap
- Long pauses → Wrong GC or too large heap

---

### Q18: What is finalize() method?

**Answer**:
Method called by GC before reclaiming object.

**Status**: **DEPRECATED** in Java 9, removed in Java 18

**Why deprecated**:
- Unpredictable when called
- Can resurrect objects
- Impacts GC performance
- No guarantee it will run

**Alternative**: Use `try-with-resources` or `Cleaner` API

```java
// DON'T use finalize
protected void finalize() { }

// DO use try-with-resources
try (Connection conn = getConnection()) {
    // Auto-closed
}
```

---

## 🎯 Scenario-Based Questions

### Q19: How would you debug a production memory leak?

**Answer**:
1. **Monitor**: Watch heap usage over time
2. **GC Logs**: Enable verbose GC logging
3. **Heap Dump**: Take heap dump when memory high
4. **Analyze**: Use Eclipse MAT or VisualVM
5. **Look for**:
   - Large collections
   - Objects with unexpected retention
   - Duplicate objects
6. **Fix**: Remove references, use weak refs, close resources

### Q20: Application is slow. How to check if GC is the issue?

**Answer**:
1. **Enable GC logging**: `-Xlog:gc*`
2. **Check metrics**:
   - GC pause times (>100ms is concerning)
   - GC frequency (too frequent?)
   - Heap usage after GC (growing?)
3. **Look for**:
   - Frequent Full GCs
   - Long pause times
   - Heap usage plateau near max
4. **Actions**:
   - If frequent GC: Increase heap
   - If long pauses: Try different GC (G1 or ZGC)
   - If memory leak: Fix leak first

---

**Use these as flashcards for preparation! Good luck! 🚀**
