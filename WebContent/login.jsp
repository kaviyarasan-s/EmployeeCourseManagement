<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>login page</title>
</head>
<body>
	<form method="post" action="LoginServlet">
		<div>
			User name: <input type="text" name="username" required> *required
		</div>
		<div style="margin-top:1%">
			Password: <input type="password" name="password" required> *required
		</div>
		<div style="margin-top:1%">
			<button type="submit">Login</button>
		</div>
	</form>

	<div>
		<c:if test="${show}">
			<h4>${message}</h4>
		</c:if>
	</div>

</body>
</html>