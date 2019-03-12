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

@WebServlet("/RemoveCourseServlet")
public class RemoveCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * This method is used to load drop down in removecourse.jsp page 
	 * parameters:request,response 
	 * return to removecourse.jsp
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		CourseDAO courseDAO = new CourseDAO();
		try {
			ArrayList<Courses> courseList = courseDAO.selectAllCourse();
			if (courseList != null && !courseList.isEmpty()) {
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
	 * This method is used to remove course 
	 * parameters:request,response 
	 * return to removecourse.jsp
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
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
				try {
					boolean courseRemovedResult = courseDAO.removeCourse(course);
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
				request.setAttribute("message", "Invalid input");
				doGet(request, response);
			}

		}
	}

}
