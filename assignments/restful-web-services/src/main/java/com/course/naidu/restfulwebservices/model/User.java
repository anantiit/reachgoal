package com.course.naidu.restfulwebservices.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class User implements Serializable {

	private static final long serialVersionUID = 8232606698142711114L;

	private Integer id;
	@Size(min = 2, message = "Name should have atleast 2 chacters")
	private String name;
	@Past(message = "DOB should be past date")
	private LocalDate dob;

	public User(Integer id, String name, LocalDate dob) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", dob=" + dob + "]";
	}

}
