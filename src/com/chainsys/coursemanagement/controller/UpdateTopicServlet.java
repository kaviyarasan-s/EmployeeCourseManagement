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
 * Servlet implementation class UpdateTopicServlet
 */
@WebServlet("/UpdateTopicServlet")
public class UpdateTopicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateTopicServlet() {
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
			
			if (request.getAttribute("message") != null)
				request.setAttribute("message", request.getAttribute("message"));
			else
				request.setAttribute("message", null);

			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("updatetopic.jsp");
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
		TopicDAO topicDAO=new TopicDAO();
		
		try {
			int courseId = Integer.parseInt(request.getParameter("coursename"));
			String topicName = request.getParameter("topicname");
			
			Topic topic=new Topic();
			Courses courses=new Courses();
			courses.setId(courseId);
			topic.setName(topicName);
			topic.setCourse(courses);
			Topic topicDetails=topicDAO.selectTopicsIdByName(topic);
			String newTopicName = request.getParameter("newtopicname");
			
			topic.setId(topicDetails.getId());
			topic.setName(newTopicName);
			int topicUpdatedResult=topicDAO.updateTopics(topic);
			if(topicUpdatedResult>0)
				request.setAttribute("message", "Topic updated successfully");
			else
				request.setAttribute("message", "Topic not updated");
			
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
