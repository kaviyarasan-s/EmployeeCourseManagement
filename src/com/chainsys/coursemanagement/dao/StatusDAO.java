package com.chainsys.coursemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.chainsys.coursemanagement.connectionutil.ConnectionUtil;
import com.chainsys.coursemanagement.model.Status;

public class StatusDAO {

	
	public ArrayList<Status> selectAllStatus() throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtil.getConnection();
		String query="SELECT id,name FROM status";
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		
		ResultSet resultSet=preparedStatement.executeQuery();
		
		ArrayList<Status> courseList=new ArrayList<Status>();
		
		while(resultSet.next())
		{
			Status statusDetails=new Status();
			statusDetails.setId(resultSet.getInt("id"));
			statusDetails.setName(resultSet.getString("name"));
			courseList.add(statusDetails);
		}
		ConnectionUtil.closeConnection(connection, preparedStatement, resultSet);
		return courseList;
	}
}
