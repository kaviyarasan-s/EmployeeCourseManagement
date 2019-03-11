package com.chainsys.coursemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.chainsys.coursemanagement.connectionutil.ConnectionUtil;
import com.chainsys.coursemanagement.model.Department;
import com.chainsys.coursemanagement.model.Employee;
import com.chainsys.coursemanagement.model.Job;
import com.chainsys.coursemanagement.model.Manager;

public class EmployeeDAO {

	public boolean addEmployeeWithManager(Employee employee) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int registrationResult = 0;
		boolean success = false;
		try {

			connection = ConnectionUtil.getConnection();

			String query = "INSERT INTO employee(id,firstname,lastname,phonenumber,email,password,department_id,job_id,status,isadmin,ismanager,manager_id,createdon,createdby) VALUES(employee_id_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, employee.getFirstName());
			preparedStatement.setString(2, employee.getLastName());
			preparedStatement.setString(3, employee.getPhonenumber());
			preparedStatement.setString(4, employee.getEmail());
			preparedStatement.setString(5, employee.getPassword());
			preparedStatement.setInt(6, employee.getDepartment().getId());
			preparedStatement.setInt(7, employee.getJob().getId());
			preparedStatement.setInt(8, employee.getStatus());
			preparedStatement.setInt(9, employee.getIsAdmin());
			preparedStatement.setInt(10, employee.getIsManager());

			preparedStatement.setInt(11, employee.getManager().getId());
			preparedStatement.setTimestamp(12,
					Timestamp.valueOf(employee.getCreatedOn()));
			preparedStatement.setInt(13, employee.getCreatedBy());

			registrationResult = preparedStatement.executeUpdate();
			if (registrationResult > 0)
				success = true;
		} catch (Exception e) {
			throw new Exception("Unable add employee");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement, null);
		}
		return success;

	}

	public boolean addEmployeeWithOutManager(Employee employee)
			throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int registrationResult = 0;
		boolean success = false;

		System.out.println(employee.getFirstName() + " "
				+ employee.getLastName() + " " + employee.getPhonenumber()
				+ " " + employee.getEmail() + " " + employee.getPassword()
				+ " " + employee.getDepartment().getId() + " "
				+ employee.getJob().getId() + " " + employee.getStatus() + " "
				+ employee.getIsAdmin() + " " + employee.getIsManager() + " "

				+ employee.getCreatedOn() + " " + employee.getCreatedBy() + " "
				+ employee.getModifiedOn() + " " + employee.getModifiedBy());
		try {
			connection = ConnectionUtil.getConnection();

			String query = "INSERT INTO employee(id,firstname,lastname,phonenumber,email,password,department_id,job_id,status,isadmin,ismanager,createdon,createdby) VALUES(employee_id_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, employee.getFirstName());
			preparedStatement.setString(2, employee.getLastName());
			preparedStatement.setString(3, employee.getPhonenumber());
			preparedStatement.setString(4, employee.getEmail());
			preparedStatement.setString(5, employee.getPassword());
			preparedStatement.setInt(6, employee.getDepartment().getId());
			preparedStatement.setInt(7, employee.getJob().getId());
			preparedStatement.setInt(8, employee.getStatus());
			preparedStatement.setInt(9, employee.getIsAdmin());
			preparedStatement.setInt(10, employee.getIsManager());
			preparedStatement.setTimestamp(11,
					Timestamp.valueOf(employee.getCreatedOn()));
			preparedStatement.setInt(12, employee.getCreatedBy());

			registrationResult = preparedStatement.executeUpdate();
			System.out.println(registrationResult);
			if (registrationResult > 0)
				success = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Unable add employee");

		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement, null);
		}
		return success;

	}

	public boolean removeEmployee(Employee employee) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int removedResult = 0;
		boolean success = false;
		try {
			connection = ConnectionUtil.getConnection();

			String query = "UPDATE employee SET status=? WHERE id=?";
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, employee.getStatus());
			preparedStatement.setInt(2, employee.getId());
			removedResult = preparedStatement.executeUpdate();
			if (removedResult > 0)
				success = true;
		} catch (Exception e) {
			throw new Exception("Unable remove employee");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement, null);
		}
		return success;

	}

	public Employee selectEmployeeDetailsByEmail(Employee employee)
			throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Employee employeeDetails = null;

		try {
			connection = ConnectionUtil.getConnection();
			String query = "select e.id as employeeid,e.firstname as firstname,e.lastname as lastname,e.phonenumber as phonenumber,e.email as email,e.password as password,d.name as departmentname,j.name as jobname,e.isadmin as isadmin,e.ismanager as ismanager,e.manager_id as managerid from employee e "
					+ "join employee_department d on d.id=e.department_id "
					+ "join job j on j.id=e.job_id "
					+ "WHERE email=? and password=? and status=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, employee.getEmail());
			preparedStatement.setString(2, employee.getPassword());
			preparedStatement.setInt(3, employee.getStatus());
			resultSet = preparedStatement.executeQuery();

			if (resultSet != null) {
				while (resultSet.next()) {
					employeeDetails = new Employee();
					employeeDetails.setId(resultSet.getInt("employeeid"));
					employeeDetails.setFirstName(resultSet
							.getString("firstname"));
					employeeDetails
							.setLastName(resultSet.getString("lastname"));
					employeeDetails.setPhonenumber(resultSet
							.getString("phonenumber"));
					employeeDetails.setEmail(resultSet.getString("email"));
					employeeDetails
							.setPassword(resultSet.getString("password"));
					employeeDetails.setIsAdmin(resultSet.getInt("isadmin"));
					employeeDetails.setIsManager(resultSet.getInt("ismanager"));
					if (employeeDetails.getIsManager() == 0) {
						Manager manager = new Manager();
						manager.setId(resultSet.getInt("managerid"));
						employeeDetails.setManager(manager);
					}
					Department department = new Department();
					department.setName(resultSet.getString("departmentname"));
					employeeDetails.setDepartment(department);
					Job job = new Job();
					job.setName(resultSet.getString("jobname"));
					employeeDetails.setJob(job);

				}
			}
		} catch (Exception e) {
			throw new Exception("Unable to Login");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement,
					resultSet);
		}
		return employeeDetails;
	}

	public Employee selectEmployeeDetailsById(Employee employee)
			throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Employee employeeDetails = null;
		try {
			connection = ConnectionUtil.getConnection();
			String query = "select e.id as employeeid,e.firstname as firstname,e.lastname as lastname,e.phonenumber as phonenumber,e.email as email,e.password as password,d.name as departmentname,j.name as jobname,e.isadmin as isadmin,e.ismanager as ismanager,e.manager_id as managerid from employee e "
					+ "join employee_department d on d.id=e.department_id "
					+ "join job j on j.id=e.job_id "
					+ "WHERE e.id=? and e.status=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, employee.getId());
			preparedStatement.setInt(2, employee.getStatus());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				employeeDetails = new Employee();
				employeeDetails.setId(resultSet.getInt("employeeid"));
				employeeDetails.setFirstName(resultSet.getString("firstname"));
				employeeDetails.setLastName(resultSet.getString("lastname"));
				employeeDetails.setPhonenumber(resultSet
						.getString("phonenumber"));
				employeeDetails.setEmail(resultSet.getString("email"));
				employeeDetails.setPassword(resultSet.getString("password"));
				employeeDetails.setIsAdmin(resultSet.getInt("isadmin"));
				employeeDetails.setIsManager(resultSet.getInt("ismanager"));
				if (employeeDetails.getIsManager() == 0) {
					Manager manager = new Manager();
					manager.setId(resultSet.getInt("managerid"));
					employeeDetails.setManager(manager);
				}
				Department department = new Department();
				department.setName(resultSet.getString("departmentname"));
				employeeDetails.setDepartment(department);
				Job job = new Job();
				job.setName(resultSet.getString("jobname"));
				employeeDetails.setJob(job);
			}
		} catch (Exception e) {
			throw new Exception("Unable to find user record");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement,
					resultSet);
		}
		return employeeDetails;
	}

	public List<Manager> selectNonProjectManagersList() throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Manager> managerList = null;
		try {
			connection = ConnectionUtil.getConnection();
			String query = "select id,firstname,lastname from employee e where e.id not in (select manager_id from projects) and e.ismanager=1";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				managerList = new ArrayList<Manager>();
				while (resultSet.next()) {
					Manager manager = new Manager();

					manager.setId(resultSet.getInt("id"));
					if (resultSet.getString("lastname") != null)
						manager.setName(resultSet.getString("firstname")
								+ resultSet.getString("lastname"));
					else
						manager.setName(resultSet.getString("firstname"));

					managerList.add(manager);

				}
			}
		} catch (Exception e) {
			throw new Exception("Unable to find managers list");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement,
					resultSet);
		}
		return managerList;
	}

	public List<Employee> selectEmployee() throws Exception {
		List<Employee> employeeDetails = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionUtil.getConnection();

			String query = "SELECT id,firstname,lastname FROM employee";
			preparedStatement = connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				employeeDetails = new ArrayList<Employee>();
				while (resultSet.next()) {
					Employee employee = new Employee();
					employee.setId(resultSet.getInt("id"));
					employee.setFirstName(resultSet.getString("firstname"));
					employee.setLastName(resultSet.getString("lastname"));
					employeeDetails.add(employee);
				}

			}
		} catch (Exception e) {
			throw new Exception("Unable to find employee record");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement,
					resultSet);
		}
		return employeeDetails;
	}

	public Manager selectManagerName(Manager manager) throws Exception {
		Connection connection = null;
		Manager managerDetails = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionUtil.getConnection();
			String query = "SELECT id,firstname,lastname FROM employee where id=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, manager.getId());
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				managerDetails = new Manager();
				while (resultSet.next()) {
					managerDetails.setId(resultSet.getInt("id"));
					managerDetails.setName(resultSet.getString("firstname")
							+ resultSet.getString("lastname"));
				}
			}
		} catch (Exception e) {
			throw new Exception("Unable to find manager record");
		} finally {
			ConnectionUtil.closeConnection(connection, preparedStatement,
					resultSet);
		}
		return managerDetails;
	}
}
