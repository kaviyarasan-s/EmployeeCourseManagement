package com.chainsys.coursemanagement.controller;

import java.io.IOException;
import java.time.LocalDateTime;

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

@WebServlet("/AddCourseServlet")
public class AddCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}
	/**
	 * This method is used to insert course parameters:request,response return:
	 * redirect to addcourse.jsp
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String courseName = request.getParameter("coursename");
		Courses course = new Courses();
		course.setName(courseName);
		course.setStatus(1);
		course.setCreatedOn(LocalDateTime.now());
		HttpSession httpSession = request.getSession();
		if (request.getSession() != null) {
			course.setCreatedBy((int) httpSession.getAttribute("empid"));
		}
		boolean validationResult = CourseValidation
				.addCoursesValidation(course);
		if (!validationResult) {
			request.setAttribute("message", "Invalid inputs");
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("addcourse.jsp");
			requestDispatcher.forward(request, response);
		} else {
			CourseDAO courseDAO = new CourseDAO();
			try {
				boolean addCourseResult = courseDAO.addCourse(course);
				if (addCourseResult) {
					request.setAttribute("message",
							"Course added successfully.");
				} else {
					request.setAttribute("message", "Course not added.");
				}
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("addcourse.jsp");
				requestDispatcher.forward(request, response);
			} catch (Exception e) {
				request.setAttribute("message", e.getMessage());
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("addcourse.jsp");
				requestDispatcher.forward(request, response);
			}
		}
	}
}
