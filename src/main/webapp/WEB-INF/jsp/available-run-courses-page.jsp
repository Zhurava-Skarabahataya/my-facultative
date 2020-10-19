<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value="${page_title}"/></title>
<style>
<%@ include file="/css/style.css"%>
</style>

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.available_run_courses.available_run_courses" var="loc"
	scope="session" />

<fmt:message bundle="${loc}" key="page_title" var="page_title" />
<fmt:message bundle="${loc}" key="available_courses" var="available_courses" />
<fmt:message bundle="${loc}" key="course_name" var="course_name" />
<fmt:message bundle="${loc}" key="start_date" var="start_date" />
<fmt:message bundle="${loc}" key="end_date" var="end_date" />
<fmt:message bundle="${loc}" key="lecturer_name" var="lecturer_name" />
<fmt:message bundle="${loc}" key="shecule" var="shecule" />
<fmt:message bundle="${loc}" key="vacant_places" var="vacant_places" />
<fmt:message bundle="${loc}" key="status" var="status" />
<fmt:message bundle="${loc}" key="more" var="more" />

<c:set var="commandToLanguageChanger" scope="session" value="go_to_available_run_courses_page" />


</head>
<body>
	<jsp:include page="header.jsp" />

	<c:choose>
		<c:when test="${requestScope.courses != null}">
			<div class="inscription"><c:out value="${available_courses}"/></div>
			<table class="courses_table">
			<tr>
				<th><c:out value="${course_name}"/></th>
				<th><c:out value="${start_date}"/></th>
				<th><c:out value="${end_date}"/></th>
				<th><c:out value="${lecturer_name}"/></th>
				<th><c:out value="${shecule}"/></th>
				<th><c:out value="${vacant_places}"/></th>
				<th><c:out value="${status}"/></th>
				<th><c:out value="${more}"/></th>
			</tr>
			
			
			<c:forEach var="run_course" items="${requestScope.courses}">
				<tr>
					<td><c:out value="${run_course.courseName}"/></td>
					<td><c:out value="${run_course.dateOfStart}"/></td>
					<td><c:out value="${run_course.dateOfEnd}"/></td>
					<td><c:out value="${run_course.lecturerName}"/></td>
					<td><c:out value="${run_course.shedule}"/></td>
					<td><c:out value="${run_course.studentLimit - run_course.studentAmount}"/></td>
					<td><c:out value="${run_course.courseStatusName}"/></td>
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
			<c:out value="Доступных курсов нет"/>
		</c:otherwise>
	
	
	</c:choose>
	
	
		
	


	<jsp:include page="footer.jsp" />

</body>
</html>