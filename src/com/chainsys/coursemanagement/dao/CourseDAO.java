package com.chainsys.coursemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.chainsys.coursemanagement.connectionutil.ConnectionUtil;
import com.chainsys.coursemanagement.model.Courses;

public class CourseDAO {
	/**
	 * This method is used to add course	 * 
	 * @param course
	 * @return boolean
	 * @throws Exception
	 */
	public boolean addCourse(Courses course) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		boolean success = false;
		try {
			connection = ConnectionUtil.getConnection();
			String query = "INSERT INTO courses(id,name,status,createdon,createdby) VALUES(courses_id_seq.nextval,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, course.getName());
			preparedStatement.setInt(2, course.getStatus());
			preparedStatement.setTimestamp(3,
					Timestamp.valueOf(course.getCreatedOn()));
			preparedStatement.setInt(4, course.getCreatedBy());
			int noOfCourseAdded = preparedStatement.executeUpdate();
			if (noOfCourseAdded > 0) {
				success = true;
			}
		} catch (Exception e) {
			throw new Exception("Unable to add course");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement, null);
		}
		return success;
	}
	/**
	 * This method is used to update course
	 * @param course
	 * @return boolean
	 * @throws Exception
	 */
	public boolean updateCourse(Courses course) throws Exception {
		Connection connection = null;
		boolean success = false;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionUtil.getConnection();
			String query = "UPDATE courses SET name=?,modifiedon=?,modifiedby=? WHERE id=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, course.getName());
			preparedStatement.setTimestamp(2,
					Timestamp.valueOf(course.getModifiedOn()));
			preparedStatement.setInt(3, course.getModifiedBy());
			preparedStatement.setInt(4, course.getId());
			int noOfCourseUpdated = preparedStatement.executeUpdate();
			if (noOfCourseUpdated > 0) {
				success = true;
			}
		} catch (Exception e) {
			throw new Exception("Unable to update course");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement, null);
		}
		return success;
	}
	/**
	 * This method is used to remove course
	 * @param course
	 * @return boolean
	 * @throws Exception
	 */
	public boolean removeCourse(Courses course) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		boolean success = false;
		try {
			connection = ConnectionUtil.getConnection();
			String query = "UPDATE courses SET status=?,modifiedon=?,modifiedby=? WHERE id=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, course.getStatus());
			preparedStatement.setTimestamp(2,
					Timestamp.valueOf(course.getModifiedOn()));
			preparedStatement.setInt(3, course.getModifiedBy());
			preparedStatement.setInt(4, course.getId());
			int noOfCourseRemoved = preparedStatement.executeUpdate();
			if (noOfCourseRemoved > 0) {
				success = true;
			}
		} catch (Exception e) {
			throw new Exception("Unable to remove course");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement, null);
		}
		return success;
	}
	/**
	 * This method used to select all course
	 * @return list of courses
	 * @throws Exception
	 */
	public ArrayList<Courses> selectAllCourse() throws Exception {
		Connection connection = null;
		ArrayList<Courses> courseList = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionUtil.getConnection();
			String query = "SELECT id,name FROM courses WHERE status=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, 1);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				courseList = new ArrayList<Courses>();
				while (resultSet.next()) {
					Courses courseDetails = new Courses();
					courseDetails.setId(resultSet.getInt("id"));
					courseDetails.setName(resultSet.getString("name"));
					courseList.add(courseDetails);
				}
			}
		} catch (Exception e) {
			throw new Exception("Courses not found!");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement, null);
		}
		return courseList;
	}
}
