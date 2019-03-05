<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<title>Topics list</title>
<script>
	$(document).ready(
			function() {

				$("#course").change(
						function() {
							$('#topiclist').empty();
							$.post('ViewTopicServlet', {
								courseId : $("#course").val()
							}, function(response) {

								var list = response.split(',');
								var i = 0;
								$('#topiclist').append(
										"<tr><td>" + "S.no" + "</td>" + "<td>"
												+ "Name" + "</td></tr>");
								for ( var item in list) {
									i = i + 1;
									$('#topiclist')
											.append(
													"<tr><td>" + (i) + "</td>"
															+ "<td>"
															+ list[item]
															+ "</td></tr>");

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
	<br> Topics list:
	<table id="topiclist">
	</table>
	<a href="topicoperation.html">Back</a>
</body>
</html>