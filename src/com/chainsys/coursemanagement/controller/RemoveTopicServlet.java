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
 * Servlet implementation class RemoveTopicServlet
 */
@WebServlet("/RemoveTopicServlet")
public class RemoveTopicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RemoveTopicServlet() {
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
						.getRequestDispatcher("removetopic.jsp");
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
		String topicName = request.getParameter("topicname");
		if (courseName.equals("Select")) {
			request.setAttribute("message", "Select course");
			doGet(request, response);
		} else {
			if (topicName.equals("Select")) {
				request.setAttribute("message", "Select topic");
				doGet(request, response);
			} else {

				int courseId = Integer.parseInt(courseName);
				TopicDAO topicDAO = new TopicDAO();
				Topic topic = new Topic();
				topic.setName(topicName);				
				Courses courses = new Courses();
				courses.setId(courseId);
				topic.setCourse(courses);
				topic.setModifiedOn(LocalDateTime.now());
				HttpSession httpSession=request.getSession();
				topic.setModifiedBy((int)httpSession.getAttribute("empid"));
				Topic topicDetails;
				try {
					topicDetails = topicDAO.selectTopicsIdByName(topic);
					topic.setStatus(0);
					topic.setId(topicDetails.getId());
					boolean validationResult = TopicValidation
							.removeTopicValidation(topic);
					if (validationResult) {
						boolean topicRemovedResult=false;
						try {
							topicRemovedResult = topicDAO.removeTopics(topic);
							if (topicRemovedResult)
								request.setAttribute("message",
										"Topics removed successfully.");
							else
								request.setAttribute("message",
										"Topics not removed.");

							doGet(request, response);
						} catch (Exception e) {
							request.setAttribute("message", e.getMessage());

							doGet(request, response);
						}
					} else {
						request.setAttribute("message",
								"Unable to remove topics");

						doGet(request, response);
					}
				} catch (Exception e) {
					request.setAttribute("message", e.getMessage());

					doGet(request, response);
				}

			}
		}
	}
}
