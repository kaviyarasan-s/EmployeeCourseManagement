<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin profile</title>
</head>
<body>
	<div>
		<table>
			<tr>
				<td><a href="courseoperation.html">Course operation</a></td>

				<td><a href="topicoperation.html">Topic operation</a></td>

				<td><a href="ViewEmployeeStatusServlet">view employee
						status</a></td>

				<td><a href="ViewEmployeeByStatusServlet">view employee by
						status</a></td>

				<td><a href="RegistrationServlet">Register</a></td>
				
				<td><a href="AddProjectServlet">Add project</a></td>

				<td><a href="LogoutServlet">Logout</a></td>
			</tr>
		</table>
	</div>
	<div>
	Profile:
		<table>
			<tr>
				<td>Firstname: </td>
				<td>${ADMINPROFILE.firstName}</td>
			</tr>
			<tr>
				<td>Lastname: </td>
				<td>${ADMINPROFILE.lastName}</td>
			</tr>
			<tr>
				<td>Phonenumber: </td>
				<td>${ADMINPROFILE.phonenumber}</td>
			</tr>
			<tr>
				<td>Email: </td>
				<td>${ADMINPROFILE.email}</td>
			</tr>
			<tr>
				<td>Password: </td>
				<td>${ADMINPROFILE.password}</td>
			</tr>
		</table>
	</div>
</body>
</html>