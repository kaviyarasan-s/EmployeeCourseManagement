package com.chainsys.coursemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.chainsys.coursemanagement.connectionutil.ConnectionUtil;
import com.chainsys.coursemanagement.model.Job;

public class JobDAO {

	public ArrayList<Job> selectAllDepartment() throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtil.getConnection();
		String query="SELECT id,name FROM job";
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		
		ResultSet resultSet=preparedStatement.executeQuery();
		
		ArrayList<Job> jobList=new ArrayList<Job>();
		
		while(resultSet.next())
		{
			Job jobDetails=new Job();
			jobDetails.setId(resultSet.getInt("id"));
			jobDetails.setName(resultSet.getString("name"));
			jobList.add(jobDetails);
		}
		ConnectionUtil.closeConnection(connection, preparedStatement, resultSet);
		return jobList;
	}
	
}

