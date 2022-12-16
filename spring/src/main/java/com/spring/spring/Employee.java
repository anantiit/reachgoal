package com.spring.spring;

/**
 * Hello world!
 *
 */
public class Employee {
	int eSalary;
	String eName;
	String eid;
	Address address;

	public Employee(int eSalary, String eName, Address address) {
		super();
		this.eSalary = eSalary;
		this.eName = eName;
		this.address = address;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	@Override
	public String toString() {
		return "Employee [eSalary=" + eSalary + ", eName=" + eName + ", eid=" + eid + ", address=" + address + "]";
	}

	public static void myInit() {
		System.out.println("Object initialized");
	}

	public static void myDestroy() {
		System.out.println("Object destroyed");
	}
}
