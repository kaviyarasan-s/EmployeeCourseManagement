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

/**
 * Servlet implementation class AddStatusServlet
 */
@WebServlet("/AddStatusServlet")
public class AddStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddStatusServlet() {
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
		StatusDAO statusDAO = new StatusDAO();

		ArrayList<Courses> courseList = null;
		ArrayList<Status> statusList = null;
		try {
			courseList = courseDAO.selectAllCourse();
			if (courseList != null) {
				request.setAttribute("COURSELIST", courseList);
				statusList = statusDAO.selectAllStatus();
				if (statusList != null) {
					request.setAttribute("STATUSLIST", statusList);
					if (request.getAttribute("message") != null)
						request.setAttribute("message",
								request.getAttribute("message"));
					else
						request.setAttribute("message", null);
					RequestDispatcher requestDispatcher = request
							.getRequestDispatcher("addstatus.jsp");
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
		// TODO Auto-generated method stub
		String courseName = request.getParameter("coursename");
		String statusName = request.getParameter("statusname");
		String topicName = request.getParameter("topicname");
		if (courseName.equals("Select")) {
			request.setAttribute("message", "Select course");
			doGet(request, response);

		} else {
			if (topicName.equals("Select")) {
				request.setAttribute("message", "Select topic");
				doGet(request, response);
			} else {
				if (statusName.equals("Select")) {
					request.setAttribute("message", "Select status");
					doGet(request, response);
				} else {
					int courseId = Integer.parseInt(courseName);
					Topic topic = new Topic();
					topic.setName(topicName);
					Courses course = new Courses();
					course.setId(courseId);
					topic.setCourse(course);
					TopicDAO topicDAO = new TopicDAO();
					Topic topicDetails = null;
					try {
						topicDetails = topicDAO.selectTopicsIdByName(topic);
						if (request.getParameter("button").equals("addstatus")) {
							int statusId = Integer.parseInt(statusName);
							EmployeeTopic employeeTopic = new EmployeeTopic();
							topic.setId(topicDetails.getId());
							Status status = new Status();
							status.setId(statusId);
							employeeTopic.setStatus(status);
							employeeTopic.setTopic(topic);
							Employee employee = new Employee();
							HttpSession httpSession = request.getSession();
							int empid = (int) httpSession.getAttribute("empid");
							employee.setId(empid);
							employeeTopic.setEmployee(employee);
							employeeTopic.setCreatedOn(LocalDateTime.now());
							employeeTopic.setCreatedBy(empid);
							
							boolean statusValidation = StatusValidation
									.addStatusValidation(employeeTopic);
							if (statusValidation) {
								EmployeeTopicStatusDAO employeeTopicStatusDAO = new EmployeeTopicStatusDAO();
								boolean noOfTopicStatusAdded = employeeTopicStatusDAO
										.addEmployeeTopicStatus(employeeTopic);
								if (noOfTopicStatusAdded)
									request.setAttribute("message",
											"Status added.");
								else
									request.setAttribute("message",
											"Status not added.");

								doGet(request, response);
							} else {
								request.setAttribute("message",
										"Unable to add Status");

								doGet(request, response);
							}

						} else if (request.getParameter("button").equals(
								"checkstatusexist")) {

							EmployeeTopicStatusDAO employeeTopicStatusDAO = new EmployeeTopicStatusDAO();
							EmployeeTopic employeeTopic = employeeTopicStatusDAO
									.selectStatusIdByTopicId(topicDetails);
							if (employeeTopic != null) {
								if (employeeTopic.getStatus().getId() > 0)
									response.getWriter().write("1");
								else
									response.getWriter().write("0");
							} else
								response.getWriter().write("0");

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
