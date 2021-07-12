package com.reachgoal.springjdbc.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmployeeMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Employee e = new Employee();
		e.seteSalary(rs.getInt("eSalary"));
		e.setEid(rs.getString("eSalary"));
		e.seteName(rs.getString("eName"));
		e.setePassword(rs.getString("ePassword"));
		return e;
	}

}
