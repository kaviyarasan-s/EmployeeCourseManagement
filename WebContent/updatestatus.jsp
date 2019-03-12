<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update status</title>
<script src="script\jquery.min.js"></script>


<script>
	$(document).ready(
			function() {

				$("#course").change(
						function() {
							$('#topic').empty();
							$.post('ViewTopicServlet', {
								courseId : $("#course").val()
							}, function(response) {
								if (response != null && response != '') {
									var list = response.split(',');
									$('#topic').append(
											"<option value='Select'>"
													+ "Select" + "</option>");
									for ( var item in list) {
										$('#topic').append(
												"<option value="+list[item]+">"
														+ list[item]
														+ "</option>");
									}
								} else {
									$('#topic').append(
											"<option value='Select'>"
													+ "Topics not found"
													+ "</option>");
								}
							});

						});

				$("#topic").change(function() {
					$.ajax({
						type : "POST",
						url : "UpdateStatusServlet",
						data : {
							topicname : $("#topic").val(),
							coursename : $("#course").val(),
							button : "getoldstatus"
						},
						success : function(data) {
							if (data != '') {
								$('#status').val(data);
							} else {
								$('#statusmessage').val("Unable to update.");
							}

						},

					});

				});

			});
</script>
</head>
<body>

	<form method="post" action="UpdateStatusServlet">
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
			Status:<select name="statusname" id="status">
				<option value="Select">Select</option>
				<c:forEach var="statusList" items="${STATUSLIST}">
					<option value="${statusList.id}">${statusList.name}</option>
				</c:forEach>
			</select>
		</div>
		<div style="margin-top: 1%">
			<button type="submit" value="updatestatus" name='button'>Update
				status</button>
		</div>
	</form>

	<div>
		<%
			if (request.getAttribute("message") != null) {
				out.print(request.getAttribute("message"));
			}
		%>
	</div>
	<div id="statusmessage"></div>

	<div>
		<a href="HomeServlet">back</a>
	</div>
</body>
</html>