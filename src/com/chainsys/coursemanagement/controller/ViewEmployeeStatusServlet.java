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
import com.chainsys.coursemanagement.model.Courses;
import com.chainsys.coursemanagement.model.Employee;
import com.chainsys.coursemanagement.model.EmployeeTopic;
import com.chainsys.coursemanagement.model.Topic;

/**
 * Servlet implementation class ViewEmployeeStatusServlet
 */
@WebServlet("/ViewEmployeeStatusServlet")
public class ViewEmployeeStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewEmployeeStatusServlet() {
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
			request.setAttribute("COURSELIST", courseList);
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("viewemployeestatus.jsp");
			requestDispatcher.forward(request, response);
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

		String courseName = request.getParameter("courseId");
		if (courseName.equals("Select")) {
			response.getWriter().write("");
		} else {
			String employeeName = request.getParameter("employeeId");
			if (!employeeName.equals(null) || !employeeName.isEmpty()) {
				int employeeId = Integer.parseInt(employeeName);
				int courseId = Integer.parseInt(courseName);
				Topic topic = new Topic();
				Courses courses = new Courses();
				courses.setId(courseId);
				topic.setCourse(courses);
				EmployeeTopic employeeTopic = new EmployeeTopic();
				Employee employee = new Employee();
				employee.setId(employeeId);
				employeeTopic.setEmployee(employee);
				employeeTopic.setTopic(topic);

				EmployeeTopicStatusDAO employeeTopicStatusDAO = new EmployeeTopicStatusDAO();

				ArrayList<EmployeeTopic> employeeTopicList = null;
				try {
					employeeTopicList = employeeTopicStatusDAO
							.selectTopicsStatusList(employeeTopic);
					String topicStatusList = null;
					if (employeeTopicList != null&& !employeeTopicList.isEmpty()) {

						for (EmployeeTopic employeeTopicStatus : employeeTopicList) {

							topicStatusList = topicStatusList + ","
									+ employeeTopicStatus.getTopic().getName()
									+ ","
									+ employeeTopicStatus.getStatus().getName();
						}
						response.getWriter().write(topicStatusList);
					} else {
						response.getWriter().write("");
					}

				} catch (Exception e) {
					response.getWriter().write("");
				}
			} else {
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("pagenotfound.html");
				requestDispatcher.forward(request, response);
			}
		}
	}
}
