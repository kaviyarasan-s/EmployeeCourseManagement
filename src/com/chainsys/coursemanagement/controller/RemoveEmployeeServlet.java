package com.chainsys.coursemanagement.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chainsys.coursemanagement.dao.EmployeeDAO;
import com.chainsys.coursemanagement.model.Employee;

/**
 * Servlet implementation class RemoveEmployeeServlet
 */
@WebServlet("/RemoveEmployeeServlet")
public class RemoveEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RemoveEmployeeServlet() {
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

		String employeeId = request.getParameter("empid");
		if (employeeId.isEmpty() || employeeId.equals(null)) {
			request.setAttribute("message", "EmployeeId empty!");
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("removeemployee.jsp");
			requestDispatcher.forward(request, response);
		} else {
			Employee employee = new Employee();
			employee.setId(Integer.parseInt(employeeId));
			employee.setStatus(0);
			EmployeeDAO employeeDAO = new EmployeeDAO();
			boolean removedResult=false;
			try {
				removedResult = employeeDAO.removeEmployee(employee);

				if (removedResult)
					request.setAttribute("message", "Removed successfully.");
				else
					request.setAttribute("message", "Employee not removed.");
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("removeemployee.jsp");
				requestDispatcher.forward(request, response);
			} catch (Exception e) {
				request.setAttribute("message", e.getMessage());
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("removeemployee.jsp");
				requestDispatcher.forward(request, response);
			}

		}

	}

}
