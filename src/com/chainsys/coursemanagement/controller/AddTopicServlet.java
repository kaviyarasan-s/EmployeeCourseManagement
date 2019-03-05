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
import com.chainsys.coursemanagement.dao.TopicDAO;
import com.chainsys.coursemanagement.model.Courses;
import com.chainsys.coursemanagement.model.Topic;

/**
 * Servlet implementation class AddTopicServlet
 */
@WebServlet("/AddTopicServlet")
public class AddTopicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddTopicServlet() {
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
		ArrayList<Courses> courseList;
		try {
			courseList = courseDAO.selectAllCourse();
			request.setAttribute("COURSELIST", courseList);
			if (request.getAttribute("message") != null)
				request.setAttribute("message", request.getAttribute("message"));
			else
				request.setAttribute("message", null);

			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("addtopic.jsp");
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

		int courseId = Integer.parseInt(request.getParameter("coursename"));
		String topicName = request.getParameter("topicname");

		if (topicName == null || topicName.isEmpty() || courseId == 0) {

			request.setAttribute("message", "Topic name is empty");
			doGet(request, response);
		} else {
			Topic topic = new Topic();

			Courses courses = new Courses();
			courses.setId(courseId);
			topic.setCourse(courses);
			topic.setName(topicName);
			topic.setStatus(1);
			TopicDAO topicDAO = new TopicDAO();
			try {
				int topicAddedResult = topicDAO.addTopics(topic);
				if (topicAddedResult > 0)
					request.setAttribute("message", "Topic Added");
				else
					request.setAttribute("message", "Topic not added");
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

}
