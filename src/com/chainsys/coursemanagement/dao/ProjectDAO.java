package com.chainsys.coursemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.chainsys.coursemanagement.connectionutil.ConnectionUtil;
import com.chainsys.coursemanagement.model.Department;
import com.chainsys.coursemanagement.model.Manager;
import com.chainsys.coursemanagement.model.Project;

public class ProjectDAO {

	public boolean addProject(Project project) throws Exception {
		Connection connection = null;
		int addProjectResult = 0;
		PreparedStatement preparedStatement = null;
		boolean success = false;
		try {
			connection = ConnectionUtil.getConnection();

			String query = "INSERT INTO projects(id,name,department_id,manager_id,createdon,createdby) VALUES(project_id_seq.nextval,?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, project.getName());
			preparedStatement.setInt(2, project.getDepartment().getId());
			preparedStatement.setInt(3, project.getManager().getId());
			preparedStatement.setTimestamp(4,Timestamp.valueOf(project.getCreatedOn()));
			preparedStatement.setInt(5, project.getCreatedBy());

			addProjectResult = preparedStatement.executeUpdate();
			if (addProjectResult > 0) {
				success = true;
			}
		} catch (Exception e) {
			throw new Exception("Unable to assign project");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement, null);
		}

		return success;

	}

	public Project selectProject(Manager manager) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Project project = null;
		try {
			connection = ConnectionUtil.getConnection();
			String query = "SELECT p.manager_id as managerid,p.name as projectname ,d.name as departmentname from projects p "
					+ "join employee_department d on p.department_id=d.id "
					+ "where manager_id=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, manager.getId());
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				project = new Project();
				while (resultSet.next()) {
					Manager managerDetails = new Manager();
					managerDetails.setId(resultSet.getInt("managerid"));
					project.setManager(managerDetails);
					project.setName(resultSet.getString("projectname"));
					Department department = new Department();
					department.setName(resultSet.getString("departmentname"));
					project.setDepartment(department);
				}
			}
		} catch (Exception e) {
			throw new Exception("Unable to fetch project details");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement, null);
		}
		return project;
	}

}
