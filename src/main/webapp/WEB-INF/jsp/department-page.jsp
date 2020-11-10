<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value="${requestScope.department.departmentName}"/></title>
<style>
<%@ include file="/css/style.css"%>
</style>

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.departments_page.departments_page" var="loc"
	scope="session" />

<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_current_department_page_command&department_id=${requestScope.department.departmentID}" />

<fmt:message bundle="${loc}" key="title" var="title" />
<fmt:message bundle="${loc}" key="our_dean" var="our_dean" />
<fmt:message bundle="${loc}" key="our_lecturers" var="our_lecturers" />
<fmt:message bundle="${loc}" key="our_courses" var="our_courses" />
<fmt:message bundle="${loc}" key="about_course" var="about_course" />
</head>
<body>
<jsp:include page="header.jsp" />

	<div class="inscription"><c:out value="${requestScope.department.departmentName}"/></div>
	
	<div class="inscription"><c:out value="${our_dean}"/></div>
	<table class="dept_table">
		<tr>
			<td><c:out value="${requestScope.department.deanName}"/>
			</td>
			<td><img src="${requestScope.department.dean.userPhotoLink}" width=200px>
			</td>
		</tr>
	</table>
	
	<div class="inscription"><c:out value="${our_lecturers}"/></div>
	<table  class="dept_table">
		<c:forEach var="lecturer" items="${requestScope.department.lecturers}">
			<c:if test="${lecturer.userRoleId == 2}">
			<tr>
				<td><c:out value="${lecturer.userFirstName}"/> 
					<c:if test="${lecturer.userPatronymic != null}">
						<c:out value="${lecturer.userPatronymic}"/> 
					</c:if>
				
				<c:out value="${lecturer.userSecondName}"/>
				</td>
				<td><img src="${lecturer.userPhotoLink}"  width=200px>
				</td>
			</tr>
			</c:if>
		</c:forEach>
		
	</table>
	
	
	
	<div class="inscription"><c:out value="${our_courses}"/></div>
	<table class="dept_table">
		<c:forEach var="course" items="${requestScope.department.courses}">
			<tr>
				<td><c:out value="${course.courseName}"/>
					</td>
				<td><c:out value="${course.courseDescription}"/>
				</td>
				<td>
				<form  action="Controller"	method="post">
					<input type="hidden" name="command" value="go_to_course_page_command" /> 
					<input type = "hidden" name="courseId" value="${course.courseId}"/>
					<input type="submit" value="${about_course}" />
					</form>
				</td>
			</tr>
		
		</c:forEach>
		
	</table>
	

<jsp:include page="footer.jsp" />
</body>
</html>