package com.example.java_collections.streams;

import java.util.*;
import java.util.stream.*;

/**
 * Stream API Fundamentals: Creation, Intermediate, and Terminal Operations
 */
public class StreamBasics {
    
    public static void main(String[] args) {
        System.out.println("=== STREAM API BASICS ===\n");
        
        demonstrateStreamCreation();
        demonstrateIntermediateOperations();
        demonstrateTerminalOperations();
        demonstrateLazyEvaluation();
        demonstrateStatefulOperations();
        demonstrateShortCircuiting();
    }
    
    private static void demonstrateStreamCreation() {
        System.out.println("1. Stream Creation Methods");
        
        Stream<String> fromCollection = Arrays.asList("A", "B", "C").stream();
        System.out.println("   From collection: " + fromCollection.count());
        
        Stream<Integer> fromArray = Arrays.stream(new Integer[]{1, 2, 3});
        System.out.println("   From array: " + fromArray.count());
        
        Stream<String> fromValues = Stream.of("X", "Y", "Z");
        System.out.println("   From values: " + fromValues.count());
        
        Stream<Double> fromGenerate = Stream.generate(Math::random).limit(3);
        System.out.println("   Generated (limited): " + fromGenerate.count());
        
        Stream<Integer> fromIterate = Stream.iterate(0, n -> n + 2).limit(5);
        System.out.println("   Iterated: " + fromIterate.collect(Collectors.toList()));
        
        IntStream intRange = IntStream.range(1, 5);
        System.out.println("   IntStream.range(1,5): " + intRange.boxed().collect(Collectors.toList()));
        
        Stream<String> fromBuilder = Stream.<String>builder()
            .add("One").add("Two").add("Three").build();
        System.out.println("   From builder: " + fromBuilder.collect(Collectors.toList()) + "\n");
    }
    
    private static void demonstrateIntermediateOperations() {
        System.out.println("2. Intermediate Operations (return Stream, lazy)");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        List<Integer> filtered = numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());
        System.out.println("   filter (even): " + filtered);
        
        List<Integer> mapped = numbers.stream()
            .map(n -> n * 2)
            .collect(Collectors.toList());
        System.out.println("   map (double): " + mapped);
        
        List<Integer> flatMapped = Arrays.asList(
            Arrays.asList(1, 2),
            Arrays.asList(3, 4),
            Arrays.asList(5, 6)
        ).stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
        System.out.println("   flatMap: " + flatMapped);
        
        List<Integer> distinct = Arrays.asList(1, 2, 2, 3, 3, 4).stream()
            .distinct()
            .collect(Collectors.toList());
        System.out.println("   distinct: " + distinct);
        
        List<Integer> sorted = Arrays.asList(5, 2, 8, 1, 9).stream()
            .sorted()
            .collect(Collectors.toList());
        System.out.println("   sorted: " + sorted);
        
        List<Integer> limited = numbers.stream()
            .limit(3)
            .collect(Collectors.toList());
        System.out.println("   limit(3): " + limited);
        
        List<Integer> skipped = numbers.stream()
            .skip(7)
            .collect(Collectors.toList());
        System.out.println("   skip(7): " + skipped + "\n");
    }
    
    private static void demonstrateTerminalOperations() {
        System.out.println("3. Terminal Operations (trigger execution)");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        numbers.stream().forEach(n -> System.out.print(n + " "));
        System.out.println("  <- forEach");
        
        long count = numbers.stream().count();
        System.out.println("   count: " + count);
        
        List<Integer> collected = numbers.stream().collect(Collectors.toList());
        System.out.println("   collect: " + collected);
        
        Optional<Integer> min = numbers.stream().min(Integer::compareTo);
        System.out.println("   min: " + min.orElse(null));
        
        Optional<Integer> max = numbers.stream().max(Integer::compareTo);
        System.out.println("   max: " + max.orElse(null));
        
        boolean anyMatch = numbers.stream().anyMatch(n -> n > 3);
        System.out.println("   anyMatch (>3): " + anyMatch);
        
        boolean allMatch = numbers.stream().allMatch(n -> n > 0);
        System.out.println("   allMatch (>0): " + allMatch);
        
        boolean noneMatch = numbers.stream().noneMatch(n -> n > 10);
        System.out.println("   noneMatch (>10): " + noneMatch);
        
        Optional<Integer> findFirst = numbers.stream().findFirst();
        System.out.println("   findFirst: " + findFirst.orElse(null));
        
        int sum = numbers.stream().reduce(0, Integer::sum);
        System.out.println("   reduce (sum): " + sum + "\n");
    }
    
    private static void demonstrateLazyEvaluation() {
        System.out.println("4. Lazy Evaluation");
        System.out.println("   Intermediate operations not executed until terminal operation");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        Stream<Integer> stream = numbers.stream()
            .filter(n -> {
                System.out.println("     Filtering: " + n);
                return n % 2 == 0;
            })
            .map(n -> {
                System.out.println("     Mapping: " + n);
                return n * 2;
            });
        
        System.out.println("   Stream created, but nothing executed yet");
        System.out.println("   Calling terminal operation:");
        
        List<Integer> result = stream.collect(Collectors.toList());
        System.out.println("   Result: " + result + "\n");
    }
    
    private static void demonstrateStatefulOperations() {
        System.out.println("5. Stateful vs Stateless Operations");
        System.out.println("   Stateless: filter, map, flatMap");
        System.out.println("   Stateful: distinct, sorted, limit, skip");
        
        List<Integer> numbers = Arrays.asList(5, 2, 8, 1, 9, 2, 5);
        
        List<Integer> result = numbers.stream()
            .filter(n -> n > 2)       // Stateless
            .distinct()               // Stateful - needs to track seen elements
            .sorted()                 // Stateful - needs all elements
            .limit(3)                 // Stateful - needs to count
            .collect(Collectors.toList());
        
        System.out.println("   Result: " + result + "\n");
    }
    
    private static void demonstrateShortCircuiting() {
        System.out.println("6. Short-Circuiting Operations");
        System.out.println("   Can complete without processing entire stream");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        Optional<Integer> firstEven = numbers.stream()
            .peek(n -> System.out.print("   Checking: " + n + " "))
            .filter(n -> n % 2 == 0)
            .findFirst();
        
        System.out.println("\n   First even: " + firstEven.orElse(null));
        System.out.println("   Stream stopped after finding first match");
        
        boolean anyGreaterThan5 = numbers.stream()
            .peek(n -> System.out.print("   Checking: " + n + " "))
            .anyMatch(n -> n > 5);
        
        System.out.println("\n   Any > 5: " + anyGreaterThan5);
        
        System.out.println("\n=== KEY TAKEAWAY ===");
        System.out.println("Streams are lazy, evaluated only on terminal operations");
        System.out.println("Intermediate operations return Stream, terminal operations produce result");
        System.out.println("Short-circuiting operations can optimize performance");
    }
}
