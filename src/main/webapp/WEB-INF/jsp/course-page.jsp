<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri = "/myTag" prefix = "my" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value="${title}"/></title>

<style>
<%@ include file="/css/style.css"%>
</style>

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.course_page.course_page" var="loc"
	scope="session" />

<fmt:message bundle="${loc}" key="description" var="description" />
<fmt:message bundle="${loc}" key="title" var="title" />
<fmt:message bundle="${loc}" key="program" var="program" />
<fmt:message bundle="${loc}" key="requirements" var="requirements" />
<fmt:message bundle="${loc}" key="duration" var="duration" />
<fmt:message bundle="${loc}" key="available_run_courses" var="available_run_courses" />
<fmt:message bundle="${loc}" key="no_available_run_courses" var="no_available_run_courses" />
<fmt:message bundle="${loc}" key="start_date" var="start_date" />
<fmt:message bundle="${loc}" key="end_date" var="end_date" />
<fmt:message bundle="${loc}" key="lecturer_name" var="lecturer_name" />
<fmt:message bundle="${loc}" key="shecule" var="shecule" />
<fmt:message bundle="${loc}" key="vacant_places" var="vacant_places" />
<fmt:message bundle="${loc}" key="status" var="status" />
<fmt:message bundle="${loc}" key="more" var="more" />
<fmt:message bundle="${loc}" key="course_ended" var="course_ended" />
<fmt:message bundle="${loc}" key="course_cancelled" var="course_cancelled" />
<fmt:message bundle="${loc}" key="course_recruting" var="course_recruting" />
<fmt:message bundle="${loc}" key="course_running" var="course_running" />

<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_course_page_command&courseId=${requestScope.course.courseId}" />

</head>
<body>
	<jsp:include page="header.jsp" />	


	<div class="inscription"><c:out value="${requestScope.course.courseName}"/></div>

	<table>
		<tr>
			<td><c:out value="${description}"/></td>
			<td><c:out value="${requestScope.course.courseDescription}"/></td>
		</tr>
		<tr>
			<td><c:out value="${program}"/></td>
			<td><c:out value="${requestScope.course.courseProgram}"/></td>
		</tr>
		<tr>
			<td><c:out value="${requirements}"/></td>
			<td><c:out value="${requestScope.course.courseRequirement}"/></td>
		</tr>
		<tr>
			<td><c:out value="${duration}"/></td>
			<td><c:out value="${requestScope.course.courseDuration}"/></td>
		</tr>
	</table>

		<div class="inscription"><c:out value="${available_run_courses}"/></div>
	
	<c:choose>
		<c:when test="${!empty requestScope.course.runCourses}">
			<table class="courses_table">
				<tr>
				<th><c:out value="${start_date}"/></th>
				<th><c:out value="${end_date}"/></th>
				<th><c:out value="${lecturer_name}"/></th>
				<th><c:out value="${shecule}"/></th>
				<th><c:out value="${vacant_places}"/></th>
				<th><c:out value="${status}"/></th>
				<th><c:out value="${more}"/></th>
			</tr>
			<c:forEach var="run_course" items="${requestScope.course.runCourses}">
				<tr>
					<td><my:formDate date="${run_course.dateOfStart}"/></td>
					<td><my:formDate date="${run_course.dateOfEnd}"/></td>
					<td><c:out value="${run_course.lecturerName}"/></td>
					<td><c:out value="${run_course.shedule}"/></td>
					<td><c:out value="${run_course.studentLimit - run_course.studentAmount}"/></td>
					<td><c:choose>
				<c:when test="${run_course.currentState == 1}"><c:out value="${course_canselled}"/> </c:when>
				<c:when test="${run_course.currentState== 2}"><c:out value="${course_ended}"/> </c:when>
				<c:when test="${run_course.currentState== 3}"><c:out value="${course_recruting}"/> </c:when>
				<c:when test="${run_course.currentState== 4}"><c:out value="${course_running}"/> </c:when>
				
			</c:choose></td>
					<td><form action="Controller" method="post">
					<input type="hidden" name="command" value="go_to_run_course_page" />
					<input type="hidden" name="run_course_id" value = "${run_course.runCourseId}"/>
					<input type="submit" value="${more}" />
					</form></td>
				</tr>
			</c:forEach>
			
			</table>
		</c:when>
		<c:otherwise>
			<div><c:out value="${no_available_run_courses}"/></div>
		</c:otherwise>
	</c:choose>

	<jsp:include page="footer.jsp" />

</body>
</html>