<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script	src="script\jquery.min.js"></script>

<title>Add status</title>
<script>
	$(document)
			.ready(
					function() {

						$("#course")
								.change(
										function() {

											$("#topic").empty();
											$.post(
															'ViewTopicServlet',
															{
																courseId : $(
																		"#course")
																		.val()
															},
															function(response) {
																console.log(response);
																if (response != null&&response!='') {
																	var list = response
																			.split(',');
																	$('#topic')
																			.append(
																					"<option value='Select'>Select</option>");
																	for ( var item in list) {

																		$('#topic')
																				.append(
																						"<option value="+list[item]+">"
																								+ list[item]
																								+ "</option>");
																	}
																}
																else
																	{
																	$('#topic')
																	.append(
																			"<option value='Select'>"
																					+ "Topics not found"
																					+ "</option>");
																	}

															});

										});

						$("#topic")
								.change(
										function() {
											$('#topicexists').text("");
											$.ajax({
														type : "POST",
														url : "AddStatusServlet",
														data : {
															topicname : $(
																	"#topic")
																	.val(),
															coursename : $(
																	"#course")
																	.val(),
															button : "checkstatusexist"
														},
														success : function(data) {

															if (data == '1') {
																$('#topicexists')
																		.text(
																				"status already updated.");
															}

														}

													});

										});

					});
</script>
</head>
<body>

	<form method="post" action="AddStatusServlet">
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
				<option value='Select'>Select</option>
			</select>
		</div>
		<div id="topicexists"></div>
		<div style="margin-top: 1%">
			Status:<select name="statusname" id="status">
				<option value="Select">Select</option>
				<c:forEach var="statusList" items="${STATUSLIST}">
					<option value="${statusList.id}">${statusList.name}</option>
				</c:forEach>
			</select>
		</div>
		<div style="margin-top: 1%">
			<button type="submit" name="button" value="addstatus">Add
				status</button>
		</div>
	</form>
	<%
		if (request.getAttribute("message") != null) {
			out.print(request.getAttribute("message"));
		}
	%>
	<td><a href="HomeServlet">back</a></td>

</body>
</html>