package com.chainsys.coursemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.chainsys.coursemanagement.connectionutil.ConnectionUtil;
import com.chainsys.coursemanagement.model.Department;

public class DepartmentDAO {

	public ArrayList<Department> selectAllDepartment()
 throws Exception {
		Connection connection = null;		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Department> departmentList = null;
		try {
			connection = ConnectionUtil.getConnection();
			String query = "SELECT id,name FROM employee_department";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet != null) {
				departmentList = new ArrayList<Department>();
				while (resultSet.next()) {
					Department departmentDetails = new Department();
					departmentDetails.setId(resultSet.getInt("id"));
					departmentDetails.setName(resultSet.getString("name"));
					departmentList.add(departmentDetails);
				}
			}
		} catch (Exception e) {
			throw new Exception("Unable to found departments!");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement,
					resultSet);
		}

		return departmentList;
	}
}
