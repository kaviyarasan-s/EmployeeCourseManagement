package com.chainsys.coursemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.chainsys.coursemanagement.connectionutil.ConnectionUtil;
import com.chainsys.coursemanagement.model.Courses;
import com.chainsys.coursemanagement.model.Topic;

public class TopicDAO {
	/**
	 * This method is used to add topics
	 * @param topic
	 * @return boolean true or false
	 * @throws Exception
	 */
	public boolean addTopics(Topic topic) throws Exception {
		Connection connection = null;
		boolean success = false;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionUtil.getConnection();
			String query = "INSERT INTO topics(id,name,courses_id,status,createdon,createdby) VALUES(topics_id_seq.nextval,?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, topic.getName());
			preparedStatement.setInt(2, topic.getCourse().getId());
			preparedStatement.setInt(3, topic.getStatus());
			preparedStatement.setTimestamp(4, Timestamp.valueOf(topic.getCreatedOn()));
			preparedStatement.setInt(5, topic.getCreatedBy());			
			int noOfTopicsAdded = preparedStatement.executeUpdate();
			if (noOfTopicsAdded > 0)
				success = true;
		} catch (Exception e) {
			throw new Exception("Unable to add topics");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement, null);
		}
		return success;
	}
	/**
	 * This method is used to update topics
	 * @param topic
	 * @return boolean true or false
	 * @throws Exception
	 */
	public boolean updateTopics(Topic topic) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		boolean success = false;
		try {
			connection = ConnectionUtil.getConnection();
			String query = "UPDATE topics SET name=?,modifiedon=?,modifiedby=? WHERE courses_id=? AND id=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, topic.getName());
			preparedStatement.setTimestamp(2, Timestamp.valueOf(topic.getModifiedOn()));
			preparedStatement.setInt(3, topic.getModifiedBy());
			preparedStatement.setInt(4, topic.getCourse().getId());
			preparedStatement.setInt(5, topic.getId());			
			int noOfTopicsUpdated = preparedStatement.executeUpdate();
			if (noOfTopicsUpdated > 0) {
				success = true;
			}
		} catch (Exception e) {
			throw new Exception("Unable to update topics");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement, null);
		}
		return success;
	}
	/**
	 * This method is used to remove topics
	 * @param topic
	 * @return boolean true or false
	 * @throws Exception
	 */
	public boolean removeTopics(Topic topic) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		boolean success = false;
		try {
			connection = ConnectionUtil.getConnection();
			String query = "UPDATE topics SET status=?,modifiedon=?,modifiedby=? WHERE courses_id=? AND id=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, topic.getStatus());
			preparedStatement.setTimestamp(2, Timestamp.valueOf(topic.getModifiedOn()));
			preparedStatement.setInt(3, topic.getModifiedBy());
			preparedStatement.setInt(4, topic.getCourse().getId());
			preparedStatement.setInt(5, topic.getId());			
			int noOfTopicsRemoved = preparedStatement.executeUpdate();
			if (noOfTopicsRemoved > 0)
				success = true;
		} catch (Exception e) {
			throw new Exception("Unable to remove topics");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement, null);
		}
		return success;
	}
	/**
	 * This method is used to select topics id using topics name
	 * @param topic
	 * @return topic object
	 * @throws Exception
	 */
	public Topic selectTopicsIdByName(Topic topic) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Topic topicDetails = null;
		try {
			connection = ConnectionUtil.getConnection();
			String query = "SELECT id FROM topics WHERE name=? AND courses_id=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, topic.getName());
			preparedStatement.setInt(2, topic.getCourse().getId());
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				topicDetails = new Topic();
				while (resultSet.next()) {
					topicDetails.setId(resultSet.getInt("id"));
				}
			}
		} catch (Exception e) {
			throw new Exception("Unable to find topics");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement, null);
		}
		return topicDetails;
	}
	/**
	 * This method is used to select topics based on course 
	 * @param course
	 * @return list of topics
	 * @throws Exception
	 */
	public ArrayList<Topic> selectAllTopicsByCourse(Courses course)
			throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Topic> topicList = null;
		try {
			connection = ConnectionUtil.getConnection();
			String query = "SELECT id,name FROM topics WHERE courses_id=? AND status=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, course.getId());
			preparedStatement.setInt(2, course.getStatus());
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				topicList = new ArrayList<Topic>();
				while (resultSet.next()) {
					Topic topicDetails = new Topic();
					topicDetails.setId(resultSet.getInt("id"));
					topicDetails.setName(resultSet.getString("name"));
					topicList.add(topicDetails);
				}
			}
		} catch (Exception e) {
			throw new Exception("Unable to find topics list");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement,
					resultSet);
		}
		return topicList;
	}
}
