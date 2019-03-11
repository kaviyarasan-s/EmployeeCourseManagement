<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Course</title>
</head>
<body>
	<form method="post" action="UpdateCourseServlet">
		<div>
			Select Old Course Name: <select name="oldcoursename">
				<option value="Select">Select</option>
				<c:forEach var="courseList" items="${COURSELIST}">
					<option value="${courseList.id}">${courseList.name}</option>
				</c:forEach>
			</select>
		</div>
		<div style="margin-top: 1%">
			Update Course Name:<input type="text" name="newcourseName">
		</div>
		<div style="margin-top: 1%">
			<button type="submit">Update</button>
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