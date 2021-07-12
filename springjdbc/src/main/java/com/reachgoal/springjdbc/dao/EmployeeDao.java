package com.reachgoal.springjdbc.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.reachgoal.springjdbc.entities.Employee;
import com.reachgoal.springjdbc.entities.EmployeeMapper;

public class EmployeeDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private PlatformTransactionManager platformTransactionManager;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}

	public PlatformTransactionManager getPlatformTransactionManager() {
		return platformTransactionManager;
	}

	public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
		this.platformTransactionManager = platformTransactionManager;
	}

	public int addEmployee(Employee e) {
		int count = 0;
		String query = "insert into employee values (?,?,?,?)";
		jdbcTemplate.update(query, e.getEid(), e.geteName(), e.geteSalary(), e.getePassword());
		return count;
	}

	public List<Employee> getEmployeeById(String eid) {
		String sql = "select * from employee where eid=?";
		List<Employee> employees = jdbcTemplate.query(sql, new EmployeeMapper(), eid);
		return employees;
	}

}
