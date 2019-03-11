<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View course</title>
</head>
<body>


	Course List:
	<table>
			<tr>
				<td>S.no</td>
				<td>Name</td>
			</tr>
			<%
				int i = 0;
			%>
			<c:forEach var="courseList" items="${COURSELIST}">
				<tr>

					<td>
						<%
							i = i + 1;
									out.print(i);
						%>
					</td>
					<td>${courseList.name}</td>
				</tr>
			</c:forEach>

		</table>
	
	<a href="courseoperation.html">back</a>
</body>
</html>