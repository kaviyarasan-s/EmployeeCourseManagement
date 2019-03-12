<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manager profile</title>
</head>
<body>
	<div>
		<table>
			<tr>
				<td><a href="AddStatusServlet">Add status</a></td>

				<td><a href="UpdateStatusServlet">Update status</a></td>

				<td><a href="ViewStatusServlet">View status</a></td>

				<td><a href="LogoutServlet">Logout</a></td>


			</tr>
		</table>
		Profile:
		<table>
			<tr>
				<td>Firstname:</td>
				<td>${MANAGERPROFILE.firstName}</td>
			</tr>
			<tr>
				<td>Lastname:</td>
				<td>${MANAGERPROFILE.lastName}</td>
			</tr>
			<tr>
				<td>Phonenumber:</td>
				<td>${MANAGERPROFILE.phonenumber}</td>
			</tr>
			<tr>
				<td>Email:</td>
				<td>${MANAGERPROFILE.email}</td>
			</tr>
			<tr>
				<td>Password:</td>
				<td>${MANAGERPROFILE.password}</td>
			</tr>
			<tr>
				<td>Department:</td>
				<td>${MANAGERPROFILE.department.name}</td>
			</tr>
			<tr>
				<td>Job Role:</td>
				<td>${MANAGERPROFILE.job.name}</td>
			</tr>
			<c:if test="${PROJECTDETAILS.name !=null }">
				<tr>
					<td>Project name:</td>
					<td>${PROJECTDETAILS.name}</td>
				</tr>
			</c:if>


		</table>

	</div>
</body>
</html>