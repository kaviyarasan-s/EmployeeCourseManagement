package com.chainsys.coursemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.chainsys.coursemanagement.connectionutil.ConnectionUtil;
import com.chainsys.coursemanagement.model.Employee;
import com.chainsys.coursemanagement.model.EmployeeTopic;
import com.chainsys.coursemanagement.model.Status;
import com.chainsys.coursemanagement.model.Topic;

public class EmployeeTopicStatusDAO {

	public int addEmployeeTopicStatus(EmployeeTopic employeeTopic)
			throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtil.getConnection();
		String query = "INSERT INTO employee_topic(id,emp_id,topics_id,status_id) VALUES(employee_topic_id_seq.nextval,?,?,?)";
		PreparedStatement preparedStatement = connection
				.prepareStatement(query);
		preparedStatement.setInt(1, employeeTopic.getEmployee().getId());
		preparedStatement.setInt(2, employeeTopic.getTopic().getId());
		preparedStatement.setInt(3, employeeTopic.getStatus().getId());
		int noOfTopicStatusAdded = preparedStatement.executeUpdate();
		ConnectionUtil.closeConnection(connection, preparedStatement, null);
		return noOfTopicStatusAdded;

	}

	public int updateEmployeeTopicStatus(EmployeeTopic employeeTopic)
			throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtil.getConnection();
		String query = "UPDATE employee_topic set status_id=? WHERE topics_id=? AND emp_id=? ";
		PreparedStatement preparedStatement = connection
				.prepareStatement(query);
		preparedStatement.setInt(1, employeeTopic.getStatus().getId());
		preparedStatement.setInt(2, employeeTopic.getTopic().getId());
		preparedStatement.setInt(3, employeeTopic.getEmployee().getId());
		int noOfTopicStatusUpdated = preparedStatement.executeUpdate();
		ConnectionUtil.closeConnection(connection, preparedStatement, null);
		return noOfTopicStatusUpdated;

	}

	public EmployeeTopic selectStatusIdByTopicId(Topic topic)
			throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionUtil.getConnection();
		String query = "SELECT status_id from employee_topic WHERE topics_id=?";
		PreparedStatement preparedStatement = connection
				.prepareStatement(query);
		preparedStatement.setInt(1, topic.getId());
		ResultSet resultSet = preparedStatement.executeQuery();
		EmployeeTopic employeeTopic=null;
		while (resultSet.next()) {
			employeeTopic = new EmployeeTopic();
			Status status = new Status();
			status.setId(resultSet.getInt("status_id"));
			employeeTopic.setStatus(status);
		}
		ConnectionUtil
				.closeConnection(connection, preparedStatement, resultSet);
		return employeeTopic;
	}

	public ArrayList<EmployeeTopic> selectTopicsStatusList(
			EmployeeTopic employeeTopic) throws SQLException,
			ClassNotFoundException {
		Connection connection = ConnectionUtil.getConnection();
		String query = "select c.name,t.name as topicname,s.name as status from courses c join topics t on c.id=t.courses_id "
				+ " join employee_topic et on et.topics_id=t.id"
				+ " join status s on et.status_id=s.id"
				+ " where et.emp_id=? and c.id=?";
		PreparedStatement preparedStatement = connection
				.prepareStatement(query);
		preparedStatement.setInt(1, employeeTopic.getEmployee().getId());
		preparedStatement.setInt(2, employeeTopic.getTopic().getCourse()
				.getId());
		ResultSet resultSet = preparedStatement.executeQuery();

		ArrayList<EmployeeTopic> employeeTopicList = new ArrayList<EmployeeTopic>();
		while (resultSet.next()) {
			EmployeeTopic employeeTopicStatusDetails = new EmployeeTopic();
			Status status = new Status();

			status.setName(resultSet.getString("status"));
			employeeTopicStatusDetails.setStatus(status);
			Topic topic = new Topic();

			topic.setName(resultSet.getString("topicname"));
			employeeTopicStatusDetails.setTopic(topic);
			employeeTopicList.add(employeeTopicStatusDetails);
		}
		ConnectionUtil
				.closeConnection(connection, preparedStatement, resultSet);
		return employeeTopicList;
	}

	public ArrayList<EmployeeTopic> selectEmployeeByStatus(
			EmployeeTopic employeeTopic) throws SQLException,
			ClassNotFoundException {
		Connection connection = ConnectionUtil.getConnection();
		String query = "select count(topics_id) as topiccount,et.emp_id,e.firstname from employee_topic et "
				+ " join topics t on et.topics_id =  t.id"
				+ " join employee e on e.id=et.emp_id"
				+ " where status_id=? and t.courses_id=?"
				+ " group by emp_id,firstname ";
		PreparedStatement preparedStatement = connection
				.prepareStatement(query);
		preparedStatement.setInt(1, employeeTopic.getStatus().getId());
		preparedStatement.setInt(2, employeeTopic.getTopic().getCourse()
				.getId());

		ResultSet resultSet = preparedStatement.executeQuery();

		ArrayList<EmployeeTopic> employeeTopicList = new ArrayList<EmployeeTopic>();
		while (resultSet.next()) {
			EmployeeTopic employeeTopicStatusDetails = new EmployeeTopic();
			Employee employee = new Employee();

			employee.setId(resultSet.getInt("emp_id"));

			employee.setFirstName(resultSet.getString("firstname"));

			employeeTopicStatusDetails.setTopiccount(resultSet
					.getInt("topiccount"));
			employeeTopicStatusDetails.setEmployee(employee);
			employeeTopicList.add(employeeTopicStatusDetails);
		}
		ConnectionUtil
				.closeConnection(connection, preparedStatement, resultSet);
		return employeeTopicList;
	}
}