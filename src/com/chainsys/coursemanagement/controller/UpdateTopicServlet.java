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


@WebServlet("/UpdateTopicServlet")
public class UpdateTopicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * This method is used to load drop down in updatetopic.jsp
	 * parameters:request,response 
	 * return to updatetopic.jsp
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		CourseDAO courseDAO = new CourseDAO();		 
		try {
			ArrayList<Courses> courseList = courseDAO.selectAllCourse();
			if (courseList != null && !courseList.isEmpty()) {
				request.setAttribute("COURSELIST", courseList);
				if (request.getAttribute("message") != null)
					request.setAttribute("message",
							request.getAttribute("message"));
				else
					request.setAttribute("message", null);

				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("updatetopic.jsp");
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
	 * This method is used to update topic
	 * parameters:request,response 
	 * return to updatetopic.jsp
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		TopicDAO topicDAO = new TopicDAO();
		String courseName = request.getParameter("coursename");		
		String topicName = request.getParameter("topicname");
		if (courseName.equals("Select")) {
			request.setAttribute("message", "Select course");
			doGet(request, response);
		} else {
			if (topicName.equals("Select")) {
				request.setAttribute("message", "Select Topic");
				doGet(request, response);
			} else {
				int courseId  = Integer.parseInt(courseName);
				Topic topic = new Topic();
				Courses courses = new Courses();
				courses.setId(courseId);
				topic.setName(topicName);
				topic.setCourse(courses);
				topic.setModifiedOn(LocalDateTime.now());
				HttpSession httpSession=request.getSession();
				topic.setModifiedBy((int)httpSession.getAttribute("empid"));				
				try {
					Topic topicDetails= topicDAO.selectTopicsIdByName(topic);
					String newTopicName = request.getParameter("newtopicname");
					topic.setId(topicDetails.getId());
					topic.setName(newTopicName);
					boolean validationResult = TopicValidation
							.updateTopicValidation(topic);
					if (validationResult) {						
						try {
							boolean topicUpdatedResult = topicDAO.updateTopics(topic);
							if (topicUpdatedResult)
								request.setAttribute("message",
										"Topic updated successfully");
							else
								request.setAttribute("message",
										"Topic not updated");
							doGet(request, response);
						} catch (Exception e) {
							request.setAttribute("message", e.getMessage());
							doGet(request, response);
						}
					} else {
						request.setAttribute("message",
								"Invalid inputs");
						doGet(request, response);
					}
				} catch (Exception e1) {
					request.setAttribute("message", e1.getMessage());
					doGet(request, response);
				}
			}
		}
	}
}
