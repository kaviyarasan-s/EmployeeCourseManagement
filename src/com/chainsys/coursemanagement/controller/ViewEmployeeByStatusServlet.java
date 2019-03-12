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
import com.chainsys.coursemanagement.dao.EmployeeTopicStatusDAO;
import com.chainsys.coursemanagement.dao.StatusDAO;
import com.chainsys.coursemanagement.model.Courses;
import com.chainsys.coursemanagement.model.EmployeeTopic;
import com.chainsys.coursemanagement.model.Status;
import com.chainsys.coursemanagement.model.Topic;

@WebServlet("/ViewEmployeeByStatusServlet")
public class ViewEmployeeByStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * This method is used to load drop down in viewemployeebystatus.jsp
	 * parameters:request,response 
	 * return to viewemployeebystatus.jsp
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		CourseDAO courseDAO = new CourseDAO();
		StatusDAO statusDAO = new StatusDAO();
		try {
			ArrayList<Courses> courseList = courseDAO.selectAllCourse();
			if (courseList != null) {
				request.setAttribute("COURSELIST", courseList);
				ArrayList<Status> statusList = statusDAO.selectAllStatus();
				request.setAttribute("STATUSLIST", statusList);
				if (statusList != null) {
					if (request.getAttribute("searched") != null)
						request.setAttribute("permission", true);
					else
						request.setAttribute("permission", false);
					RequestDispatcher requestDispatcher = request
							.getRequestDispatcher("viewemployeebystatus.jsp");
					requestDispatcher.forward(request, response);
				} else {
					RequestDispatcher requestDispatcher = request
							.getRequestDispatcher("pagenotfound.html");
					requestDispatcher.forward(request, response);
				}
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
	 * This method is used to view viewemployeebystatus
	 * parameters:request,response 
	 * return to viewemployeebystatus.jsp
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String courseName = request.getParameter("coursename");
		String statusName = request.getParameter("statusname");
		if (!courseName.equals("Select")) {
			int courseId = Integer.parseInt(courseName);
			Topic topic = new Topic();
			Courses courses = new Courses();
			courses.setId(courseId);
			topic.setCourse(courses);
			int statusId = Integer.parseInt(statusName);
			Status status = new Status();
			status.setId(statusId);
			EmployeeTopic employeeTopic = new EmployeeTopic();
			employeeTopic.setTopic(topic);
			employeeTopic.setStatus(status);
			EmployeeTopicStatusDAO employeeTopicStatusDAO = new EmployeeTopicStatusDAO();
			ArrayList<EmployeeTopic> employeeTopicStatus;
			try {
				employeeTopicStatus = employeeTopicStatusDAO
						.selectEmployeeByStatus(employeeTopic);
				if (employeeTopicStatus != null
						&& !employeeTopicStatus.isEmpty()) {
					request.setAttribute("EMPLOYEESTATUSLIST",
							employeeTopicStatus);
					request.setAttribute("searched", true);
					doGet(request, response);
				} else {
					request.setAttribute("searched", null);
					doGet(request, response);
				}
			} catch (Exception e) {
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("pagenotfound.html");
				requestDispatcher.forward(request, response);
			}
		} else {
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("pagenotfound.html");
			requestDispatcher.forward(request, response);
		}
	}
}
