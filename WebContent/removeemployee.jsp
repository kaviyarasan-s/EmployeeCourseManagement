<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Remove employee</title>
</head>
<body>

	<form method="post" action="RemoveEmployeeServlet">
		Employee id:<input type="text" name="empid">
		<button type="submit">Remove</button>
	</form>
	<%
		if (request.getAttribute("message") != null) {

			out.print(request.getAttribute("message"));
		}
	%>

</body>
</html>