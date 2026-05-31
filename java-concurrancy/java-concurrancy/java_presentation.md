# **Advanced Java: Concurrency & GC in Production**
## Principal Engineer Presentation to Development Team

### Assumptions & Environment
- **Java Version:** Examples assume **Java 17** as the baseline. Any features that require **Java 21+** (for example, *virtual threads*) are explicitly labeled as such.
- **Ecosystem:** Core concepts are demonstrated using **plain Java SE**. A few slides include **optional Spring Boot–style examples** (REST controllers, schedulers) to connect the ideas to real-world microservices.
- **Usage Mode:** This deck is designed for **learning and workshops**:
  - each major topic includes **concrete code examples** you can paste into a small demo project;
  - many slides naturally suggest **small exercises** (e.g., “refactor this to use ReadWriteLock”, “add GC logging and inspect the output”).
- **Scope:** Focus is on **in-process concurrency**, **multi‑JVM coordination**, and **GC/heap behavior** in production JVMs.

---

## **TABLE OF CONTENTS**

### **SECTION 1: JAVA CONCURRENCY DEEP DIVE**
- Slide 1: Opening - The Concurrency Imperative
- Slide 2: The Java Memory Model - Foundation
- Slide 3: Race Conditions - The Silent Killer
- Slide 4: Modern Synchronization Arsenal
- Slide 5: Thread Pools - Production Grade
- Slide 6: CompletableFuture - Modern Async
- Slide 7: Virtual Threads (Java 21+)
- Slide 8: Lock-Free Programming - AtomicInteger
- Slide 9: Coordination Utilities
- Slide 10: Deadlock Prevention

### **SECTION 2: MULTI-JVM REALITY - THE DISTRIBUTED CHALLENGE**
- Slide 11: The Harsh Reality of Production
- Slide 12: The Rate Limiter That Failed
- Slide 13: Why JVM Concurrency Still Matters
- Slide 14: Distributed Concurrency Layer
- Slide 15: The Two-Layer Pattern
- Slide 16: Idempotency - The Hidden Requirement
- Slide 17: Cache Coherence Problem
- Slide 18: Technology Stack
- Slide 19: Key Takeaways - Multi-JVM

### **SECTION 3: GARBAGE COLLECTION MASTERY**
- Slide 20: GC Fundamentals
- Slide 21: GC Events Impact
- Slide 22: GC Algorithms - Choosing Wisely
- Slide 23: Memory Leaks in Java
- Slide 24: Reference Types
- Slide 25: GC Tuning Methodology
- Slide 26: Heap Sizing Rules
- Slide 27: Production Monitoring
- Slide 28: Common GC Anti-Patterns
- Slide 29: GC Best Practices
- Slide 30: Summary - GC Mastery

### **CLOSING**
- Slide 31: Bringing It All Together
- Slide 32: Action Items
- Slide 33: Q&A and Discussion

---

# **SECTION 1: JAVA CONCURRENCY DEEP DIVE**

---

## **Slide 1: Opening - The Concurrency Imperative**

### **Title:** Why Concurrency Mastery Separates Good from Great Engineers

### **Content:**

**The Modern Reality:**
- Modern applications: 100s-1000s of concurrent requests per instance
- Single-threaded = Single-core utilization (wasted hardware investment)
- **Critical insight:** Thread safety bugs are production killers - silent, intermittent, catastrophic

**Key Statistics:**
- Race conditions: ~30% harder to debug than regular bugs
- Average cost of production race condition: 4-8 hours of emergency response
- Modern servers: 64-128 cores → Must exploit parallelism

**Why This Matters:**
- Performance: Utilize all available CPU cores
- Responsiveness: Non-blocking I/O and async operations
- Scalability: Handle increasing load without linear hardware growth
- Reliability: Prevent data corruption and race conditions

**Presenter Notes:**
- Start with a real production incident example from your organization
- Emphasize that concurrency bugs often surface only under load
- Set the tone: This is not academic, this is production survival

---

## **Slide 2: The Java Memory Model - Foundation**

### **Title:** Understanding the Real Enemy: Memory Visibility

### **Content:**

**The CPU Cache Hierarchy Reality:**

```
Thread 1 (Core 1)          Thread 2 (Core 2)
L1 Cache: flag=true        L1 Cache: flag=false ← STALE!
    ↓                          ↓
L2 Cache (shared between cores)
    ↓
Main Memory: flag=true
```

**The Problem:**
- Each CPU core has its own L1 cache (fastest, most recent)
- Threads running on different cores may see different values
- Without synchronization, changes may never propagate
- Leads to infinite loops, incorrect state, data corruption

**Critical Takeaways:**
- ❌ Operations are NOT atomic by default (even `count++`)
- ❌ CPU caches create visibility problems
- ✅ `volatile` ensures visibility, NOT atomicity
- ✅ `synchronized` provides BOTH mutual exclusion AND memory visibility

**The Happens-Before Guarantee:**
- Write to volatile → Read from volatile
- Unlock monitor → Lock same monitor
- Thread start → First statement in new thread
- Final statement in thread → Thread.join() returns

**Best Practice:** 
Always assume memory is cached unless proven otherwise. Use proper synchronization primitives.

**Presenter Notes:**
- Draw the cache hierarchy on whiteboard if possible
- Emphasize that this is hardware-level behavior, not a Java quirk
- Mention that this affects ALL multi-threaded programming, not just Java

---

## **Slide 3: Race Conditions - The Silent Killer**

### **Title:** Why `counter++` Loses Data in Production

### **Content:**

**Live Scenario - E-commerce Inventory Bug:**

```java
// Production Bug: E-commerce inventory
private int inventory = 100;

public void purchase() {
    if (inventory > 0) {     // Thread 1 reads 100
        inventory--;          // Thread 2 reads 100
    }                         // Both decrement → inventory = 99 (SOLD 2, LOST 1)
}
```

**What Happened:**
1. Thread 1 checks: `inventory > 0` (true, sees 100)
2. Thread 2 checks: `inventory > 0` (true, sees 100)
3. Thread 1 decrements: `inventory = 99`
4. Thread 2 decrements: `inventory = 99`
5. **Expected:** 98, **Actual:** 99 (one sale lost!)

**Real Impact:**
- Overselling inventory → backorders, customer dissatisfaction
- Financial losses (fulfilled orders at a loss)
- Compliance violations (promised but can't deliver)
- Data integrity issues cascade to other systems

**Why It's Hard to Catch:**
- Works fine in development (low concurrency)
- Manifests only under high load
- Non-deterministic (happens randomly)
- No exceptions thrown

**Engineering Solution - AtomicInteger:**

```java
private final AtomicInteger inventory = new AtomicInteger(100);

public boolean purchase() {
    // Compare-and-swap loop - thread-safe
    return inventory.updateAndGet(current ->
        current > 0 ? current - 1 : current
    ) >= 0;
}
```

**Alternative - Synchronized:**

```java
private int inventory = 100;

public synchronized boolean purchase() {
    if (inventory > 0) {
        inventory--;
        return true;
    }
    return false;
}
```

**Presenter Notes:**
- Ask audience if they've experienced similar bugs
- Emphasize financial impact - makes it real
- Show that even simple operations need protection

---

## **Slide 4: Modern Synchronization Arsenal**

### **Title:** Choose Your Weapon Wisely

### **Content:**

**Synchronization Primitives Comparison:**

| Primitive | Use Case | Performance | Complexity | Interruptible |
|-----------|----------|-------------|------------|---------------|
| **synchronized** | General-purpose, simple critical sections | Medium | Low | ❌ No |
| **ReentrantLock** | Timeout needs, fairness, try-lock | Medium | Medium | ✅ Yes |
| **ReadWriteLock** | Read-heavy (10:1+ ratio) | High (for reads) | Medium | ✅ Yes |
| **StampedLock** | Optimistic reads, Java 8+ | Highest | High | Partial |
| **Atomic classes** | Counters, flags, simple CAS | Highest | Low-Medium | N/A |

**Decision Framework:**

```
START
  ↓
Need lock? → No → Use Atomic classes (if simple operation)
  ↓ Yes
  ↓
Simple critical section? → Yes → Use synchronized
  ↓ No
  ↓
Need timeout/tryLock? → Yes → Use ReentrantLock
  ↓ No
  ↓
Read-heavy workload (>80% reads)? → Yes → Use ReadWriteLock
  ↓ No
  ↓
Use ReentrantLock (most flexible)
```

**Code Examples:**

**1. synchronized (Simple, default choice):**
```java
public synchronized void increment() {
    count++;
}

// Or block-level
synchronized(lock) {
    // Critical section
}
```

**2. ReentrantLock (Advanced features):**
```java
private final ReentrantLock lock = new ReentrantLock();

public void processWithTimeout() {
    if (lock.tryLock(1, TimeUnit.SECONDS)) {
        try {
            // Critical section
        } finally {
            lock.unlock(); // MUST be in finally
        }
    } else {
        // Handle timeout
    }
}
```

**3. ReadWriteLock (Optimize reads):**
```java
private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

public String read() {
    rwLock.readLock().lock();
    try {
        return data; // Multiple readers can access
    } finally {
        rwLock.readLock().unlock();
    }
}

public void write(String value) {
    rwLock.writeLock().lock();
    try {
        data = value; // Exclusive write access
    } finally {
        rwLock.writeLock().unlock();
    }
}
```

**4. AtomicInteger (Lock-free):**
```java
private final AtomicInteger counter = new AtomicInteger(0);

public void increment() {
    counter.incrementAndGet(); // Thread-safe, no locks
}
```

**Performance Characteristics:**

```
Contention Level:  Low          Medium        High
synchronized:      ████████     ██████        ████
ReentrantLock:     ████████     ███████       █████
ReadWriteLock:     ██████████   █████████     ███████ (read-heavy)
AtomicInteger:     ██████████   ████████      ██████ (spins under high contention)
```

**Best Practices:**
- ✅ Default to `synchronized` unless you need specific features
- ✅ Use `ReentrantLock` when you need tryLock, timeout, or fairness
- ✅ Use `ReadWriteLock` for caches and read-heavy data structures
- ✅ Use `Atomic` classes for simple counters and flags
- ❌ Don't over-engineer - synchronized is often sufficient

**Presenter Notes:**
- Emphasize that synchronized is JIT-optimized in modern JVMs
- Show that ReentrantLock is more code but more control
- Real-world example: Cache uses ReadWriteLock (many reads, few writes)

---

## **Slide 5: Thread Pools - Production Grade**

### **Title:** Never Create Threads Manually in Production

### **Content:**

**Anti-Pattern (Memory Bomb):**

```java
// DON'T DO THIS IN PRODUCTION!
for (Request req : requests) {
    new Thread(() -> process(req)).start();
    // 10,000 requests = 10,000 threads = ~10GB RAM!
}
```

**Problems:**
- ❌ Each thread: ~1MB stack memory
- ❌ Thread creation overhead: ~1ms per thread
- ❌ Context switching overhead with 1000s of threads
- ❌ No queue, no backpressure, no lifecycle management
- ❌ OutOfMemoryError under load

**Production Pattern - Thread Pool:**

```java
private final ExecutorService executor = Executors.newFixedThreadPool(
    Runtime.getRuntime().availableProcessors()
);

// Handles unlimited requests with bounded threads
public void handleRequest(Request req) {
    executor.submit(() -> process(req));
}

// Proper shutdown
@PreDestroy
public void shutdown() {
    executor.shutdown();
    executor.awaitTermination(30, TimeUnit.SECONDS);
}
```

**Thread Pool Sizing Formula:**

**CPU-Bound Tasks:**
```
Optimal Threads = Number of CPU Cores
```

```java
int cores = Runtime.getRuntime().availableProcessors();
ExecutorService executor = Executors.newFixedThreadPool(cores);
```

**I/O-Bound Tasks:**
```
Optimal Threads = Cores * (1 + Wait Time / Compute Time)
```

```java
// Example: 8 cores, 80% I/O wait (waitTime/computeTime = 4)
// Optimal = 8 * (1 + 4) = 40 threads
ExecutorService executor = Executors.newFixedThreadPool(40);
```

**Types of Thread Pools:**

**1. FixedThreadPool - Most Common:**
```java
ExecutorService executor = Executors.newFixedThreadPool(10);
// Fixed number of threads, unbounded queue
// Use when: Predictable workload, want to limit concurrency
```

**2. CachedThreadPool - Elastic:**
```java
ExecutorService executor = Executors.newCachedThreadPool();
// Creates threads on demand, reuses idle threads
// Use when: Unpredictable bursts, many short-lived tasks
// Warning: Can create unlimited threads!
```

**3. ScheduledThreadPool - Periodic Tasks:**
```java
ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

// Run once after delay
scheduler.schedule(() -> cleanup(), 1, TimeUnit.HOURS);

// Run periodically
scheduler.scheduleAtFixedRate(() -> healthCheck(), 0, 30, TimeUnit.SECONDS);
```

**4. SingleThreadExecutor - Sequential:**
```java
ExecutorService executor = Executors.newSingleThreadExecutor();
// All tasks execute sequentially (FIFO)
// Use when: Need guaranteed ordering, event-driven systems
```

**Custom Thread Pool (Production-grade):**

```java
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    10,                              // corePoolSize
    50,                              // maximumPoolSize
    60, TimeUnit.SECONDS,            // keepAliveTime
    new ArrayBlockingQueue<>(1000),  // workQueue (bounded!)
    new ThreadPoolExecutor.CallerRunsPolicy() // rejectionPolicy
);

// With custom thread factory for naming
executor.setThreadFactory(r -> {
    Thread t = new Thread(r, "MyApp-Worker-" + counter.incrementAndGet());
    t.setDaemon(false);
    return t;
});
```

**Rejection Policies:**

| Policy | Behavior | Use When |
|--------|----------|----------|
| **AbortPolicy** (default) | Throws RejectedExecutionException | Fail-fast, can't lose tasks |
| **CallerRunsPolicy** | Runs task in caller's thread | Natural backpressure |
| **DiscardPolicy** | Silently discards task | Tasks are optional |
| **DiscardOldestPolicy** | Discards oldest queued task | New tasks more important |

**Monitoring Thread Pools:**

```java
ThreadPoolExecutor tpe = (ThreadPoolExecutor) executor;

// Metrics
int activeCount = tpe.getActiveCount();
int poolSize = tpe.getPoolSize();
long completedTasks = tpe.getCompletedTaskCount();
int queueSize = tpe.getQueue().size();

// Alert if queue is filling up
if (queueSize > 800) {
    logger.warn("Thread pool queue at {}%, consider scaling",
                queueSize / 10.0);
}
```

**Presenter Notes:**
- Show thread dump of app with 1000s of threads vs proper pool
- Emphasize that thread pools are mandatory in production
- Discuss how to choose pool size (measure, don't guess)

---

## **Slide 6: CompletableFuture - Modern Async**

### **Title:** Say Goodbye to Callback Hell

### **Content:**

**Old Way (Blocking, Sequential):**

```java
// Blocking approach - wastes threads
public DashboardData getDashboard(Long userId) {
    User user = userService.getUser(userId);           // Blocks 100ms
    Orders orders = orderService.getOrders(userId);     // Blocks 150ms
    Payment payment = paymentService.getPayment(userId); // Blocks 80ms

    return new DashboardData(user, orders, payment);
    // Total: 330ms sequential
}
```

**Modern Way (Non-blocking, Parallel):**

```java
public CompletableFuture<DashboardData> getDashboard(Long userId) {
    CompletableFuture<User> userFuture =
        CompletableFuture.supplyAsync(() -> userService.getUser(userId));

    CompletableFuture<Orders> ordersFuture =
        CompletableFuture.supplyAsync(() -> orderService.getOrders(userId));

    CompletableFuture<Payment> paymentFuture =
        CompletableFuture.supplyAsync(() -> paymentService.getPayment(userId));

    // Combine all three
    return CompletableFuture.allOf(userFuture, ordersFuture, paymentFuture)
        .thenApply(v -> new DashboardData(
            userFuture.join(),
            ordersFuture.join(),
            paymentFuture.join()
        ));

    // Total: 150ms parallel (longest operation wins)
    // Performance Gain: 2.2x faster
}
```

**CompletableFuture Composition Patterns:**

**1. thenApply - Transform Result:**
```java
CompletableFuture<Integer> future = CompletableFuture
    .supplyAsync(() -> fetchUserId())
    .thenApply(userId -> fetchUserDetails(userId))
    .thenApply(details -> details.getAge());
```

**2. thenCompose - Chain Async Operations:**
```java
CompletableFuture<OrderStatus> future = CompletableFuture
    .supplyAsync(() -> validateOrder(orderId))
    .thenCompose(validOrder ->
        CompletableFuture.supplyAsync(() -> processPayment(validOrder))
    )
    .thenCompose(payment ->
        CompletableFuture.supplyAsync(() -> shipOrder(payment))
    );
```

**3. thenCombine - Combine Two Independent Futures:**
```java
CompletableFuture<String> weather =
    CompletableFuture.supplyAsync(() -> fetchWeather());

CompletableFuture<String> stocks =
    CompletableFuture.supplyAsync(() -> fetchStocks());

CompletableFuture<Dashboard> combined = weather.thenCombine(stocks,
    (w, s) -> new Dashboard(w, s)
);
```

**4. allOf - Wait for All:**
```java
CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> task1());
CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> task2());
CompletableFuture<String> f3 = CompletableFuture.supplyAsync(() -> task3());

CompletableFuture.allOf(f1, f2, f3).thenRun(() -> {
    // All completed
    System.out.println(f1.join() + f2.join() + f3.join());
});
```

**5. anyOf - Wait for First:**
```java
// Call multiple services, use fastest response
CompletableFuture<String> server1 = callServer("http://api1.com");
CompletableFuture<String> server2 = callServer("http://api2.com");
CompletableFuture<String> server3 = callServer("http://api3.com");

CompletableFuture<Object> fastest = CompletableFuture.anyOf(
    server1, server2, server3
);

String result = (String) fastest.join(); // Returns first to complete
```

**Error Handling:**

```java
CompletableFuture<String> future = CompletableFuture
    .supplyAsync(() -> {
        if (Math.random() > 0.5) {
            throw new RuntimeException("Service error");
        }
        return "Success";
    })
    .exceptionally(ex -> {
        logger.error("Error occurred", ex);
        return "Fallback value";
    })
    .thenApply(result -> result.toUpperCase());
```

**handle - Process Both Success and Failure:**
```java
CompletableFuture<String> future = CompletableFuture
    .supplyAsync(() -> riskyOperation())
    .handle((result, ex) -> {
        if (ex != null) {
            return "Error: " + ex.getMessage();
        } else {
            return "Success: " + result;
        }
    });
```

**whenComplete - Side Effects:**
```java
CompletableFuture<String> future = CompletableFuture
    .supplyAsync(() -> fetchData())
    .whenComplete((result, ex) -> {
        if (ex != null) {
            logger.error("Failed", ex);
            metrics.incrementErrors();
        } else {
            logger.info("Success: {}", result);
            metrics.incrementSuccess();
        }
    });
```

**Best Practices:**
- ✅ Use for I/O-bound operations (API calls, DB queries)
- ✅ Combine independent operations with `thenCombine` or `allOf`
- ✅ Always handle exceptions (exceptionally, handle, whenComplete)
- ✅ Don't block on `join()` or `get()` in async code
- ❌ Don't use for CPU-bound tasks (use ForkJoinPool)

**Presenter Notes:**
- Show actual latency improvements with parallel calls
- Emphasize non-blocking = better thread utilization
- Compare to JavaScript Promises (similar concept)

---

## **Slide 7: Virtual Threads (Java 21+)**

### **Title:** The Game Changer for I/O-Heavy Applications

### **Content:**

**Traditional Platform Threads (Pre-Java 21):**
- OS-managed (expensive to create)
- ~1MB stack per thread
- Max ~few thousand threads per JVM
- Context switching overhead
- Thread-per-request model doesn't scale

**Virtual Threads (Java 21+):**
- JVM-managed (lightweight)
- Tiny memory footprint (~few KB)
- **Millions** of virtual threads possible
- Mounted on platform threads only when needed
- Write blocking code that scales like async code

**Architecture:**

```
Traditional Model:
Request → Platform Thread (1MB) → Blocks on I/O
10,000 requests = 10,000 threads = 10GB RAM ❌

Virtual Thread Model:
Request → Virtual Thread (few KB) → Unmounted during I/O
10,000 requests = 10,000 virtual threads = ~100MB RAM ✅
```

**Code Example - Before (Platform Threads):**

```java
// Limited by thread pool size
ExecutorService executor = Executors.newFixedThreadPool(200);

for (int i = 0; i < 10000; i++) {
    executor.submit(() -> {
        handleRequest(); // Blocks thread during I/O
    });
}
// Can only handle 200 concurrent requests
// 9,800 requests wait in queue
```

**Code Example - After (Virtual Threads):**

```java
// No limit - creates virtual thread per task
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    for (int i = 0; i < 10000; i++) {
        executor.submit(() -> {
            handleRequest(); // Thread unmounted during I/O
        });
    }
}
// All 10,000 requests handled concurrently!
```

**Simple API - Create Virtual Threads:**

```java
// Method 1: Direct creation
Thread.startVirtualThread(() -> {
    processApiRequest();
});

// Method 2: Builder
Thread vThread = Thread.ofVirtual()
    .name("api-handler")
    .start(() -> processApiRequest());

// Method 3: ExecutorService (recommended)
ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
executor.submit(() -> processApiRequest());
```

**Use Case - High-Concurrency REST API:**

```java
@RestController
public class OrderController {

    @GetMapping("/orders/{id}")
    public Order getOrder(@PathVariable Long id) {
        // This runs on a virtual thread
        // Can block freely - thread is cheap!

        // Call external API (blocks 100ms)
        PaymentInfo payment = paymentClient.getPayment(id);

        // Query database (blocks 50ms)
        Order order = orderRepository.findById(id);

        return order;
        // Thread unmounted during I/O, remounted when ready
        // Scales to millions of concurrent requests
    }
}
```

**Spring Boot Configuration:**

```properties
# application.properties (Spring Boot 3.2+)
spring.threads.virtual.enabled=true
```

**Performance Comparison:**

```
Benchmark: 10,000 concurrent requests, each blocks for 100ms

Platform Threads (pool of 200):
- Time: 5000ms (queue backlog)
- Memory: 200MB
- Throughput: 2000 req/sec

Virtual Threads:
- Time: 100ms (all concurrent)
- Memory: 50MB
- Throughput: 100,000 req/sec
- 50x improvement!
```

**When to Use Virtual Threads:**
- ✅ High-concurrency I/O-bound applications
- ✅ REST API servers (millions of requests)
- ✅ Database I/O operations
- ✅ Network calls, file I/O
- ✅ Microservices making many external calls

**When NOT to Use:**
- ❌ CPU-intensive computations (no benefit)
- ❌ Using synchronized excessively (pinning issue)
- ❌ Java < 21

**Pinning Issue - Be Aware:**

```java
// BAD: Virtual thread can't unmount while in synchronized
synchronized(lock) {
    blockingIOCall(); // Virtual thread "pinned" to platform thread
}

// GOOD: Use ReentrantLock instead
lock.lock();
try {
    blockingIOCall(); // Virtual thread can unmount
} finally {
    lock.unlock();
}
```

**Migration Path:**
1. Upgrade to Java 21+
2. Enable virtual threads in thread pools
3. Test under load
4. Monitor for pinning issues
5. Replace synchronized with ReentrantLock where needed

**Presenter Notes:**
- This is a paradigm shift - biggest change since Java 8
- Simplifies async programming (write synchronous code that scales)
- Show metrics from migrated service if available
- Mention that Spring Boot 3.2+ has built-in support

---

## **Slide 8: Lock-Free Programming - AtomicInteger**

### **Title:** CAS: The Foundation of High-Performance Concurrency

### **Content:**

**Compare-And-Swap (CAS) - Hardware Atomic Operation:**

```java
// Pseudo-code of CAS operation
boolean compareAndSwap(int expected, int newValue) {
    // ALL AS ONE ATOMIC CPU INSTRUCTION
    if (this.value == expected) {
        this.value = newValue;
        return true; // Success
    } else {
        return false; // Someone else changed it
    }
}
```

**How CAS Works:**
1. Read current value
2. Compute new value
3. Atomically check if value hasn't changed
4. If unchanged, update; if changed, retry

**AtomicInteger Implementation:**

```java
public class AtomicInteger {
    private volatile int value;

    public final int incrementAndGet() {
        int current, next;
        do {
            current = value;
            next = current + 1;
        } while (!compareAndSet(current, next));
        return next;
    }
}
```

**Usage Example:**

```java
// Thread-safe counter without locks
AtomicInteger counter = new AtomicInteger(0);

// 10 threads, each incrementing 100,000 times
ExecutorService executor = Executors.newFixedThreadPool(10);
for (int i = 0; i < 10; i++) {
    executor.submit(() -> {
        for (int j = 0; j < 100_000; j++) {
            counter.incrementAndGet(); // Lock-free, thread-safe
        }
    });
}

executor.shutdown();
executor.awaitTermination(1, TimeUnit.MINUTES);

System.out.println(counter.get()); // Always 1,000,000 ✓
```

**Performance Comparison (10 threads, 100K increments each):**

```
synchronized:          85ms  ████████████████████
ReentrantLock:         78ms  ███████████████████
AtomicInteger:         45ms  ██████████  (1.89x faster!)
```

**Atomic Classes Family:**

**Numeric Atomics:**
```java
AtomicInteger counter = new AtomicInteger(0);
counter.incrementAndGet();        // ++counter
counter.getAndIncrement();        // counter++
counter.addAndGet(5);             // counter += 5
counter.compareAndSet(10, 20);    // if (counter == 10) counter = 20

AtomicLong longCounter = new AtomicLong(0);
// Same methods as AtomicInteger

AtomicBoolean flag = new AtomicBoolean(false);
flag.compareAndSet(false, true);  // Set to true if currently false
```

**Reference Atomics:**
```java
AtomicReference<User> currentUser = new AtomicReference<>();

currentUser.set(new User("John"));
User user = currentUser.get();

// Atomic update
currentUser.updateAndGet(current -> {
    if (current.isActive()) {
        return new User(current.name + "-active");
    }
    return current;
});

// CAS
User expected = currentUser.get();
User newUser = new User("Jane");
boolean updated = currentUser.compareAndSet(expected, newUser);
```

**Array Atomics:**
```java
AtomicIntegerArray array = new AtomicIntegerArray(10);

array.set(0, 100);
array.incrementAndGet(0);           // Increment array[0]
array.compareAndSet(0, 101, 200);   // CAS on array[0]
```

**Real-World Example - Metrics Collection:**

```java
public class MetricsCollector {
    private final AtomicLong requestCount = new AtomicLong(0);
    private final AtomicLong errorCount = new AtomicLong(0);
    private final AtomicReference<LocalDateTime> lastUpdate =
        new AtomicReference<>(LocalDateTime.now());

    public void recordRequest() {
        requestCount.incrementAndGet();
        lastUpdate.set(LocalDateTime.now());
    }

    public void recordError() {
        errorCount.incrementAndGet();
    }

    public Metrics getMetrics() {
        return new Metrics(
            requestCount.get(),
            errorCount.get(),
            lastUpdate.get()
        );
    }
}
```

**Advanced - ABA Problem:**

```
Thread 1: Reads value A
Thread 2: Changes A → B → A (back to original!)
Thread 1: CAS succeeds (sees A) but doesn't know it changed!
```

**Solution - AtomicStampedReference:**

```java
AtomicStampedReference<User> ref =
    new AtomicStampedReference<>(user, 0); // Initial stamp = 0

int[] stampHolder = new int[1];
User current = ref.get(stampHolder);
int stamp = stampHolder[0];

// CAS with stamp check
boolean success = ref.compareAndSet(
    current,        // expected reference
    newUser,        // new reference
    stamp,          // expected stamp
    stamp + 1       // new stamp
);
// Fails if reference changed back to same value but stamp different
```

**When to Use Atomics:**
- ✅ Simple counters, metrics
- ✅ Flags and boolean state
- ✅ Single-variable updates
- ✅ Low-to-medium contention
- ❌ Complex multi-step operations
- ❌ Need to coordinate multiple variables

**Performance Characteristics:**

```
Contention:  Low      Medium    High
Atomic:      ⚡⚡⚡    ⚡⚡       ⚡  (spins, wastes CPU)
Lock:        ⚡⚡      ⚡⚡       ⚡⚡ (blocks, sleeps)
```

**Best Practices:**
- Use for simple operations (increment, set, CAS)
- Avoid complex updateAndGet logic (keep it simple)
- Monitor under high contention (may spin excessively)
- Combine with locks for multi-variable coordination

**Presenter Notes:**
- Emphasize that Atomics are lock-free, not wait-free
- Show CAS retry loop to illustrate spin behavior
- Mention that under extreme contention, locks may be better

---

## **Slide 9: Coordination Utilities**

### **Title:** Don't Reinvent wait()/notify()

### **Content:**

Java provides high-level utilities for thread coordination. Use them instead of low-level wait/notify.

**1. CountDownLatch - Wait for N Events:**

**Purpose:** One or more threads wait until N operations complete

```java
// Service startup coordination
CountDownLatch startupLatch = new CountDownLatch(3);

executor.submit(() -> {
    database.start();
    System.out.println("Database started");
    startupLatch.countDown();
});

executor.submit(() -> {
    cache.start();
    System.out.println("Cache started");
    startupLatch.countDown();
});

executor.submit(() -> {
    messageQueue.start();
    System.out.println("Message queue started");
    startupLatch.countDown();
});

// Main thread waits for all services
if (!startupLatch.await(30, TimeUnit.SECONDS)) {
    throw new IllegalStateException("Services failed to start");
}

System.out.println("All services ready!");
```

**Key Methods:**
- `countDown()`: Decrement count
- `await()`: Block until count reaches zero
- `await(timeout, unit)`: Block with timeout
- `getCount()`: Get current count

**Use Cases:**
- Service initialization
- Waiting for parallel tasks to complete
- Testing (wait for background operations)

---

**2. CyclicBarrier - Threads Wait for Each Other:**

**Purpose:** N threads wait for each other at a barrier point (reusable)

```java
CyclicBarrier barrier = new CyclicBarrier(4, () -> {
    System.out.println("All players ready! Starting game round...");
});

// 4 player threads
for (int i = 1; i <= 4; i++) {
    final int playerId = i;
    new Thread(() -> {
        while (gameRunning) {
            prepareForRound(playerId);
            System.out.println("Player " + playerId + " ready");

            barrier.await(); // Wait for all 4 players

            playRound(playerId);
            barrier.await(); // Wait again for next round (reusable!)
        }
    }).start();
}
```

**Difference from CountDownLatch:**
- ✅ Reusable (resets after all threads arrive)
- ✅ Threads wait for each other (mutual wait)
- ✅ Optional barrier action (runs when all arrive)
- ❌ Can't decrease count dynamically

**Use Cases:**
- Parallel algorithms (phases)
- Game synchronization
- Iterative computations

---

**3. Semaphore - Limit Concurrent Access:**

**Purpose:** Control number of threads accessing a resource

```java
// Database connection pool with max 20 connections
Semaphore dbPool = new Semaphore(20);

public void executeQuery(String sql) {
    dbPool.acquire(); // Blocks if no permits available
    try {
        Connection conn = getConnection();
        // Execute query
        conn.executeQuery(sql);
    } finally {
        dbPool.release(); // MUST release in finally
    }
}
```

**Advanced - Try Acquire:**
```java
if (dbPool.tryAcquire(2, TimeUnit.SECONDS)) {
    try {
        // Got permit within timeout
        executeQuery();
    } finally {
        dbPool.release();
    }
} else {
    throw new TimeoutException("Connection pool exhausted");
}
```

**Fairness:**
```java
Semaphore fairSemaphore = new Semaphore(10, true); // FIFO order
```

**Use Cases:**
- Connection pools (DB, HTTP clients)
- Rate limiting
- Resource pools (threads, sockets)

---

**4. BlockingQueue - Producer-Consumer:**

**Purpose:** Thread-safe queue with blocking operations

```java
BlockingQueue<Task> taskQueue = new ArrayBlockingQueue<>(100);

// Producer thread
new Thread(() -> {
    while (running) {
        Task task = generateTask();
        taskQueue.put(task); // Blocks if queue is full
    }
}).start();

// Consumer thread
new Thread(() -> {
    while (running) {
        Task task = taskQueue.take(); // Blocks if queue is empty
        processTask(task);
    }
}).start();
```

**Types of BlockingQueue:**

| Type | Capacity | Ordering | Use Case |
|------|----------|----------|----------|
| **ArrayBlockingQueue** | Bounded (fixed) | FIFO | Fixed buffer size |
| **LinkedBlockingQueue** | Optionally bounded | FIFO | Flexible sizing |
| **PriorityBlockingQueue** | Unbounded | Priority | Task prioritization |
| **SynchronousQueue** | 0 (hand-off) | N/A | Direct hand-off |
| **DelayQueue** | Unbounded | Delay expiration | Scheduled tasks |

**Advanced Methods:**
```java
// Try operations with timeout
boolean added = queue.offer(task, 1, TimeUnit.SECONDS);
Task task = queue.poll(1, TimeUnit.SECONDS);

// Drain to collection
List<Task> tasks = new ArrayList<>();
queue.drainTo(tasks, 50); // Remove up to 50 elements
```

**Real-World Example - Task Processing:**
```java
@Service
public class TaskProcessor {
    private final BlockingQueue<Task> queue =
        new LinkedBlockingQueue<>(1000);

    @PostConstruct
    public void startWorkers() {
        for (int i = 0; i < 10; i++) {
            Thread worker = new Thread(() -> {
                while (!Thread.interrupted()) {
                    try {
                        Task task = queue.take();
                        processTask(task);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            worker.start();
        }
    }

    public void submitTask(Task task) {
        if (!queue.offer(task)) {
            throw new IllegalStateException("Queue full");
        }
    }
}
```

**Use Cases:**
- Task queues
- Message passing between threads
- Work stealing algorithms
- Producer-consumer patterns

**Presenter Notes:**
- These are production-tested, optimized implementations
- Don't reinvent with wait/notify (error-prone)
- Show how these simplify complex coordination

---

## **Slide 10: Deadlock Prevention**

### **Title:** The Four Horsemen of Deadlock

### **Content:**

**Deadlock Definition:**
Two or more threads wait for each other indefinitely, unable to proceed.

**Conditions for Deadlock (ALL must be true):**
1. **Mutual Exclusion**: Resources can't be shared
2. **Hold and Wait**: Thread holds resource while waiting for another
3. **No Preemption**: Resources can't be forcibly taken
4. **Circular Wait**: Thread A waits for B, B waits for A

**Classic Deadlock Example:**

```java
Object lock1 = new Object();
Object lock2 = new Object();

// Thread 1
new Thread(() -> {
    synchronized(lock1) {
        System.out.println("Thread 1: Holding lock1");
        Thread.sleep(100);

        System.out.println("Thread 1: Waiting for lock2");
        synchronized(lock2) { // Blocks waiting for Thread 2 to release
            System.out.println("Thread 1: Acquired lock2");
        }
    }
}).start();

// Thread 2
new Thread(() -> {
    synchronized(lock2) {
        System.out.println("Thread 2: Holding lock2");
        Thread.sleep(100);

        System.out.println("Thread 2: Waiting for lock1");
        synchronized(lock1) { // Blocks waiting for Thread 1 to release
            System.out.println("Thread 2: Acquired lock1");
        }
    }
}).start();

// DEADLOCK! Both threads wait forever
```

**Prevention Strategy 1: Lock Ordering**

Break circular wait by always acquiring locks in consistent order:

```java
public void transfer(Account from, Account to, BigDecimal amount) {
    // Always lock accounts in ID order
    Account first = from.getId() < to.getId() ? from : to;
    Account second = first == from ? to : from;

    synchronized(first) {
        synchronized(second) {
            // Now safe from deadlock
            from.debit(amount);
            to.credit(amount);
        }
    }
}
```

**Prevention Strategy 2: Timeout with tryLock**

```java
ReentrantLock lock1 = new ReentrantLock();
ReentrantLock lock2 = new ReentrantLock();

public void processWithTimeout() {
    boolean lock1Acquired = false;
    boolean lock2Acquired = false;

    try {
        lock1Acquired = lock1.tryLock(1, TimeUnit.SECONDS);
        if (!lock1Acquired) {
            throw new TimeoutException("Couldn't acquire lock1");
        }

        lock2Acquired = lock2.tryLock(1, TimeUnit.SECONDS);
        if (!lock2Acquired) {
            throw new TimeoutException("Couldn't acquire lock2");
        }

        // Both locks acquired - proceed
        criticalSection();

    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    } finally {
        if (lock2Acquired) lock2.unlock();
        if (lock1Acquired) lock1.unlock();
    }
}
```

**Prevention Strategy 3: Lock-Free Algorithms**

```java
// Use atomics instead of locks when possible
AtomicReference<Account> accountRef = new AtomicReference<>();

public void updateBalance(BigDecimal amount) {
    accountRef.updateAndGet(current -> {
        Account updated = current.copy();
        updated.addBalance(amount);
        return updated;
    });
}
```

**Detection in Production:**

**1. Thread Dump Analysis:**
```bash
jstack <pid> > thread_dump.txt
# Look for "waiting to lock" and "locked by"
```

**2. JConsole/VisualVM:**
- Deadlock detection tab shows circular dependencies

**3. Programmatic Detection:**
```java
ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();

if (deadlockedThreads != null) {
    ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(deadlockedThreads);
    for (ThreadInfo threadInfo : threadInfos) {
        logger.error("Deadlocked thread: {}", threadInfo.getThreadName());
    }
}
```

**Best Practices:**
- ✅ Always acquire locks in consistent order
- ✅ Use tryLock with timeout instead of lock()
- ✅ Keep critical sections short
- ✅ Use lock-free alternatives when possible
- ✅ Monitor thread dumps regularly
- ❌ Never hold multiple locks if avoidable
- ❌ Don't call external code while holding locks

**Presenter Notes:**
- Show real thread dump from production deadlock
- Emphasize that deadlock = complete system freeze
- Discuss monitoring and alerting strategies

---

# **SECTION 2: MULTI-JVM REALITY - THE DISTRIBUTED CHALLENGE**

---

## **Slide 11: The Harsh Reality of Production**

### **Title:** Your Perfect Concurrency Code Just Became Useless (Kind Of)

### **Content:**

**Development Environment (Single JVM):**
```
┌─────────────────────────────┐
│      Your Application       │
│                             │
│  ✅ synchronized works      │
│  ✅ AtomicInteger works     │
│  ✅ ReentrantLock works     │
│  ✅ All concurrency perfect │
└─────────────────────────────┘

Everything thread-safe!
All tests pass!
```

**Production Environment (Multi-JVM Horizontal Scaling):**
```
                Load Balancer
                      ↓
      ┌───────────────┼───────────────┐
      │               │               │
  ┌───▼───┐       ┌───▼───┐       ┌───▼───┐
  │ JVM 1 │       │ JVM 2 │       │ JVM 3 │
  │       │       │       │       │       │
  │ ✅ sync│       │ ✅ sync│       │ ✅ sync│
  │ works │       │ works │       │ works │
  └───────┘       └───────┘       └───────┘

  ✅ Thread-safe WITHIN each JVM
  ❌ NO synchronization ACROSS JVMs!
```

**The Hard Truth:**
- Each JVM is a **separate process** with its own memory
- JVMs don't share heap memory
- `synchronized` blocks only affect threads **within the same JVM**
- `AtomicInteger` counters are **per-JVM**, not global
- Static variables are **per-JVM**, not shared

**Example of the Problem:**

```java
// This code is thread-safe WITHIN one JVM
@Service
public class VisitorCounter {
    private final AtomicInteger count = new AtomicInteger(0);

    public int incrementAndGet() {
        return count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }
}

// With 3 JVM instances:
// User makes request → JVM 1: count = 1
// User makes request → JVM 2: count = 1  ← Same user, different JVM!
// User makes request → JVM 3: count = 1
// Total visits: 3, but each JVM shows count = 1
```

**What Still Works:**
- ✅ Thread safety **within** each JVM instance
- ✅ Handling concurrent requests in one instance
- ✅ Local caching
- ✅ Thread pools
- ✅ Request-scoped data

**What Breaks:**
- ❌ Global counters/metrics
- ❌ Distributed locks
- ❌ Unique ID generation
- ❌ Rate limiting across instances
- ❌ Leader election
- ❌ Cache coherence

**Presenter Notes:**
- This is the biggest "gotcha" for developers moving to production
- Emphasize that JVM concurrency is NOT obsolete, just insufficient alone
- Real story: Production incident where counter was off by 4x (4 instances)

---

## **Slide 12: The Rate Limiter That Failed**

### **Title:** Case Study - Production Incident Report

### **Content:**

**Background:**
- E-commerce API with rate limiting: 1000 requests/minute per user
- Deployed to 4 JVM instances behind load balancer
- Used "thread-safe" Java code

**The Code (Looks Correct):**

```java
@Service
public class RateLimiter {
    private final ConcurrentHashMap<String, AtomicInteger> counters =
        new ConcurrentHashMap<>();

    // Reset counters every minute
    @Scheduled(fixedRate = 60000)
    public void reset() {
        counters.clear();
    }

    public boolean allowRequest(String userId) {
        AtomicInteger counter = counters.computeIfAbsent(
            userId,
            k -> new AtomicInteger(0)
        );

        int count = counter.incrementAndGet();

        if (count <= 1000) {
            return true;  // Allow
        } else {
            logger.warn("Rate limit exceeded for user: {}", userId);
            return false; // Block
        }
    }
}
```

**Incident Timeline:**

**Week 1:** Deployed to production (4 instances)
**Week 2:** Customer reports making 4000+ requests/minute without being blocked
**Week 3:** Investigation begins

**Root Cause Analysis:**

```
User ABC makes 4000 requests in 1 minute:

Load Balancer distributes evenly:
├─ JVM 1: Receives 1000 requests
│  └─ counter[ABC] = 1000 ✅ All allowed (at limit)
├─ JVM 2: Receives 1000 requests
│  └─ counter[ABC] = 1000 ✅ All allowed (at limit)
├─ JVM 3: Receives 1000 requests
│  └─ counter[ABC] = 1000 ✅ All allowed (at limit)
└─ JVM 4: Receives 1000 requests
   └─ counter[ABC] = 1000 ✅ All allowed (at limit)

Expected: 1000 requests MAX
Actual: 4000 requests (4x the limit!)
```

**Impact:**
- 🔴 API abuse: Scrapers bypassing rate limits
- 🔴 Infrastructure costs: 4x expected API gateway costs
- 🔴 Service degradation: Legitimate users affected
- 🔴 SLA violations: Response times degraded

**The Fix - Distributed Rate Limiting:**

```java
@Service
public class DistributedRateLimiter {
    private final RedisTemplate<String, String> redis;

    public boolean allowRequest(String userId) {
        String key = "rate_limit:" + userId;

        // Increment counter in Redis (atomic, across all JVMs)
        Long count = redis.opsForValue().increment(key);

        if (count == 1) {
            // First request - set expiration
            redis.expire(key, 1, TimeUnit.MINUTES);
        }

        return count <= 1000;
    }
}
```

**Alternative - Lua Script (Atomic):**

```java
public boolean allowRequest(String userId) {
    String luaScript =
        "local current = redis.call('incr', KEYS[1]) " +
        "if current == 1 then " +
        "  redis.call('expire', KEYS[1], 60) " +
        "end " +
        "return current";

    Long count = redis.execute(
        RedisScript.of(luaScript, Long.class),
        Collections.singletonList("rate_limit:" + userId)
    );

    return count <= 1000;
}
```

**Lessons Learned:**
1. JVM concurrency primitives are **local**, not distributed
2. Always consider multi-instance deployment in design
3. Test with multiple instances, not just one
4. Use distributed coordination for global state
5. Monitor metrics across all instances

**Presenter Notes:**
- This is a real pattern that happens often
- Ask if anyone has experienced similar issues
- Emphasize testing in production-like environment

---

## **Slide 13: Why JVM Concurrency Still Matters**

### **Title:** Two-Layer Concurrency - Both Are Essential

### **Content:**

**Critical Understanding:**
JVM concurrency is NOT obsolete in multi-JVM environments. Here's why:

**Each JVM Instance Still Handles Concurrent Requests:**

```
        Single JVM Instance
┌─────────────────────────────────┐
│  Request Thread Pool (200)      │
│  ├─ Thread 1: User A request   │
│  ├─ Thread 2: User B request   │
│  ├─ Thread 3: User A request   │  ← Same user, different thread!
│  └─ Thread 4-200: ...          │
│                                 │
│  Shared Within JVM:             │
│  ├─ Connection Pool (50)        │  ← Must be thread-safe!
│  ├─ Local Cache (HashMap)       │  ← Must be thread-safe!
│  ├─ Metrics/Counters            │  ← Must be thread-safe!
│  └─ Request-scoped objects      │  ← Must be thread-safe!
└─────────────────────────────────┘
```

**Real-World Example - Spring Boot REST API:**

```java
@RestController
public class UserController {
    // Shared across ALL request threads in this JVM
    private final ConcurrentHashMap<Long, User> cache =
        new ConcurrentHashMap<>();  // ← MUST be thread-safe!

    private final UserService userService;

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        // 100 threads might call this concurrently
        // within the SAME JVM instance

        return cache.computeIfAbsent(id, key -> {
            // If not in cache, load from service
            return userService.loadUser(key);
        });

        // Without thread-safety:
        // - Cache corruption
        // - Lost updates
        // - Concurrent modification exceptions
    }
}
```

**Layer 1: Intra-JVM Concurrency (What We Learned)**

**Purpose:** Handle concurrent threads **within a single JVM**
**Scope:** Single process, shared memory
**Tools:**
- `synchronized`, `ReentrantLock`
- `AtomicInteger`, `AtomicReference`
- `ConcurrentHashMap`, `BlockingQueue`
- Thread pools, `CompletableFuture`

**Still Needed For:**
- ✅ Request handling (100s of concurrent requests per instance)
- ✅ Connection pool management
- ✅ Local caching
- ✅ Background task coordination
- ✅ Metrics collection within instance
- ✅ Any shared mutable state in JVM

**Example - Connection Pool:**

```java
@Service
public class DatabaseService {
    // Shared connection pool within this JVM
    private final BlockingQueue<Connection> connectionPool =
        new ArrayBlockingQueue<>(20);

    public void executeQuery(String sql) throws InterruptedException {
        // Multiple request threads competing for connections
        // MUST be thread-safe coordination!
        Connection conn = connectionPool.take(); // Thread-safe blocking
        try {
            conn.execute(sql);
        } finally {
            connectionPool.put(conn); // Return to pool
        }
    }
}
```

**The Bottom Line:**

```
Even with 4 JVM instances:
- Each instance: 200 request threads running concurrently
- Total: 800 threads across cluster
- Each JVM: Still needs full thread safety!

JVM Concurrency = Foundation (always required)
Distributed Coordination = Additional layer (when scaling horizontally)
```

**Common Misconception:**

❌ "We have distributed locks, so we don't need synchronized"
✅ "We need synchronized for intra-JVM safety AND distributed locks for inter-JVM coordination"

**Presenter Notes:**
- Emphasize: Both layers work together, not either/or
- Show Spring Boot actuator metrics from multi-instance deployment
- Ask: How many request threads does your production JVM handle?

---

## **Slide 14: Distributed Concurrency Layer**

### **Title:** Coordination Across JVMs

### **Content:**

**When JVM Primitives Fail - Need Distributed Solutions:**

| Problem | JVM Solution (Fails Multi-JVM) | Distributed Solution |
|---------|-------------------------------|----------------------|
| **Global rate limit** | `AtomicInteger` per JVM | Redis INCR + Lua script |
| **Unique ID generation** | `AtomicLong` per JVM (collisions!) | Snowflake / DB sequence |
| **Mutual exclusion** | `synchronized` (per JVM) | Redis SETNX / Redlock |
| **Leader election** | Not applicable | Zookeeper / etcd |
| **Distributed cache** | `ConcurrentHashMap` (stale!) | Redis / Hazelcast |
| **Global counter** | `AtomicLong` (wrong total) | Redis counter |
| **Singleton pattern** | One per JVM (multiple!) | Distributed lock |

**Technology Stack for Distributed Coordination:**

**1. Redis - Most Common Choice:**

```java
// Distributed Lock
@Service
public class DistributedLockService {
    private final StringRedisTemplate redis;

    public boolean acquireLock(String resourceId, int ttlSeconds) {
        Boolean acquired = redis.opsForValue()
            .setIfAbsent(
                "lock:" + resourceId,
                "locked",
                Duration.ofSeconds(ttlSeconds)
            );
        return Boolean.TRUE.equals(acquired);
    }

    public void releaseLock(String resourceId) {
        redis.delete("lock:" + resourceId);
    }
}

// Usage
if (lockService.acquireLock("daily-report", 300)) {
    try {
        // Only ONE JVM across cluster executes this
        generateDailyReport();
    } finally {
        lockService.releaseLock("daily-report");
    }
}
```

**2. Redlock Algorithm (Production-Grade):**

```java
// Using Redisson library
@Configuration
public class RedisConfig {
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
            .setAddress("redis://localhost:6379");
        return Redisson.create(config);
    }
}

@Service
public class TaskService {
    private final RedissonClient redisson;

    public void runUniqueTask(String taskId) {
        RLock lock = redisson.getLock("task:" + taskId);

        try {
            // Try to acquire lock (blocks up to 10s, holds up to 60s)
            if (lock.tryLock(10, 60, TimeUnit.SECONDS)) {
                try {
                    // Guaranteed: Only ONE JVM executes this
                    processTask(taskId);
                } finally {
                    lock.unlock();
                }
            } else {
                logger.warn("Could not acquire lock for task: {}", taskId);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

**3. Zookeeper / etcd - Consensus-Based:**

```java
// Leader Election with Curator (Zookeeper client)
@Service
public class LeaderElectionService {
    private final CuratorFramework curator;
    private LeaderLatch leaderLatch;

    @PostConstruct
    public void start() throws Exception {
        leaderLatch = new LeaderLatch(curator, "/app/leader");
        leaderLatch.addListener(new LeaderLatchListener() {
            @Override
            public void isLeader() {
                logger.info("This instance is now the LEADER");
                startLeaderTasks();
            }

            @Override
            public void notLeader() {
                logger.info("This instance is now a FOLLOWER");
                stopLeaderTasks();
            }
        });

        leaderLatch.start();
    }

    public boolean isLeader() {
        return leaderLatch.hasLeadership();
    }
}
```

**4. Database Locks - Traditional Approach:**

```java
@Service
public class OrderProcessingService {

    @Transactional
    public void processOrder(Long orderId) {
        // Pessimistic lock - blocks other JVMs at DB level
        Order order = entityManager.find(
            Order.class,
            orderId,
            LockModeType.PESSIMISTIC_WRITE
        );

        // Only ONE JVM can process this order at a time
        if (order.getStatus() == OrderStatus.PENDING) {
            order.setStatus(OrderStatus.PROCESSING);
            processOrderInternal(order);
        }
    }
}
```

**5. Hazelcast - Distributed Data Structures:**

```java
@Configuration
public class HazelcastConfig {
    @Bean
    public Config hazelcastConfig() {
        return new Config()
            .setClusterName("my-app-cluster")
            .setNetworkConfig(new NetworkConfig()
                .setJoin(new JoinConfig()
                    .setMulticastConfig(new MulticastConfig()
                        .setEnabled(true))));
    }
}

@Service
public class DistributedCacheService {
    private final HazelcastInstance hazelcast;

    public void updateInventory(String productId, int quantity) {
        // Distributed map - shared across ALL JVMs
        IMap<String, Integer> inventory = hazelcast.getMap("inventory");

        // Distributed lock automatically
        inventory.lock(productId);
        try {
            Integer current = inventory.get(productId);
            inventory.put(productId, current + quantity);
        } finally {
            inventory.unlock(productId);
        }
    }
}
```

**Comparison Table:**

| Technology | Complexity | Performance | Use Case |
|------------|------------|-------------|----------|
| **Redis** | Low | High | Locks, caching, counters |
| **Zookeeper** | High | Medium | Leader election, config |
| **Database** | Low | Low | Simple locks, transactions |
| **Hazelcast** | Medium | High | Drop-in distributed collections |

**Best Practices:**
- ✅ Start with Redis for most distributed needs
- ✅ Use Zookeeper for critical consensus (leader election)
- ✅ Database locks for transactional consistency
- ✅ Always set TTL on distributed locks (prevent deadlock)
- ✅ Handle lock acquisition failures gracefully

**Presenter Notes:**
- Show architecture diagram with Redis in the middle
- Discuss trade-offs: complexity vs guarantees
- Mention that Redis is most commonly used

---

## **Slide 15: The Two-Layer Pattern**

### **Title:** Combining JVM + Distributed Concurrency

### **Content:**

**Production-Grade Pattern - Both Layers Working Together:**

```java
@Service
public class OrderProcessingService {

    // LAYER 2: Distributed coordination (cross-JVM)
    private final RedissonClient redisson;

    // LAYER 1: JVM-local coordination (intra-JVM)
    private final ReentrantLock localLock = new ReentrantLock();
    private final ConcurrentHashMap<String, Order> localCache =
        new ConcurrentHashMap<>();

    public void processOrder(String orderId) {
        // LAYER 2: Acquire distributed lock
        // Ensures only ONE JVM processes this order
        RLock distributedLock = redisson.getLock("order:" + orderId);

        try {
            if (distributedLock.tryLock(5, 30, TimeUnit.SECONDS)) {
                try {
                    // LAYER 1: Multiple threads in THIS JVM
                    // might call this (retry logic, background tasks)
                    localLock.lock();
                    try {
                        // Check local cache (thread-safe within JVM)
                        Order order = localCache.computeIfAbsent(
                            orderId,
                            this::loadOrderFromDB
                        );

                        // Process order
                        processOrderInternal(order);

                        // Update cache
                        localCache.put(orderId, order);

                    } finally {
                        localLock.unlock(); // LAYER 1 unlock
                    }
                } finally {
                    distributedLock.unlock(); // LAYER 2 unlock
                }
            } else {
                logger.warn("Could not acquire lock for order: {}", orderId);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

**Why Both Layers?**

```
Without LAYER 2 (Distributed Lock):
    JVM 1: Processes order #123
    JVM 2: Processes order #123  ← Duplicate processing!
    Result: Double charge, inventory error

Without LAYER 1 (Local Lock):
    JVM 1, Thread A: Loads order from cache
    JVM 1, Thread B: Loads order from cache
    Both threads: Modify order concurrently
    Result: Lost updates within same JVM
```

**Real-World Example - Background Job Processing:**

```java
@Service
public class ScheduledJobService {
    private final RedissonClient redisson;
    private final ExecutorService localThreadPool =
        Executors.newFixedThreadPool(10);

    @Scheduled(fixedRate = 60000) // Every minute on ALL instances
    public void processJobs() {
        // LAYER 2: Leader election - only one instance processes
        RLock leaderLock = redisson.getLock("job:leader");

        if (leaderLock.tryLock()) {
            try {
                logger.info("This instance is leader, processing jobs");

                List<Job> jobs = fetchPendingJobs();

                // LAYER 1: Process jobs in parallel within this JVM
                jobs.forEach(job -> {
                    localThreadPool.submit(() -> {
                        processJob(job);
                    });
                });

            } finally {
                leaderLock.unlock();
            }
        } else {
            logger.debug("Another instance is leader, skipping");
        }
    }
}
```

**Mental Model:**

```
┌────────────────────────────────────────────────────┐
│         DISTRIBUTED LAYER (Cross-JVM)              │
│  Redis / Zookeeper / Database                      │
│  - Global locks, leader election, counters         │
└──────┬─────────────┬─────────────┬────────────────┘
       │             │             │
   ┌───▼───┐     ┌───▼───┐     ┌───▼───┐
   │ JVM 1 │     │ JVM 2 │     │ JVM 3 │
   ├───────┤     ├───────┤     ├───────┤
   │ LOCAL │     │ LOCAL │     │ LOCAL │
   │ LAYER │     │ LAYER │     │ LAYER │
   │       │     │       │     │       │
   │ Locks │     │ Locks │     │ Locks │
   │Thread │     │Thread │     │Thread │
   │ Pools │     │ Pools │     │ Pools │
   │Atomic │     │Atomic │     │Atomic │
   │ Cache │     │ Cache │     │ Cache │
   └───────┘     └───────┘     └───────┘
```

**Decision Tree:**

```
Need coordination?
    ├─ Within single JVM?
    │   └─ Use: synchronized, locks, atomics (LAYER 1)
    │
    └─ Across multiple JVMs?
        ├─ Simple lock?
        │   └─ Use: Redis SETNX (LAYER 2)
        ├─ Leader election?
        │   └─ Use: Zookeeper (LAYER 2)
        ├─ Global counter?
        │   └─ Use: Redis INCR (LAYER 2)
        └─ Transactional consistency?
            └─ Use: Database locks (LAYER 2)

Often need BOTH layers simultaneously!
```

**Presenter Notes:**
- Draw the two layers on whiteboard
- Emphasize they're complementary, not alternatives
- Show that removing either layer causes bugs

---

## **Slide 16: Idempotency - The Hidden Requirement**

### **Title:** Handle Duplicate Requests Gracefully

### **Content:**

**The Problem - At-Least-Once Delivery in Distributed Systems:**

```
Timeline:
T0: Client sends payment request ($100)
    → Load Balancer → JVM 1
T1: JVM 1 processes payment successfully
T2: Network glitch - response lost
T3: Client times out, retries same request
    → Load Balancer → JVM 2  ← Different instance!
T4: JVM 2 processes payment AGAIN

Result: Customer charged $200 instead of $100! ❌
```

**Why This Happens:**
- Network failures common in distributed systems
- Load balancers can route retries to different instances
- Client-side retries (exponential backoff)
- Message queues (at-least-once delivery guarantee)

**Solution - Idempotency Key:**

```java
@RestController
public class PaymentController {
    private final PaymentService paymentService;
    private final RedisTemplate<String, PaymentResult> redis;

    @PostMapping("/payments")
    public PaymentResult processPayment(
        @RequestBody PaymentRequest request,
        @RequestHeader("Idempotency-Key") String idempotencyKey
    ) {
        // Check if already processed (across ALL JVMs)
        String cacheKey = "payment:" + idempotencyKey;
        PaymentResult cached = redis.opsForValue().get(cacheKey);

        if (cached != null) {
            logger.info("Duplicate request detected: {}", idempotencyKey);
            return cached; // Return cached result
        }

        // Process payment (first time)
        PaymentResult result = paymentService.charge(request);

        // Store result with 24-hour TTL
        redis.opsForValue().set(
            cacheKey,
            result,
            24,
            TimeUnit.HOURS
        );

        return result;
    }
}
```

**Database-Based Idempotency:**

```java
@Entity
public class ProcessedRequest {
    @Id
    private String idempotencyKey;
    private String result;
    private LocalDateTime processedAt;
}

@Service
public class IdempotentPaymentService {

    @Transactional
    public PaymentResult processPayment(String idempotencyKey, PaymentRequest req) {
        // Check if already processed
        Optional<ProcessedRequest> existing =
            requestRepository.findById(idempotencyKey);

        if (existing.isPresent()) {
            return deserialize(existing.get().getResult());
        }

        // Process payment
        PaymentResult result = paymentGateway.charge(req);

        // Store as processed (unique constraint prevents duplicates)
        ProcessedRequest record = new ProcessedRequest();
        record.setIdempotencyKey(idempotencyKey);
        record.setResult(serialize(result));
        record.setProcessedAt(LocalDateTime.now());

        try {
            requestRepository.save(record);
        } catch (DataIntegrityViolationException e) {
            // Race condition - another JVM processed it
            return requestRepository.findById(idempotencyKey)
                .map(r -> deserialize(r.getResult()))
                .orElseThrow();
        }

        return result;
    }
}
```

**Best Practices:**
- ✅ Client generates UUID as idempotency key
- ✅ Store results with TTL (24-48 hours typical)
- ✅ Return exact same response for duplicates
- ✅ Use distributed storage (Redis/Database)
- ✅ Handle race conditions (unique constraints)

**Presenter Notes:**
- This is critical for payment/order processing
- Ask about retry strategies in audience's systems
- Mention Stripe's idempotency key implementation

---

## **Slide 17: Cache Coherence Problem**

### **Title:** When Local Caches Lie

### **Content:**

**The Staleness Problem:**

```
T0: User updates profile (name: "John" → "Jane")
    → Request hits JVM 1
    → JVM 1: Updates database
    → JVM 1: Updates local cache (name: "Jane")

T1: User loads profile
    → Request hits JVM 2
    → JVM 2: Checks local cache
    → JVM 2: Returns OLD data (name: "John") ❌

JVM 1 Cache: "Jane" ✅
JVM 2 Cache: "John" ❌ Stale!
JVM 3 Cache: "John" ❌ Stale!
```

**Solution 1: Cache Invalidation with Pub/Sub:**

```java
@Service
public class UserService {
    private final ConcurrentHashMap<Long, User> localCache =
        new ConcurrentHashMap<>();
    private final RedisTemplate<String, Long> redis;

    @PostConstruct
    public void subscribeToCacheInvalidation() {
        redis.getConnectionFactory()
            .getConnection()
            .subscribe((message, pattern) -> {
                Long userId = Long.parseLong(new String(message.getBody()));
                localCache.remove(userId);
                logger.info("Invalidated cache for user: {}", userId);
            }, "cache:invalidate:user".getBytes());
    }

    public void updateUser(User user) {
        // Update database
        userRepository.save(user);

        // Invalidate local cache
        localCache.remove(user.getId());

        // Publish invalidation to ALL JVMs
        redis.convertAndSend("cache:invalidate:user", user.getId());
    }

    public User getUser(Long userId) {
        return localCache.computeIfAbsent(userId, id -> {
            return userRepository.findById(id).orElseThrow();
        });
    }
}
```

**Solution 2: Short TTL:**

```java
@Service
public class TimedCacheService {
    private final LoadingCache<Long, User> cache = CacheBuilder.newBuilder()
        .expireAfterWrite(30, TimeUnit.SECONDS) // Stale for max 30s
        .maximumSize(10_000)
        .build(new CacheLoader<Long, User>() {
            @Override
            public User load(Long userId) {
                return userRepository.findById(userId).orElseThrow();
            }
        });

    public User getUser(Long userId) {
        return cache.getUnchecked(userId);
    }
}
```

**Solution 3: No Local Cache (Distributed Only):**

```java
@Service
public class DistributedCacheService {
    private final RedisTemplate<Long, User> redis;

    public User getUser(Long userId) {
        User cached = redis.opsForValue().get(userId);

        if (cached != null) {
            return cached;
        }

        User user = userRepository.findById(userId).orElseThrow();
        redis.opsForValue().set(userId, user, 5, TimeUnit.MINUTES);

        return user;
    }

    public void updateUser(User user) {
        userRepository.save(user);
        redis.opsForValue().set(user.getId(), user, 5, TimeUnit.MINUTES);
    }
}
```

**Trade-offs:**

| Approach | Consistency | Performance | Complexity |
|----------|-------------|-------------|------------|
| **Pub/Sub Invalidation** | High | High | Medium |
| **Short TTL** | Medium | High | Low |
| **Distributed Only** | High | Medium | Low |
| **Write-Through** | High | Varies | Medium |

**Presenter Notes:**
- Most apps use combination: local cache + short TTL
- Critical data: Use distributed cache only
- Show Redis Pub/Sub in action if possible

---

## **Slide 18: Technology Stack**

### **Title:** Tools for Distributed Coordination

**(Content showing comparison table and quick summaries - already covered in slide 14, can reference or expand)**

---

## **Slide 19: Key Takeaways - Multi-JVM**

### **Title:** The Production Excellence Mindset

**Critical Insights:**

**1. JVM Concurrency ≠ Obsolete**
- Every instance handles 100s of concurrent requests
- Thread safety ALWAYS required within each JVM
- Foundation that everything else builds on

**2. Two Layers, Not One:**
- **Intra-JVM (Layer 1):** synchronized, locks, atomics
  - Scope: Within single process
  - Always needed
- **Distributed (Layer 2):** Redis, Zookeeper, DB locks
  - Scope: Across all processes
  - Needed when scaling horizontally

**3. When to Add Distributed Layer:**
- ✅ Multiple instances deployed
- ✅ Need global state/counters
- ✅ Leader election required
- ✅ Cross-instance coordination
- ✅ Cache coherence across instances

**4. Start Simple, Scale Smart:**
- Single instance? JVM concurrency sufficient
- Horizontal scaling? Add distributed layer incrementally
- Don't over-engineer early

**5. Common Patterns:**
- **Rate limiting:** Redis counters
- **Unique IDs:** Snowflake algorithm / DB sequences
- **Locks:** Redis Redlock / Zookeeper
- **Leader election:** Zookeeper / etcd
- **Caching:** Local cache + Pub/Sub invalidation
- **Idempotency:** Redis/DB with idempotency keys

**Remember:**
Most production bugs happen at BOTH layers. Know which layer your bug is in!

**Presenter Notes:**
- Reinforce that this is practical, not academic
- Emphasize testing with multiple instances
- Share war stories if available

---

# **SECTION 3: GARBAGE COLLECTION MASTERY**

---

## **Slide 20: GC Fundamentals**

### **Title:** Memory Management - The Hidden Performance Tax

### **Content:**

**Why GC Matters:**
- GC pauses = application pauses
- Poor GC tuning = degraded user experience
- Understanding GC = troubleshooting production issues

**Heap Structure - Generational Hypothesis:**

```
┌─────────────────────────────────────────────────────┐
│                   JVM Heap                          │
├─────────────────────────────────────────────────────┤
│  Young Generation (~1/3 heap)                       │
│  ┌───────────────────────────────────────────────┐  │
│  │ Eden Space (80%)                              │  │
│  │ ├─ New objects allocated here                 │  │
│  │ └─ Fills up quickly                           │  │
│  ├───────────────────────────────────────────────┤  │
│  │ Survivor 0 (10%)                              │  │
│  │ └─ Objects surviving 1 GC                     │  │
│  ├───────────────────────────────────────────────┤  │
│  │ Survivor 1 (10%)                              │  │
│  │ └─ Objects surviving 2+ GCs                   │  │
│  └───────────────────────────────────────────────┘  │
├─────────────────────────────────────────────────────┤
│  Old Generation (~2/3 heap)                         │
│  └─ Tenured Space                                   │
│     └─ Long-lived objects (survived many GCs)       │
└─────────────────────────────────────────────────────┘

Metaspace (Java 8+) / PermGen (Java 7)
└─ Class metadata, static variables, constants
```

**Generational Hypothesis:**
- **Observation:** ~98% of objects die young
- **Strategy:** Optimize for short-lived objects
- **Result:**
  - Minor GC (young gen): Frequent, fast (10-50ms)
  - Major GC (old gen): Infrequent, slower (100-500ms)
  - Full GC: Rare in healthy app (500ms-5s)

**Object Lifecycle:**

```
1. Created:      new Object() → Allocated in Eden
2. Minor GC:     Survives → Moved to Survivor 0
3. More GCs:     Survives → Copied between Survivors
4. Promotion:    After N GCs → Moved to Old Gen
5. Major GC:     Old gen fills → Collected from old gen
```

**GC Roots (What Keeps Objects Alive):**
- Local variables (stack)
- Static variables
- JNI references
- Thread references

**Presenter Notes:**
- Draw the heap structure on whiteboard
- Explain why generational is efficient
- Mention that most tuning focuses on young gen

---

## **Slide 21: GC Events Impact**

### **Title:** Understanding the Performance Cost

### **Content:**

**Types of GC Events:**

| Event Type | Scope | Frequency | Pause Time | Stop-the-World | Impact |
|------------|-------|-----------|------------|----------------|--------|
| **Minor GC** | Young generation | Seconds | 10-50ms | Yes | Low |
| **Major GC** | Old generation | Minutes/Hours | 100-500ms | Partial | Medium |
| **Full GC** | Entire heap + metaspace | Rare (ideally never) | 500ms-5s | Yes | **CRITICAL** |

**Production Impact:**

```
Normal Application:
Requests: ████████████████████████████ (steady throughput)

During Full GC (2 seconds):
Requests: ████████████                   (pause) ████████████
          ↑                                      ↑
      GC starts                              GC ends

Impact:
- All request threads frozen
- No new requests processed
- Users see timeouts
- Load balancer may mark instance unhealthy
```

**GC Metrics to Monitor:**

**1. Heap Usage:**
- Used / Committed / Max
- Old gen growth rate (should be near zero in steady state)
- Young gen allocation rate

**2. GC Frequency:**
- Minor GC: Should happen regularly (every few seconds = healthy)
- Major GC: Infrequent (minutes to hours)
- Full GC: **SHOULD NOT HAPPEN** in steady state

**3. Pause Times:**
- Average pause time
- Max pause time
- P99 pause time (most important for SLAs)

**Real Production Example:**

```
Before Tuning:
- Full GC every 10 minutes (2s pause each)
- P99 latency: 3500ms
- Timeout rate: 5%

After Tuning (increased heap, tuned G1):
- No Full GCs
- P99 latency: 150ms
- Timeout rate: 0.1%
```

**Red Flags:**

| Symptom | Likely Cause | Action |
|---------|--------------|--------|
| Full GC every few minutes | Memory leak or heap too small | Heap dump analysis |
| Pause times >1s | Wrong GC or heap size | Change GC, adjust size |
| Old gen growing steadily | Memory leak | Find and fix leak |
| High CPU during GC | Excessive allocation rate | Reduce object creation |
| OutOfMemoryError | Leak or truly need more memory | Analyze heap dump |

**Presenter Notes:**
- Show real GC logs from production
- Emphasize that Full GC = red alert
- Discuss SLA impact (P99, P99.9)

---

## **Slide 22: GC Algorithms - Choosing Wisely**

### **Title:** Know Your Garbage Collectors

### **Content:**

**1. Serial GC (`-XX:+UseSerialGC`)**

**Characteristics:**
- Single-threaded GC
- Stop-the-world for all GC events
- Low memory overhead
- Simplest implementation

**When to Use:**
- Small applications (<100MB heap)
- Single-CPU systems
- Client applications (not servers)

**Performance:**
- Throughput: Low
- Pause times: High (100ms-1s)
- Memory overhead: Lowest

---

**2. Parallel GC (`-XX:+UseParallelGC`)**

**Characteristics:**
- Multi-threaded GC
- Stop-the-world but uses all CPU cores
- Throughput-optimized
- Default in Java 8

**When to Use:**
- Batch processing
- Number crunching
- Throughput > Latency

**Configuration:**
```bash
-XX:+UseParallelGC
-XX:ParallelGCThreads=8        # GC threads
-XX:MaxGCPauseMillis=500       # Target pause (best effort)
```

**Performance:**
- Throughput: **Highest**
- Pause times: Medium-High (200ms-1s)
- Memory overhead: Medium

---

**3. G1 GC (`-XX:+UseG1GC`) ⭐ Default Java 9+**

**Characteristics:**
- Divides heap into regions (~2048 regions)
- Predictable pause times
- Concurrent marking, parallel copying
- Balanced throughput + latency

**When to Use:**
- **General-purpose (default choice)**
- Large heaps (>4GB)
- Need predictable pauses (<200ms)
- 99% of production applications

**Configuration:**
```bash
-XX:+UseG1GC
-XX:MaxGCPauseMillis=200       # Target max pause (200ms typical)
-XX:G1HeapRegionSize=16m       # Region size
-XX:InitiatingHeapOccupancyPercent=45  # When to start marking
```

**How It Works:**
```
Heap divided into regions:
[E][E][O][O][S][E][H][E][O][E]...

E = Eden
S = Survivor
O = Old
H = Humongous (large objects)

G1 prioritizes regions with most garbage ("Garbage First")
```

**Performance:**
- Throughput: High
- Pause times: Low-Medium (50-200ms, predictable)
- Memory overhead: Medium-High
- **Best balance** for most apps

---

**4. ZGC (`-XX:+UseZGC`) - Java 15+**

**Characteristics:**
- **Ultra-low latency** (<10ms pauses)
- Concurrent (most work done concurrently)
- Scales to multi-TB heaps
- Pause times independent of heap size

**When to Use:**
- Ultra-low latency requirements
- Large heaps (>10GB, up to 16TB)
- Real-time systems
- High-frequency trading

**Configuration:**
```bash
-XX:+UseZGC
-Xms16g -Xmx16g               # Fixed heap size recommended
-XX:ZCollectionInterval=5     # Min seconds between GCs
```

**Performance:**
- Throughput: Medium-High
- Pause times: **Ultra-low (<10ms)**
- Memory overhead: Higher
- CPU overhead: Higher

**Trade-offs:**
- ✅ Consistent low latency
- ✅ Huge heap support
- ❌ Higher memory usage (~15-20% overhead)
- ❌ More CPU usage

---

**5. Shenandoah GC (`-XX:+UseShenandoahGC`)**

**Characteristics:**
- Similar goals to ZGC
- Concurrent compaction
- Low pause times

**When to Use:**
- Alternative to ZGC
- Available in OpenJDK (not Oracle JDK until Java 15)

---

**Comparison Summary:**

| GC | Pause Time | Throughput | Heap Size | Use Case |
|----|-----------|------------|-----------|----------|
| **Serial** | Highest | Lowest | <100MB | Client apps |
| **Parallel** | High | **Highest** | Any | Batch processing |
| **G1** | Medium | High | >4GB | **General purpose** |
| **ZGC** | **Lowest** | Medium | >10GB | Ultra-low latency |
| **Shenandoah** | **Lowest** | Medium | >10GB | Alternative to ZGC |

**Decision Tree:**

```
What's your priority?

├─ Maximum throughput (batch jobs)
│   └─ Use: Parallel GC
│
├─ Ultra-low latency (<10ms)
│   └─ Use: ZGC (Java 15+)
│
├─ Small heap (<100MB, client app)
│   └─ Use: Serial GC
│
└─ General purpose / Don't know
    └─ Use: G1 GC (default, best balance)
```

**Presenter Notes:**
- G1 GC is the safe default
- ZGC is the future for latency-sensitive apps
- Show GC logs comparing pause times

---

## **Slide 23: Memory Leaks in Java**

### **Title:** Yes, Java Can Leak Memory!

### **Content:**

**Definition:** Objects that should be garbage collected but aren't because they're still referenced.

**Common Leak Patterns:**

**1. Static Collections (Most Common):**

```java
// LEAK!
public class UserCache {
    private static final List<User> cache = new ArrayList<>();

    public static void addUser(User user) {
        cache.add(user); // Never removed, grows forever
    }
}

// Fix: Bounded cache or WeakHashMap
public class UserCache {
    private static final Map<String, User> cache = new WeakHashMap<>();
    // Or use Guava Cache with size limit
}
```

**2. Unclosed Resources:**

```java
// LEAK!
public void processFile(String path) {
    FileInputStream fis = new FileInputStream(path);
    // Forgot to close - file handle leak
}

// Fix: try-with-resources
public void processFile(String path) throws IOException {
    try (FileInputStream fis = new FileInputStream(path)) {
        // Auto-closed
    }
}
```

**3. Listeners Not Removed:**

```java
// LEAK!
@Service
public class NotificationService {
    @Autowired
    private EventBus eventBus;

    @PostConstruct
    public void init() {
        eventBus.register(this); // 'this' held by eventBus
    }

    // Bean gets destroyed but listener still registered!
}

// Fix: Unregister in cleanup
@PreDestroy
public void cleanup() {
    eventBus.unregister(this);
}
```

**4. ThreadLocal in Thread Pools:**

```java
// LEAK!
private static ThreadLocal<LargeObject> threadLocal = new ThreadLocal<>();

public void handleRequest() {
    threadLocal.set(new LargeObject()); // 1MB
    // Process request
    // Thread pool reuses thread, LargeObject never released!
}

// Fix: Always remove
public void handleRequest() {
    try {
        threadLocal.set(new LargeObject());
        // Process request
    } finally {
        threadLocal.remove(); // CRITICAL!
    }
}
```

**5. Inner Classes Holding Outer References:**

```java
// LEAK!
public class Outer {
    private byte[] hugeArray = new byte[1024 * 1024]; // 1MB

    class Inner {
        // Implicitly holds reference to Outer
        // If Inner is cached, entire Outer stays in memory
    }
}

// Fix: Use static inner class
public class Outer {
    private byte[] hugeArray = new byte[1024 * 1024];

    static class Inner {
        // No reference to Outer
    }
}
```

**Detection Tools:**

**1. Heap Dump Analysis:**
```bash
# Take heap dump
jmap -dump:live,format=b,file=heap.bin <pid>

# Analyze with Eclipse MAT
# Look for:
# - Largest objects
# - Duplicate strings
# - Collection sizes
```

**2. VisualVM:**
- Heap dump on demand
- Memory profiling
- GC monitoring

**3. Production Monitoring:**
```java
// Alert on old gen growth
if (oldGenUsage > 80% && growthRate > 0) {
    alert("Possible memory leak");
}
```

**Symptoms:**
- ✋ Heap usage grows over time
- ✋ Old gen never stabilizes
- ✋ OutOfMemoryError
- ✋ Frequent Full GCs with little memory freed

**Presenter Notes:**
- ThreadLocal is the most common culprit
- Always analyze heap dumps, don't guess
- Show MAT analysis if possible

---

## **Slide 24: Reference Types**

### **Title:** Control Object Lifetime Precisely

### **Content:**

**Reference Strength Hierarchy:**

```
Strong → Soft → Weak → Phantom
(Never GC'd)  (GC when memory low)  (GC next cycle)  (Post-finalization)
```

**1. Strong Reference (Default):**

```java
User user = new User(); // Strong reference
// Never GC'd while 'user' variable is in scope
```

**Use:** Normal object references

---

**2. SoftReference - Memory-Sensitive Caches:**

```java
public class ImageCache {
    private Map<String, SoftReference<BufferedImage>> cache =
        new ConcurrentHashMap<>();

    public BufferedImage getImage(String url) {
        SoftReference<BufferedImage> ref = cache.get(url);

        if (ref != null) {
            BufferedImage img = ref.get();
            if (img != null) {
                return img; // Cache hit
            }
        }

        // Cache miss or GC'd - reload
        BufferedImage img = loadImage(url);
        cache.put(url, new SoftReference<>(img));
        return img;
    }
}
```

**Behavior:**
- JVM keeps soft references as long as possible
- Only GC'd when memory is low
- Perfect for caches (auto-eviction under pressure)

**Use Cases:**
- Image caches
- Parsed data caches
- Any cache that can be recomputed

---

**3. WeakReference - Canonicalizing Maps:**

```java
// WeakHashMap - Entries removed when key no longer referenced
WeakHashMap<User, Metadata> userMetadata = new WeakHashMap<>();

User user = new User("John");
userMetadata.put(user, new Metadata());

user = null; // No more strong references to user
System.gc();

// userMetadata entry automatically removed
```

**Behavior:**
- GC'd in next GC cycle
- Doesn't prevent object collection

**Use Cases:**
- Weak caches (WeakHashMap)
- Canonicalizing maps
- Observers that shouldn't prevent GC

---

**4. PhantomReference - Post-Mortem Cleanup:**

```java
ReferenceQueue<Object> queue = new ReferenceQueue<>();
PhantomReference<Object> ref = new PhantomReference<>(obj, queue);

// ref.get() always returns null
// Used for cleanup actions after object is finalized
```

**Use Cases:**
- Tracking object collection
- Off-heap memory cleanup
- Alternative to finalize()

---

**Comparison:**

| Type | get() returns null? | GC'd when? | Use Case |
|------|-------------------|-----------|----------|
| **Strong** | Never | Never (while referenced) | Normal references |
| **Soft** | After GC (if memory low) | When memory pressure | Memory-sensitive caches |
| **Weak** | After GC | Next GC cycle | Weak caches, canonicalizing |
| **Phantom** | Always | After finalization | Post-mortem cleanup |

**Presenter Notes:**
- SoftReference most commonly used in practice
- Guava Cache uses SoftReference internally
- PhantomReference rarely used directly

---

## **Slide 25-30: GC Tuning, Monitoring, Best Practices**

*(Due to length constraints, I'll provide abbreviated versions of the remaining GC slides)*

### **Slide 25: GC Tuning Methodology**

1. **Enable GC Logging** (always in production)
2. **Establish Baseline** (measure current state)
3. **Identify Issues** (full GCs, long pauses)
4. **Tune ONE parameter** at a time
5. **Measure Again** and iterate

---

### **Slide 26: Heap Sizing Rules**

**Formula:** `Heap Size = Peak Live Data × (2 to 4)`

```bash
# Set min = max (avoid resize overhead)
-Xms4g -Xmx4g

# Container environments
-XX:MaxRAMPercentage=75.0
```

---

### **Slide 27: Production Monitoring**

**Key Metrics:**
- Heap usage (used/committed/max)
- GC frequency and pause times
- Old gen growth rate
- P99 latency

**Tools:** jstat, VisualVM, Prometheus/Grafana

---

### **Slide 28: GC Anti-Patterns**

❌ Calling `System.gc()`
❌ Using `finalize()`
❌ Ignoring GC logs
❌ Premature optimization

---

### **Slide 29: GC Best Practices**

✅ Enable GC logging always
✅ Start with G1 GC (default)
✅ Set `-Xms = -Xmx`
✅ Monitor P99 pause times
✅ Fix leaks before tuning

---

### **Slide 30: GC Summary**

**Key Points:**
- GC pauses = app pauses
- G1 GC = best default
- ZGC = ultra-low latency (Java 15+)
- Monitor always, tune when needed
- Fix leaks first, tune second

---

# **CLOSING**

---

## **Slide 31: Bringing It All Together**

### **Title:** The Production Excellence Stack

**Three Pillars of Java Production Excellence:**

**Pillar 1: JVM Concurrency (Foundation)**
- Thread pools, locks, atomics
- Handles concurrent requests within each instance
- **Always required**

**Pillar 2: Distributed Coordination (Scale-Out)**
- Redis, Zookeeper, distributed locks
- Coordinates across instances
- **Required when scaling horizontally**

**Pillar 3: Memory Management (Performance)**
- GC tuning, leak prevention
- Ensures low latency, high throughput
- **Critical for user experience**

**All Three Required:**
```
Production System
    ├─ Distributed Layer (Redis, Zookeeper)
    ├─ Application Layer (your code)
    ├─ JVM Concurrency Layer (thread pools, locks)
    └─ GC Layer (memory management)
```

---

## **Slide 32: Action Items**

### **Title:** Next Steps for Your Team

**Immediate (This Sprint):**
1. ✅ Audit shared mutable state for thread safety
2. ✅ Enable GC logging: `-Xlog:gc*:file=gc.log`
3. ✅ Review thread pool configurations
4. ✅ Check for unclosed resources and ThreadLocal leaks

**Short-Term (This Quarter):**
1. ✅ Implement distributed locking for critical sections
2. ✅ Add idempotency keys to payment/order APIs
3. ✅ Set up GC monitoring dashboards (Grafana)
4. ✅ Conduct heap dump analysis for memory leaks
5. ✅ Test with multiple instances locally

**Long-Term (Next 6 Months):**
1. ✅ Evaluate Virtual Threads (Java 21) for I/O services
2. ✅ Consider ZGC for latency-sensitive microservices
3. ✅ Build distributed tracing for multi-JVM debugging
4. ✅ Implement cache coherence strategy

**Resources:**
- Internal Wiki: [Concurrency Best Practices]
- Code Examples: [GitHub Repo]
- Recommended Reading: "Java Concurrency in Practice" by Brian Goetz

---

## **Slide 33: Q&A and Discussion**

### **Title:** Open Forum

**Discussion Topics:**
- What concurrency challenges are you facing?
- Have you experienced GC issues in production?
- What distributed coordination patterns have you used?
- Questions about Java 21 Virtual Threads?

**Share Your Experiences:**
- Production incidents related to threading?
- Memory leak war stories?
- Multi-JVM coordination challenges?

**Thank You!**

---

**END OF PRESENTATION**
