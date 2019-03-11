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
import com.chainsys.coursemanagement.dao.TopicDAO;
import com.chainsys.coursemanagement.model.Courses;
import com.chainsys.coursemanagement.model.Topic;
import com.chainsys.coursemanagement.validate.TopicValidation;

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
		CourseDAO courseDAO = new CourseDAO();
		ArrayList<Courses> courseList = null;
		try {
			courseList = courseDAO.selectAllCourse();
			if (courseList != null) {
				request.setAttribute("COURSELIST", courseList);
				if (request.getAttribute("message") != null)
					request.setAttribute("message",
							request.getAttribute("message"));
				else
					request.setAttribute("message", null);

				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("addtopic.jsp");
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String courseName = request.getParameter("coursename");
		int courseId = 0;
		if (!courseName.equals("Select")) {
			courseId = Integer.parseInt(courseName);
			String topicName = request.getParameter("topicname");
			Topic topic = new Topic();
			Courses courses = new Courses();
			courses.setId(courseId);
			topic.setCourse(courses);
			topic.setName(topicName);
			topic.setStatus(1);
			topic.setCreatedOn(LocalDateTime.now());
			HttpSession httpSession=request.getSession();
			topic.setCreatedBy((int)httpSession.getAttribute("empid"));
			boolean validationResult = TopicValidation
					.addTopicValidation(topic);
			if (validationResult) {
				TopicDAO topicDAO = new TopicDAO();
				boolean topicAddedResult = false;
				try {
					topicAddedResult = topicDAO.addTopics(topic);
					if (topicAddedResult)
						request.setAttribute("message", "Topic Added");
					else
						request.setAttribute("message", "Topic not added");
					doGet(request, response);
				} catch (Exception e) {
					request.setAttribute("message", e.getMessage());
					doGet(request, response);
				}
			} else {
				request.setAttribute("message", "Unable to addtopics");
				doGet(request, response);
			}
		} else {
			request.setAttribute("message", "Select course");
			doGet(request, response);
		}

	}

}
