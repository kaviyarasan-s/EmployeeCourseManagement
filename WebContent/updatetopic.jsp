<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>update topic</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
	$(document).ready(
			function() {

				$("#course").change(
						function() {

							$.post('ViewTopicServlet', {
								courseId : $("#course").val()
							},
									function(response) {

										var list = response.split(',');

										for ( var item in list) {
											$('#topic').append(
													"<option value="+list[item]+">"
															+ list[item]
															+ "</option>");
										}

									});

						});

			});
</script>
</head>
<body>


	<form method="post" action="UpdateTopicServlet">
		Course List: <select name="coursename" id="course">
			<option value="">Select</option>
			<c:forEach var="courseList" items="${COURSELIST}">
				<option value="${courseList.id}">${courseList.name}</option>
			</c:forEach>
		</select> Topic List: <select name="topicname" id="topic">
			<option value="">Select</option>

		</select> New Topic Name:<input type="text" name="newtopicname">

		<button type="submit">Update</button>
	</form>
	<%
		if (request.getAttribute("message") != null) {
			request.getAttribute("message");
		}
	%>
	<a href="topicoperation.html">Back</a>


</body>
</html>