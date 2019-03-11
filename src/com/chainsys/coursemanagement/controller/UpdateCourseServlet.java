package com.chainsys.coursemanagement.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chainsys.coursemanagement.dao.CourseDAO;
import com.chainsys.coursemanagement.model.Courses;
import com.chainsys.coursemanagement.validate.CourseValidation;

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
		CourseDAO courseDAO = new CourseDAO();
		ArrayList<Courses> courseList = null;

		try {
			courseList = courseDAO.selectAllCourse();
			if (courseList != null && !courseList.isEmpty()) {
				request.setAttribute("COURSELIST", courseList);
				if (request.getAttribute("message") != null)
					request.setAttribute("message",
							request.getAttribute("message"));
				else
					request.setAttribute("message", null);

				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("updatecourse.jsp");
				requestDispatcher.forward(request, response);
			}
			else
			{
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String oldCourseName = request.getParameter("oldcoursename");
		int oldCourseId = 0;
		if (!oldCourseName.equals("Select")) {
			oldCourseId = Integer.parseInt(oldCourseName);
			String newCourseName = request.getParameter("newcourseName");
			Courses course = new Courses();
			course.setId(oldCourseId);
			course.setName(newCourseName);
			course.setModifiedOn(LocalDateTime.now());
			HttpSession httpSession=request.getSession();
			course.setModifiedBy((int)httpSession.getAttribute("empid"));
			boolean validationResult = CourseValidation
					.updateCoursesValidation(course);
			if (validationResult) {

				CourseDAO courseDAO = new CourseDAO();

				boolean updateResult = false;
				try {
					updateResult = courseDAO.updateCourse(course);

					if (updateResult)
						request.setAttribute("message", "updated successfully");
					else
						request.setAttribute("message", "updation failed");
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
			request.setAttribute("message", "Select course");
			doGet(request, response);
		}

	}

}
