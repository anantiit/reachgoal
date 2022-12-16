package com.reachgoal.springjdbc.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.reachgoal.springjdbc.dao.EmployeeDao;
import com.reachgoal.springjdbc.entities.Employee;

public class EmployeeEndPoint {
	public static void main(String args[]) {
		final ApplicationContext context = new ClassPathXmlApplicationContext("springcourse.xml");
		EmployeeDao eDao = (EmployeeDao) context.getBean("eDao");
		Employee e1 = new Employee("B084", 3000, "Naidu", "1234");
		// eDao.addEmployee(e1);
		System.out.println(eDao.getEmployeeById("B084"));
	}
}
