package com.chainsys.coursemanagement.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chainsys.coursemanagement.dao.CourseDAO;
import com.chainsys.coursemanagement.model.Courses;

/**
 * Servlet implementation class AddCourseServlet
 */
@WebServlet("/AddCourseServlet")
public class AddCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddCourseServlet() {
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

		String courseName = request.getParameter("coursename");
		if (courseName == null || courseName.isEmpty()) {
			request.setAttribute("message", "coursename is empty");
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("addcourse.jsp");
			requestDispatcher.forward(request, response);
		} else {
			Courses course = new Courses();
			course.setName(courseName);
			course.setStatus(1);
			CourseDAO courseDAO = new CourseDAO();
			try {
				int addCourseResult = courseDAO.addCourse(course);
				if (addCourseResult > 0) {
					request.setAttribute("message",
							"Course added successfully.");

				} else {
					request.setAttribute("message", "Course not added.");
				}
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("addcourse.jsp");
				requestDispatcher.forward(request, response);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
