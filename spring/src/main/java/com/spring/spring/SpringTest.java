package com.spring.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {
	public static void main(String args[]) {
		// Employee e = new Employee(30000, "Naidu", "B084");
		// System.out.println(e);

		// Inversion of Control
		// Spring core usage
		// Dependency injection
		// Add spring core jar files to the dependency
		final ApplicationContext context = new ClassPathXmlApplicationContext("springcourse.xml");
		Employee e1 = (Employee) context.getBean("employee");
		System.out.println(e1);
		((ClassPathXmlApplicationContext) context).close();
	}

}
