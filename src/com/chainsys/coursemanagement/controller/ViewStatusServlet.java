package com.chainsys.coursemanagement.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chainsys.coursemanagement.dao.CourseDAO;
import com.chainsys.coursemanagement.dao.EmployeeTopicStatusDAO;
import com.chainsys.coursemanagement.model.Courses;
import com.chainsys.coursemanagement.model.Employee;
import com.chainsys.coursemanagement.model.EmployeeTopic;
import com.chainsys.coursemanagement.model.Topic;


@WebServlet("/ViewStatusServlet")
public class ViewStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * This method is used to load drop down in viewstatus.jsp
	 * parameters:request,response 
	 * return to viewstatus.jsp
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		CourseDAO courseDAO = new CourseDAO();		 
		try {
			ArrayList<Courses> courseList = courseDAO.selectAllCourse();
			if (courseList != null) {
				request.setAttribute("COURSELIST", courseList);
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("viewstatus.jsp");
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
	 * This method is used to view viewstatus
	 * parameters:request,response 
	 * return to viewstatus.jsp
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String courseName = request.getParameter("courseId");		
		if (courseName.equals("Select")) {
			response.getWriter().write("null");
		} else {
			EmployeeTopic employeeTopic = new EmployeeTopic();
			int courseId = Integer.parseInt(courseName);
			Topic topic = new Topic();
			Courses courses = new Courses();
			courses.setId(courseId);
			topic.setCourse(courses);
			Employee employee = new Employee();
			HttpSession httpSession = request.getSession();
			int empid = (int) httpSession.getAttribute("empid");
			employee.setId(empid);
			employeeTopic.setEmployee(employee);
			employeeTopic.setTopic(topic);
			EmployeeTopicStatusDAO employeeTopicStatusDAO = new EmployeeTopicStatusDAO();			 
			try {
				ArrayList<EmployeeTopic> employeeTopicList = employeeTopicStatusDAO
						.selectTopicsStatusList(employeeTopic);
				String topicStatusList = null;
				if (employeeTopicList != null && !employeeTopicList.isEmpty()) {
					for (EmployeeTopic employeeTopicStatus : employeeTopicList) {
						topicStatusList = topicStatusList + ","
								+ employeeTopicStatus.getTopic().getName()
								+ ","
								+ employeeTopicStatus.getStatus().getName();
					}
					response.getWriter().write(topicStatusList);
				} else {					
					response.getWriter().write("null");
				}
			} catch (Exception e) {				
				response.getWriter().write("null");
			}
		}
	}
}
