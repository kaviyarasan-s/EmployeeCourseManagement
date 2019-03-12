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
import com.chainsys.coursemanagement.dao.ProjectDAO;
import com.chainsys.coursemanagement.model.Department;
import com.chainsys.coursemanagement.model.Manager;
import com.chainsys.coursemanagement.model.Project;
import com.chainsys.coursemanagement.validate.ProjectValidation;


@WebServlet("/AddProjectServlet")
public class AddProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * This method is used to load drop down in addproject.jsp
	 * parameters:request,response
	 * return: redirect to addproject.jsp
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		EmployeeDAO employeeDAO = new EmployeeDAO(); 
		try {
			List<Manager> managerList = employeeDAO.selectNonProjectManagersList();
			if (managerList != null && !managerList.isEmpty()) {
				request.setAttribute("MANAGERLIST", managerList);
				DepartmentDAO departmentDAO = new DepartmentDAO();
				ArrayList<Department> departmentList = departmentDAO.selectAllDepartment();
				if (departmentList != null && !departmentList.isEmpty()) {
					request.setAttribute("DEPARTMENTLIST", departmentList);
					if (request.getAttribute("message") != null)
						request.setAttribute("message",
								request.getAttribute("message"));
					else
						request.setAttribute("message",
								request.getAttribute("message"));

					RequestDispatcher requestDispatcher = request
							.getRequestDispatcher("addproject.jsp");
					requestDispatcher.forward(request, response);
				} else {
					RequestDispatcher requestDispatcher = request
							.getRequestDispatcher("pagenotfound.html");
					requestDispatcher.forward(request, response);
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
	/**
	 * This method is used to add project
	 * parameters:request,response
	 * return: redirect to addproject.jsp
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String projectName = request.getParameter("projectname");
		if (!projectName.equals(null) || !projectName.isEmpty()) {
			String departmentName = request.getParameter("department");
			if (departmentName.equals("Select")) {
				request.setAttribute("message", "Select department");
				doGet(request, response);
			} else {
				int departmentID = Integer.parseInt(departmentName);
				String managerName = request.getParameter("managername");
				if (!managerName.equals("Select")) {
					int managerID = Integer.parseInt(managerName);
					Project project = new Project();
					Manager manager = new Manager();
					manager.setId(managerID);
					Department department = new Department();
					department.setId(departmentID);
					project.setDepartment(department);
					project.setManager(manager);
					project.setName(projectName);
					project.setCreatedOn(LocalDateTime.now());
					HttpSession httpSession = request.getSession();
					project.setCreatedBy((int) httpSession
							.getAttribute("empid"));
					boolean validationResult = ProjectValidation
							.addProjectValidation(project);
					if (validationResult) {
						ProjectDAO projectDAO = new ProjectDAO();
						boolean noOfProjectAdded = false;
						try {
							noOfProjectAdded = projectDAO.addProject(project);
							if (noOfProjectAdded)
								request.setAttribute("message", "Project added");
							else
								request.setAttribute("message",
										"Project not added");
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
					request.setAttribute("message", "Select manager");
					doGet(request, response);
				}
			}
		}
		else {
			request.setAttribute("message", "ProjectName is empty");
			doGet(request, response);
		}
	}
}
