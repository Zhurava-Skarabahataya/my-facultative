<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value="${title}" /></title>
<style>
<%@ include file="/css/style.css"%>
</style>

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.run_course_page.run_course_page"
	var="loc" scope="session" />


<fmt:message bundle="${loc}" key="title" var="title" />
<fmt:message bundle="${loc}" key="course_name" var="course_name" />
<fmt:message bundle="${loc}" key="department_name" var="department_name" />
<fmt:message bundle="${loc}" key="start_date" var="start_date" />
<fmt:message bundle="${loc}" key="end_date" var="end_date" />
<fmt:message bundle="${loc}" key="description" var="description" />
<fmt:message bundle="${loc}" key="course_program" var="course_program" />
<fmt:message bundle="${loc}" key="requirements" var="requirements" />
<fmt:message bundle="${loc}" key="duration" var="duration" />
<fmt:message bundle="${loc}" key="shcedule" var="shcedule" />
<fmt:message bundle="${loc}" key="lecturer" var="lecturer" />
<fmt:message bundle="${loc}" key="classroom" var="classroom" />
<fmt:message bundle="${loc}" key="students_limit" var="students_limit" />
<fmt:message bundle="${loc}" key="available_places"
	var="available_places" />
<fmt:message bundle="${loc}" key="apply" var="apply" />
<fmt:message bundle="${loc}" key="remove_application" var="remove_application" />
<fmt:message bundle="${loc}" key="course_status" var="course_status" />
<fmt:message bundle="${loc}" key="successfully_enrolled" var="successfully_enrolled" />
<fmt:message bundle="${loc}" key="dropped" var="dropped" />
<fmt:message bundle="${loc}" key="no_vacant_places"
	var="no_vacant_places" />


<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_run_course_page" />

</head>
<body>
	<jsp:include page="header.jsp" />
<div class="inscription"><c:out value="${requestScope.run_course.courseName}" /></div>


	<table class="courses_table">
		
		<tr>
			<td><c:out value="${department_name}" /></td>
			<td><c:out value="${run_course.infoAboutCourse.departmentName}" /></td>
		</tr>
		<tr>
			<td><c:out value="${start_date}" /></td>
			<td><c:out value="${run_course.dateOfStart}" /></td>
		</tr>
		<tr>
			<td><c:out value="${end_date}" /></td>
			<td><c:out value="${run_course.dateOfEnd}" /></td>
		</tr>
		<tr>
			<td><c:out value="${course_status}" /></td>
			<td><c:out value="${run_course.courseStatusName}" /></td>
		</tr>
		<tr>
			<td><c:out value="${description}" /></td>
			<td><c:out
					value="${run_course.infoAboutCourse.courseDescription}" /></td>
		</tr>
		<tr>
			<td><c:out value="${course_program}" /></td>
			<td><c:out value="${run_course.infoAboutCourse.courseProgram}" /></td>
		</tr>
		<tr>
			<td><c:out value="${requirements}" /></td>
			<td><c:out
					value="${run_course.infoAboutCourse.courseRequirement}" /></td>
		</tr>
		<tr>
			<td><c:out value="${lecturer}" /></td>
			<td><c:out value="${run_course.lecturerName}" />ФОТО<img alt=""
				src=""></td>
		</tr>
		<tr>
			<td><c:out value="${duration}" /></td>
			<td><c:out value="${run_course.infoAboutCourse.courseDuration}" /></td>
		</tr>
		<tr>
			<td><c:out value="${shcedule}" /></td>
			<td><c:out value="${run_course.shedule}" /></td>
		</tr>
		<tr>
			<td><c:out value="${classroom}" /></td>
			<td><c:out value="${run_course.classroomNumber}" /></td>
		</tr>
		<tr>
			<td><c:out value="${students_limit}" /></td>
			<td><c:out value="${run_course.studentLimit}" /></td>
		</tr>
		<tr>
			<td><c:out value="${available_places}" /></td>
			<td><c:out
					value="${run_course.studentLimit - run_course.studentAmount}" /></td>
		</tr>




	</table>
	<c:choose>
		
		<c:when test="${sessionScope.bean.userRoleId == 1}">
			<c:choose>
				<c:when test="${requestScope.user_approval_status_id == 0}">
					<c:choose>
						<c:when
							test="${(run_course.studentLimit - run_course.studentAmount) > 0}">
							<form  action="Controller" method="post">
								<input type="hidden" name="command" value="apply_for_course" />
								<input type="hidden" name="run_course_id"
									value="${run_course.runCourseId}" /> <input type="submit"
									value="${apply}" /><br />
							</form>
						</c:when>
						<c:otherwise>
							<c:out value="${no_vacant_places}" />
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:when test="${requestScope.user_approval_status_id == 1}">
					<form  action="Controller" method="post">
								<input type="hidden" name="command" value="apply_for_course" />
								<input type="hidden" name="run_course_id"
									value="${run_course.runCourseId}" /> <input type="submit"
									value="${remove_application}" /><br />
							</form>
				</c:when>
				<c:when test="${requestScope.user_approval_status_id == 2}">
					<c:out value="${successfully_enrolled}"/>
					<c:if test="${run_course.courseStatusName == Курс завершён}">ТУТ ФОРМА ОТЗЫВА</c:if>
				</c:when>
				<c:when test="${requestScope.user_approval_status_id == 3}">
					<div class="inscription"><c:out value="${dropped}"/></div>
				</c:when>

			</c:choose>

		</c:when>
		
		<c:when test="${sessionScope.bean.userRoleId > 1}">
			СПИСОК СТУДЕНТОВ
		</c:when>
		
		<c:otherwise>
			<div class="inscription">Для записи на курсы необходимо зарегистрироваться</div>
		</c:otherwise>


	</c:choose>


	<jsp:include page="footer.jsp" />

</body>
</html>