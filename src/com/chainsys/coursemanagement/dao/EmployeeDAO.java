package com.chainsys.coursemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.chainsys.coursemanagement.connectionutil.ConnectionUtil;
import com.chainsys.coursemanagement.model.Employee;

public class EmployeeDAO {

	public int addEmployee(Employee employee) throws ClassNotFoundException,
			SQLException {
		Connection connection = ConnectionUtil.getConnection();
		String query = "INSERT INTO employee(id,firstname,lastname,phonenumber,email,password,department_id,job_id,status) VALUES(employee_id_seq.nextval,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = connection
				.prepareStatement(query);
		preparedStatement.setString(1, employee.getFirstName());
		preparedStatement.setString(2, employee.getLastName());
		preparedStatement.setString(3, employee.getPhonenumber());
		preparedStatement.setString(4, employee.getEmail());
		preparedStatement.setString(5, employee.getPassword());
		preparedStatement.setInt(6, employee.getDepartment().getId());
		preparedStatement.setInt(7, employee.getJob().getId());
		preparedStatement.setInt(8, employee.getStatus());
		int registrationResult = preparedStatement.executeUpdate();
		ConnectionUtil.closeConnection(connection, preparedStatement, null);
		return registrationResult;

	}

	
	public Employee selectAllEmployeeDetails(Employee employee) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtil.getConnection();
		String query="SELECT id,firstname,lastname,phonenumber,email,password FROM employee WHERE email=? and password=? and status=?";
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		preparedStatement.setString(1, employee.getEmail());
		preparedStatement.setString(2, employee.getPassword());
		preparedStatement.setInt(3, employee.getStatus());
		ResultSet resultSet=preparedStatement.executeQuery();
		
		
		Employee employeeDetails=null;
		while(resultSet.next())
		{
			employeeDetails=new Employee();
			employeeDetails.setId(resultSet.getInt("id"));
			employeeDetails.setFirstName(resultSet.getString("firstname"));
			employeeDetails.setLastName(resultSet.getString("lastname"));
			employeeDetails.setPhonenumber(resultSet.getString("phonenumber"));
			employeeDetails.setEmail(resultSet.getString("email"));
			employeeDetails.setPassword(resultSet.getString("password"));
		}
		ConnectionUtil.closeConnection(connection, preparedStatement, resultSet);
		return employeeDetails;
	}
}
