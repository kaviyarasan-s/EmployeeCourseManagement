package com.chainsys.coursemanagement.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chainsys.coursemanagement.dao.DepartmentDAO;
import com.chainsys.coursemanagement.dao.EmployeeDAO;
import com.chainsys.coursemanagement.dao.JobDAO;
import com.chainsys.coursemanagement.model.Department;
import com.chainsys.coursemanagement.model.Employee;
import com.chainsys.coursemanagement.model.Job;
import com.chainsys.coursemanagement.model.Manager;
import com.chainsys.coursemanagement.validate.RegisterValidation;


@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * This method is used to load drop down in registration.jsp page 
	 * parameters:request,response 
	 * return to registration.jsp
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		DepartmentDAO departmentDAO = new DepartmentDAO();
		JobDAO jobDAO = new JobDAO();
		EmployeeDAO employeeDAO = new EmployeeDAO();	
		try {
			ArrayList<Department> departmentList = departmentDAO.selectAllDepartment();
			if (departmentList != null) {
				ArrayList<Job> jobList = jobDAO.selectAllJob();
				if (jobList != null) {
					List<Employee> employeeList = employeeDAO.selectEmployee();
					if (employeeList != null) {
						request.setAttribute("DEPARTMENTLIST", departmentList);
						request.setAttribute("JOBLIST", jobList);
						request.setAttribute("MANAGERLIST", employeeList);
						if (request.getAttribute("message") != null)
							request.setAttribute("message",
									request.getAttribute("message"));
						else
							request.setAttribute("message", null);
						RequestDispatcher requestDispatcher = request
								.getRequestDispatcher("registration.jsp");
						requestDispatcher.forward(request, response);
					} else {
						request.setAttribute("message", "Select employee");
						doGet(request, response);
					}
				} else {
					request.setAttribute("message", "Selecr job");
					doGet(request, response);
				}
			} else {
				request.setAttribute("message", "Select department");
				doGet(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("message", e.getMessage());
			doGet(request, response);
		}
	}
	/**
	 * This method is used to register user 
	 * parameters:request,response 
	 * return to registration.jsp
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String phoneNumber = request.getParameter("phonenumber");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String departmentName = request.getParameter("department");
		if (!("Select").equals(departmentName)) {
			int departmentId = Integer.parseInt(departmentName);
			String jobName = request.getParameter("job");
			if (!("Select").equals(jobName)) {
				int jobId = Integer.parseInt(jobName);
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
				employee.setIsAdmin(0);
				if (!request.getParameter("managername").equals("Select")) {
					employee.setIsManager(0);
					Manager manager = new Manager();
					manager.setId(Integer.parseInt(request
							.getParameter("managername")));
					employee.setManager(manager);
				} else {
					employee.setIsManager(1);
				}
				HttpSession httpSession = request.getSession();
				employee.setCreatedOn(LocalDateTime.now());
				employee.setCreatedBy((int) httpSession.getAttribute("empid"));
				employee.setModifiedOn(null);
				employee.setModifiedBy(0);
				boolean registerationValidation = RegisterValidation
						.registerValidation(employee);
				if (registerationValidation) {
					EmployeeDAO employeeDAO = new EmployeeDAO();
					boolean registrationResult = false;
					try {
						if (employee.getIsManager() == 1)
							registrationResult = employeeDAO
									.addEmployeeWithOutManager(employee);
						else
							registrationResult = employeeDAO
									.addEmployeeWithManager(employee);
						if (registrationResult)
							request.setAttribute("message",
									"Registered succesfully");
						else
							request.setAttribute("message", "Not Registered ");
						doGet(request, response);
					} catch (Exception e) {
						request.setAttribute("message", e.getMessage());
						doGet(request, response);
					}
				} else {
					request.setAttribute("message", "Invalid inputs");
					doGet(request, response);
				}
			} else {
				request.setAttribute("message", "Select job");
				doGet(request, response);
			}
		} else {
			request.setAttribute("message", "Select department");
			doGet(request, response);
		}
	}
}
