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

<fmt:setBundle basename="localization.success_page.success_page"
	var="loc" scope="session" />

<fmt:message bundle="${loc}" key="title" var="title" />
<fmt:message bundle="${loc}" key="success_apply_message" var="success_apply_message" />
<fmt:message bundle="${loc}" key="success_remove_apply_message" var="success_remove_apply_message" />
<fmt:message bundle="${loc}" key="approve_student_message" var="approve_student_message" />
<fmt:message bundle="${loc}" key="return_to_run_course_page" var="return_to_run_course_page" />
<fmt:message bundle="${loc}" key="return_to_user_courses_page" var="return_to_user_courses_page" />
<fmt:message bundle="${loc}" key="return_to_user_page" var="return_to_user_page" />
<fmt:message bundle="${loc}" key="success_created_course_message" var="success_created_course_message" />
<fmt:message bundle="${loc}" key="success_registration" var="success_registration" />
<fmt:message bundle="${loc}" key="success_authorization" var="success_authorization" />
<fmt:message bundle="${loc}" key="success_student_approved" var="success_student_approved" />
<fmt:message bundle="${loc}" key="success_employee_disapproved" var="success_employee_disapproved" />
<fmt:message bundle="${loc}" key="success_employee_approved" var="success_employee_approved" />
<fmt:message bundle="${loc}" key="go_to_user_courses_page" var="go_to_user_courses_page" />
<fmt:message bundle="${loc}" key="success_leaving_feedback" var="success_leaving_feedback" />


</head>
<body>

<jsp:include page="header.jsp" />

	<div class="inscription">
		<c:choose>
			<c:when test="${requestScope.message=='success_apply'}">
					<c:out value="${success_apply_message}" />
			</c:when>
			<c:when test="${requestScope.message=='success_remove_apply'}">
					<c:out value="${success_remove_apply_message}" />
				
			</c:when>
			<c:when test="${requestScope.message=='success_leaving_feedback'}">
					<c:out value="${success_leaving_feedback}" />
					<form action="Controller" method="post">
				<input type="hidden" name="command"	value="go_to_user_courses_page" /> 
					<input type="submit" value="${go_to_user_courses_page}" /><br />
			</form>
				
			</c:when>
			<c:when test="${requestScope.message =='success_created_course'}">
					<c:out value="${success_created_course_message}" />
					
						<form action="Controller" method="post">
				<input type="hidden" name="command"	value="go_to_user_courses_page" /> 
					<input type="submit" value="${go_to_user_courses_page}" /><br />
			</form>
				
			</c:when>
			<c:when test="${requestScope.message =='success_registration'}">
					<c:out value="${success_registration}" />
				
			</c:when>
			<c:when test="${requestScope.message =='success_authorization'}">
					<c:out value="${success_authorization}" />
				
			</c:when>
			<c:when test="${requestScope.message =='success_student_approved'}">
					<c:out value="${success_student_approved}" />
				
			</c:when>
			<c:when test="${requestScope.message =='success_employee_approved'}">
					<c:out value="${success_employee_approved}" />
					<form action="Controller" method="post">
				<input type="hidden" name="command"	value="go_to_staff_page" /> 
					<input type="submit" value="${go_to_staff_page}" /><br />
			</form>
				
			</c:when>
			<c:when test="${requestScope.message =='success_employee_disapproved'}">
					<c:out value="${success_employee_disapproved}" />
						<form action="Controller" method="post">
				<input type="hidden" name="command"	value="go_to_staff_page" /> 
					<input type="submit" value="${go_to_staff_page}" /><br />
			</form>
				
			</c:when>
			<c:when test="${requestScope.message=='student_approved'}">
					<c:out value="${approve_student_message}" />
					<form action="Controller" method="post">
						<input type="hidden" name="command" value="go_to_run_course_page"/>
						<input type="hidden" name="run_course_id" value="${requestScope.run_course_id}"/>
						<input type="submit" value="${return_to_run_course_page}"/>
					</form>
			</c:when>
			<c:otherwise>Что-то не так</c:otherwise>
		</c:choose>
		
				
		
	</div>
	<form action="Controller" method="post">
				<input type="hidden" name="command" value="go_to_welcome_page" />
				<input type="submit" value="Перейти на главную страницу" /><br />
			</form>
			
	
					
					
					<form action="Controller" method="post">
						<input type="hidden" name="command" value="go_to_user_page"/>
						<input type="submit" value="${return_to_user_page}"/>
					</form>
	
<jsp:include page="footer.jsp" />

</body>
</html>