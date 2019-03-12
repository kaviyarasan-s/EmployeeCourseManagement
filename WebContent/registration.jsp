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
		Firstname:<input type="text" name="firstname" required> <br> <br>
		Lastname:<input type="text" name="lastname" required> <br> <br>
		Phonenumber:<input type="text" name="phonenumber" required maxlength=10> <br> <br>
		Email:<input type="text" name="email" required> <br> <br>
		Password:<input type="text" name="password" required maxlength=8> <br> <br>
		Department:<select name="department" required>
			<br>
			<br>
			<option value="Select">Select</option>
			<c:forEach var="departmentlist" items="${DEPARTMENTLIST}">
				<option value="${departmentlist.id}">${departmentlist.name}</option>
			</c:forEach>
		</select> <br> <br> Job: <select name="job" required>
			<option value="Select">Select</option>
			<c:forEach var="joblist" items="${JOBLIST}">
				<option value="${joblist.id}">${joblist.name}</option>
			</c:forEach>
		</select> <br> <br> Select Manager: <select name="managername" required>
			<option value="Select" >Select</option>
			<c:forEach var="employeeList" items="${MANAGERLIST}">
				<option value="${employeeList.id}">${employeeList.firstName}${employeeList.lastName}</option>
			</c:forEach>
		</select> <br> <br>
		<button type="submit">Register</button>
	</form>
	<div>
		<%if(request.getAttribute("message")!=null){
		out.print(request.getAttribute("message"));
		}%>
	</div>
	<a href="HomeServlet">back</a>
</body>
</html>