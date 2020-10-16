<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Создать новый курс</title>

<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_create_course_page" />

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.new_course_page.new_course"
	var="loc" scope="session" />
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
				<input type="hidden" name="command" value="go_to_create_course_page" />
				<input type="submit" value="${create_course}" /><br />
			</form>

		</c:if>
	</div>

	<div class="creatingCourse">
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="create_course_command" />
			<table>
				<tr>
					<td>Выберите название из списка доступных курсов</td>
					<td><select name="course" size="1">
							<option selected="selected" disabled><c:out
									value="Выберите курс" /></option>
							<c:forEach var="course" items="${requestScope.listOfCourses}">
								<option value="${course.courseId}"><c:out
										value="${course.courseName}" /></option>

							</c:forEach>

					</select></td>

				</tr>
				<tr>
					<td>Дата начала:</td>
					<td><input type="date" required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" name="startDate" value="" /></td>

				</tr>
				<tr>
					<td>Дата окончания:</td>
					<td><input type="date" required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" name="endDate" value="" /></td>

				</tr>
				<tr>
					<td>Расписание:</td>
					<td><input type="text" required name="shedule" value="" /></td>

				</tr>
				<tr>
					<td>Аудитория</td>
					<td><input type="text" required name="classroom" value="" /></td>

				</tr>
				<tr>
					<td>Максимальное количество студентов:</td>
					<td><input type="text" required pattern="^[ 0-9]+$"
						name="student_limit" value="" /></td>
				</tr>

			</table>
			
			<input type="submit" value="${send_create_course}">
		</form>
	</div>

</body>
</html>