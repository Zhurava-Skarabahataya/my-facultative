<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Мои Курсы</title>


<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.user_courses_page" var="loc"
	scope="session" />

<fmt:message bundle="${loc}" key="my_page" var="my_page" />
<fmt:message bundle="${loc}" key="my_courses" var="my_courses" />
<fmt:message bundle="${loc}" key="my_rating" var="my_rating" />
<fmt:message bundle="${loc}" key="create_course" var="create_course" />

<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_user_courses_page" />


</head>
<body>
	<jsp:include page="header.jsp" />

	<div class="navigate_buttons">

		<form style="display: inline" action="Controller" method="post">
			<input type="hidden" name="command" value="go_to_user_page" /> <input
				type="submit" value="${my_page}" /><br />
		</form>

		<form style="display: inline" action="Controller" method="post">
			<input type="hidden" name="command" value="go_to_user_courses_page" />
			<input type="submit" value="${my_courses}" /><br />
		</form>

		<c:if test="${sessionScope.bean.userRoleId == 1}">

			<form style="display: inline; float: center" action="Controller"
				method="post">
				<input type="hidden" name="command" value="go_to_user_rating_page" />
				<input type="submit" value="${my_rating}" /><br />
			</form>


		</c:if>
		<c:if test="${sessionScope.bean.userRoleId == 2}">

			<form style="display: inline; float: center" action="Controller"
				method="post">
				<input type="hidden" name="command" value="go_to_create_course_page" />
				<input type="submit" value="${create_course}" /><br />
			</form>

		</c:if>
	</div>

	<div>
		<c:if test="${sessionScope.bean.courses != null}"> 
	МОИ КУРСЫ:
		<table class="courses_info_table">
				<tr>
					<th>Название</th>
					<th>Дата старта</th>

					<th>Дата окончания</th>
					<th>Статус курса</th>
					<th>Расписание</th>
					<th>Аудитория</th>
					<c:if test="${sessionScope.bean.userRoleId > 1}">
						<th>Количество студентов на курсе</th>
						<th>Максимальное количество студентов</th>
					</c:if>

					<c:if test="${sessionScope.bean.userRoleId == 1}">
						<th>Преподаватель</th>
						<th>Осталось мест</th>
					</c:if>
					<c:if test="${sessionScope.bean.userRoleId == 1}">
						<th>Мой статус</th>
					</c:if>

					<th>Прочее</th>
				</tr>
				<c:forEach var="course" items="${sessionScope.bean.courses}">
					<tr>
						<td>
							<form action="Controller" method="post">
								<input type="hidden" name="command" value="go_to_course_page" />
								<c:out value="${course.courseName}" />
							</form>

						</td>
						<td><c:out value="${course.dateOfStart}" /></td>
						<td><c:out value="${course.dateOfEnd}" /></td>
						<td><c:out value="${course.courseStatusName}" /></td>

						<td><c:out value="${course.shedule}" /></td>
						<td><c:out value="${course.classroomNumber}" /></td>
						<c:if test="${sessionScope.bean.userRoleId > 1}">
							<td><c:out value="${course.studentAmount}" /></td>
							<td><c:out value="${course.studentLimit}" /></td>
						</c:if>

						<c:if test="${sessionScope.bean.userRoleId == 1}">
							<td><c:out value="${course.lecturerName}" /></td>
							<td><c:out
									value="${course.studentLimit - course.studentAmount}" /></td>
						</c:if>
						<c:if test="${sessionScope.bean.userRoleId == 1}">
							<td><c:out value="${course.studentStatusName}" /></td>
						</c:if>
						<td>

							<form action="Controller" method="post">
								<input type="hidden" name="command"
									value="go_to_run_course_page" /> 
									<input type="hidden" name="run_course_id"
									value="${course.runCourseId}" /><input type="submit"
									value="Подробности о курсе" />
							</form>

						</td>

					</tr>

				</c:forEach>

			</table>
		</c:if>
	</div>
	<br>
	<div style="padding: 15px">
		<c:if test="${sessionScope.bean.courses == null}"> 
	Пока нету курсов...<br>
			<br>

		</c:if>

		<c:if test="${sessionScope.bean.userRoleId == 1}">

			<form action="Controller" method="post">
				<input type="hidden" name="command"
					value="go_to_available_run_courses_page" /> <input type="submit"
					value="Просмотреть доступные курсы" />
			</form>

		</c:if>
	</div>




	<jsp:include page="footer.jsp" />

</body>
</html>