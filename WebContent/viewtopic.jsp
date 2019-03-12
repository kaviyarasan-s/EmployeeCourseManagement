<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script	src="script/jquery.min.js"></script>


<title>Topics list</title>
<script>
	$(document).ready(
			function() {

				$("#course").change(
						function() {
							$('#topiclist').empty();
							$('#message').empty();
							$.post('ViewTopicServlet', {
								courseId : $("#course").val()
							}, function(response) {

								var list = response.split(',');
								
								var i = 0;
								if (list != '') {
									$('#topiclist').append("Topics list:");
									$('#topiclist').append(
											"<tr><td>" + "S.no" + "</td>"
													+ "<td>" + "Name"
													+ "</td></tr>");
									for ( var item in list) {
										i = i + 1;
										$('#topiclist').append(
												"<tr><td>" + (i) + "</td>"
														+ "<td>" + list[item]
														+ "</td></tr>");

									}
								} else {
									$('#message').text("Topics not found!")
								}

							});

						});

			});
</script>
</head>
<body>


	<form>
		Course List: <select name="coursename" id="course">
			<option value="">Select</option>
			<c:forEach var="courseList" items="${COURSELIST}">
				<option value="${courseList.id}">${courseList.name}</option>
			</c:forEach>
		</select>

	</form>
	<br>
	<table id="topiclist">
	</table>
	<div id="message"></div>
	<a href="topicoperation.html">Back</a>
</body>
</html>