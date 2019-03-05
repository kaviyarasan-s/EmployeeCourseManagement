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
		// TODO Auto-generated method stub

		CourseDAO courseDAO = new CourseDAO();
		ArrayList<Courses> courseList;
		try {
			courseList = courseDAO.selectAllCourse();
			request.setAttribute("COURSELIST", courseList);
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("viewtopic.jsp");
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
		int courseId = Integer.parseInt(request.getParameter("courseId"));

		Courses course = new Courses();
		course.setId(courseId);

		course.setStatus(1);
		TopicDAO topicDAO = new TopicDAO();
		ArrayList<Topic> topicList = null;
		try {
			topicList = topicDAO.selectAllTopicsByCourse(course);
			String topicListString = bindTopicList(topicList);
			response.getWriter().write(topicListString);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
