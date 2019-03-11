<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<form method="post" action="AddProjectServlet">
		<div>
			Project name:<input type="text" name="projectname">
		</div>
		<div>
			Department List: <select name="department" id="department">
				<option value="Select">Select</option>
				<c:forEach var="departmentList" items="${DEPARTMENTLIST}">
					<option value="${departmentList.id}">${departmentList.name}</option>
				</c:forEach>
			</select>
		</div>
		<div>
			Manager List: <select name="managername" id="mangername">
				<option value="Select">Select</option>
				<c:forEach var="managerList" items="${MANAGERLIST}">
					<option value="${managerList.id}">${managerList.name}</option>
				</c:forEach>
			</select>
		</div>
		<div>
			<button type="submit">Add</button>
		</div>
	</form>

	<%
		if (request.getAttribute("message") != null) {

		out.print(request.getAttribute("message"));
			}
	%>
	<div>
		<a href="HomeServlet">back</a>
	</div>
</body>
</html>