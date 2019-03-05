package com.chainsys.coursemanagement.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chainsys.coursemanagement.dao.EmployeeDAO;
import com.chainsys.coursemanagement.model.Employee;

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
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String userName = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		Employee employee = new Employee();
		employee.setEmail(userName);
		employee.setPassword(password);
		employee.setStatus(1);
		EmployeeDAO employeeDAO = new EmployeeDAO();
		try {
			Employee employeeDetails = employeeDAO
					.selectAllEmployeeDetails(employee);
			
			if (employeeDetails != null) {
				if (employeeDetails.getEmail().equals("admin@gmail.com")) {
					HttpSession httpSession = request.getSession();
					httpSession.setAttribute("empid", employeeDetails.getId());
					System.out.println(httpSession.getAttribute("empid"));
					RequestDispatcher requestDispatcher = request
							.getRequestDispatcher("adminoperation.html");
					requestDispatcher.forward(request, response);
				} else {
					HttpSession httpSession = request.getSession();
					httpSession.setAttribute("empid", employeeDetails.getId());
					System.out.println(httpSession.getAttribute("empid"));
					RequestDispatcher requestDispatcher = request
							.getRequestDispatcher("employeeoperation.html");
					requestDispatcher.forward(request, response);
				}
			} else {
				request.setAttribute("show", true);
				request.setAttribute("message", "Invalid username and password");
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("login.jsp");
				requestDispatcher.forward(request, response);
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
