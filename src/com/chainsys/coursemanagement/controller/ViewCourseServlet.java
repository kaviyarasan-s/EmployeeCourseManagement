package com.chainsys.coursemanagement.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chainsys.coursemanagement.dao.CourseDAO;
import com.chainsys.coursemanagement.model.Courses;


@WebServlet("/ViewCourseServlet")
public class ViewCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * This method is used to load drop down in viewcourse.jsp
	 * parameters:request,response 
	 * return to viewcourse.jsp
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		CourseDAO courseDAO = new CourseDAO();	
		try {
			ArrayList<Courses> courseList = courseDAO.selectAllCourse();
			if (courseList != null&&!courseList.isEmpty()) {
				request.setAttribute("COURSELIST", courseList);
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("viewcourse.jsp");
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
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {	

	}

}
