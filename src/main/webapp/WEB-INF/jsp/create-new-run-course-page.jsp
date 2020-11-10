<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title><c:out value="${create_new_run_course}"/></title>

<style>
<%@ include file="/css/style.css"%>
</style>

<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_create_course_page" />

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.new_course_page.new_course"
	var="loc" scope="session" />
<fmt:message bundle="${loc}" key="create_new_run_course" var="create_new_run_course" />
<fmt:message bundle="${loc}" key="choose_title_from_list" var="choose_title_from_list" />
<fmt:message bundle="${loc}" key="choose_course" var="choose_course" />
<fmt:message bundle="${loc}" key="start_date" var="start_date" />
<fmt:message bundle="${loc}" key="end_date" var="end_date" />
<fmt:message bundle="${loc}" key="schedule" var="schedule" />
<fmt:message bundle="${loc}" key="classroom" var="classroom" />
<fmt:message bundle="${loc}" key="max_amount_students" var="max_amount_students" />
<fmt:message bundle="${loc}" key="log_out" var="log_out" />

<fmt:message bundle="${loc}" key="button.my_page" var="my_page" />
<fmt:message bundle="${loc}" key="button.my_courses" var="my_courses" />
<fmt:message bundle="${loc}" key="button.create_course"
	var="create_course" />
<fmt:message bundle="${loc}" key="button.send_create_course"
	var="send_create_course" />

</head>
<body>
	<jsp:include page="header.jsp" />
	<div>

		<form style="display: inline" action="Controller" method="post">
			<input type="hidden" name="command" value="go_to_user_page" /> <input
				type="submit" value="${my_page}" /><br />
		</form>

		<form style="display: inline" action="Controller" method="post">
			<input type="hidden" name="command" value="go_to_user_courses_page" />
			<input type="submit" value="${my_courses}" /><br />
		</form>

		<c:if test="${sessionScope.bean.userRoleId > 1}">

			<form style="display: inline; float: center" action="Controller"
				method="post">
				<input type="hidden" name="command" value="go_to_create_run_course_page" />
				<input type="submit" value="${create_course}" /><br />
			</form>

		</c:if>
		<form style = "display:inline" action="Controller" method="post">
				<input type="hidden" name="command"
					value="logout" /> <input type="submit"
					value="${log_out}" /><br />
			</form>
	</div>

	<div class="creatingCourse">
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="create_course_command" />
			<table>
				<tr>
					<td><c:out value="${choose_title_from_list}"/></td>
					<td><select name="courseId" size="1">
							<option selected="selected" disabled><c:out
									value="${choose_course}" /></option>
							<c:forEach var="course" items="${requestScope.listOfCourses}">
								<option value="${course.courseId}"><c:out
										value="${course.courseName}" /></option>

							</c:forEach>

					</select></td>

				</tr>
				<tr>
					<td><c:out value="${start_date}"/></td>
					<td><input type="date" required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" name="startDate" min ="${requestScope.today}" value="" /></td>

				</tr>
				<tr>
					<td><c:out value="${end_date}"/></td>
					<td><input type="date" required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" name="endDate"   min ="${requestScope.today}"  value="" /></td>

				</tr>
				<tr>
					<td><c:out value="${schedule}"/></td>
					<td><input type="text" required name="shedule" value="" /></td>

				</tr>
				<tr>
					<td><c:out value="${classroom}"/></td>
					<td><input type="text" required name="classroom" pattern="^[0-9]+$" value="" /></td>

				</tr>
				<tr>
					<td><c:out value="${max_amount_students}"/></td>
					<td><input type="text" required pattern="^[ 0-9]+$"
						name="student_limit" value="" /></td>
				</tr>

			</table>
			
			<input type="submit" value="${send_create_course}">
		</form>
	</div>

	<jsp:include page="footer.jsp" />

</body>
</html>