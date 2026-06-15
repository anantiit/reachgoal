# Java Collections & Streams - Quick Reference Card
## One-Page Cheat Sheet for Presentation

---

## 🗂️ Collections Decision Tree

```
Need to store elements?
│
├─ Key-value pairs? → MAP
│  ├─ Thread-safe? → ConcurrentHashMap
│  ├─ Sorted? → TreeMap
│  ├─ Insertion order? → LinkedHashMap
│  └─ Default → HashMap
│
├─ Unique elements? → SET
│  ├─ Sorted? → TreeSet
│  ├─ Insertion order? → LinkedHashSet
│  ├─ Enum type? → EnumSet
│  └─ Default → HashSet
│
├─ Priority-based? → PriorityQueue
│
├─ FIFO/LIFO? → QUEUE/STACK
│  ├─ Thread-safe? → BlockingQueue
│  ├─ Both ends? → ArrayDeque
│  └─ Default → ArrayDeque
│
└─ Ordered collection → LIST
   ├─ Random access? → ArrayList
   ├─ Both ends operations? → LinkedList (rare)
   └─ Default → ArrayList
```

---

## ⚡ Performance Quick Reference

| Operation | ArrayList | LinkedList | HashSet | TreeSet | HashMap | TreeMap |
|-----------|-----------|------------|---------|---------|---------|---------|
| Get | **O(1)** | O(n) | - | - | **O(1)** | O(log n) |
| Add (end) | **O(1)** | **O(1)** | **O(1)** | O(log n) | **O(1)** | O(log n) |
| Add (start) | O(n) | **O(1)** | **O(1)** | O(log n) | **O(1)** | O(log n) |
| Remove | O(n) | O(n) | **O(1)** | O(log n) | **O(1)** | O(log n) |
| Contains | O(n) | O(n) | **O(1)** | O(log n) | **O(1)** | O(log n) |
| Memory | Low | High | Medium | High | Medium | High |

**Bold** = Best performance

---

## 🎯 When to Use What

### Collections

**ArrayList**: 95% of List use cases
- Random access, iteration, append operations
- **Avoid**: Frequent insertion/deletion at beginning

**LinkedList**: Rare, specific use cases only
- Deque operations (both ends)
- **Avoid**: Random access, general list operations

**HashSet**: Default Set choice
- Uniqueness, no ordering needed
- **Requires**: Good hashCode() implementation

**TreeSet**: Sorted set with range operations
- Sorted iteration, NavigableSet operations
- **Cost**: O(log n) vs O(1)

**HashMap**: Default Map choice
- General key-value storage
- **Allows**: One null key

**ConcurrentHashMap**: Concurrent access
- High read/write concurrency
- **Disallows**: Null keys/values

**TreeMap**: Sorted map with navigation
- Sorted keys, range queries
- **Cost**: O(log n) vs O(1)

### Streams

**Sequential Stream**: Default
- Predictable order, simple operations
- **Use**: Always start here

**Parallel Stream**: Large datasets + CPU-intensive
- 10,000+ elements, complex computations
- **Avoid**: I/O operations, small datasets, shared state

---

## 💡 Critical Rules

### Collections
1. **ArrayList is default List** - Use LinkedList only for deque operations
2. **HashSet is default Set** - TreeSet only when sorting needed
3. **HashMap is default Map** - ConcurrentHashMap for concurrency
4. **Always override hashCode() with equals()**
5. **Make Map keys immutable**
6. **Use EnumSet/EnumMap for enums** (10x faster)
7. **Specify initial capacity** if size known

### Streams
1. **Intermediate operations are lazy** - Not executed until terminal operation
2. **Streams are one-time use** - Create new stream for each operation
3. **Avoid side-effects** - Except in terminal forEach
4. **Use primitive streams** - IntStream, LongStream, DoubleStream (no boxing)
5. **collect() over reduce()** - For mutable accumulation
6. **Parallel is not always faster** - Measure first, overhead for small datasets
7. **Use method references** - Cleaner than lambdas

---

## 🔴 Common Pitfalls

### Collections
❌ `ArrayList<Integer> list = new ArrayList<>();` // Good, but...
✅ `List<Integer> list = new ArrayList<>();` // Better - program to interface

❌ Forgetting to override hashCode() when overriding equals()
✅ Always override both together

❌ Using LinkedList for general List operations
✅ Use ArrayList (3000x faster for random access)

❌ `map.get(key); if (value == null) map.put(key, new Value());`
✅ `map.computeIfAbsent(key, k -> new Value());`

### Streams
❌ `list.stream().forEach(item -> list.add(newItem));` // ConcurrentModificationException
✅ Use collect() or create separate result list

❌ `int[] sum = {0}; stream.parallel().forEach(n -> sum[0] += n);` // Race condition
✅ `int sum = stream.parallel().reduce(0, Integer::sum);`

❌ `stream.filter(...); stream.map(...);` // Nothing happens, no terminal operation
✅ `stream.filter(...).map(...).collect(Collectors.toList());`

❌ Parallel stream on 100 elements (overhead > benefit)
✅ Parallel stream on 100,000+ elements with CPU-intensive operations

---

## 🎨 Code Templates

### LRU Cache
```java
class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;
    
    LRUCache(int capacity) {
        super(capacity, 0.75f, true); // accessOrder = true
        this.capacity = capacity;
    }
    
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}
```

### Word Frequency Counter
```java
Map<String, Integer> frequency = new HashMap<>();
for (String word : words) {
    frequency.compute(word, (k, v) -> v == null ? 1 : v + 1);
}
// Or with streams:
Map<String, Long> frequency = words.stream()
    .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
```

### Group and Aggregate
```java
Map<String, Double> avgSalaryByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.averagingDouble(Employee::getSalary)
    ));
```

### Thread-Safe Counter
```java
ConcurrentHashMap<String, Integer> counters = new ConcurrentHashMap<>();
counters.compute("key", (k, v) -> v == null ? 1 : v + 1);
// Or
counters.merge("key", 1, Integer::sum);
```

---

## 🚀 Running Examples

```bash
# Build with Gradle
cd /Users/appalanaidu/naidu/reachgoal/java-practice/java-collections
./gradlew build

# Run Collections examples
java -cp build/classes/java/main com.example.java_collections.collections.ListExamples
java -cp build/classes/java/main com.example.java_collections.collections.SetExamples
java -cp build/classes/java/main com.example.java_collections.collections.MapExamples
java -cp build/classes/java/main com.example.java_collections.collections.QueueExamples
java -cp build/classes/java/main com.example.java_collections.collections.ImmutableCollections
java -cp build/classes/java/main com.example.java_collections.collections.ComparatorExamples

# Stream examples
java -cp build/classes/java/main com.example.java_collections.streams.StreamBasics
java -cp build/classes/java/main com.example.java_collections.streams.AdvancedStreams
java -cp build/classes/java/main com.example.java_collections.streams.ParallelStreams
```

---

## 📚 Key Files

- **StudyGuide.md**: Complete reference (580 lines)
- **PresentationSlides.md**: 25 slides for 60-min presentation
- **README-PRESENTATION.md**: Detailed usage instructions
- **This file**: Quick reference during presentation

---

## ⏱️ Presentation Timing

- Collections (42 min): Slides 1-17
- Streams (18 min): Slides 18-25
- Total: 60 minutes

---

**Print this page for quick reference during your presentation!**
