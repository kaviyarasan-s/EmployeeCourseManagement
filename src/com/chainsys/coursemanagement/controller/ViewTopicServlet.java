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
import com.chainsys.coursemanagement.dao.TopicDAO;
import com.chainsys.coursemanagement.model.Courses;
import com.chainsys.coursemanagement.model.Topic;

/**
 * Servlet implementation class ViewTopicServlet
 */
@WebServlet("/ViewTopicServlet")
public class ViewTopicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewTopicServlet() {
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
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("viewtopic.jsp");
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

		int courseId = Integer.parseInt(request.getParameter("courseId"));
		Courses course = new Courses();
		course.setId(courseId);
		course.setStatus(1);
		TopicDAO topicDAO = new TopicDAO();
		ArrayList<Topic> topicList = null;
		try {
			topicList = topicDAO.selectAllTopicsByCourse(course);
			
			if (topicList != null) {
				String topicListString = bindTopicList(topicList);
				response.getWriter().write(topicListString);
			}
		} catch (Exception e) {
			response.getWriter().write("Unable to find topics");
		}

	}

	public String bindTopicList(ArrayList<Topic> topicsList) {
		String topic = "";
		int arraySize = topicsList.size();
		for (int i = 0; i < arraySize; i++) {
			topic = topic + topicsList.get(i).getName();
			if (i != arraySize - 1)
				topic = topic + ",";
		}
		return topic;
	}

}
