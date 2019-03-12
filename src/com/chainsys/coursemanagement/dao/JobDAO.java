package com.chainsys.coursemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.chainsys.coursemanagement.connectionutil.ConnectionUtil;
import com.chainsys.coursemanagement.model.Job;

public class JobDAO {
	/**
	 * This method is used to select jobs list
	 * @return list of job
	 * @throws Exception
	 */
	public ArrayList<Job> selectAllJob() throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Job> jobList = null;
		try {
			connection = ConnectionUtil.getConnection();
			String query = "SELECT id,name FROM job";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				jobList = new ArrayList<Job>();
				while (resultSet.next()) {
					Job jobDetails = new Job();
					jobDetails.setId(resultSet.getInt("id"));
					jobDetails.setName(resultSet.getString("name"));
					jobList.add(jobDetails);
				}
			}
		} catch (Exception e) {
			throw new Exception("Unable to found job!");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement,
					resultSet);
		}
		return jobList;
	}
}
