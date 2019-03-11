package com.chainsys.coursemanagement.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chainsys.coursemanagement.dao.EmployeeDAO;
import com.chainsys.coursemanagement.dao.ProjectDAO;
import com.chainsys.coursemanagement.model.Employee;
import com.chainsys.coursemanagement.model.Manager;
import com.chainsys.coursemanagement.model.Project;
import com.chainsys.coursemanagement.validate.Validation;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 *      Login Servlet
	 *      parameters:Username,password
	 *      return :Logged user page with details
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		Employee employee = new Employee();
		employee.setEmail(userName);
		employee.setPassword(password);
		employee.setStatus(1);
		boolean validationResult = Validation.loginValidation(employee);
		
		if (validationResult) {
			EmployeeDAO employeeDAO = new EmployeeDAO();
			Employee employeeDetails = null;
			try {
				
				employeeDetails = employeeDAO
						.selectEmployeeDetailsByEmail(employee);
				
				if (employeeDetails != null) {
					HttpSession httpSession = request.getSession();
					httpSession.setAttribute("empid",employeeDetails.getId());
					if (employeeDetails.getIsAdmin() == 1) {						
						request.setAttribute("ADMINPROFILE", employeeDetails);
						RequestDispatcher requestDispatcher = request
								.getRequestDispatcher("admin.jsp");
						requestDispatcher.forward(request, response);
					} else {
						if (employeeDetails.getIsManager() == 0) {
							Manager manager = new Manager();
							manager.setId(employeeDetails.getManager().getId());
							Manager managerDetails = employeeDAO
									.selectManagerName(manager);
							employeeDetails.setManager(managerDetails);							
							request.setAttribute("EMPLOYEEPROFILE",
									employeeDetails);
							RequestDispatcher requestDispatcher = request
									.getRequestDispatcher("employee.jsp");
							requestDispatcher.forward(request, response);
						} else if (employeeDetails.getIsManager() == 1) {
							ProjectDAO projectDAO = new ProjectDAO();
							Manager manager = new Manager();
							manager.setId(employeeDetails.getId());
							Project projectDetails = projectDAO
									.selectProject(manager);							
							request.setAttribute("MANAGERPROFILE",
									employeeDetails);
							request.setAttribute("PROJECTDETAILS",
									projectDetails);
							RequestDispatcher requestDispatcher = request
									.getRequestDispatcher("manager.jsp");
							requestDispatcher.forward(request, response);
						}
					}
				}
				else
				{
					request.setAttribute("show", true);
					request.setAttribute("message", "Invalid username & password");
					RequestDispatcher requestDispatcher = request
							.getRequestDispatcher("login.jsp");
					requestDispatcher.forward(request, response);
				}
				
			} catch (Exception e) {
				request.setAttribute("show", true);
				request.setAttribute("message", e.getMessage());
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("login.jsp");
				requestDispatcher.forward(request, response);
			}
		} else {
			request.setAttribute("show", true);
			request.setAttribute("message", "Invalid username and password");
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("login.jsp");
			requestDispatcher.forward(request, response);
		}
	}
}
