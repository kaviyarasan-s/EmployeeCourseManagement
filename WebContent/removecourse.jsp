<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Remove course</title>
</head>
<body>



	<form method="post" action="RemoveCourseServlet">
		<select name="coursename">
			<option value="">Select</option>
			<c:forEach var="courseList" items="${COURSELIST}">
				<option value="${courseList.id}">${courseList.name}</option>
			</c:forEach>
		</select>
		<button type="submit">Remove</button>
	</form>

	<%
		if (request.getAttribute("message") != null) {
			out.print(request.getAttribute("message"));
		}
	%>
	<a href="courseoperation.html">back</a>
</body>
</html>