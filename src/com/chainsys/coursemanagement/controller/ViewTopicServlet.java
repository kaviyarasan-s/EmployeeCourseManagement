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


@WebServlet("/ViewTopicServlet")
public class ViewTopicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * This method is used to load drop down in viewtopic.jsp
	 * parameters:request,response 
	 * return to viewtopic.jsp
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		CourseDAO courseDAO = new CourseDAO();		 
		try {
			ArrayList<Courses> courseList = courseDAO.selectAllCourse();
			if (courseList != null && !courseList.isEmpty()) {
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
	 * This method is used to view topic
	 * parameters:request,response 
	 * return to viewtopic.jsp
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String courseName=request.getParameter("courseId");
		if (!courseName.equals("Select")) {
			int courseId = Integer.parseInt(courseName);
			Courses course = new Courses();
			course.setId(courseId);
			course.setStatus(1);
			TopicDAO topicDAO = new TopicDAO();			 
			try {
				ArrayList<Topic> topicList = topicDAO.selectAllTopicsByCourse(course);
				if (topicList != null && !topicList.isEmpty()) {
					String topicListString = bindTopicList(topicList);
					response.getWriter().write(topicListString);
				} else {
					response.getWriter().write("");
				}
			} catch (Exception e) {
				response.getWriter().write("");
			}
		}
		else
		{
			response.getWriter().write("");
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
