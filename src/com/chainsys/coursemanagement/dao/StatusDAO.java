package com.chainsys.coursemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.chainsys.coursemanagement.connectionutil.ConnectionUtil;
import com.chainsys.coursemanagement.model.Status;

public class StatusDAO {

	public ArrayList<Status> selectAllStatus() throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Status> courseList = null;
		try {
			connection = ConnectionUtil.getConnection();
			String query = "SELECT id,name FROM status";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				courseList = new ArrayList<Status>();
				while (resultSet.next()) {
					Status statusDetails = new Status();
					statusDetails.setId(resultSet.getInt("id"));
					statusDetails.setName(resultSet.getString("name"));
					courseList.add(statusDetails);
				}
			}
		} catch (Exception e) {
			throw new Exception("Unable to found status");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement,
					resultSet);
		}
		return courseList;
	}
}
