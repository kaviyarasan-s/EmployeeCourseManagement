package com.chainsys.coursemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.chainsys.coursemanagement.connectionutil.ConnectionUtil;
import com.chainsys.coursemanagement.model.Department;

public class DepartmentDAO {

	
	public ArrayList<Department> selectAllDepartment() throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtil.getConnection();
		String query="SELECT id,name FROM employee_department";
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		
		ResultSet resultSet=preparedStatement.executeQuery();
		
		ArrayList<Department> departmentList=new ArrayList<Department>();
		
		while(resultSet.next())
		{
			Department departmentDetails=new Department();
			departmentDetails.setId(resultSet.getInt("id"));
			departmentDetails.setName(resultSet.getString("name"));
			departmentList.add(departmentDetails);
		}
		ConnectionUtil.closeConnection(connection, preparedStatement, resultSet);
		return departmentList;
	}
}
