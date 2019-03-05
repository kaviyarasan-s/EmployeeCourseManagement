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
import javax.servlet.http.HttpSession;

import com.chainsys.coursemanagement.dao.CourseDAO;
import com.chainsys.coursemanagement.dao.EmployeeTopicStatusDAO;
import com.chainsys.coursemanagement.dao.StatusDAO;
import com.chainsys.coursemanagement.dao.TopicDAO;
import com.chainsys.coursemanagement.model.Courses;
import com.chainsys.coursemanagement.model.Employee;
import com.chainsys.coursemanagement.model.EmployeeTopic;
import com.chainsys.coursemanagement.model.Status;
import com.chainsys.coursemanagement.model.Topic;

/**
 * Servlet implementation class AddStatusServlet
 */
@WebServlet("/AddStatusServlet")
public class AddStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddStatusServlet() {
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
		StatusDAO statusDAO = new StatusDAO();
		try {
			ArrayList<Courses> courseList = courseDAO.selectAllCourse();
			request.setAttribute("COURSELIST", courseList);
			ArrayList<Status> statusList = statusDAO.selectAllStatus();
			request.setAttribute("STATUSLIST", statusList);

			if (request.getAttribute("message") != null)
				request.setAttribute("message", request.getAttribute("message"));
			else
				request.setAttribute("message", null);
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("addstatus.jsp");

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
		if (!request.getParameter("button").equals("status")) {
			String topicName = request.getParameter("topicname");
			int courseId = Integer.parseInt(request.getParameter("coursename"));
			int statusId = Integer.parseInt(request.getParameter("statusname"));
			EmployeeTopic employeeTopic = new EmployeeTopic();

			Topic topic = new Topic();
			topic.setName(topicName);
			Courses course = new Courses();
			course.setId(courseId);
			topic.setCourse(course);
			TopicDAO topicDAO = new TopicDAO();

			try {

				Topic topicDetails = topicDAO.selectTopicsIdByName(topic);
				topic.setId(topicDetails.getId());

				Status status = new Status();
				status.setId(statusId);
				employeeTopic.setStatus(status);
				employeeTopic.setTopic(topic);
				Employee employee = new Employee();
				HttpSession httpSession = request.getSession();
				int empid = (int) httpSession.getAttribute("empid");
				employee.setId(empid);
				employeeTopic.setEmployee(employee);

				EmployeeTopicStatusDAO employeeTopicStatusDAO = new EmployeeTopicStatusDAO();
				int noOfTopicStatusAdded = employeeTopicStatusDAO
						.addEmployeeTopicStatus(employeeTopic);
				if (noOfTopicStatusAdded > 0)
					request.setAttribute("message", "Status added.");
				else
					request.setAttribute("message", "Status not added.");

				doGet(request, response);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			String topicName = request.getParameter("topicname");
			int courseId = Integer.parseInt(request.getParameter("courseId"));
			Topic topic = new Topic();
			topic.setName(topicName);
			Courses courses = new Courses();
			courses.setId(courseId);
			topic.setCourse(courses);

			TopicDAO topicDAO = new TopicDAO();
			Topic topicDetails;
			try {
				topicDetails = topicDAO.selectTopicsIdByName(topic);
				EmployeeTopicStatusDAO employeeTopicStatusDAO = new EmployeeTopicStatusDAO();
				EmployeeTopic employeeTopic = employeeTopicStatusDAO
						.selectStatusIdByTopicId(topicDetails);
				if (employeeTopic != null) {
					if (employeeTopic.getStatus().getId() > 0)
						response.getWriter().write("1");
				} else
					response.getWriter().write("0");

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
