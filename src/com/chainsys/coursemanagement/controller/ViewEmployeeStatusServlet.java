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
		// TODO Auto-generated method stub
		CourseDAO courseDAO = new CourseDAO();
		try {
			ArrayList<Courses> courseList = courseDAO.selectAllCourse();

			request.setAttribute("COURSELIST", courseList);
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("viewemployeestatus.jsp");
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
		int employeeId = Integer.parseInt(request.getParameter("employeeId"));
		int courseId = Integer.parseInt(request.getParameter("courseId"));
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
		try {
			ArrayList<EmployeeTopic> employeeTopicList = employeeTopicStatusDAO
					.selectTopicsStatusList(employeeTopic);
			
			String topicStatusList = null;
			for (EmployeeTopic employeeTopicStatus : employeeTopicList) {

				topicStatusList = topicStatusList + ","
						+ employeeTopicStatus.getTopic().getName() + ","
						+ employeeTopicStatus.getStatus().getName();
			}
			response.getWriter().write(topicStatusList);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
