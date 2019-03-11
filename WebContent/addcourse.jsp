<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add course</title>
</head>
<body>
	<form method="post" action="AddCourseServlet">
		<div>
			Course Name:<input type="text" name="coursename" required>
		</div>
		<div>
			<button type="submit">Add</button>
		</div>
	</form>
	<div>
		<%
			if (request.getAttribute("message") != null) {
				out.print(request.getAttribute("message"));
			}
		%>
	</div>
	<div>
		<a href="courseoperation.html">back</a>
	</div>
</body>
</html>