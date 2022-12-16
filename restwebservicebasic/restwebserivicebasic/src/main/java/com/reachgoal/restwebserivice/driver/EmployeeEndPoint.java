package com.reachgoal.restwebserivice.driver;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.reachgoal.restwebserivice.dao.EmployeeDao;
import com.reachgoal.restwebserivice.entities.Employee;

//@Path("/employee")
public class EmployeeEndPoint {
	EmployeeDao eDao;

	EmployeeEndPoint(EmployeeDao eDao) {
		this.eDao = eDao;
	}

//	@GET
//	@Path("/{eId}")
//	public Response getEmployee(@PathParam("eId") String eId) {
//		// eDao.addEmployee(e1);
//		List<Employee> employees = eDao.getEmployeeById(eId);
//		return Response.status(200).entity(employees.get(0)).build();
//	}

	public static void main(String args[]) {
		final ApplicationContext context = new ClassPathXmlApplicationContext("springcourse.xml");
		EmployeeDao eDao = (EmployeeDao) context.getBean("eDao");
		Employee e1 = new Employee("B084", 3000, "Naidu", "1234");
		// eDao.addEmployee(e1);
		System.out.println(eDao.getEmployeeById("B084"));
	}
}
