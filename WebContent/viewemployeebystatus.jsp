<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EmployeeList by status</title>

</head>
<body>
	<form method="post" action="ViewEmployeeByStatusServlet">
		Course List: <select name="coursename" id="course">
			<option value="Select">Select</option>
			<c:forEach var="courseList" items="${COURSELIST}">
				<option value="${courseList.id}">${courseList.name}</option>
			</c:forEach>
		</select> Status: <select name="statusname" id="status">
			<option value="Select">Select</option>
			<c:forEach var="statusList" items="${STATUSLIST}">
				<option value="${statusList.id}">${statusList.name}</option>
			</c:forEach>
		</select>

		<button type="submit" id="search">Search</button>
	</form>
	
	<c:if test="${permission}">
		<table>
			<tr>
				<td>emp id</td>
				<td>employee name</td>
				<td>topic count</td>
			</tr>
			<c:forEach var="employeestatuslist" items="${EMPLOYEESTATUSLIST}">
				<tr>
					<td>${employeestatuslist.employee.id}</td>

					<td>${employeestatuslist.employee.firstName}</td>

					<td>${employeestatuslist.topiccount}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:if test="${!permission}">
	<div>Status not found!</div>
	</c:if>
	<a href="HomeServlet">back</a>
</body>
</html>