package com.reachgoal.springjdbc.entities;

/**
 * Hello world!
 *
 */
public class Employee {
	int eSalary;
	String eName;
	String eid;
	String ePassword;

	public Employee() {
		super();
	}

	public Employee(String eid, int eSalary, String eName, String ePassword) {
		super();
		this.eid = eid;
		this.eSalary = eSalary;
		this.eName = eName;
		this.ePassword = ePassword;
	}

	public int geteSalary() {
		return eSalary;
	}

	public void seteSalary(int eSalary) {
		this.eSalary = eSalary;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getePassword() {
		return ePassword;
	}

	public void setePassword(String ePassword) {
		this.ePassword = ePassword;
	}

	@Override
	public String toString() {
		return "Employee [eSalary=" + eSalary + ", eName=" + eName + ", eid=" + eid + ", ePassword=" + ePassword + "]";
	}

	public static void myInit() {
		System.out.println("Object initialized");
	}

	public static void myDestroy() {
		System.out.println("Object destroyed");
	}
}
