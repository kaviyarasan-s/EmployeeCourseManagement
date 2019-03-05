package com.chainsys.coursemanagement.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chainsys.coursemanagement.dao.DepartmentDAO;
import com.chainsys.coursemanagement.dao.EmployeeDAO;
import com.chainsys.coursemanagement.dao.JobDAO;
import com.chainsys.coursemanagement.model.Department;
import com.chainsys.coursemanagement.model.Employee;
import com.chainsys.coursemanagement.model.Job;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		DepartmentDAO departmentDAO = new DepartmentDAO();
		JobDAO jobDAO = new JobDAO();
		try {
			ArrayList<Department> departmentList = departmentDAO
					.selectAllDepartment();
			ArrayList<Job> jobList = jobDAO.selectAllDepartment();
			
			request.setAttribute("DEPARTMENTLIST", departmentList);
			request.setAttribute("JOBLIST", jobList);
			RequestDispatcher requestDispatcher=request.getRequestDispatcher("registration.jsp");
			requestDispatcher.forward(request, response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String phoneNumber = request.getParameter("phonenumber");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		int departmentId = Integer.parseInt(request.getParameter("department"));
		int jobId = Integer.parseInt(request.getParameter("job"));		
		Employee employee = new Employee();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setPhonenumber(phoneNumber);
		employee.setEmail(email);
		employee.setPassword(password);
		Department department = new Department();
		department.setId(departmentId);
		employee.setDepartment(department);
		Job job = new Job();
		job.setId(jobId);
		employee.setJob(job);
		employee.setStatus(1);
		EmployeeDAO employeeDAO = new EmployeeDAO();
		try {
			int registrationResult = employeeDAO.addEmployee(employee);
			if (registrationResult > 0)
				request.setAttribute("message", "Registered succesfully");
			else
				request.setAttribute("message", "Not Registered ");
			doGet(request, response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
