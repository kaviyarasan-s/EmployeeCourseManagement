<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>update topic</title>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
	$(document).ready(function() {
				$("#course").change(
						function() {
							$.post('ViewTopicServlet', {
								courseId : $("#course").val()
							},function(response) {
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
		<div>
			Course List: <select name="coursename" id="course">
				<option value="Select">Select</option>
				<c:forEach var="courseList" items="${COURSELIST}">
					<option value="${courseList.id}">${courseList.name}</option>
				</c:forEach>
			</select>
		</div>
		<div style="margin-top: 1%">
			Topic List: <select name="topicname" id="topic">
				<option value="Select">Select</option>

			</select>
		</div>
		<div style="margin-top: 1%">
			New Topic Name:<input type="text" name="newtopicname">
		</div>

		<div style="margin-top: 1%">
			<button type="submit">Update</button>
		</div>
	</form>
	<div style="margin-top: 1%">
		<%
			if (request.getAttribute("message") != null) {
				out.print(request.getAttribute("message"));
			}
		%>
	</div>
	<div style="margin-top: 1%">
		<a href="topicoperation.html">Back</a>
	</div>


</body>
</html>