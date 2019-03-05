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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CourseDAO courseDAO = new CourseDAO();
		ArrayList<Courses> courseList;
		try {
			courseList = courseDAO.selectAllCourse();
			request.setAttribute("COURSELIST", courseList);
			request.setAttribute("removepermission", true);
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("removetopic.jsp");
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		try {
			int courseId=Integer.parseInt(request.getParameter("coursename"));
			String topicName=request.getParameter("topicname");
			TopicDAO topicDAO=new TopicDAO();
			Topic topic=new Topic();
			topic.setName(topicName);	
			Courses courses=new Courses();
			courses.setId(courseId);
			topic.setCourse(courses);
			Topic topicDetails=topicDAO.selectTopicsIdByName(topic);
			
			topic.setStatus(0);
			topic.setId(topicDetails.getId());
			int topicRemovedResult=topicDAO.removeTopics(topic);
			if(topicRemovedResult>0)
				request.setAttribute("message", "Topics removed successfully.");
			else
				request.setAttribute("message", "Topics not removed.");
			
			doGet(request,response);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
