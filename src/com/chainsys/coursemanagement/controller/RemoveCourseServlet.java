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
 * Servlet implementation class RemoveCourseServlet
 */
@WebServlet("/RemoveCourseServlet")
public class RemoveCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RemoveCourseServlet() {
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
			if (courseList != null) {
				request.setAttribute("COURSELIST", courseList);
				if (request.getAttribute("message") != null)
					request.setAttribute("message",
							request.getAttribute("message"));
				else
					request.setAttribute("message", null);

				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("removecourse.jsp");
				requestDispatcher.forward(request, response);
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String courseName = request.getParameter("coursename");
		if (courseName.equals("Select")) {
			request.setAttribute("message", "Select course");
			doGet(request, response);
		} else {

			int courseId = Integer.parseInt(courseName);
			Courses course = new Courses();
			course.setId(courseId);
			course.setStatus(0);
			course.setModifiedOn(LocalDateTime.now());
			HttpSession httpSession=request.getSession();
			course.setModifiedBy((int)httpSession.getAttribute("empid"));
			boolean validationResult = CourseValidation
					.removeCoursesValidation(course);
			if (validationResult) {
				CourseDAO courseDAO = new CourseDAO();
				boolean courseRemovedResult = false;
				try {
					courseRemovedResult = courseDAO.removeCourse(course);
					if (courseRemovedResult)
						request.setAttribute("message",
								"Course removed successfully.");
					else
						request.setAttribute("message", "Course not removed");
					doGet(request, response);
				} catch (Exception e) {
					request.setAttribute("message", e.getMessage());
					doGet(request, response);
				}
			} else {
				request.setAttribute("message", "Unable to remove");
				doGet(request, response);
			}

		}
	}

}
