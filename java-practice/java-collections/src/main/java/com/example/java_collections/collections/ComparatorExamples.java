package com.example.java_collections.collections;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Comparable, Comparator, and advanced sorting techniques
 */
public class ComparatorExamples {
    
    public static void main(String[] args) {
        System.out.println("=== COMPARABLE & COMPARATOR ===\n");
        
        demonstrateComparable();
        demonstrateComparator();
        demonstrateComparatorChaining();
        demonstrateNullsFirstLast();
        demonstrateReverseOrder();
        demonstrateComparingMethods();
    }
    
    private static void demonstrateComparable() {
        System.out.println("1. Comparable Interface - Natural Ordering");
        
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 30, 75000),
            new Employee("Bob", 25, 60000),
            new Employee("Charlie", 35, 85000)
        );
        
        Collections.sort(employees);
        System.out.println("   Sorted by name (natural order):");
        employees.forEach(e -> System.out.println("     " + e));
        System.out.println();
    }
    
    private static void demonstrateComparator() {
        System.out.println("2. Comparator Interface - Custom Ordering");
        
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 30, 75000),
            new Employee("Bob", 25, 60000),
            new Employee("Charlie", 35, 85000)
        );
        
        employees.sort(Comparator.comparingInt(Employee::getAge));
        System.out.println("   Sorted by age:");
        employees.forEach(e -> System.out.println("     " + e));
        System.out.println();
    }
    
    private static void demonstrateComparatorChaining() {
        System.out.println("3. Comparator Chaining - thenComparing()");
        
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 30, 75000),
            new Employee("Bob", 30, 60000),
            new Employee("Charlie", 25, 85000),
            new Employee("David", 30, 70000)
        );
        
        Comparator<Employee> comparator = Comparator
            .comparingInt(Employee::getAge)
            .thenComparingDouble(Employee::getSalary)
            .thenComparing(Employee::getName);
        
        employees.sort(comparator);
        System.out.println("   Sorted by age, then salary, then name:");
        employees.forEach(e -> System.out.println("     " + e));
        System.out.println();
    }
    
    private static void demonstrateNullsFirstLast() {
        System.out.println("4. Handling Nulls - nullsFirst() & nullsLast()");
        
        List<String> names = Arrays.asList("Alice", null, "Bob", "Charlie", null, "David");
        
        names.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
        System.out.println("   nullsFirst: " + names);
        
        names = Arrays.asList("Alice", null, "Bob", "Charlie", null, "David");
        names.sort(Comparator.nullsLast(Comparator.naturalOrder()));
        System.out.println("   nullsLast: " + names + "\n");
    }
    
    private static void demonstrateReverseOrder() {
        System.out.println("5. Reverse Ordering");
        
        List<Integer> numbers = Arrays.asList(5, 2, 8, 1, 9, 3);
        
        numbers.sort(Comparator.reverseOrder());
        System.out.println("   Reverse natural order: " + numbers);
        
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 30, 75000),
            new Employee("Bob", 25, 60000),
            new Employee("Charlie", 35, 85000)
        );
        
        employees.sort(Comparator.comparingDouble(Employee::getSalary).reversed());
        System.out.println("   Employees by salary (descending):");
        employees.forEach(e -> System.out.println("     " + e));
        System.out.println();
    }
    
    private static void demonstrateComparingMethods() {
        System.out.println("6. Comparator Factory Methods");
        System.out.println("   - comparing(): General purpose");
        System.out.println("   - comparingInt/Long/Double(): Primitive specializations (no boxing)");
        
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 30, 75000),
            new Employee("Bob", 25, 60000)
        );
        
        Comparator<Employee> byName = Comparator.comparing(Employee::getName);
        Comparator<Employee> byAge = Comparator.comparingInt(Employee::getAge);
        Comparator<Employee> bySalary = Comparator.comparingDouble(Employee::getSalary);
        
        System.out.println("   - comparing() avoids boxing overhead");
        System.out.println("\n=== KEY TAKEAWAY ===");
        System.out.println("Comparable: Single natural ordering, modify class");
        System.out.println("Comparator: Multiple orderings, external to class");
        System.out.println("Use comparingInt/Long/Double for primitives (performance)");
    }
    
    static class Employee implements Comparable<Employee> {
        private String name;
        private int age;
        private double salary;
        
        public Employee(String name, int age, double salary) {
            this.name = name;
            this.age = age;
            this.salary = salary;
        }
        
        public String getName() { return name; }
        public int getAge() { return age; }
        public double getSalary() { return salary; }
        
        @Override
        public int compareTo(Employee other) {
            return this.name.compareTo(other.name);
        }
        
        @Override
        public String toString() {
            return String.format("%s (age=%d, salary=%.0f)", name, age, salary);
        }
    }
}
