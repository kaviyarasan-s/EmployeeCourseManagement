<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee profile</title>
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
				<td>${EMPLOYEEPROFILE.firstName}</td>
			</tr>
			<tr>
				<td>Lastname:</td>
				<td>${EMPLOYEEPROFILE.lastName}</td>
			</tr>
			<tr>
				<td>Phonenumber:</td>
				<td>${EMPLOYEEPROFILE.phonenumber}</td>
			</tr>
			<tr>
				<td>Email:</td>
				<td>${EMPLOYEEPROFILE.email}</td>
			</tr>
			<tr>
				<td>Password:</td>
				<td>${EMPLOYEEPROFILE.password}</td>
			</tr>
			<tr>
				<td>Manager name:</td>
				<td>${EMPLOYEEPROFILE.manager.name}</td>
			</tr>
		</table>

	</div>

</body>
</html>