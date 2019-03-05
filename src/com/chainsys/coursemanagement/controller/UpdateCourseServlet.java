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

import com.chainsys.coursemanagement.dao.CourseDAO;
import com.chainsys.coursemanagement.model.Courses;

/**
 * Servlet implementation class UpdateCourseServlet
 */
@WebServlet("/UpdateCourseServlet")
public class UpdateCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateCourseServlet() {
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
		CourseDAO courseDAO = new CourseDAO();
		ArrayList<Courses> courseList;
		try {
			courseList = courseDAO.selectAllCourse();
			request.setAttribute("COURSELIST", courseList);

			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("updatecourse.jsp");
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

		int oldCourseId = Integer.parseInt(request
				.getParameter("oldcoursename"));
		String newCourseName = request.getParameter("newcourseName");
		if (newCourseName == null || newCourseName.isEmpty()) {
			request.setAttribute("message", "coursename is empty");
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("updatecourse.jsp");
			requestDispatcher.forward(request, response);
		} else {
			Courses course = new Courses();
			course.setId(oldCourseId);
			course.setName(newCourseName);

			CourseDAO courseDAO = new CourseDAO();
			try {
				int updateResult = courseDAO.updateCourse(course);

				if (updateResult > 0)
					request.setAttribute("message", "updated successfully");
				else
					request.setAttribute("message", "updation failed");
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("updatecourse.jsp");
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
