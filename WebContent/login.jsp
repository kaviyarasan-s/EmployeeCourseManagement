<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>login</title>


</head>
<body>
	<form method="post" action="LoginServlet">
		Username: <input type="text" name="username"> Password: <input
			type="password" name="password">
		<button type="submit">Login</button>
	</form>
	
		<c:if test="${show}">
			<h4>${message}</h4>
		</c:if>
	
</body>
</html>