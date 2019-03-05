package com.chainsys.coursemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.chainsys.coursemanagement.connectionutil.ConnectionUtil;
import com.chainsys.coursemanagement.model.Courses;

public class CourseDAO {

	public int addCourse(Courses course) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtil.getConnection();
		String query="INSERT INTO courses(id,name,status) VALUES(courses_id_seq.nextval,?,?)";
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		preparedStatement.setString(1, course.getName());
		preparedStatement.setInt(2, course.getStatus());
		int noOfCourseAdded=preparedStatement.executeUpdate();
		ConnectionUtil.closeConnection(connection, preparedStatement, null);
		return noOfCourseAdded;
		
	}
	
	public int updateCourse(Courses course) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtil.getConnection();
		String query="UPDATE courses SET name=? WHERE id=?";
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		preparedStatement.setString(1, course.getName());
		preparedStatement.setInt(2, course.getId());
		int noOfCourseUpdated=preparedStatement.executeUpdate();
		ConnectionUtil.closeConnection(connection, preparedStatement, null);
		return noOfCourseUpdated;
		
	}
	
	public int removeCourse(Courses course) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtil.getConnection();
		String query="UPDATE courses SET status=? WHERE id=?";
		PreparedStatement preparedStatement=connection.prepareStatement(query);		
		
		preparedStatement.setInt(1, course.getStatus());
		preparedStatement.setInt(2, course.getId());
		int noOfCourseRemoved=preparedStatement.executeUpdate();
		ConnectionUtil.closeConnection(connection, preparedStatement, null);
		return noOfCourseRemoved;
		
	}
	
	public ArrayList<Courses> selectAllCourse() throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtil.getConnection();
		String query="SELECT id,name FROM courses WHERE status=?";
		PreparedStatement preparedStatement=connection.prepareStatement(query);
		preparedStatement.setInt(1, 1);
		ResultSet resultSet=preparedStatement.executeQuery();
		
		ArrayList<Courses> courseList=new ArrayList<Courses>();
		
		while(resultSet.next())
		{
			Courses courseDetails=new Courses();
			courseDetails.setId(resultSet.getInt("id"));
			courseDetails.setName(resultSet.getString("name"));
			courseList.add(courseDetails);
		}
		ConnectionUtil.closeConnection(connection, preparedStatement, resultSet);
		return courseList;
	}
	
	
	

}
