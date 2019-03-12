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


@WebServlet("/RemoveEmployeeServlet")
public class RemoveEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {		
	}
	/**
	 * This method is used to remove employee 
	 * parameters:request,response 
	 * return to removeemployee.jsp
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
			try {
				boolean removedResult = employeeDAO.removeEmployee(employee);
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
