<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Status</title>
<script
	src="script\jquery.min.js"></script>

<script>
	$(document).ready(function() {

						$("#course")
								.change(
										function() {
											$('#statuslist').empty();
											$('#message').empty();
											$.post('ViewStatusServlet',
															{
																courseId : $(
																		"#course")
																		.val()
															},
															function(response) {
																console.log(response);
																if (response == ''
																		|| response == null||response=="null") {
																	$('#message')
																			.text(
																					"status not found!");
																} else {
																	var list = response
																	.split(',');
															
																	$('#statuslist')
																			.append(
																					"<tr><td>Sno</td><td>Topic name</td><td>Status</td></tr>");
																	var sno = 0;
																	for (var i = 1; i < list.length; i = i + 2) {

																		sno = sno + 1;
																		$('#statuslist')
																				.append(
																						"<tr>"
																								+ "<td>"
																								+ sno
																								+ "</td>"
																								+ "<td>"
																								+ list[i]
																								+ "</td>"
																								+ "<td>"
																								+ list[i + 1]
																								+ "</td></tr>");

																	}
																}

															});

										});

					});
</script>
</head>
<body>
	<form>
		<select name="coursename" id="course">
			<option value="Select">Select</option>
			<c:forEach var="courseList" items="${COURSELIST}">
				<option value="${courseList.id}">${courseList.name}</option>
			</c:forEach>
		</select>
		<table id="statuslist"></table>
	</form>
	<div id="message"></div>

	<%
		if (request.getAttribute("message") != null) {
			out.print(request.getAttribute("message"));

		}
	%>

	<td><a href="HomeServlet">back</a></td>
</body>
</html>