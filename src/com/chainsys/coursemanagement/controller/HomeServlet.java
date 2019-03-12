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

@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * This method is used to load user profiles 
	 * parameters:request,response
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Employee employee = new Employee();
		HttpSession httpSession = request.getSession();
		employee.setId((int) httpSession.getAttribute("empid"));
		employee.setStatus(1);
		EmployeeDAO employeeDAO = new EmployeeDAO();
		try {
			Employee employeeDetails = employeeDAO
					.selectEmployeeDetailsById(employee);
			if (employeeDetails != null) {
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
						request.setAttribute("EMPLOYEEPROFILE", employeeDetails);
						RequestDispatcher requestDispatcher = request
								.getRequestDispatcher("employee.jsp");
						requestDispatcher.forward(request, response);
					} else if (employeeDetails.getIsManager() == 1) {
						ProjectDAO projectDAO = new ProjectDAO();
						Manager manager = new Manager();
						manager.setId(employeeDetails.getId());
						Project projectDetails = projectDAO
								.selectProject(manager);
						request.setAttribute("MANAGERPROFILE", employeeDetails);
						request.setAttribute("PROJECTDETAILS", projectDetails);
						RequestDispatcher requestDispatcher = request
								.getRequestDispatcher("manager.jsp");
						requestDispatcher.forward(request, response);
					}
				}
			} else {
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("pagenotfound.html");
				requestDispatcher.forward(request, response);
			}
		} catch (Exception e) {
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("pagenotfound.html");
			requestDispatcher.forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
