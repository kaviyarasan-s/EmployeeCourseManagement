<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
</head>
<body>
	<form method="post" action="RegistrationServlet">
		Firstname:<input type="text" name="firstname"> <br><br>
		Lastname:<input	type="text" name="lastname"> <br><br>
		Phonenumber:<input type="text" name="phonenumber"> <br><br> 
		Email:<input type="text" name="email"> <br><br>
		 Password:<input type="text" name="password"> <br><br>
		 Department:<select name="department">
			<br><br>
			<option value="">Select</option>
			<c:forEach var="departmentlist" items="${DEPARTMENTLIST}">
				<option value="${departmentlist.id}">${departmentlist.name}</option>
			</c:forEach>
		</select> <br><br> 
		Job: <select name="job">
			<option value="">Select</option>
			<c:forEach var="joblist" items="${JOBLIST}">
				<option value="${joblist.id}">${joblist.name}</option>
			</c:forEach>
		</select>
		<br><br>
		<button type="submit">Register</button>
	</form>
	<a href="adminoperation.html">back</a>
</body>
</html>