# Java Collections & Streams - Comprehensive Study Guide
### For Experienced Developers | Java 17+

---

## Table of Contents

1. [Collections Framework Overview](#1-collections-framework-overview)
2. [List Implementations](#2-list-implementations)
3. [Set Implementations](#3-set-implementations)
4. [Map Implementations](#4-map-implementations)
5. [Queue & Deque Implementations](#5-queue--deque-implementations)
6. [Immutable Collections](#6-immutable-collections)
7. [Comparable & Comparator](#7-comparable--comparator)
8. [Stream API Fundamentals](#8-stream-api-fundamentals)
9. [Advanced Stream Operations](#9-advanced-stream-operations)
10. [Parallel Streams](#10-parallel-streams)
11. [Performance Considerations](#11-performance-considerations)
12. [Best Practices](#12-best-practices)

---

## 1. Collections Framework Overview

### Hierarchy
```
Collection (interface)
├── List (interface) - Ordered, allows duplicates
│   ├── ArrayList
│   ├── LinkedList
│   └── Vector (legacy, synchronized)
├── Set (interface) - No duplicates
│   ├── HashSet
│   ├── LinkedHashSet
│   └── TreeSet (SortedSet, NavigableSet)
└── Queue (interface) - FIFO operations
    ├── PriorityQueue
    ├── ArrayDeque
    └── BlockingQueue implementations

Map (interface) - Key-value pairs
├── HashMap
├── LinkedHashMap
├── TreeMap (SortedMap, NavigableMap)
├── ConcurrentHashMap
└── Hashtable (legacy, synchronized)
```

### Key Interfaces
- **Collection**: Root interface (size, isEmpty, add, remove, iterator)
- **List**: Positional access, search (get, set, add at index)
- **Set**: Mathematical set (no duplicates)
- **Map**: Key-value mappings (not extending Collection)
- **Queue**: FIFO operations (offer, poll, peek)
- **Deque**: Double-ended queue (addFirst, addLast, removeFirst, removeLast)

---

## 2. List Implementations

### ArrayList

#### What is ArrayList?
**Definition**: ArrayList is a resizable-array implementation of the List interface that provides dynamic array functionality with automatic growth.

**Internal Structure**:
- Backed by an Object[] array
- Elements stored contiguously in memory
- Default capacity: 16 elements
- Growth strategy: `newCapacity = oldCapacity + (oldCapacity >> 1)` (approximately 1.5x)

**Key Characteristics**:
- **Random Access**: Direct index-based access (implements RandomAccess marker interface)
- **Dynamic Sizing**: Automatically grows when capacity exceeded
- **Ordered**: Maintains insertion order
- **Allows Duplicates**: Same element can appear multiple times
- **Allows Null**: Can store null values
- **Not Thread-Safe**: Not synchronized

**Time Complexity**:
- get(index): O(1)
- add(element): O(1) amortized, O(n) when resizing
- add(index, element): O(n) - shift elements
- remove(index): O(n) - shift elements
- contains(element): O(n) - linear search

**Memory**: approximately 4 bytes per element + array overhead

#### Why Use ArrayList?

✅ **Fast Random Access**
- O(1) index-based retrieval
- Best cache locality due to contiguous memory
- Excellent for read-heavy workloads

✅ **Efficient Appending**
- O(1) amortized time for adding at the end
- Minimal overhead for sequential additions

✅ **Memory Efficient**
- Lower memory overhead compared to LinkedList
- Compact storage (just array + few fields)

✅ **Iteration Performance**
- Fastest iteration among List implementations
- Cache-friendly sequential access

✅ **Flexible Size**
- Grows automatically as needed
- Can pre-size to avoid resizing overhead

#### When to Use ArrayList?

**Use ArrayList When:**

✅ **Frequent Random Access**
- Need to access elements by index frequently
- Reading data more than modifying

✅ **Append-Heavy Operations**
- Adding elements at the end (no insertion/deletion in middle)
- Building lists incrementally

✅ **Read-Heavy Workloads**
- Iterating over elements frequently
- Minimal insertions/deletions

✅ **Memory Constraints**
- Need lower memory overhead
- Large number of small lists

✅ **Default Choice**
- General-purpose list usage
- No specific performance requirements

**Don't Use ArrayList When:**

❌ **Frequent Insertions/Deletions at Beginning**
- O(n) operation due to element shifting
- Use LinkedList or ArrayDeque instead

❌ **Frequent Insertions in Middle**
- Requires shifting elements
- Consider LinkedList if many such operations

❌ **Thread-Safe Access Needed**
- Not synchronized by default
- Use CopyOnWriteArrayList or external synchronization

❌ **Queue/Deque Operations**
- Inefficient for addFirst/removeFirst
- Use ArrayDeque or LinkedList

**Code Example**:
```java
// GOOD: Random access and append operations
List<String> fruits = new ArrayList<>();
fruits.add("Apple");   // O(1) - append at end
fruits.add("Banana");
fruits.add("Cherry");

String second = fruits.get(1); // O(1) - fast random access
System.out.println(second); // "Banana"

// Pre-size if you know the capacity
List<String> items = new ArrayList<>(1000); // Avoids resizing

// GOOD: Efficient iteration
for (String fruit : fruits) {
    System.out.println(fruit); // Fast iteration
}

// BAD: Frequent insertions at beginning
for (int i = 0; i < 1000; i++) {
    fruits.add(0, "New"); // O(n) each time - very slow!
}

// GOOD Alternative: Build in reverse and reverse once
List<String> reversed = new ArrayList<>();
for (int i = 0; i < 1000; i++) {
    reversed.add("Item" + i); // O(1)
}
Collections.reverse(reversed); // O(n) once
```

**Example Reference**: examples/collections/ListExamples.java → demonstrateArrayListInternals()

---

### LinkedList

#### What is LinkedList?
**Definition**: LinkedList is a doubly-linked list implementation of the List and Deque interfaces, where each element is a separate node containing data and references to previous/next nodes.

**Internal Structure**:
- Doubly-linked nodes: `Node<E> { E item; Node<E> next; Node<E> prev; }`
- Maintains references to first and last nodes
- No backing array

**Key Characteristics**:
- **Sequential Access**: Must traverse nodes to reach an element
- **Implements List and Deque**: Can be used as list, queue, or stack
- **Ordered**: Maintains insertion order
- **Allows Duplicates**: Same element can appear multiple times
- **Allows Null**: Can store null values
- **Not Thread-Safe**: Not synchronized

**Time Complexity**:
- get(index): O(n) - traverse from head/tail
- add(element): O(1) - append to tail
- add(index, element): O(n) - traverse + O(1) insert
- addFirst/addLast: O(1)
- removeFirst/removeLast: O(1)
- remove(index): O(n) - traverse + O(1) unlink

**Memory**: approximately 40 bytes overhead per node (24 bytes Node object + 16 bytes references + element)

#### Why Use LinkedList?

✅ **Efficient Insertions/Deletions at Ends**
- O(1) for addFirst, addLast, removeFirst, removeLast
- Perfect for queue/deque operations

✅ **No Resizing Overhead**
- Grows one node at a time
- No large array allocations

✅ **Efficient Middle Insertions (if already positioned)**
- O(1) insertion once you have the node reference
- Good for iterator-based insertions

✅ **Deque Operations**
- Natural implementation for double-ended queue
- Stack and queue operations

**Drawbacks:**

❌ **Poor Random Access**
- O(n) to access by index
- Must traverse nodes

❌ **Higher Memory Overhead**
- ~40 bytes per element vs ~4 bytes for ArrayList
- Poor cache locality

❌ **Slower Iteration**
- Pointer chasing through memory
- Cache misses

#### When to Use LinkedList?

**Use LinkedList When:**

✅ **Queue/Deque Implementation**
- Need FIFO (queue) or LIFO (stack) behavior
- Frequent operations at both ends

✅ **Frequent Insertions/Deletions at Ends**
- addFirst, addLast, removeFirst, removeLast are O(1)
- Building queues or processing pipelines

✅ **Iterator-Based Modifications**
- Adding/removing while iterating
- LinkedList's ListIterator is efficient

✅ **No Random Access Needed**
- Only sequential traversal
- Processing elements in order

**Don't Use LinkedList When:**

❌ **Random Access Required**
- Accessing by index frequently
- Use ArrayList instead

❌ **Memory Constrained**
- 10x memory overhead compared to ArrayList
- Use ArrayList for large lists

❌ **Read-Heavy Workload**
- Iteration slower than ArrayList
- Poor cache performance

❌ **Default List Usage**
- ArrayList is almost always better
- LinkedList has very specific use cases

**Performance Reality Check:**
```
ArrayList vs LinkedList - 100,000 elements:

Random Access (get):
- ArrayList: 0.002 ms
- LinkedList: 2,500 ms (1,000,000x slower!)

Add at End:
- ArrayList: 2 ms
- LinkedList: 5 ms

Add at Beginning:
- ArrayList: 150 ms
- LinkedList: 0.001 ms (150,000x faster!)

Memory:
- ArrayList: ~400 KB
- LinkedList: ~4,000 KB (10x more!)
```

**Code Example**:
```java
// GOOD: Using as Deque (double-ended queue)
Deque<String> deque = new LinkedList<>();

// O(1) operations at both ends
deque.addFirst("First");    // [First]
deque.addLast("Last");      // [First, Last]
deque.addFirst("New First"); // [New First, First, Last]

String first = deque.removeFirst(); // "New First" - O(1)
String last = deque.removeLast();   // "Last" - O(1)

// GOOD: Queue operations
Queue<Task> taskQueue = new LinkedList<>();
taskQueue.offer(new Task("Task1")); // Add to tail - O(1)
taskQueue.offer(new Task("Task2"));
Task next = taskQueue.poll();       // Remove from head - O(1)

// BAD: Random access (very slow!)
LinkedList<String> list = new LinkedList<>();
for (int i = 0; i < 1000; i++) {
    list.add("Item" + i);
}
String middle = list.get(500); // O(n) - traverses 500 nodes!

// BAD: Using LinkedList as default List
List<String> items = new LinkedList<>(); // Should be ArrayList!
items.add("A");
items.get(0); // Slower than necessary

// GOOD: Iterator-based modification
LinkedList<String> names = new LinkedList<>(Arrays.asList("A", "B", "C", "D"));
ListIterator<String> iter = names.listIterator();
while (iter.hasNext()) {
    String name = iter.next();
    if (name.equals("B")) {
        iter.add("B2"); // O(1) insertion at current position
    }
}
// Result: [A, B, B2, C, D]
```

**Example Reference**: examples/collections/ListExamples.java → demonstrateLinkedListInternals()

---

### ArrayList vs LinkedList: Decision Matrix

| Operation | Use ArrayList | Use LinkedList |
|-----------|--------------|----------------|
| Random access by index | ✅ Always | ❌ Never |
| Sequential iteration | ✅ Preferred | ⚠️ Acceptable |
| Add at end | ✅ Preferred | ⚠️ Acceptable |
| Add at beginning | ❌ Slow | ✅ Always |
| Insert in middle (by index) | ⚠️ If infrequent | ❌ Still slow |
| Remove from beginning | ❌ Slow | ✅ Always |
| Remove from end | ✅ Fast | ✅ Fast |
| Queue/Deque operations | ❌ Use ArrayDeque | ✅ Good choice |
| Memory efficiency | ✅ Always | ❌ Never |
| General-purpose list | ✅ Default | ❌ Rarely |

**Performance Comparison**: examples/collections/ListExamples.java → compareRandomAccess(), compareInsertionPerformance()

---

### Fail-Fast Iterators
All collections (except concurrent) have fail-fast iterators that throw ConcurrentModificationException if collection modified during iteration.

**Code Example**:
```java
List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));

// BAD: ConcurrentModificationException
for (String item : list) {
    if (item.equals("B")) {
        list.remove(item); // Throws exception!
    }
}

// GOOD: Use Iterator.remove()
Iterator<String> iter = list.iterator();
while (iter.hasNext()) {
    String item = iter.next();
    if (item.equals("B")) {
        iter.remove(); // Safe removal
    }
}

// GOOD: Use removeIf (Java 8+)
list.removeIf(item -> item.equals("B"));
```

**Example Reference**: examples/collections/ListExamples.java → demonstrateFailFastBehavior()

---

## 3. Set Implementations

### HashSet

#### What is HashSet?
**Definition**: HashSet is a hash table-based implementation of the Set interface that stores unique elements with no duplicates and no guaranteed order.

**Internal Structure**:
- Backed by HashMap (elements stored as keys with dummy PRESENT object as value)
- Default capacity: 16 buckets
- Load factor: 0.75
- Hash-based bucket distribution

**Key Characteristics**:
- **No Duplicates**: Automatically rejects duplicate elements
- **No Ordering**: Iteration order is unpredictable
- **Null Allowed**: Can store one null element
- **Fast Operations**: O(1) average time for add, remove, contains
- **Not Thread-Safe**: Not synchronized

**Time Complexity**:
- add(element): O(1) average
- remove(element): O(1) average
- contains(element): O(1) average
- iteration: O(capacity + size)

#### Why Use HashSet?

✅ **Uniqueness Guarantee**
- Automatically prevents duplicates
- No manual duplicate checking needed

✅ **Fast Membership Testing**
- O(1) contains() operation
- Ideal for "does this exist?" queries

✅ **Efficient Add/Remove**
- O(1) average time
- Much faster than List.contains() for large datasets

✅ **Set Operations**
- Union, intersection, difference
- Mathematical set semantics

#### When to Use HashSet?

**Use HashSet When:**

✅ **Need Unique Elements**
- Eliminating duplicates from data
- Maintaining a collection of distinct items

✅ **Fast Lookups Required**
- Frequent contains() checks
- "Is this element present?" queries

✅ **Order Doesn't Matter**
- No need for sorted or insertion order
- Just need unique collection

✅ **Set Operations Needed**
- Union, intersection, difference
- Mathematical set operations

**Don't Use HashSet When:**

❌ **Order Matters**
- Need sorted order: Use TreeSet
- Need insertion order: Use LinkedHashSet

❌ **Duplicates Allowed**
- Use List instead

❌ **Need Index Access**
- Sets don't support get(index)
- Use List instead

**Code Example**:
```java
// GOOD: Remove duplicates
List<String> listWithDupes = Arrays.asList("A", "B", "A", "C", "B");
Set<String> unique = new HashSet<>(listWithDupes);
System.out.println(unique); // [A, B, C] (order may vary)

// GOOD: Fast membership testing
Set<String> validCodes = new HashSet<>(Arrays.asList("US", "UK", "CA", "AU"));
if (validCodes.contains("US")) { // O(1)
    System.out.println("Valid country code");
}

// GOOD: Set operations
Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3, 4));
Set<Integer> set2 = new HashSet<>(Arrays.asList(3, 4, 5, 6));

// Union
Set<Integer> union = new HashSet<>(set1);
union.addAll(set2); // {1, 2, 3, 4, 5, 6}

// Intersection
Set<Integer> intersection = new HashSet<>(set1);
intersection.retainAll(set2); // {3, 4}

// Difference
Set<Integer> difference = new HashSet<>(set1);
difference.removeAll(set2); // {1, 2}

// GOOD: Efficient duplicate detection in large dataset
Set<String> seen = new HashSet<>();
for (String item : largeList) {
    if (!seen.add(item)) { // add() returns false if duplicate
        System.out.println("Duplicate found: " + item);
    }
}
```

**Example Reference**: examples/collections/SetExamples.java → demonstrateHashSetInternals()

---

### LinkedHashSet

#### What is LinkedHashSet?
**Definition**: LinkedHashSet is a hash table and linked list implementation of Set that maintains insertion order while preserving HashSet's performance.

**Internal Structure**:
- Extends HashSet with doubly-linked list through entries
- Combines HashMap's speed with predictable iteration order
- Slightly more memory overhead than HashSet

**Key Characteristics**:
- **No Duplicates**: Like HashSet
- **Insertion Order**: Maintains order elements were added
- **Predictable Iteration**: Always iterates in insertion order
- **Null Allowed**: Can store one null element
- **Performance**: Slightly slower than HashSet, faster than TreeSet

**Time Complexity**:
- add(element): O(1)
- remove(element): O(1)
- contains(element): O(1)
- iteration: O(size) in insertion order

#### Why Use LinkedHashSet?

✅ **Predictable Iteration Order**
- Insertion order preserved
- Deterministic behavior

✅ **Unique + Ordered**
- Best of both worlds: uniqueness + order
- Alternative to maintaining separate List and Set

✅ **Cache-Friendly**
- Good for LRU cache implementations
- Ordered processing of unique items

#### When to Use LinkedHashSet?

**Use LinkedHashSet When:**

✅ **Need Unique Elements + Insertion Order**
- Remove duplicates while maintaining order
- Predictable iteration required

✅ **Building Ordered Unique Collections**
- Processing items in the order they arrive
- Maintaining history of unique events

✅ **Deterministic Testing**
- Test results need to be reproducible
- Order-dependent output

**Don't Use LinkedHashSet When:**

❌ **Order Doesn't Matter**
- Use HashSet (slightly faster)

❌ **Need Sorted Order**
- Use TreeSet instead

❌ **Maximum Performance Critical**
- HashSet is marginally faster

**Code Example**:
```java
// GOOD: Remove duplicates while maintaining order
List<String> items = Arrays.asList("C", "A", "B", "A", "C", "D");
Set<String> orderedUnique = new LinkedHashSet<>(items);
System.out.println(orderedUnique); // [C, A, B, D] - insertion order preserved!

// Compare with HashSet
Set<String> unordered = new HashSet<>(items);
System.out.println(unordered); // [A, B, C, D] or any order - unpredictable

// GOOD: Processing unique requests in order
Set<String> processedRequests = new LinkedHashSet<>();
processedRequests.add("Request-1");
processedRequests.add("Request-2");
processedRequests.add("Request-1"); // Duplicate ignored
processedRequests.add("Request-3");

// Process in the order first seen
for (String request : processedRequests) {
    processRequest(request); // Processes: Request-1, Request-2, Request-3
}
```

**Example Reference**: examples/collections/SetExamples.java → demonstrateLinkedHashSetOrdering()

---

### TreeSet

#### What is TreeSet?
**Definition**: TreeSet is a NavigableSet implementation based on a Red-Black tree that stores elements in sorted order.

**Internal Structure**:
- Backed by TreeMap (Red-Black self-balancing binary search tree)
- Elements stored in sorted order
- No null elements allowed (unless custom comparator permits)

**Key Characteristics**:
- **Sorted Order**: Elements always sorted (natural or custom)
- **No Duplicates**: Set semantics
- **NavigableSet**: Rich navigation methods (ceiling, floor, higher, lower)
- **Range Operations**: Efficient subSet, headSet, tailSet
- **No Null**: NullPointerException if null added (with natural ordering)
- **Not Thread-Safe**: Not synchronized

**Time Complexity**:
- add(element): O(log n)
- remove(element): O(log n)
- contains(element): O(log n)
- first/last: O(log n)
- navigation methods: O(log n)

#### Why Use TreeSet?

✅ **Automatic Sorting**
- Elements always in sorted order
- No manual sorting needed

✅ **Range Queries**
- Efficient subSet, headSet, tailSet operations
- Find elements in a range

✅ **Navigation Operations**
- Find closest elements (ceiling, floor, higher, lower)
- First, last elements

✅ **Sorted Iteration**
- Iterate in sorted order guaranteed

#### When to Use TreeSet?

**Use TreeSet When:**

✅ **Need Sorted Unique Collection**
- Elements must be sorted automatically
- Frequent sorted iteration

✅ **Range Operations Required**
- Finding elements between two values
- Subset operations

✅ **Navigation Queries**
- "Find smallest element >= x"
- "Find largest element < y"

✅ **Sorted Processing**
- Processing elements in order is critical
- Min/max queries

**Don't Use TreeSet When:**

❌ **Order Not Important**
- Use HashSet (3x faster)

❌ **Need Fast Operations**
- HashSet: O(1) vs TreeSet: O(log n)
- Performance critical code

❌ **Elements Not Comparable**
- Elements must implement Comparable or provide Comparator

**Code Example**:
```java
// GOOD: Automatically sorted collection
TreeSet<Integer> scores = new TreeSet<>();
scores.addAll(Arrays.asList(85, 92, 78, 95, 88));
System.out.println(scores); // [78, 85, 88, 92, 95] - always sorted!

// GOOD: NavigableSet operations
System.out.println(scores.first());      // 78 - lowest score
System.out.println(scores.last());       // 95 - highest score
System.out.println(scores.ceiling(86));  // 88 - smallest >= 86
System.out.println(scores.floor(86));    // 85 - largest <= 86
System.out.println(scores.higher(88));   // 92 - next higher than 88
System.out.println(scores.lower(88));    // 85 - next lower than 88

// GOOD: Range queries
SortedSet<Integer> passingScores = scores.tailSet(80); // [85, 88, 92, 95]
SortedSet<Integer> lowScores = scores.headSet(85);     // [78]
SortedSet<Integer> midRange = scores.subSet(80, 93);   // [85, 88, 92]

// GOOD: Custom sorting
TreeSet<String> names = new TreeSet<>(Comparator.reverseOrder());
names.addAll(Arrays.asList("Charlie", "Alice", "Bob"));
System.out.println(names); // [Charlie, Bob, Alice]

// GOOD: Priority-based processing
TreeSet<Task> tasks = new TreeSet<>(Comparator.comparing(Task::getPriority));
tasks.add(new Task("Low", 3));
tasks.add(new Task("High", 1));
tasks.add(new Task("Medium", 2));

// Process in priority order
for (Task task : tasks) {
    processTask(task); // Processes: High(1), Medium(2), Low(3)
}

// BAD: When you don't need sorting
TreeSet<String> set = new TreeSet<>(); // O(log n) operations
// Better: HashSet<String> set = new HashSet<>(); // O(1) operations
```

**Example Reference**: examples/collections/SetExamples.java → demonstrateTreeSetOrdering()

---

### EnumSet

#### What is EnumSet?
**Definition**: EnumSet is a specialized Set implementation for use with enum types, internally represented as a bit vector.

**Internal Structure**:
- Bit vector implementation (extremely compact)
- JumboEnumSet for enums with > 64 constants
- RegularEnumSet for enums with <= 64 constants

**Key Characteristics**:
- **Enum Only**: Type-safe, compile-time checked
- **Bit Vector**: 1 bit per enum constant (incredibly efficient)
- **Natural Order**: Iteration in enum declaration order
- **No Null**: NullPointerException if null added
- **Not Thread-Safe**: Not synchronized

**Time Complexity**:
- All operations: O(1)
- Bulk operations: Extremely fast (bitwise operations)

**Memory**:
- Minimal: 1 bit per enum constant
- 64 enums = 8 bytes for entire set!

#### Why Use EnumSet?

✅ **Extreme Efficiency**
- 64x more memory efficient than HashSet
- Bitwise operations (fastest possible)

✅ **Type Safety**
- Compile-time enum type checking
- No ClassCastException

✅ **Rich Factory Methods**
- of(), range(), allOf(), noneOf(), complementOf()

✅ **Natural Ordering**
- Iterates in enum declaration order

#### When to Use EnumSet?

**Use EnumSet When:**

✅ **Working with Enums**
- Storing sets of enum values
- Flags, options, permissions

✅ **Performance Critical**
- Need absolute best performance
- Large number of set operations

✅ **Memory Constrained**
- Many enum sets in memory
- Minimal memory footprint needed

**Always Use EnumSet Instead of:**
- `Set<EnumType>` with HashSet/TreeSet
- `EnumSet` is always better for enums

**Code Example**:
```java
enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

// GOOD: EnumSet factory methods
EnumSet<Day> weekdays = EnumSet.range(Day.MONDAY, Day.FRIDAY);
EnumSet<Day> weekend = EnumSet.of(Day.SATURDAY, Day.SUNDAY);
EnumSet<Day> allDays = EnumSet.allOf(Day.class);
EnumSet<Day> noDays = EnumSet.noneOf(Day.class);

// GOOD: Complement
EnumSet<Day> workDays = EnumSet.range(Day.MONDAY, Day.FRIDAY);
EnumSet<Day> offDays = EnumSet.complementOf(workDays); // [SATURDAY, SUNDAY]

// GOOD: Efficient set operations
EnumSet<Day> days1 = EnumSet.of(Day.MONDAY, Day.WEDNESDAY, Day.FRIDAY);
EnumSet<Day> days2 = EnumSet.of(Day.WEDNESDAY, Day.THURSDAY);

days1.addAll(days2); // Union - bitwise OR
days1.retainAll(days2); // Intersection - bitwise AND

// GOOD: Permission/flags system
enum Permission {
    READ, WRITE, EXECUTE, DELETE
}

class User {
    private EnumSet<Permission> permissions;

    public User(Permission... perms) {
        permissions = EnumSet.noneOf(Permission.class);
        permissions.addAll(Arrays.asList(perms));
    }

    public boolean hasPermission(Permission perm) {
        return permissions.contains(perm); // O(1) bitwise check
    }
}

User admin = new User(Permission.READ, Permission.WRITE, Permission.DELETE);
User guest = new User(Permission.READ);

System.out.println(admin.hasPermission(Permission.DELETE)); // true
System.out.println(guest.hasPermission(Permission.WRITE));  // false

// BAD: Using HashSet for enums
Set<Day> badSet = new HashSet<>(); // Much slower and uses more memory
// GOOD: Always use EnumSet
EnumSet<Day> goodSet = EnumSet.noneOf(Day.class); // 64x more efficient!
```

**Performance Comparison (1 million operations)**:
```
EnumSet:  5 ms,  Memory: 8 bytes
HashSet:  150 ms, Memory: 512 bytes
```

**Example Reference**: examples/collections/SetExamples.java → demonstrateEnumSet()

---

### Set Implementations: Decision Matrix

| Feature | HashSet | LinkedHashSet | TreeSet | EnumSet |
|---------|---------|---------------|---------|---------|
| **Order** | None | Insertion | Sorted | Declaration |
| **Performance** | O(1) | O(1) | O(log n) | O(1) bitwise |
| **Memory** | Medium | High | High | Minimal |
| **Null** | 1 allowed | 1 allowed | Not allowed | Not allowed |
| **Use Case** | General unique | Ordered unique | Sorted unique | Enum flags |
| **Best For** | Fast lookups | Predictable iteration | Range queries | Enums only |

### hashCode() & equals() Contract
For objects in HashSet/HashMap:
1. If a.equals(b) then a.hashCode() == b.hashCode()
2. If a.hashCode() != b.hashCode() then !a.equals(b)
3. Consistent: Multiple calls return same value if object unchanged
4. Override both together or neither

**Code Example**:
```java
class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return age == person.age &&
               Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}

// Usage in HashSet
Set<Person> people = new HashSet<>();
people.add(new Person("Alice", 30));
people.add(new Person("Alice", 30)); // Duplicate - not added
System.out.println(people.size()); // 1

// If hashCode() not overridden, both would be added!
```

**Example Reference**: examples/collections/SetExamples.java → demonstrateCustomObjects()

---

## 4. Map Implementations

### HashMap

#### What is HashMap?
**Definition**: HashMap is a hash table-based implementation of the Map interface that stores key-value pairs with fast lookup, insertion, and deletion.

**Internal Structure**:
- Array of Node buckets (default capacity: 16)
- Load factor: 0.75 (resize when 75% full)
- Hash calculation: `(h = key.hashCode()) XOR (h >>> 16)` - spreads high bits
- Index calculation: `(n - 1) & hash` - n must be power of 2
- Collision handling:
  - Java 7: Linked list (O(n) worst case)
  - Java 8+: Linked list → Red-Black tree when bin size > 8 (TREEIFY_THRESHOLD)

**Key Characteristics**:
- **Key-Value Storage**: Associates keys with values
- **No Duplicate Keys**: Each key maps to one value (new value overwrites)
- **No Order**: Iteration order unpredictable
- **Null Allowed**: One null key, multiple null values
- **Fast Operations**: O(1) average for get/put/remove
- **Not Thread-Safe**: Not synchronized

**Time Complexity**:
- get/put/remove: O(1) average, O(log n) worst case (after treeification)
- containsKey: O(1) average
- iteration: O(capacity + size)

#### Why Use HashMap?

✅ **Fast Key-Value Lookups**
- O(1) average time for retrieval by key
- Ideal for caching, indexing, counting

✅ **Flexible Associations**
- Map any object to any object
- Perfect for dictionaries, indexes, mappings

✅ **Efficient Updates**
- O(1) for insert/update/delete by key
- No resizing on individual operations (amortized)

✅ **Rich API (Java 8+)**
- compute, computeIfAbsent, computeIfPresent
- merge, putIfAbsent, getOrDefault
- Atomic-like operations for cleaner code

#### When to Use HashMap?

**Use HashMap When:**

✅ **Need Fast Key Lookups**
- Caching results by key
- Indexing data for quick access
- Counting frequencies

✅ **Associating Data**
- User ID → User object
- Product code → Product details
- Word → Definition

✅ **No Ordering Required**
- Don't care about iteration order
- Just need key-value associations

✅ **Unique Keys**
- Each key represents one value
- Later values overwrite earlier ones

**Don't Use HashMap When:**

❌ **Need Ordering**
- Sorted order: Use TreeMap
- Insertion order: Use LinkedHashMap

❌ **Thread-Safe Access Required**
- Use ConcurrentHashMap
- Or synchronize externally

❌ **Memory Critical with Many Small Maps**
- Each HashMap has ~96 bytes overhead + array
- Consider alternatives for many tiny maps

**Code Example**:
```java
// GOOD: Frequency counting
List<String> words = Arrays.asList("apple", "banana", "apple", "cherry", "banana");
Map<String, Integer> frequency = new HashMap<>();

for (String word : words) {
    frequency.put(word, frequency.getOrDefault(word, 0) + 1);
}
System.out.println(frequency); // {apple=2, banana=2, cherry=1}

// BETTER: Using merge (Java 8+)
Map<String, Integer> freq2 = new HashMap<>();
words.forEach(word -> freq2.merge(word, 1, Integer::sum));

// GOOD: Caching expensive results
Map<String, Result> cache = new HashMap<>();

Result getResult(String key) {
    return cache.computeIfAbsent(key, k -> expensiveComputation(k));
}

// GOOD: Indexing objects by ID
List<User> users = loadUsers();
Map<Long, User> userIndex = new HashMap<>();
for (User user : users) {
    userIndex.put(user.getId(), user);
}

// Fast lookup - O(1)
User user = userIndex.get(12345L);

// GOOD: Grouping (before Java 8 Streams)
Map<String, List<Employee>> byDepartment = new HashMap<>();
for (Employee emp : employees) {
    byDepartment.computeIfAbsent(emp.getDepartment(), k -> new ArrayList<>())
                .add(emp);
}

// GOOD: Replace get-check-put pattern
// BAD (old way):
if (!map.containsKey(key)) {
    map.put(key, computeValue());
}

// GOOD (new way):
map.computeIfAbsent(key, k -> computeValue());

// GOOD: Null key/value support
Map<String, String> map = new HashMap<>();
map.put(null, "nullKey");     // OK - one null key allowed
map.put("key1", null);         // OK - multiple null values allowed
map.put("key2", null);         // OK
```

**Example Reference**: examples/collections/MapExamples.java → demonstrateHashMapInternals(), demonstrateHashCollisions()

---

### LinkedHashMap

#### What is LinkedHashMap?
**Definition**: LinkedHashMap extends HashMap with a doubly-linked list running through all entries, maintaining insertion order (or access order).

**Internal Structure**:
- HashMap + doubly-linked list through entries
- Each entry has before/after references
- Slightly more memory than HashMap

**Key Characteristics**:
- **Predictable Iteration**: Insertion order or access order
- **All HashMap Features**: Same performance as HashMap
- **LRU Cache Support**: Built-in eviction mechanism
- **Null Allowed**: One null key, multiple null values
- **Not Thread-Safe**: Not synchronized

**Time Complexity**:
- get/put/remove: O(1)
- iteration: O(size) in predictable order

#### Why Use LinkedHashMap?

✅ **Predictable Iteration Order**
- Insertion order maintained
- Deterministic behavior

✅ **LRU Cache Implementation**
- Access-order mode for LRU
- Built-in removeEldestEntry() hook

✅ **Ordered Key-Value Pairs**
- Maintain order of configuration properties
- Preserve order for JSON serialization

#### When to Use LinkedHashMap?

**Use LinkedHashMap When:**

✅ **Need HashMap + Order**
- Want fast lookups AND predictable iteration
- JSON/XML serialization with order

✅ **Building LRU Cache**
- Access-order mode + removeEldestEntry()
- Perfect for caching with eviction

✅ **Configuration Management**
- Preserve order of properties
- Maintain insertion sequence

**Don't Use LinkedHashMap When:**

❌ **Order Doesn't Matter**
- Use HashMap (slightly faster, less memory)

❌ **Need Sorted Order**
- Use TreeMap instead

**Code Example**:
```java
// GOOD: Insertion order (default)
Map<String, Integer> insertionOrder = new LinkedHashMap<>();
insertionOrder.put("C", 3);
insertionOrder.put("A", 1);
insertionOrder.put("B", 2);
System.out.println(insertionOrder); // {C=3, A=1, B=2} - order preserved!

// Compare with HashMap
Map<String, Integer> hashMap = new HashMap<>();
hashMap.put("C", 3);
hashMap.put("A", 1);
hashMap.put("B", 2);
System.out.println(hashMap); // Order unpredictable

// GOOD: LRU Cache implementation
class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    LRUCache(int capacity) {
        super(16, 0.75f, true); // true = access-order mode
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity; // Evict when exceeds capacity
    }
}

LRUCache<String, String> cache = new LRUCache<>(3);
cache.put("A", "1");
cache.put("B", "2");
cache.put("C", "3");
cache.get("A"); // Access A - moves to end
cache.put("D", "4"); // Evicts B (least recently used)
System.out.println(cache); // {C=3, A=1, D=4}

// GOOD: Access order mode
Map<String, Integer> accessOrder = new LinkedHashMap<>(16, 0.75f, true);
accessOrder.put("First", 1);
accessOrder.put("Second", 2);
accessOrder.put("Third", 3);

accessOrder.get("First"); // Access "First"
// Order now: {Second=2, Third=3, First=1}

// GOOD: Configuration properties with order
Map<String, String> config = new LinkedHashMap<>();
config.put("database.url", "jdbc:mysql://localhost");
config.put("database.user", "admin");
config.put("database.password", "secret");
// Iteration maintains this order
```

**Example Reference**: examples/collections/MapExamples.java → demonstrateLinkedHashMapOrdering()

---

### TreeMap

#### What is TreeMap?
**Definition**: TreeMap is a Red-Black tree-based NavigableMap implementation that stores entries in sorted order by key.

**Internal Structure**:
- Red-Black self-balancing binary search tree
- Each node contains key-value pair + color (red/black)
- No null keys (with natural ordering)

**Key Characteristics**:
- **Sorted Order**: Keys always sorted (natural or custom)
- **NavigableMap**: Rich navigation methods (ceiling, floor, higher, lower)
- **Range Operations**: Efficient subMap, headMap, tailMap
- **No Null Keys**: NullPointerException (with Comparable)
- **Null Values**: Allowed
- **Not Thread-Safe**: Not synchronized

**Time Complexity**:
- get/put/remove: O(log n)
- firstKey/lastKey: O(log n)
- navigation methods: O(log n)

#### Why Use TreeMap?

✅ **Automatic Sorting**
- Keys always in sorted order
- No manual sorting needed

✅ **Navigation Operations**
- Find closest keys (ceiling, floor, higher, lower)
- Range queries (subMap, headMap, tailMap)

✅ **Ordered Iteration**
- Guaranteed sorted iteration
- Min/max in O(log n)

#### When to Use TreeMap?

**Use TreeMap When:**

✅ **Need Sorted Key-Value Pairs**
- Keys must be sorted automatically
- Frequent sorted iteration

✅ **Range Queries Required**
- "All entries between key1 and key2"
- "All entries >= key"

✅ **Navigation Needed**
- "Smallest key >= x"
- "Largest key < y"

✅ **Ordered Processing**
- Process entries in sorted key order
- Priority-based mappings

**Don't Use TreeMap When:**

❌ **Order Not Important**
- Use HashMap (5-10x faster)

❌ **Maximum Performance Critical**
- O(log n) vs HashMap's O(1)

❌ **Keys Not Comparable**
- Must implement Comparable or provide Comparator

**Code Example**:
```java
// GOOD: Automatically sorted by key
TreeMap<Integer, String> scores = new TreeMap<>();
scores.put(85, "Bob");
scores.put(92, "Alice");
scores.put(78, "Charlie");
scores.put(95, "Diana");

System.out.println(scores);
// {78=Charlie, 85=Bob, 92=Alice, 95=Diana} - sorted by key!

// GOOD: NavigableMap operations
System.out.println(scores.firstKey());      // 78 - lowest
System.out.println(scores.lastKey());       // 95 - highest
System.out.println(scores.ceilingKey(86));  // 92 - smallest >= 86
System.out.println(scores.floorKey(86));    // 85 - largest <= 86
System.out.println(scores.higherKey(85));   // 92 - next higher than 85
System.out.println(scores.lowerKey(85));    // 78 - next lower than 85

// GOOD: Range queries
SortedMap<Integer, String> range = scores.subMap(80, 93); // [80, 93)
System.out.println(range); // {85=Bob, 92=Alice}

SortedMap<Integer, String> topScores = scores.tailMap(90); // >= 90
System.out.println(topScores); // {92=Alice, 95=Diana}

SortedMap<Integer, String> lowScores = scores.headMap(85); // < 85
System.out.println(lowScores); // {78=Charlie}

// GOOD: Custom ordering (reverse)
TreeMap<String, Integer> reverseMap = new TreeMap<>(Comparator.reverseOrder());
reverseMap.put("Apple", 1);
reverseMap.put("Banana", 2);
reverseMap.put("Cherry", 3);
System.out.println(reverseMap); // {Cherry=3, Banana=2, Apple=1}

// GOOD: Time-based event processing
TreeMap<LocalDateTime, Event> events = new TreeMap<>();
events.put(LocalDateTime.now().plusHours(2), new Event("Meeting"));
events.put(LocalDateTime.now().plusHours(1), new Event("Call"));
events.put(LocalDateTime.now().plusHours(3), new Event("Lunch"));

// Process in chronological order
for (Map.Entry<LocalDateTime, Event> entry : events.entrySet()) {
    processEvent(entry.getValue()); // Processed in time order
}

// GOOD: Find next event after specific time
LocalDateTime now = LocalDateTime.now().plusMinutes(90);
Map.Entry<LocalDateTime, Event> nextEvent = events.ceilingEntry(now);
System.out.println("Next event: " + nextEvent.getValue());
```

**Example Reference**: examples/collections/MapExamples.java → demonstrateTreeMapNavigable()

---

### ConcurrentHashMap

#### What is ConcurrentHashMap?
**Definition**: ConcurrentHashMap is a thread-safe hash table implementation optimized for high concurrency without locking the entire map.

**Internal Structure** (Java 8+):
- Segmented array of Nodes (like HashMap)
- Lock-free reads using volatile fields
- CAS (Compare-And-Swap) operations
- Synchronized writes at bin level (fine-grained locking)
- No global lock

**Key Characteristics**:
- **Thread-Safe**: Safe for concurrent access
- **Lock-Free Reads**: Multiple readers, no blocking
- **Fine-Grained Locking**: Locks only affected bins for writes
- **No Null**: Neither keys nor values allowed
- **Weakly Consistent**: Iterators don't throw ConcurrentModificationException
- **Atomic Operations**: Built-in atomic compute methods

**Time Complexity**:
- get: O(1) - lock-free
- put/remove: O(1) average - with minimal locking
- All operations thread-safe

#### Why Use ConcurrentHashMap?

✅ **Thread-Safe Without Synchronization**
- No need for external locks
- Multiple threads can read/write safely

✅ **High Concurrency Performance**
- Lock-free reads
- Writers don't block readers
- Fine-grained locking for writes

✅ **Atomic Operations**
- compute, computeIfAbsent, merge
- No race conditions
- Cleaner concurrent code

✅ **Scalable**
- Performance scales with threads
- Minimal contention

#### When to Use ConcurrentHashMap?

**Use ConcurrentHashMap When:**

✅ **Multi-Threaded Access**
- Multiple threads reading/writing map
- Concurrent caching
- Shared state between threads

✅ **High Read Concurrency**
- Many readers, few writers
- Lock-free reads are critical

✅ **Atomic Updates Needed**
- Incrementing counters
- Conditional updates
- Accumulating values

✅ **No External Locking Desired**
- Want thread-safety without synchronized blocks
- Simplify concurrent code

**Don't Use ConcurrentHashMap When:**

❌ **Single-Threaded**
- Use HashMap (less overhead)

❌ **Need Null Keys/Values**
- ConcurrentHashMap doesn't allow nulls
- Use synchronized Map wrapper

❌ **Strict Consistency Required**
- Iterators are weakly consistent
- May not reflect latest updates

❌ **Small Maps with Rare Updates**
- Overhead not justified
- Simple synchronization may suffice

**Code Example**:
```java
// GOOD: Shared counter across threads
ConcurrentHashMap<String, Integer> counters = new ConcurrentHashMap<>();

// Thread-safe increment
counters.compute("requests", (k, v) -> v == null ? 1 : v + 1);

// Better: using merge
counters.merge("requests", 1, Integer::sum); // Atomic increment

// GOOD: Multi-threaded frequency count
ConcurrentHashMap<String, Long> wordCount = new ConcurrentHashMap<>();

// Safe from multiple threads
ExecutorService executor = Executors.newFixedThreadPool(10);
for (String word : largeWordList) {
    executor.submit(() ->
        wordCount.merge(word, 1L, Long::sum) // Thread-safe accumulation
    );
}

// GOOD: Concurrent caching
ConcurrentHashMap<String, User> userCache = new ConcurrentHashMap<>();

User getUser(String id) {
    return userCache.computeIfAbsent(id, this::loadUserFromDB);
    // Only one thread will call loadUserFromDB for same id
}

// GOOD: putIfAbsent for thread-safe initialization
ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

Connection getConnection(String url) {
    Connection existing = connections.putIfAbsent(url, createConnection(url));
    return existing != null ? existing : connections.get(url);
}

// GOOD: Atomic compare-and-swap pattern
boolean updateIfMatches(String key, Integer expectedValue, Integer newValue) {
    return counters.replace(key, expectedValue, newValue);
}

// GOOD: Bulk parallel operations
ConcurrentHashMap<String, Integer> scores = new ConcurrentHashMap<>();
// ... populate with data ...

// Parallel forEach
scores.forEach(1, (key, value) -> processScore(key, value));

// Parallel search
String result = scores.search(1, (key, value) ->
    value > 90 ? key : null
);

// Parallel reduce
int sum = scores.reduceValues(1, Integer::sum);

// BAD: Don't use for single thread
ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
// Should use HashMap - no concurrency needed!

// BAD: Compound operations without atomicity
// WRONG:
if (map.containsKey(key)) {
    map.put(key, map.get(key) + 1); // Race condition!
}

// CORRECT:
map.compute(key, (k, v) -> v == null ? 1 : v + 1); // Atomic
```

**Performance Comparison (4 threads, 1M operations)**:
```
ConcurrentHashMap:     150 ms
Synchronized HashMap:  850 ms (5.6x slower)
Collections.synchronizedMap: 900 ms (6x slower)
```

**Example Reference**: examples/collections/MapExamples.java → demonstrateConcurrentHashMap()

---

### Map Implementations: Decision Matrix

| Feature | HashMap | LinkedHashMap | TreeMap | ConcurrentHashMap |
|---------|---------|---------------|---------|-------------------|
| **Order** | None | Insertion/Access | Sorted | None |
| **Performance** | O(1) | O(1) | O(log n) | O(1) |
| **Thread-Safe** | No | No | No | Yes |
| **Null Key** | 1 allowed | 1 allowed | Not allowed | Not allowed |
| **Null Values** | Allowed | Allowed | Allowed | Not allowed |
| **Memory** | Low | Medium | High | Medium |
| **Use Case** | General | Ordered/LRU | Sorted/Range | Concurrent |
| **Best For** | Fast lookups | Predictable order | Navigation | Multi-threaded |

### Java 8+ Compute Methods
- compute(key, BiFunction): Always computes new value
- computeIfAbsent(key, Function): Compute if key absent
- computeIfPresent(key, BiFunction): Compute if key present
- merge(key, value, BiFunction): Merge with existing value

**Example Reference**: examples/collections/MapExamples.java → demonstrateComputeOperations(), demonstrateMergeOperation()

---

## 5. Queue & Deque Implementations

### PriorityQueue

#### What is PriorityQueue?
**Definition**: PriorityQueue is a heap-based priority queue implementation where elements are ordered by their natural ordering or a custom Comparator, with the smallest element always at the head.

**Internal Structure**:
- Binary heap (array-based complete binary tree)
- Min-heap by default (smallest element at root)
- Dynamically resizable array

**Key Characteristics**:
- **Priority Ordering**: Not FIFO - elements ordered by priority
- **Head is Minimum**: Smallest element (by natural order or Comparator) always first
- **No Null**: NullPointerException if null added
- **Unbounded**: Grows as needed
- **Not Thread-Safe**: Not synchronized

**Time Complexity**:
- offer/add: O(log n) - bubble up
- peek: O(1) - look at root
- poll/remove: O(log n) - bubble down
- contains: O(n) - linear search
- remove(Object): O(n) - find + O(log n) remove

#### Why Use PriorityQueue?

✅ **Automatic Priority Ordering**
- Always get highest priority element first
- No manual sorting needed

✅ **Efficient Priority Operations**
- O(log n) for insert/remove highest priority
- Better than sorting entire list

✅ **Dynamic Prioritization**
- Add elements as they arrive
- Always process most important first

#### When to Use PriorityQueue?

**Use PriorityQueue When:**

✅ **Need Priority-Based Processing**
- Task scheduling by priority
- Event processing by urgency
- Top-K problems

✅ **Always Need Min/Max Element**
- Process smallest/largest repeatedly
- Streaming min/max queries

✅ **Heap Algorithms**
- Dijkstra's shortest path
- Huffman coding
- Merge K sorted lists

**Don't Use PriorityQueue When:**

❌ **Need FIFO Order**
- Use ArrayDeque or LinkedList

❌ **Need Fast Contains/Remove**
- O(n) for contains/remove(Object)
- Use TreeSet if needed

❌ **Need Sorted Iteration**
- Iterator not in sorted order
- Use TreeSet instead

**Code Example**:
```java
// GOOD: Task scheduling by priority
PriorityQueue<Task> taskQueue = new PriorityQueue<>(
    Comparator.comparingInt(Task::getPriority)
);

taskQueue.offer(new Task("Low priority", 3));
taskQueue.offer(new Task("High priority", 1));
taskQueue.offer(new Task("Medium priority", 2));

// Process in priority order
while (!taskQueue.isEmpty()) {
    Task task = taskQueue.poll(); // Gets highest priority (1, then 2, then 3)
    processTask(task);
}

// GOOD: Find K largest elements
PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);
for (int num : numbers) {
    minHeap.offer(num);
    if (minHeap.size() > k) {
        minHeap.poll(); // Remove smallest
    }
}
// minHeap contains k largest elements

// GOOD: Merge K sorted lists
PriorityQueue<ListNode> pq = new PriorityQueue<>(
    Comparator.comparingInt(node -> node.val)
);

// Add head of each list
for (ListNode list : lists) {
    if (list != null) pq.offer(list);
}

// Merge
while (!pq.isEmpty()) {
    ListNode node = pq.poll();
    result.add(node.val);
    if (node.next != null) {
        pq.offer(node.next);
    }
}

// GOOD: Event processing by timestamp
PriorityQueue<Event> events = new PriorityQueue<>(
    Comparator.comparing(Event::getTimestamp)
);

events.offer(new Event("Event1", LocalDateTime.now().plusHours(2)));
events.offer(new Event("Event2", LocalDateTime.now().plusHours(1)));

// Process earliest event
Event next = events.poll(); // Event2 (earlier timestamp)

// BAD: Expecting FIFO order
PriorityQueue<Integer> queue = new PriorityQueue<>();
queue.offer(3);
queue.offer(1);
queue.offer(2);

for (Integer num : queue) {
    System.out.println(num); // NOT in sorted order! [1, 3, 2]
}

// GOOD: If you need sorted iteration
while (!queue.isEmpty()) {
    System.out.println(queue.poll()); // 1, 2, 3 - sorted!
}
```

**Example Reference**: examples/collections/QueueExamples.java → demonstratePriorityQueue()

---

### ArrayDeque

#### What is ArrayDeque?
**Definition**: ArrayDeque (Array Double-Ended Queue) is a resizable array implementation of the Deque interface, supporting element insertion and removal at both ends.

**Internal Structure**:
- Circular array with head and tail pointers
- Resizes when full (doubles capacity)
- No capacity restrictions

**Key Characteristics**:
- **Double-Ended**: Add/remove from both ends
- **No Null**: NullPointerException if null added
- **Faster than LinkedList**: For stack/queue operations
- **Not Thread-Safe**: Not synchronized
- **No Index Access**: Not a List (no get(index))

**Time Complexity**:
- addFirst/addLast: O(1) amortized
- removeFirst/removeLast: O(1)
- getFirst/getLast: O(1)
- add/remove in middle: Not supported
- contains: O(n)

#### Why Use ArrayDeque?

✅ **Best Stack Implementation**
- Faster than Stack (legacy, synchronized)
- Faster than LinkedList

✅ **Best Queue Implementation**
- Faster than LinkedList
- No pointer overhead

✅ **Versatile**
- Can be used as Stack (LIFO) or Queue (FIFO)
- Double-ended operations

✅ **Memory Efficient**
- No node overhead like LinkedList
- Better cache locality

#### When to Use ArrayDeque?

**Use ArrayDeque When:**

✅ **Need Stack (LIFO)**
- Push/pop operations
- Replace legacy Stack class
- Undo functionality

✅ **Need Queue (FIFO)**
- Standard queue operations
- BFS algorithms
- Task queues

✅ **Need Deque Operations**
- Add/remove from both ends
- Sliding window algorithms
- Work-stealing queues

**Don't Use ArrayDeque When:**

❌ **Need Null Elements**
- ArrayDeque doesn't allow null

❌ **Need Index Access**
- Not a List, no get(index)

❌ **Thread-Safe Required**
- Use BlockingDeque implementations

**Code Example**:
```java
// GOOD: Using as Stack (LIFO)
Deque<String> stack = new ArrayDeque<>();

// Push operations
stack.push("First");   // or addFirst()
stack.push("Second");
stack.push("Third");

// Pop operations
String top = stack.pop();  // "Third" - or removeFirst()
String peek = stack.peek(); // "Second" - or peekFirst()

// GOOD: Using as Queue (FIFO)
Deque<String> queue = new ArrayDeque<>();

// Enqueue
queue.offer("First");   // or addLast()
queue.offer("Second");
queue.offer("Third");

// Dequeue
String head = queue.poll();  // "First" - or removeFirst()
String peek = queue.peek();  // "Second" - or peekFirst()

// GOOD: Deque operations (both ends)
Deque<Integer> deque = new ArrayDeque<>();

deque.addFirst(1);  // [1]
deque.addLast(2);   // [1, 2]
deque.addFirst(0);  // [0, 1, 2]
deque.addLast(3);   // [0, 1, 2, 3]

int first = deque.removeFirst(); // 0 - [1, 2, 3]
int last = deque.removeLast();   // 3 - [1, 2]

// GOOD: BFS (Breadth-First Search)
Queue<TreeNode> queue = new ArrayDeque<>();
queue.offer(root);

while (!queue.isEmpty()) {
    TreeNode node = queue.poll();
    visit(node);

    if (node.left != null) queue.offer(node.left);
    if (node.right != null) queue.offer(node.right);
}

// GOOD: Sliding window maximum
Deque<Integer> window = new ArrayDeque<>();
// Store indices of useful elements
for (int i = 0; i < nums.length; i++) {
    // Remove elements outside window
    while (!window.isEmpty() && window.peek() < i - k + 1) {
        window.poll();
    }

    // Remove smaller elements (not useful)
    while (!window.isEmpty() && nums[window.peekLast()] < nums[i]) {
        window.pollLast();
    }

    window.offer(i);

    if (i >= k - 1) {
        result.add(nums[window.peek()]);
    }
}

// BETTER than LinkedList:
// ArrayDeque: Faster, less memory
Deque<String> arrayDeque = new ArrayDeque<>();    // ✅ Preferred

// LinkedList: Slower, more memory overhead
Deque<String> linkedList = new LinkedList<>();     // ❌ Slower

// BAD: Legacy Stack class
Stack<String> stack = new Stack<>();  // ❌ Don't use - synchronized overhead
// GOOD: Use ArrayDeque instead
Deque<String> modernStack = new ArrayDeque<>(); // ✅ Much better!
```

**Performance Comparison (1M operations)**:
```
Stack Operations:
- ArrayDeque:  15 ms
- LinkedList:  45 ms (3x slower)
- Stack:       60 ms (4x slower, synchronized)

Queue Operations:
- ArrayDeque:  12 ms
- LinkedList:  40 ms (3.3x slower)
```

**Example Reference**: examples/collections/QueueExamples.java → demonstrateArrayDeque(), compareDequePerformance()

---

### BlockingQueue Implementations

#### What are BlockingQueues?
**Definition**: BlockingQueue is a thread-safe Queue interface that supports operations that wait for the queue to become non-empty when retrieving an element, and wait for space to become available when storing an element.

**Common Implementations**:
- **ArrayBlockingQueue**: Bounded, backed by array, optional fairness
- **LinkedBlockingQueue**: Optionally bounded, backed by linked nodes
- **PriorityBlockingQueue**: Unbounded, priority-ordered
- **DelayQueue**: Unbounded, elements available after delay expires
- **SynchronousQueue**: No capacity, direct handoff between threads

**Key Characteristics**:
- **Thread-Safe**: All operations are thread-safe
- **Blocking Operations**: Threads wait when queue full/empty
- **Producer-Consumer**: Perfect for producer-consumer patterns
- **No Null**: Not allowed

#### Why Use BlockingQueue?

✅ **Producer-Consumer Pattern**
- Built-in thread coordination
- No manual wait/notify needed

✅ **Thread-Safe Queue**
- Concurrent access without external synchronization
- Atomic operations

✅ **Automatic Blocking**
- Producers block when queue full
- Consumers block when queue empty

#### When to Use BlockingQueue?

**Use BlockingQueue When:**

✅ **Producer-Consumer Scenarios**
- Work queue for thread pools
- Task scheduling
- Data pipeline processing

✅ **Thread Coordination Needed**
- One thread produces, another consumes
- Rate limiting between components

✅ **Bounded Buffering**
- Limit memory usage with bounded queue
- Back-pressure mechanism

**BlockingQueue Operation Types**:

| Operation | Throws Exception | Returns Special Value | Blocks | Times Out |
|-----------|------------------|----------------------|--------|-----------|
| **Insert** | add(e) | offer(e) | put(e) | offer(e, time, unit) |
| **Remove** | remove() | poll() | take() | poll(time, unit) |
| **Examine** | element() | peek() | N/A | N/A |

**Code Example**:
```java
// === ArrayBlockingQueue ===
// GOOD: Bounded queue with fairness
BlockingQueue<Task> taskQueue = new ArrayBlockingQueue<>(100, true);

// Producer thread
new Thread(() -> {
    try {
        while (running) {
            Task task = createTask();
            taskQueue.put(task); // Blocks if queue full
        }
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}).start();

// Consumer thread
new Thread(() -> {
    try {
        while (running) {
            Task task = taskQueue.take(); // Blocks if queue empty
            processTask(task);
        }
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}).start();

// === LinkedBlockingQueue ===
// GOOD: Optionally bounded, higher throughput
BlockingQueue<String> queue = new LinkedBlockingQueue<>(1000);

// Can also be unbounded (risky!)
BlockingQueue<String> unbounded = new LinkedBlockingQueue<>();

// === PriorityBlockingQueue ===
// GOOD: Priority-based task processing
BlockingQueue<Task> priorityQueue = new PriorityBlockingQueue<>(
    100,
    Comparator.comparingInt(Task::getPriority)
);

priorityQueue.put(new Task("Low", 3));
priorityQueue.put(new Task("High", 1));
Task highPriority = priorityQueue.take(); // Gets priority 1 first

// === DelayQueue ===
// GOOD: Scheduled task execution
BlockingQueue<Delayed> delayQueue = new DelayQueue<>();

class DelayedTask implements Delayed {
    private final long executeAt;
    private final Runnable task;

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(executeAt - System.currentTimeMillis(),
                           TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.executeAt, ((DelayedTask) o).executeAt);
    }
}

delayQueue.put(new DelayedTask(task, System.currentTimeMillis() + 5000));
DelayedTask task = delayQueue.take(); // Blocks until delay expires

// === SynchronousQueue ===
// GOOD: Direct handoff, no buffering
BlockingQueue<Work> handoff = new SynchronousQueue<>();

// Producer
new Thread(() -> {
    Work work = createWork();
    handoff.put(work); // Blocks until consumer takes it
}).start();

// Consumer
new Thread(() -> {
    Work work = handoff.take(); // Blocks until producer puts
    processWork(work);
}).start();

// GOOD: Thread pool work queue
ExecutorService executor = new ThreadPoolExecutor(
    5, 10, 60L, TimeUnit.SECONDS,
    new ArrayBlockingQueue<>(100) // Bounded queue
);

// GOOD: Producer-consumer with timeout
BlockingQueue<Message> messageQueue = new ArrayBlockingQueue<>(50);

// Producer with timeout
boolean added = messageQueue.offer(message, 5, TimeUnit.SECONDS);
if (!added) {
    handleQueueFull();
}

// Consumer with timeout
Message msg = messageQueue.poll(5, TimeUnit.SECONDS);
if (msg == null) {
    handleTimeout();
}
```

**Use Case Comparison**:

| Implementation | Bounded | Ordering | Use Case |
|----------------|---------|----------|----------|
| **ArrayBlockingQueue** | Yes | FIFO | Fixed-size buffer, fairness option |
| **LinkedBlockingQueue** | Optional | FIFO | Higher throughput, optional bounds |
| **PriorityBlockingQueue** | No | Priority | Priority-based processing |
| **DelayQueue** | No | Delay | Scheduled tasks, TTL |
| **SynchronousQueue** | No (0) | N/A | Direct handoff, no buffering |

**Example Reference**: examples/collections/QueueExamples.java → demonstrateBlockingQueue(), demonstrateDelayQueue()

---

### Queue/Deque Decision Matrix

| Use Case | Use This | Why |
|----------|----------|-----|
| Stack (LIFO) | ArrayDeque | Fastest, no synchronization |
| Queue (FIFO) | ArrayDeque | Fastest, no synchronization |
| Priority queue | PriorityQueue | Heap-based ordering |
| Thread-safe queue | LinkedBlockingQueue | Good throughput |
| Bounded buffer | ArrayBlockingQueue | Fixed size, fairness |
| Direct handoff | SynchronousQueue | No buffering |
| Delayed tasks | DelayQueue | Built-in delay mechanism |
| Both ends access | ArrayDeque | O(1) both ends |

---

## 6. Immutable Collections

### What are Immutable Collections?
**Definition**: Immutable collections (Java 9+) are collections that cannot be modified after creation - no elements can be added, removed, or changed. They are truly immutable, not just unmodifiable views.

**Types of Immutable Collections**:
- **List.of()**: Immutable List
- **Set.of()**: Immutable Set
- **Map.of() / Map.ofEntries()**: Immutable Map

**Key Characteristics**:
- **Truly Immutable**: Not just unmodifiable view - independent copy
- **No Null**: NullPointerException if any element/key/value is null
- **Space-Efficient**: Optimized implementations for small sizes
- **Thread-Safe**: No synchronization needed
- **Fail-Fast**: UnsupportedOperationException on any modification attempt
- **Value-Based**: Can be cached and reused by JVM

**Factory Methods**:
```java
// Lists
List<String> list = List.of("A", "B", "C");
List<String> empty = List.of();
List<String> single = List.of("Single");

// Sets
Set<Integer> set = Set.of(1, 2, 3, 4, 5);
Set<String> setFrom = Set.copyOf(existingCollection);

// Maps (up to 10 pairs)
Map<String, Integer> map = Map.of(
    "A", 1,
    "B", 2,
    "C", 3
);

// Maps (unlimited pairs)
Map<String, Integer> largeMap = Map.ofEntries(
    Map.entry("A", 1),
    Map.entry("B", 2),
    Map.entry("C", 3),
    // ... unlimited
);
```

### Why Use Immutable Collections?

✅ **Thread-Safety Without Synchronization**
- Safe to share across threads
- No race conditions
- No need for locks

✅ **Defensive Copies Not Needed**
- Can return directly from methods
- No risk of external modification

✅ **Clearer Intent**
- Signals collection won't change
- Easier to reason about code

✅ **Performance Benefits**
- No defensive copying overhead
- JVM can optimize (caching, inlining)
- Space-efficient for small sizes

✅ **Safer APIs**
- Prevent accidental modifications
- Better encapsulation

### When to Use Immutable Collections?

**Use Immutable Collections When:**

✅ **Constant Data**
- Fixed set of values
- Configuration constants
- Lookup tables

✅ **Returning Collections from Methods**
- Prevent external modification
- No need for defensive copies

✅ **Multi-Threaded Access**
- Share safely across threads
- No synchronization needed

✅ **Map Keys or Set Elements**
- Ensure hashCode consistency
- Prevent accidental changes

✅ **Value Objects**
- DTOs, domain objects with collection fields
- Ensure immutability

**Don't Use Immutable Collections When:**

❌ **Need to Modify**
- Building collections incrementally
- Dynamic data that changes

❌ **Null Values Required**
- Immutable collections don't allow null
- Use regular collections

❌ **Large Collections Built Incrementally**
- Use mutable collection, then copy
- More efficient than multiple of() calls

**Code Example**:
```java
// GOOD: Constant data
private static final List<String> VALID_STATUSES =
    List.of("PENDING", "APPROVED", "REJECTED");

private static final Map<String, String> ERROR_CODES = Map.of(
    "E001", "Invalid input",
    "E002", "Not found",
    "E003", "Unauthorized"
);

// GOOD: Return immutable collection (no defensive copy needed)
public List<String> getPermissions() {
    return List.of("READ", "WRITE", "DELETE"); // Safe to return
}

// OLD WAY: Defensive copy needed
public List<String> getPermissions() {
    return new ArrayList<>(permissions); // Defensive copy
}

// GOOD: Multi-threaded access
List<User> users = List.of(user1, user2, user3);
// Safe to share across threads without synchronization

// GOOD: Configuration
record DatabaseConfig(
    String url,
    List<String> allowedHosts,
    Map<String, String> properties
) {
    // Make fields immutable
    public DatabaseConfig {
        allowedHosts = List.copyOf(allowedHosts);
        properties = Map.copyOf(properties);
    }
}

// GOOD: Build incrementally, then make immutable
List<String> tempList = new ArrayList<>();
for (String item : source) {
    if (isValid(item)) {
        tempList.add(item);
    }
}
List<String> finalList = List.copyOf(tempList); // Now immutable

// BAD: Multiple modifications not possible
List<String> list = List.of("A", "B", "C");
list.add("D"); // UnsupportedOperationException!
list.remove(0); // UnsupportedOperationException!
list.set(0, "X"); // UnsupportedOperationException!

// BAD: Null not allowed
List<String> withNull = List.of("A", null, "B"); // NullPointerException!

// GOOD: Set.of() rejects duplicates at creation
Set<Integer> set = Set.of(1, 2, 3); // OK
Set<Integer> dupes = Set.of(1, 2, 1); // IllegalArgumentException!

// GOOD: Performance - small size optimizations
List<String> one = List.of("A");        // Optimized for size 1
List<String> two = List.of("A", "B");   // Optimized for size 2
// JDK has specialized implementations for sizes 0-10

// GOOD: copyOf() is smart
List<String> original = List.of("A", "B", "C");
List<String> copy = List.copyOf(original);
System.out.println(original == copy); // true - same instance!
// If already immutable, returns same instance
```

### copyOf() Methods

**Purpose**: Create immutable copies of existing collections.

**Smart Behavior**:
- If source is already immutable (of same type), returns same instance
- Otherwise, creates new immutable copy

```java
// List.copyOf()
List<String> mutableList = new ArrayList<>(Arrays.asList("A", "B", "C"));
List<String> immutableList = List.copyOf(mutableList);
mutableList.add("D"); // OK - mutableList changed
// immutableList unchanged - independent copy

// Set.copyOf()
Set<Integer> mutableSet = new HashSet<>(Arrays.asList(1, 2, 3));
Set<Integer> immutableSet = Set.copyOf(mutableSet);

// Map.copyOf()
Map<String, Integer> mutableMap = new HashMap<>();
mutableMap.put("A", 1);
Map<String, Integer> immutableMap = Map.copyOf(mutableMap);

// Smart optimization
List<String> alreadyImmutable = List.of("A", "B", "C");
List<String> copy = List.copyOf(alreadyImmutable);
System.out.println(alreadyImmutable == copy); // true - same instance!
```

**Example Reference**: examples/collections/ImmutableCollections.java → demonstrateCopyOf()

---

### Unmodifiable vs Immutable

**Critical Difference**:

| Aspect | Collections.unmodifiableXxx() | List/Set/Map.of() |
|--------|------------------------------|-------------------|
| **Type** | Unmodifiable view | Truly immutable |
| **Independence** | Reflects source changes | Independent copy |
| **Null** | Allowed (if source allows) | Not allowed |
| **Thread-Safe** | Only if source not modified | Always |
| **Performance** | Wrapper overhead | Optimized |
| **JDK Version** | Java 1.2+ | Java 9+ |

**Code Example**:
```java
// === UNMODIFIABLE (old way) ===
List<String> mutableList = new ArrayList<>(Arrays.asList("A", "B", "C"));
List<String> unmodifiableView = Collections.unmodifiableList(mutableList);

// Cannot modify through view
unmodifiableView.add("D"); // UnsupportedOperationException

// BUT: changes to source reflect in view!
mutableList.add("D");
System.out.println(unmodifiableView); // [A, B, C, D] - CHANGED!

// === IMMUTABLE (new way) ===
List<String> mutableList2 = new ArrayList<>(Arrays.asList("X", "Y", "Z"));
List<String> immutableCopy = List.copyOf(mutableList2);

// Cannot modify
immutableCopy.add("W"); // UnsupportedOperationException

// Changes to source DON'T affect copy
mutableList2.add("W");
System.out.println(immutableCopy); // [X, Y, Z] - UNCHANGED!

// === Use Cases ===

// GOOD: Unmodifiable - when you want view semantics
class DataHolder {
    private List<String> data = new ArrayList<>();

    public List<String> getData() {
        return Collections.unmodifiableList(data); // View
    }

    public void updateData(String item) {
        data.add(item); // Changes reflected in view
    }
}

// GOOD: Immutable - when you want snapshot
class Snapshot {
    private final List<String> data;

    public Snapshot(List<String> source) {
        this.data = List.copyOf(source); // Independent copy
    }

    public List<String> getData() {
        return data; // Safe - won't change
    }
}

// COMPARISON
List<String> original = new ArrayList<>(Arrays.asList("A", "B"));

// Unmodifiable view
List<String> view = Collections.unmodifiableList(original);
original.add("C");
System.out.println(view); // [A, B, C] - reflects change

// Immutable copy
List<String> copy = List.copyOf(original);
original.add("D");
System.out.println(copy); // [A, B, C] - doesn't reflect new change
```

**Example Reference**: examples/collections/ImmutableCollections.java → compareUnmodifiableVsImmutable()

---

### Best Practices for Immutable Collections

1. **Prefer of() for Constants**
   ```java
   private static final List<String> DAYS =
       List.of("Mon", "Tue", "Wed", "Thu", "Fri");
   ```

2. **Use copyOf() for Defensive Copies**
   ```java
   public record Order(List<Item> items) {
       public Order {
           items = List.copyOf(items); // Defensive immutable copy
       }
   }
   ```

3. **Build Mutable, Then Convert**
   ```java
   List<String> builder = new ArrayList<>();
   for (...) { builder.add(...); }
   List<String> immutable = List.copyOf(builder);
   ```

4. **Return Immutable from APIs**
   ```java
   public List<Permission> getPermissions() {
       return List.copyOf(permissions); // Safe
   }
   ```

5. **No Null Values**
   ```java
   // Filter nulls before creating immutable
   List<String> withNulls = Arrays.asList("A", null, "B");
   List<String> immutable = withNulls.stream()
       .filter(Objects::nonNull)
       .collect(Collectors.toUnmodifiableList());
   ```

**Example Reference**: examples/collections/ImmutableCollections.java → demonstrateListOf(), demonstrateSetOf(), demonstrateMapOf()

---

## 7. Comparable & Comparator

### Comparable
- Single natural ordering
- Implemented by class itself
- compareTo(T other): returns negative, zero, positive
- Contract: sgn(x.compareTo(y)) == -sgn(y.compareTo(x))
- Transitivity: (x.compareTo(y) > 0 && y.compareTo(z) > 0) implies x.compareTo(z) > 0

**Example Reference**: examples/collections/ComparatorExamples.java → demonstrateComparable()

### Comparator
- External, multiple orderings
- Functional interface (single abstract method: compare)
- Factory methods: comparing(), comparingInt/Long/Double()
- Chaining: thenComparing(), thenComparingInt/Long/Double()
- Utilities: reversed(), nullsFirst(), nullsLast(), naturalOrder(), reverseOrder()

**Example Reference**: examples/collections/ComparatorExamples.java → demonstrateComparator(), demonstrateComparatorChaining()

### Performance Tip
Use primitive specializations (comparingInt, comparingLong, comparingDouble) to avoid boxing overhead.

**Example Reference**: examples/collections/ComparatorExamples.java → demonstrateComparingMethods()

---

## 8. Stream API Fundamentals

### What Are Streams?

**Definition**: A Stream is a sequence of elements supporting sequential and parallel aggregate operations. It's **not a data structure** but a pipeline to process data from a source (collection, array, I/O channel, etc.).

**Key Characteristics**:
- **No storage**: Streams don't store elements, they convey elements from a source
- **Functional in nature**: Operations produce a result without modifying the source
- **Lazy evaluation**: Intermediate operations are not executed until a terminal operation is invoked
- **Possibly unbounded**: Streams can be infinite (unlike collections)
- **Consumable**: Can be traversed only once, like an Iterator

#### Important: Streams Have NO Buffer Storage

**Myth vs Reality:**

❌ **MYTH**: "Streams buffer/store elements for processing"
✅ **REALITY**: Streams are just descriptors of operations - they store NO elements

**What a Stream Actually Contains:**
```java
Stream<String> stream = list.stream()
    .filter(s -> s.length() > 3)
    .map(String::toUpperCase);

// Stream object contains:
// 1. Reference to source (list)
// 2. Pipeline of operations: [filter, map]
// 3. Flags: (sequential/parallel, ordered/unordered)
//
// Stream does NOT contain:
// - Element copies
// - Intermediate results
// - Any actual data
```

**Visual Representation:**
```
Source → Stream Pipeline → Terminal Operation → Result
[List]   [filter → map]    [collect]            [List]
  ↓                                                ↓
Actual                                          Actual
Data                                            Data

Stream = Just the arrow/pipeline (no storage!)
```

**Where Buffering DOES Happen:**

1. **Stateful Operations** (require internal buffering):
   ```java
   stream.sorted()      // Must buffer ALL elements to sort
   stream.distinct()    // Buffers seen elements (Set)
   stream.limit(n)      // May buffer up to n elements
   stream.skip(n)       // May buffer to skip
   ```

2. **Collectors** (create result containers):
   ```java
   stream.collect(Collectors.toList())  // Builds ArrayList
   stream.collect(Collectors.toSet())   // Builds HashSet
   stream.collect(Collectors.groupingBy(...))  // Builds Map
   ```

3. **Parallel Streams** (temporary buffers for fork/join):
   ```java
   parallelStream()
       .map(...)        // Parallel threads may use thread-local buffers
       .collect(...)    // Merges results from multiple threads
   ```

**Proof - Streams Don't Store Data:**
```java
List<String> source = new ArrayList<>(Arrays.asList("A", "B", "C"));
Stream<String> stream = source.stream();

// Modify source BEFORE terminal operation
source.add("D");
source.remove("A");

// Stream sees the CURRENT state of source
List<String> result = stream.collect(Collectors.toList());
System.out.println(result); // [B, C, D] - reflects changes!

// This proves stream had no copy - it reads from source
```

**Memory Implications:**
```java
// NO extra memory for stream itself
List<Integer> million = createMillionIntegers();
Stream<Integer> stream = million.stream()
    .filter(n -> n % 2 == 0)     // No buffering
    .map(n -> n * 2);             // No buffering

// Memory used: Just the original list + tiny stream object (~100 bytes)

// Buffering happens ONLY at terminal operation (if needed)
List<Integer> result = stream.collect(Collectors.toList());
// Now we have 2 lists in memory: original + result
```

**Stateless vs Stateful Operations:**

| Stateless (No Buffer) | Stateful (Needs Buffer) |
|----------------------|-------------------------|
| filter() | sorted() - buffers all |
| map() | distinct() - buffers seen |
| flatMap() | limit() - may buffer |
| peek() | skip() - may buffer |
| | groupingBy() - builds Map |

**Example - Demonstrating No Buffering:**
```java
List<String> list = Arrays.asList("A", "B", "C", "D", "E");

// Stateless pipeline - NO buffering at all
long count = list.stream()
    .filter(s -> {
        System.out.println("Filtering: " + s);
        return s.compareTo("C") < 0;
    })
    .map(s -> {
        System.out.println("Mapping: " + s);
        return s.toLowerCase();
    })
    .count();

// Output:
// Filtering: A
// Mapping: A    ← processed immediately, not buffered
// Filtering: B
// Mapping: B    ← processed immediately
// Filtering: C
// Filtering: D
// Filtering: E
// Result: 2

// Each element flows through entire pipeline before next element starts
// NO intermediate collection created

// vs Stateful operation - MUST buffer
List<String> sorted = list.stream()
    .filter(s -> s.compareTo("C") < 0)
    .sorted()  // ← ALL filtered elements buffered here
    .collect(Collectors.toList());

// sorted() must see all elements before producing any output
```

**Parallel Stream Buffering:**
```java
// Parallel streams use thread-local buffers during processing
List<Integer> huge = createHugeList();

List<Integer> result = huge.parallelStream()
    .map(expensiveOperation)     // Each thread may have local buffer
    .collect(Collectors.toList()); // Merges thread results

// Buffers created:
// 1. Thread-local buffers during processing (temporary)
// 2. Final result collection
//
// Original stream still has NO buffer
```

**Key Takeaway:**
```java
// Stream = Pipeline descriptor (no data)
Stream<T> stream = source.stream().filter(...).map(...);
Memory: ~100 bytes (just metadata)

// Execution = Data flows through pipeline
stream.forEach(System.out::println);
Memory: No extra allocation (unless operation is stateful)

// Result = New collection (if collecting)
List<T> result = stream.collect(Collectors.toList());
Memory: Size of result collection
```

**Code Example**:
```java
// Traditional approach (imperative)
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
List<String> result = new ArrayList<>();
for (String name : names) {
    if (name.length() > 3) {
        result.add(name.toUpperCase());
    }
}
Collections.sort(result);
System.out.println(result); // [ALICE, CHARLIE, DAVID]

// Stream approach (declarative)
List<String> result = names.stream()
    .filter(name -> name.length() > 3)
    .map(String::toUpperCase)
    .sorted()
    .collect(Collectors.toList());
System.out.println(result); // [ALICE, CHARLIE, DAVID]
```

**Example Reference**: examples/streams/StreamBasics.java → demonstrateWhatAreStreams()

### Why Use Streams?

**1. Declarative Code**
- Focus on **what** to do, not **how** to do it
- More readable and maintainable
- Less boilerplate code

**2. Functional Programming**
- Immutability encouraged
- No side effects
- Function composition

**3. Easy Parallelization**
- Convert sequential to parallel with single method call
- Leverages multi-core processors automatically
- No manual thread management

**4. Lazy Evaluation**
- Operations executed only when needed
- Short-circuit optimization (stop processing when result known)
- Better performance for large datasets

**5. Pipeline Processing**
- Chain multiple operations fluently
- Intermediate operations combined into single pass
- Optimization opportunities

**Comparison: Imperative vs Declarative**

| Aspect | Imperative (Loops) | Declarative (Streams) |
|--------|-------------------|----------------------|
| **Focus** | How to do | What to do |
| **Readability** | More code, nested loops | Concise, linear flow |
| **Parallelization** | Manual thread management | `.parallelStream()` |
| **Side Effects** | Common (mutable state) | Discouraged |
| **Optimization** | Manual | Automatic (lazy, short-circuit) |
| **Debugging** | Easier to debug | Stack traces harder |

**Code Example - Benefits Demonstration**:
```java
List<Transaction> transactions = getTransactions();

// OLD WAY: Find top 3 expensive transactions in "Electronics" category
List<Transaction> result = new ArrayList<>();
for (Transaction t : transactions) {
    if ("Electronics".equals(t.getCategory())) {
        result.add(t);
    }
}
Collections.sort(result, new Comparator<Transaction>() {
    @Override
    public int compare(Transaction t1, Transaction t2) {
        return Double.compare(t2.getAmount(), t1.getAmount());
    }
});
List<Transaction> top3 = new ArrayList<>();
for (int i = 0; i < Math.min(3, result.size()); i++) {
    top3.add(result.get(i));
}

// NEW WAY: Same logic with streams
List<Transaction> top3 = transactions.stream()
    .filter(t -> "Electronics".equals(t.getCategory()))
    .sorted(Comparator.comparingDouble(Transaction::getAmount).reversed())
    .limit(3)
    .collect(Collectors.toList());

// PARALLEL: Process large dataset efficiently
List<Transaction> top3Parallel = transactions.parallelStream()
    .filter(t -> "Electronics".equals(t.getCategory()))
    .sorted(Comparator.comparingDouble(Transaction::getAmount).reversed())
    .limit(3)
    .collect(Collectors.toList());
```

**Example Reference**: examples/streams/StreamBasics.java → demonstrateWhyUseStreams()

### When to Use Streams?

**Use Streams When:**

✅ **Processing Collections**
- Filtering, mapping, reducing operations
- Multiple transformations needed
- Aggregating data (sum, average, grouping)

✅ **Declarative Operations**
- Logic is clearer as sequence of operations
- Readability is priority
- Avoiding temporary variables

✅ **Parallel Processing Candidates**
- Large datasets (>10,000 elements)
- CPU-intensive operations
- Independent operations (no shared state)

✅ **Pipeline Operations**
- Multiple chained transformations
- Composing operations fluently
- Short-circuit opportunities (findFirst, anyMatch)

**Don't Use Streams When:**

❌ **Simple Iterations**
- Single loop without transformations
- Direct element access by index needed
- Performance-critical hot paths (benchmark first)

❌ **Need to Modify Source**
- Streams don't modify the source
- Use traditional loops or Collections methods

❌ **Complex Control Flow**
- Multiple return points
- Exception handling within loop
- Checked exceptions (requires workarounds)

❌ **Debugging is Critical**
- Streams have complex stack traces
- Imperative loops easier to step through

❌ **Small Collections**
- Overhead > benefit for < 100 elements
- Parallel streams especially wasteful

**Decision Matrix**:

| Scenario | Use Stream? | Reason |
|----------|-------------|--------|
| Filter 10,000 products by price | ✅ Yes | Large dataset, simple filter |
| Loop 10 items and print | ❌ No | Simple iteration, small size |
| Group 1M orders by customer | ✅ Yes | Complex aggregation, parallelizable |
| Modify ArrayList in-place | ❌ No | Streams don't modify source |
| Find first matching element | ✅ Yes | Short-circuit optimization |
| Iterate with checked exceptions | ❌ No | Stream API doesn't support checked exceptions well |
| Complex multi-step data transformation | ✅ Yes | Pipeline clarity |
| Index-based array access | ❌ No | Streams not index-aware |

**Code Example - When vs When Not**:
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// BAD: Overkill for simple iteration
numbers.stream().forEach(System.out::println);
// GOOD: Use traditional loop
for (int num : numbers) {
    System.out.println(num);
}

// GOOD: Complex transformation
Map<String, Double> avgPriceByCategory = products.stream()
    .collect(Collectors.groupingBy(
        Product::getCategory,
        Collectors.averagingDouble(Product::getPrice)
    ));

// BAD: Trying to modify source (won't work)
numbers.stream().forEach(n -> n = n * 2); // Doesn't modify list!
// GOOD: Create new list or use traditional loop
List<Integer> doubled = numbers.stream()
    .map(n -> n * 2)
    .collect(Collectors.toList());

// GOOD: Short-circuit for early termination
Optional<String> found = names.stream()
    .filter(name -> name.startsWith("A"))
    .findFirst();

// BAD: Parallel for tiny dataset
List<Integer> small = Arrays.asList(1, 2, 3);
small.parallelStream().forEach(System.out::println); // Overhead > benefit

// GOOD: Parallel for large CPU-intensive work
List<ComplexObject> results = hugeDataset.parallelStream()
    .map(this::expensiveTransformation)
    .collect(Collectors.toList());
```

**Example Reference**: examples/streams/StreamBasics.java → demonstrateWhenToUseStreams()

### Stream Creation
- collection.stream(): From collection
- Arrays.stream(array): From array
- Stream.of(values...): From values
- Stream.generate(Supplier): Infinite stream
- Stream.iterate(seed, UnaryOperator): Infinite stream
- IntStream.range(start, end): Primitive stream
- Stream.builder(): Builder pattern

**Code Example**:
```java
// From collection
List<String> list = Arrays.asList("A", "B", "C");
Stream<String> stream1 = list.stream();

// From array
String[] array = {"X", "Y", "Z"};
Stream<String> stream2 = Arrays.stream(array);

// From values
Stream<Integer> stream3 = Stream.of(1, 2, 3, 4, 5);

// Infinite stream with limit
Stream<Double> randoms = Stream.generate(Math::random).limit(10);

// Iterate
Stream<Integer> evens = Stream.iterate(0, n -> n + 2).limit(5);
// Result: 0, 2, 4, 6, 8

// Primitive streams (no boxing overhead)
IntStream range = IntStream.range(1, 6); // 1, 2, 3, 4, 5
int sum = range.sum(); // 15

// Builder pattern
Stream<String> built = Stream.<String>builder()
    .add("One")
    .add("Two")
    .add("Three")
    .build();
```

**Example Reference**: examples/streams/StreamBasics.java → demonstrateStreamCreation()

### Intermediate Operations (return Stream, lazy)
- filter(Predicate): Filter elements
- map(Function): Transform elements
- flatMap(Function): Flatten nested structures
- distinct(): Remove duplicates (uses equals/hashCode)
- sorted() / sorted(Comparator): Sort
- peek(Consumer): Side-effect for debugging
- limit(n): First n elements
- skip(n): Skip first n elements

**Code Example**:
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// filter - keep only even numbers
List<Integer> evens = numbers.stream()
    .filter(n -> n % 2 == 0)
    .collect(Collectors.toList()); // [2, 4, 6, 8, 10]

// map - transform each element
List<Integer> squared = numbers.stream()
    .map(n -> n * n)
    .collect(Collectors.toList()); // [1, 4, 9, 16, ...]

// flatMap - flatten nested lists
List<List<Integer>> nested = Arrays.asList(
    Arrays.asList(1, 2),
    Arrays.asList(3, 4),
    Arrays.asList(5, 6)
);
List<Integer> flattened = nested.stream()
    .flatMap(Collection::stream)
    .collect(Collectors.toList()); // [1, 2, 3, 4, 5, 6]

// distinct - remove duplicates
List<Integer> unique = Arrays.asList(1, 2, 2, 3, 3, 4).stream()
    .distinct()
    .collect(Collectors.toList()); // [1, 2, 3, 4]

// sorted - natural order
List<Integer> sorted = Arrays.asList(5, 2, 8, 1).stream()
    .sorted()
    .collect(Collectors.toList()); // [1, 2, 5, 8]

// limit and skip
List<Integer> middle = numbers.stream()
    .skip(3)
    .limit(4)
    .collect(Collectors.toList()); // [4, 5, 6, 7]

// Chaining operations
List<Integer> result = numbers.stream()
    .filter(n -> n > 3)
    .map(n -> n * 2)
    .sorted()
    .limit(3)
    .collect(Collectors.toList()); // [8, 10, 12]
```

**Example Reference**: examples/streams/StreamBasics.java → demonstrateIntermediateOperations()

### Terminal Operations (produce result, trigger execution)
- forEach(Consumer): Side-effect for each element
- forEachOrdered(Consumer): Ordered forEach
- collect(Collector): Mutable reduction
- reduce(BinaryOperator): Immutable reduction
- count(): Count elements
- min/max(Comparator): Find min/max
- anyMatch/allMatch/noneMatch(Predicate): Boolean tests
- findFirst/findAny(): Find element

**Example Reference**: examples/streams/StreamBasics.java → demonstrateTerminalOperations()

### Lazy Evaluation
Intermediate operations not executed until terminal operation called.

**Example Reference**: examples/streams/StreamBasics.java → demonstrateLazyEvaluation()

### Stateful vs Stateless
- **Stateless**: filter, map, flatMap (process element independently)
- **Stateful**: distinct, sorted, limit, skip (need state across elements)

**Example Reference**: examples/streams/StreamBasics.java → demonstrateStatefulOperations()

### Short-Circuiting
Operations that can complete without processing entire stream:
- Intermediate: limit(), takeWhile(), dropWhile()
- Terminal: findFirst(), findAny(), anyMatch(), allMatch(), noneMatch()

**Example Reference**: examples/streams/StreamBasics.java → demonstrateShortCircuiting()

---

## 9. Advanced Stream Operations

### Collectors
**Common Collectors**:
- toList(), toSet(), toCollection()
- toMap(keyMapper, valueMapper)
- joining(delimiter), joining(delimiter, prefix, suffix)
- counting(), summingInt/Long/Double(), averagingInt/Long/Double()
- summarizingInt/Long/Double(): Returns statistics object

**Example Reference**: examples/streams/AdvancedStreams.java → demonstrateCollectors()

### groupingBy
Group elements by classifier function.

**Code Example**:
```java
class Employee {
    String name;
    String department;
    double salary;
    // constructor, getters...
}

List<Employee> employees = Arrays.asList(
    new Employee("Alice", "Engineering", 75000),
    new Employee("Bob", "Engineering", 80000),
    new Employee("Charlie", "Sales", 60000),
    new Employee("David", "Sales", 65000)
);

// Group by department
Map<String, List<Employee>> byDept = employees.stream()
    .collect(Collectors.groupingBy(Employee::getDepartment));
// {Engineering=[Alice, Bob], Sales=[Charlie, David]}

// Count by department
Map<String, Long> countByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.counting()
    ));
// {Engineering=2, Sales=2}

// Average salary by department
Map<String, Double> avgSalaryByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.averagingDouble(Employee::getSalary)
    ));
// {Engineering=77500.0, Sales=62500.0}

// Names by department
Map<String, List<String>> namesByDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::getDepartment,
        Collectors.mapping(Employee::getName, Collectors.toList())
    ));
// {Engineering=[Alice, Bob], Sales=[Charlie, David]}
```

**Example Reference**: examples/streams/AdvancedStreams.java → demonstrateGroupingBy()

### partitioningBy
Binary classification (Boolean keys).

**Example Reference**: examples/streams/AdvancedStreams.java → demonstratePartitioningBy()

### Downstream Collectors
Collectors applied after grouping/partitioning:
- mapping(Function, Collector): Transform then collect
- filtering(Predicate, Collector): Filter then collect (Java 9+)
- flatMapping(Function, Collector): FlatMap then collect (Java 9+)
- collectingAndThen(Collector, Function): Post-process result

**Example Reference**: examples/streams/AdvancedStreams.java → demonstrateDownstreamCollectors()

### teeing (Java 12+)
Apply two collectors and merge results.

**Example Reference**: examples/streams/AdvancedStreams.java → demonstrateTeeing()

### reduce
Three forms:
1. Optional<T> reduce(BinaryOperator<T> accumulator)
2. T reduce(T identity, BinaryOperator<T> accumulator)
3. U reduce(U identity, BiFunction<U, T, U> accumulator, BinaryOperator<U> combiner)

Form 3 is parallel-friendly (combiner merges partial results).

**Code Example**:
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// Form 1: No identity, returns Optional
Optional<Integer> sum1 = numbers.stream()
    .reduce((a, b) -> a + b);
System.out.println(sum1.get()); // 15

// Form 2: With identity
int sum2 = numbers.stream()
    .reduce(0, (a, b) -> a + b);
System.out.println(sum2); // 15

// Using method reference
int sum3 = numbers.stream()
    .reduce(0, Integer::sum);

// Product
int product = numbers.stream()
    .reduce(1, (a, b) -> a * b); // 120

// Max value
Optional<Integer> max = numbers.stream()
    .reduce(Integer::max);
System.out.println(max.get()); // 5

// String concatenation
List<String> words = Arrays.asList("Java", "Stream", "API");
String concatenated = words.stream()
    .reduce("", (a, b) -> a + b); // "JavaStreamAPI"

// Form 3: Parallel-friendly with combiner
// Useful when accumulating into different type
int totalLength = words.parallelStream()
    .reduce(
        0,                           // identity
        (sum, word) -> sum + word.length(), // accumulator
        Integer::sum                 // combiner (for parallel)
    );
System.out.println(totalLength); // 14
```

**Example Reference**: examples/streams/AdvancedStreams.java → demonstrateReduceAdvanced()

---

## 10. Parallel Streams

### Creation
- collection.parallelStream()
- stream.parallel()

**Example Reference**: examples/streams/ParallelStreams.java → demonstrateParallelStreamCreation()

### Fork/Join Framework
- Parallel streams use ForkJoinPool.commonPool()
- Default parallelism: Runtime.getRuntime().availableProcessors() - 1
- Custom pool: Submit to custom ForkJoinPool

**Example Reference**: examples/streams/ParallelStreams.java → demonstrateForkJoinPool()

### Common Pitfalls
1. **Shared Mutable State**: Race conditions
2. **Non-thread-safe Collections**: ArrayList, HashMap
3. **Order Dependency**: forEach() doesn't guarantee order

**Code Example**:
```java
// PITFALL 1: Shared mutable state (WRONG!)
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
int[] sum = {0}; // Shared mutable state
numbers.parallelStream()
    .forEach(n -> sum[0] += n); // Race condition!
System.out.println(sum[0]); // Unpredictable result

// CORRECT: Use reduce
int correctSum = numbers.parallelStream()
    .reduce(0, Integer::sum);
System.out.println(correctSum); // 15 (always correct)

// PITFALL 2: Non-thread-safe collection (WRONG!)
List<Integer> result = new ArrayList<>(); // Not thread-safe
IntStream.range(0, 1000).parallel()
    .forEach(result::add); // Lost updates!
System.out.println(result.size()); // < 1000

// CORRECT: Use collect
List<Integer> correctResult = IntStream.range(0, 1000)
    .parallel()
    .boxed()
    .collect(Collectors.toList());
System.out.println(correctResult.size()); // 1000

// PITFALL 3: Order dependency (WRONG!)
List<Integer> ordered = Arrays.asList(1, 2, 3, 4, 5);
ordered.parallelStream()
    .forEach(System.out::println); // Order not guaranteed!

// CORRECT: Use forEachOrdered
ordered.parallelStream()
    .forEachOrdered(System.out::println); // Maintains order
```

**Example Reference**: examples/streams/ParallelStreams.java → demonstrateCommonPitfalls()

### Thread-Safe Solutions
1. Use collect() instead of forEach() for accumulation
2. Use thread-safe collections (ConcurrentHashMap, Collections.synchronizedList)
3. Use reduce() with proper identity and combiner

**Example Reference**: examples/streams/ParallelStreams.java → demonstrateThreadSafety()

### When to Use Parallel
**Good candidates**:
- Large datasets (>10,000 elements)
- CPU-intensive operations
- Independent operations (no shared state)
- Stateless operations

**Poor candidates**:
- Small datasets (overhead > benefit)
- I/O-bound operations
- Operations requiring ordering
- Shared mutable state

**Example Reference**: examples/streams/ParallelStreams.java → demonstrateWhenToUseParallel()

---

## 11. Performance Considerations

### Collection Choice Impact
| Operation | ArrayList | LinkedList | HashSet | TreeSet |
|-----------|-----------|------------|---------|---------|
| Get by index | O(1) | O(n) | N/A | N/A |
| Add (end) | O(1)* | O(1) | O(1)* | O(log n) |
| Add (beginning) | O(n) | O(1) | O(1)* | O(log n) |
| Remove (index) | O(n) | O(n) | N/A | N/A |
| Contains | O(n) | O(n) | O(1)* | O(log n) |
| Iteration | Fast | Slow | Fast | Fast |

*Amortized

### Memory Overhead
- **ArrayList**: approximately 4 bytes per element + array overhead
- **LinkedList**: approximately 40 bytes per element (node + references)
- **HashMap**: approximately 32 bytes per entry + array overhead
- **TreeMap**: approximately 40 bytes per entry (node + references)

### Stream Performance
- **Sequential**: Single thread, predictable
- **Parallel**: Multi-thread, overhead, non-deterministic order
- **Primitive Streams**: IntStream, LongStream, DoubleStream (no boxing)

### Boxing Overhead
Avoid boxing in hot paths:
- Use mapToInt(), mapToLong(), mapToDouble() instead of map()
- Use summingInt(), summingLong(), summingDouble() instead of generic collectors
- Use primitive specializations of Comparator

---

## 12. Best Practices

### Collections
1. **Program to interfaces**: List<String> list = new ArrayList<>() not ArrayList<String> list
2. **Choose right implementation**: ArrayList default, LinkedList for deque
3. **Specify initial capacity** if size known: new ArrayList<>(expectedSize)
4. **Use EnumSet/EnumMap** for enums
5. **Prefer immutable collections** (List.of, Set.of, Map.of) when possible
6. **Override hashCode/equals** for custom objects in HashSet/HashMap
7. **Make keys immutable** in HashMap/HashSet
8. **Use compute methods** instead of get-check-put pattern

### Streams
1. **Use method references** over lambdas when possible
2. **Avoid side-effects** in stream operations (except terminal forEach)
3. **Use primitive streams** to avoid boxing
4. **Prefer collect() over reduce()** for mutable reduction
5. **Don't parallelize prematurely** - measure first
6. **Use forEachOrdered()** in parallel streams if order matters
7. **Limit intermediate operations** - each adds overhead
8. **Use Optional correctly** - don't use for collections or parameters

### Concurrency
1. **Use ConcurrentHashMap** over synchronized Map
2. **Use BlockingQueue** for producer-consumer
3. **Avoid shared mutable state** in parallel streams
4. **Use atomic operations** (compute, merge) instead of get-check-put

### Code Quality
1. **Fail fast**: Validate inputs early
2. **Defensive copying**: Copy mutable inputs/outputs
3. **Document thread-safety**: Clearly state if class is thread-safe
4. **Use @FunctionalInterface** for single-method interfaces
5. **Prefer composition over inheritance**

---

## Interview Questions & Answers

### Collections

**Q: Difference between ArrayList and LinkedList?**
A: ArrayList uses dynamic array (O(1) random access, O(n) insertion at beginning), LinkedList uses doubly-linked nodes (O(n) random access, O(1) insertion at both ends). ArrayList is generally faster due to better cache locality. Use LinkedList only for deque operations.

**Q: How does HashMap work internally?**
A: HashMap uses array of buckets. Hash code determines bucket (index = (capacity-1) & hash). Collisions handled by linked list (Java 7) or red-black tree when bin size > 8 (Java 8+). Load factor 0.75 triggers resize.

**Q: Why override hashCode when overriding equals?**
A: Hash-based collections (HashMap, HashSet) require consistent hashCode. If equals() says two objects are equal, hashCode() must return same value, otherwise objects won't be found in hash table.

**Q: ConcurrentHashMap vs Hashtable?**
A: ConcurrentHashMap uses fine-grained locking (bin-level in Java 8+), lock-free reads, better performance. Hashtable uses coarse-grained locking (entire table). ConcurrentHashMap doesn't allow null keys/values, Hashtable doesn't either. ConcurrentHashMap is preferred.

**Q: When to use TreeMap over HashMap?**
A: TreeMap when you need sorted order, range operations (subMap, headMap, tailMap), or NavigableMap operations (ceilingKey, floorKey). HashMap for general purpose, better performance.

### Streams

**Q: Stream vs Collection?**
A: Collections are data structures (storage), Streams are pipelines for processing data. Collections are eager and reusable, Streams are lazy and one-time use. Streams enable declarative programming and easy parallelization.

**Q: Intermediate vs Terminal operations?**
A: Intermediate operations (filter, map, etc.) return Stream and are lazy (not executed until terminal operation). Terminal operations (collect, forEach, etc.) produce result and trigger pipeline execution.

**Q: Parallel stream pitfalls?**
A: Shared mutable state causes race conditions. Non-thread-safe collections lose data. Overhead can exceed benefits for small datasets. Order not guaranteed with forEach(). Use collect() for accumulation, avoid side-effects.

**Q: reduce vs collect?**
A: reduce for immutable reduction (creates new value each step), good for simple aggregations (sum, max). collect for mutable reduction (mutates container), better for gathering into collections, more efficient for complex aggregations.

**Q: flatMap use cases?**
A: Flattening nested structures (List<List<T>> to Stream<T>), one-to-many transformations, handling Optional streams, processing hierarchical data.

---

## Additional Resources

### Oracle Documentation
- Collections Framework: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/doc-files/coll-overview.html
- Stream API: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/package-summary.html

### Books
- "Effective Java" by Joshua Bloch (Items 45-48, 54-67)
- "Java Concurrency in Practice" by Brian Goetz

### Online
- Baeldung Java Collections
- Baeldung Java Streams

---

**End of Study Guide**
