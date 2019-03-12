package com.chainsys.coursemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.chainsys.coursemanagement.connectionutil.ConnectionUtil;
import com.chainsys.coursemanagement.model.Employee;
import com.chainsys.coursemanagement.model.EmployeeTopic;
import com.chainsys.coursemanagement.model.Status;
import com.chainsys.coursemanagement.model.Topic;

public class EmployeeTopicStatusDAO {
	/**
	 * This method is used to add status of topics 
	 * @param employeeTopic
	 * @return boolean true or false
	 * @throws Exception
	 */
	public boolean addEmployeeTopicStatus(EmployeeTopic employeeTopic)
			throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		boolean success = false;
		try {
			connection = ConnectionUtil.getConnection();
			String query = "INSERT INTO employee_topic(id,emp_id,topics_id,status_id,createdon,createdby) VALUES(employee_topic_id_seq.nextval,?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, employeeTopic.getEmployee().getId());
			preparedStatement.setInt(2, employeeTopic.getTopic().getId());
			preparedStatement.setInt(3, employeeTopic.getStatus().getId());
			preparedStatement.setTimestamp(4, Timestamp.valueOf(employeeTopic.getCreatedOn()));
			preparedStatement.setInt(5, employeeTopic.getCreatedBy());
			int noOfTopicStatusAdded = preparedStatement.executeUpdate();
			if (noOfTopicStatusAdded > 0)
				success = true;
		} catch (Exception e) {
			throw new Exception("Unable to update status!");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement, null);
		}
		return success;
	}
	/**
	 * This method is used to update status of topics
	 * @param employeeTopic
	 * @return boolean true or false
	 * @throws Exception
	 */
	public boolean updateEmployeeTopicStatus(EmployeeTopic employeeTopic)
			throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		boolean success = false;
		try {
			connection = ConnectionUtil.getConnection();
			String query = "UPDATE employee_topic set status_id=?,modifiedon=?,modifiedby=? WHERE topics_id=? AND emp_id=? ";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, employeeTopic.getStatus().getId());			
			preparedStatement.setTimestamp(2, Timestamp.valueOf(employeeTopic.getModifiedOn()));
			preparedStatement.setInt(3, employeeTopic.getModifiedBy());
			preparedStatement.setInt(4, employeeTopic.getTopic().getId());
			preparedStatement.setInt(5, employeeTopic.getEmployee().getId());
			int noOfTopicStatusUpdated = preparedStatement.executeUpdate();
			if (noOfTopicStatusUpdated > 0)
				success = true;
		} catch (Exception e) {
			throw new Exception("Unable to update status");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement, null);
		}
		return success;
	}
	/**
	 * This method is used to select status id using topic id
	 * @param topic
	 * @return EmployeeTopic object
	 * @throws Exception
	 */
	public EmployeeTopic selectStatusIdByTopicId(Topic topic) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		EmployeeTopic employeeTopic = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionUtil.getConnection();
			String query = "SELECT status_id from employee_topic WHERE topics_id=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, topic.getId());
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				employeeTopic = new EmployeeTopic();
				while (resultSet.next()) {
					Status status = new Status();
					status.setId(resultSet.getInt("status_id"));
					employeeTopic.setStatus(status);
				}
			}
		} catch (Exception e) {
			throw new Exception("Unable to found!");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement,
					resultSet);
		}
		return employeeTopic;
	}
	/**
	 * This method is used to select topics with status
	 * @param employeeTopic
	 * @return list of employee topics
	 * @throws Exception
	 */
	public ArrayList<EmployeeTopic> selectTopicsStatusList(
			EmployeeTopic employeeTopic) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ArrayList<EmployeeTopic> employeeTopicList = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionUtil.getConnection();
			String query = "select c.name,t.name as topicname,s.name as status from courses c join topics t on c.id=t.courses_id "
					+ " join employee_topic et on et.topics_id=t.id"
					+ " join status s on et.status_id=s.id"
					+ " where et.emp_id=? and c.id=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, employeeTopic.getEmployee().getId());
			preparedStatement.setInt(2, employeeTopic.getTopic().getCourse()
					.getId());
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				employeeTopicList = new ArrayList<EmployeeTopic>();
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
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable to fetch status");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement,
					resultSet);
		}
		return employeeTopicList;
	}
	/**
	 * This method is used to select employee using status of topics
	 * @param employeeTopic
	 * @return list of employee topics 
	 * @throws Exception
	 */
	public ArrayList<EmployeeTopic> selectEmployeeByStatus(
			EmployeeTopic employeeTopic) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<EmployeeTopic> employeeTopicList = null;
		try {
			connection = ConnectionUtil.getConnection();
			String query = "select count(topics_id) as topiccount,et.emp_id,e.firstname from employee_topic et "
					+ " join topics t on et.topics_id =  t.id"
					+ " join employee e on e.id=et.emp_id"
					+ " where status_id=? and t.courses_id=?"
					+ " group by emp_id,firstname ";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, employeeTopic.getStatus().getId());
			preparedStatement.setInt(2, employeeTopic.getTopic().getCourse()
					.getId());
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				employeeTopicList = new ArrayList<EmployeeTopic>();
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
			}
		} catch (Exception e) {
			throw new Exception("unable to fetch status");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement,
					resultSet);
		}
		return employeeTopicList;
	}
}
