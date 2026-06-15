package com.example.java_collections.streams;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;

/**
 * Advanced Stream Operations: Collectors, Grouping, Partitioning, Custom Collectors
 */
public class AdvancedStreams {
    
    public static void main(String[] args) {
        System.out.println("=== ADVANCED STREAM OPERATIONS ===\n");
        
        demonstrateCollectors();
        demonstrateGroupingBy();
        demonstratePartitioningBy();
        demonstrateDownstreamCollectors();
        demonstrateTeeing();
        demonstrateFlatMapAdvanced();
        demonstrateReduceAdvanced();
    }
    
    private static void demonstrateCollectors() {
        System.out.println("1. Common Collectors");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
        
        List<String> toList = names.stream().collect(Collectors.toList());
        System.out.println("   toList: " + toList);
        
        Set<String> toSet = names.stream().collect(Collectors.toSet());
        System.out.println("   toSet: " + toSet);
        
        String joined = names.stream().collect(Collectors.joining(", "));
        System.out.println("   joining: " + joined);
        
        String joinedWithPrefix = names.stream()
            .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("   joining with delimiters: " + joinedWithPrefix);
        
        Map<String, Integer> toMap = names.stream()
            .collect(Collectors.toMap(name -> name, String::length));
        System.out.println("   toMap: " + toMap);
        
        long count = names.stream().collect(Collectors.counting());
        System.out.println("   counting: " + count + "\n");
    }
    
    private static void demonstrateGroupingBy() {
        System.out.println("2. groupingBy - Group elements by classifier");
        
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", "Engineering", 75000),
            new Employee("Bob", "Engineering", 80000),
            new Employee("Charlie", "Sales", 60000),
            new Employee("David", "Sales", 65000),
            new Employee("Eve", "HR", 55000)
        );
        
        Map<String, List<Employee>> byDepartment = employees.stream()
            .collect(Collectors.groupingBy(Employee::getDepartment));
        
        System.out.println("   Grouped by department:");
        byDepartment.forEach((dept, emps) -> 
            System.out.println("     " + dept + ": " + emps.size() + " employees")
        );
        
        Map<String, Long> countByDepartment = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.counting()
            ));
        System.out.println("   Count by department: " + countByDepartment);
        
        Map<String, Double> avgSalaryByDept = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.averagingDouble(Employee::getSalary)
            ));
        System.out.println("   Avg salary by department: " + avgSalaryByDept + "\n");
    }
    
    private static void demonstratePartitioningBy() {
        System.out.println("3. partitioningBy - Binary classification");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        Map<Boolean, List<Integer>> partitioned = numbers.stream()
            .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        
        System.out.println("   Even: " + partitioned.get(true));
        System.out.println("   Odd: " + partitioned.get(false));
        
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", "Engineering", 75000),
            new Employee("Bob", "Engineering", 45000),
            new Employee("Charlie", "Sales", 60000)
        );
        
        Map<Boolean, List<Employee>> salaryPartition = employees.stream()
            .collect(Collectors.partitioningBy(e -> e.getSalary() > 50000));
        
        System.out.println("   High earners (>50k): " + salaryPartition.get(true).size());
        System.out.println("   Low earners (<=50k): " + salaryPartition.get(false).size() + "\n");
    }
    
    private static void demonstrateDownstreamCollectors() {
        System.out.println("4. Downstream Collectors - Collectors within Collectors");
        
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", "Engineering", 75000),
            new Employee("Bob", "Engineering", 80000),
            new Employee("Charlie", "Sales", 60000),
            new Employee("David", "Sales", 65000),
            new Employee("Eve", "HR", 55000)
        );
        
        Map<String, List<String>> namesByDept = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.mapping(Employee::getName, Collectors.toList())
            ));
        System.out.println("   Names by department: " + namesByDept);
        
        Map<String, Optional<Employee>> highestPaidByDept = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))
            ));
        
        System.out.println("   Highest paid by department:");
        highestPaidByDept.forEach((dept, emp) -> 
            System.out.println("     " + dept + ": " + emp.orElse(null))
        );
        
        Map<String, Double> totalSalaryByDept = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.summingDouble(Employee::getSalary)
            ));
        System.out.println("   Total salary by department: " + totalSalaryByDept + "\n");
    }
    
    private static void demonstrateTeeing() {
        System.out.println("5. teeing - Apply two collectors and merge results (Java 12+)");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        Map<String, Object> stats = numbers.stream()
            .collect(Collectors.teeing(
                Collectors.summingInt(Integer::intValue),
                Collectors.counting(),
                (sum, count) -> Map.of("sum", sum, "count", count, "average", sum / (double) count)
            ));
        
        System.out.println("   Statistics: " + stats + "\n");
    }
    
    private static void demonstrateFlatMapAdvanced() {
        System.out.println("6. Advanced flatMap Usage");
        
        List<Department> departments = Arrays.asList(
            new Department("Engineering", Arrays.asList(
                new Employee("Alice", "Engineering", 75000),
                new Employee("Bob", "Engineering", 80000)
            )),
            new Department("Sales", Arrays.asList(
                new Employee("Charlie", "Sales", 60000),
                new Employee("David", "Sales", 65000)
            ))
        );
        
        List<String> allEmployeeNames = departments.stream()
            .flatMap(dept -> dept.getEmployees().stream())
            .map(Employee::getName)
            .collect(Collectors.toList());
        
        System.out.println("   All employee names: " + allEmployeeNames);
        
        double totalSalary = departments.stream()
            .flatMap(dept -> dept.getEmployees().stream())
            .mapToDouble(Employee::getSalary)
            .sum();
        
        System.out.println("   Total salary across all departments: " + totalSalary + "\n");
    }
    
    private static void demonstrateReduceAdvanced() {
        System.out.println("7. Advanced reduce Operations");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        int sum = numbers.stream().reduce(0, Integer::sum);
        System.out.println("   Sum: " + sum);
        
        int product = numbers.stream().reduce(1, (a, b) -> a * b);
        System.out.println("   Product: " + product);
        
        Optional<Integer> max = numbers.stream().reduce(Integer::max);
        System.out.println("   Max: " + max.orElse(null));
        
        String concatenated = Arrays.asList("A", "B", "C", "D").stream()
            .reduce("", (a, b) -> a + b);
        System.out.println("   Concatenated: " + concatenated);
        
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", "Engineering", 75000),
            new Employee("Bob", "Engineering", 80000)
        );
        
        double totalSalary = employees.stream()
            .reduce(0.0,
                (sum1, emp) -> sum1 + emp.getSalary(),
                Double::sum
            );
        System.out.println("   Total salary (parallel-friendly): " + totalSalary);
        
        System.out.println("\n=== KEY TAKEAWAY ===");
        System.out.println("Collectors.groupingBy/partitioningBy: Powerful data grouping");
        System.out.println("Downstream collectors: Nested aggregations");
        System.out.println("reduce: Custom aggregations, identity + accumulator + combiner");
    }
    
    static class Employee {
        private String name;
        private String department;
        private double salary;
        
        public Employee(String name, String department, double salary) {
            this.name = name;
            this.department = department;
            this.salary = salary;
        }
        
        public String getName() { return name; }
        public String getDepartment() { return department; }
        public double getSalary() { return salary; }
        
        @Override
        public String toString() {
            return name + "(" + department + ", $" + salary + ")";
        }
    }
    
    static class Department {
        private String name;
        private List<Employee> employees;
        
        public Department(String name, List<Employee> employees) {
            this.name = name;
            this.employees = employees;
        }
        
        public List<Employee> getEmployees() { return employees; }
    }
}
