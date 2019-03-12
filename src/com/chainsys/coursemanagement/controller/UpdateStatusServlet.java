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
import com.chainsys.coursemanagement.dao.EmployeeTopicStatusDAO;
import com.chainsys.coursemanagement.dao.StatusDAO;
import com.chainsys.coursemanagement.dao.TopicDAO;
import com.chainsys.coursemanagement.model.Courses;
import com.chainsys.coursemanagement.model.Employee;
import com.chainsys.coursemanagement.model.EmployeeTopic;
import com.chainsys.coursemanagement.model.Status;
import com.chainsys.coursemanagement.model.Topic;
import com.chainsys.coursemanagement.validate.StatusValidation;

@WebServlet("/UpdateStatusServlet")
public class UpdateStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * This method is used to load drop down in updatestatus.jsp
	 * parameters:request,response 
	 * return to updatestatus.jsp
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		CourseDAO courseDAO = new CourseDAO();
		StatusDAO statusDAO = new StatusDAO();
		try {
			ArrayList<Courses> courseList = courseDAO.selectAllCourse();
			if (courseList != null && !courseList.isEmpty()) {
				request.setAttribute("COURSELIST", courseList);
				ArrayList<Status> statusList = statusDAO.selectAllStatus();
				if (statusList != null && !statusList.isEmpty()) {
					request.setAttribute("STATUSLIST", statusList);
					if (request.getAttribute("message") != null)
						request.setAttribute("message",
								request.getAttribute("message"));
					else
						request.setAttribute("message", null);
					RequestDispatcher requestDispatcher = request
							.getRequestDispatcher("updatestatus.jsp");
					requestDispatcher.forward(request, response);
				} else {
					RequestDispatcher requestDispatcher = request
							.getRequestDispatcher("pagenotfound.html");
					requestDispatcher.forward(request, response);
				}
			} else {
				RequestDispatcher requestDispatcher = request
						.getRequestDispatcher("pagenotfound.html");
				requestDispatcher.forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("message", e.getMessage());
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("updatestatus.jsp");
			requestDispatcher.forward(request, response);
		}
	}
	/**
	 * This method is used to update status 
	 * parameters:request,response 
	 * return to updatestatus.jsp
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
				request.setAttribute("message", "Select topics");
				doGet(request, response);
			} else {
				int courseId = Integer.parseInt(courseName);
				Topic topic = new Topic();
				topic.setName(topicName);
				Courses courses = new Courses();
				courses.setId(courseId);
				topic.setCourse(courses);
				if (request.getParameter("button").equals("getoldstatus")) {
					TopicDAO topicDAO = new TopicDAO();
					try {
						Topic topicDetails = topicDAO
								.selectTopicsIdByName(topic);
						if (topicDetails != null) {
							EmployeeTopicStatusDAO employeeTopicStatusDAO = new EmployeeTopicStatusDAO();
							EmployeeTopic employeeTopic = employeeTopicStatusDAO
									.selectStatusIdByTopicId(topicDetails);
							if (employeeTopic != null) {
								response.getWriter().write(
										String.valueOf(employeeTopic
												.getStatus().getId()));
							} else {
								response.getWriter().write("");// error
							}
						} else {
							response.getWriter().write("");// error
						}
					} catch (Exception e) {
						response.getWriter().write("");// error
					}
				} else if (request.getParameter("button")
						.equals("updatestatus")) {
					if (request.getParameter("statusname").equals("Select")) {
						request.setAttribute("message", "Select status");
						doGet(request, response);
					} else {
						int statusId = 0;
						statusId = Integer.parseInt(request
								.getParameter("statusname"));
						TopicDAO topicDAO = new TopicDAO();
						Topic topicDetails = null;
						try {
							topicDetails = topicDAO.selectTopicsIdByName(topic);
							topic.setId(topicDetails.getId());
							Employee employee = new Employee();
							HttpSession httpSession = request.getSession();
							int empid = (int) httpSession.getAttribute("empid");
							employee.setId(empid);
							Status status = new Status();
							status.setId(statusId);
							EmployeeTopic employeeTopic = new EmployeeTopic();
							employeeTopic.setTopic(topic);
							employeeTopic.setEmployee(employee);
							employeeTopic.setStatus(status);
							employeeTopic.setModifiedOn(LocalDateTime.now());
							employeeTopic.setModifiedBy(empid);
							EmployeeTopicStatusDAO employeeTopicStatusDAO = new EmployeeTopicStatusDAO();
							boolean validationResult = StatusValidation
									.updateStatusValidation(employeeTopic);
							if (validationResult) {
								boolean employeeTopicStatusUpdate = employeeTopicStatusDAO
										.updateEmployeeTopicStatus(employeeTopic);
								if (employeeTopicStatusUpdate)
									request.setAttribute("message",
											"Status updated");
								else
									request.setAttribute("message",
											"Status not updated");
								doGet(request, response);
							} else {
								request.setAttribute("message",
										"Invalid inputs");
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
	}
}