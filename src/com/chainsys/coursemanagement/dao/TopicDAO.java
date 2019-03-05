package com.chainsys.coursemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.chainsys.coursemanagement.connectionutil.ConnectionUtil;
import com.chainsys.coursemanagement.model.Courses;
import com.chainsys.coursemanagement.model.Topic;

public class TopicDAO {

	public int addTopics(Topic topic) throws ClassNotFoundException,
			SQLException {
		Connection connection = ConnectionUtil.getConnection();
		String query = "INSERT INTO topics(id,name,courses_id,status) VALUES(topics_id_seq.nextval,?,?,?)";
		PreparedStatement preparedStatement = connection
				.prepareStatement(query);
		preparedStatement.setString(1, topic.getName());
		preparedStatement.setInt(2, topic.getCourse().getId());
		preparedStatement.setInt(3, topic.getStatus());
		int noOfTopicsAdded = preparedStatement.executeUpdate();

		ConnectionUtil.closeConnection(connection, preparedStatement, null);
		return noOfTopicsAdded;

	}

	public int updateTopics(Topic topic) throws ClassNotFoundException,
			SQLException {
		Connection connection = ConnectionUtil.getConnection();
		String query = "UPDATE topics SET name=? WHERE courses_id=? AND id=?";
		PreparedStatement preparedStatement = connection
				.prepareStatement(query);
		preparedStatement.setString(1, topic.getName());
		preparedStatement.setInt(2, topic.getCourse().getId());
		preparedStatement.setInt(3, topic.getId());
		int noOfTopicsUpdated = preparedStatement.executeUpdate();

		ConnectionUtil.closeConnection(connection, preparedStatement, null);
		return noOfTopicsUpdated;

	}

	public int removeTopics(Topic topic) throws ClassNotFoundException,
			SQLException {
		Connection connection = ConnectionUtil.getConnection();
		String query = "UPDATE topics SET status=? WHERE courses_id=? AND id=?";
		PreparedStatement preparedStatement = connection
				.prepareStatement(query);
		preparedStatement.setInt(1, topic.getStatus());
		preparedStatement.setInt(2, topic.getCourse().getId());
		preparedStatement.setInt(3, topic.getId());
		int noOfTopicsRemoved = preparedStatement.executeUpdate();

		ConnectionUtil.closeConnection(connection, preparedStatement, null);
		return noOfTopicsRemoved;

	}

	public Topic selectTopicsIdByName(Topic topic)
			throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtil.getConnection();
		String query = "SELECT id FROM topics WHERE name=? AND courses_id=?";
		PreparedStatement preparedStatement = connection
				.prepareStatement(query);
		preparedStatement.setString(1, topic.getName());
		preparedStatement.setInt(2, topic.getCourse().getId());

		ResultSet resultSet = preparedStatement.executeQuery();
		Topic topicDetails = null;
		while (resultSet.next()) {
			topicDetails = new Topic();
			topicDetails.setId(resultSet.getInt("id"));
		}
		ConnectionUtil.closeConnection(connection, preparedStatement, null);
		return topicDetails;

	}

	public ArrayList<Topic> selectAllTopicsByCourse(Courses course)
			throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtil.getConnection();
		String query = "SELECT id,name FROM topics WHERE courses_id=? AND status=?";
		PreparedStatement preparedStatement = connection
				.prepareStatement(query);
		preparedStatement.setInt(1, course.getId());
		preparedStatement.setInt(2, course.getStatus());
		ResultSet resultSet = preparedStatement.executeQuery();

		ArrayList<Topic> topicList = new ArrayList<Topic>();

		while (resultSet.next()) {
			Topic topicDetails = new Topic();
			topicDetails.setId(resultSet.getInt("id"));
			topicDetails.setName(resultSet.getString("name"));
			topicList.add(topicDetails);
		}
		ConnectionUtil
				.closeConnection(connection, preparedStatement, resultSet);
		return topicList;
	}

}
