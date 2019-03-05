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
import com.chainsys.coursemanagement.dao.StatusDAO;
import com.chainsys.coursemanagement.model.Courses;
import com.chainsys.coursemanagement.model.EmployeeTopic;
import com.chainsys.coursemanagement.model.Status;
import com.chainsys.coursemanagement.model.Topic;

/**
 * Servlet implementation class ViewEmployeeByStatusServlet
 */
@WebServlet("/ViewEmployeeByStatusServlet")
public class ViewEmployeeByStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewEmployeeByStatusServlet() {
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
		ArrayList<Courses> courseList;
		try {
			courseList = courseDAO.selectAllCourse();
			request.setAttribute("COURSELIST", courseList);
			ArrayList<Status> statusList = statusDAO.selectAllStatus();
			request.setAttribute("STATUSLIST", statusList);
			
			if (request.getAttribute("searched") != null)
				request.setAttribute("permission", true);
			else
				request.setAttribute("permission", false);
			
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("viewemployeebystatus.jsp");
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

		try {

			int courseId = Integer.parseInt(request.getParameter("coursename"));
			Topic topic = new Topic();
			Courses courses = new Courses();
			courses.setId(courseId);
			topic.setCourse(courses);
			int statusId = Integer.parseInt(request.getParameter("statusname"));

			Status status = new Status();
			status.setId(statusId);
			EmployeeTopic employeeTopic = new EmployeeTopic();
			employeeTopic.setTopic(topic);

			employeeTopic.setStatus(status);
			EmployeeTopicStatusDAO employeeTopicStatusDAO = new EmployeeTopicStatusDAO();

			ArrayList<EmployeeTopic> employeeTopicStatus = employeeTopicStatusDAO
					.selectEmployeeByStatus(employeeTopic);

			request.setAttribute("EMPLOYEESTATUSLIST", employeeTopicStatus);
			request.setAttribute("searched", true);
			doGet(request, response);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
