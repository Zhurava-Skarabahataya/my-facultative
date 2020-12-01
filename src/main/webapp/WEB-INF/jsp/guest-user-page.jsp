<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value="${title}"/></title>
<style>
<%@ include file="/css/style.css"%>
</style>


<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_another_user_page&userId=${requestScope.user.userId}" />

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.user_page.user_page" var="loc"
	scope="session" />
<fmt:message bundle="${loc}" key="user_profile"	var="user_profile" />
<fmt:message bundle="${loc}" key="title"	var="title" />
<fmt:message bundle="${loc}" key="name"	var="name" />
<fmt:message bundle="${loc}" key="no_photo"	var="no_photo" />
<fmt:message bundle="${loc}" key="position"	var="position" />
<fmt:message bundle="${loc}" key="faculty"	var="faculty" />
<fmt:message bundle="${loc}" key="adress"	var="adress" />
<fmt:message bundle="${loc}" key="phone"	var="phone" />
<fmt:message bundle="${loc}" key="see_student_rating"	var="see_student_rating" />
<fmt:message bundle="${loc}" key="see_student_run_courses"	var="see_student_run_courses" />
<fmt:message bundle="${loc}" key="expell_student"	var="expell_student" />
<fmt:message bundle="${loc}" key="see_lecturer_run_courses"	var="see_lecturer_run_courses" />
<fmt:message bundle="${loc}" key="fire_employee"	var="fire_employee" />
	
	</head>
<body>
	<jsp:include page="header.jsp" />
	
	<div class="inscription"><c:out value="${user_profile}"/></div>
	
	<table>
		<tr>
		<td><c:out value="${name}"/></td>
		<td><c:out value="${requestScope.user.userFirstName}"/> 
		<c:if test="${requestScope.user.userPatronymic != null}"><c:out value="${requestScope.user.userPatronymic}"/></c:if> 
		<c:out value="${requestScope.user.userSecondName}"/></td>
		</tr>
		<tr>
		<td><c:out value="${photo}"/></td>
		<td><img src="${requestScope.user.userPhotoLink}"
		 alt="${no_photo}"	height=200 width=200
			></td>
		</tr>
		<tr>
		<td><c:out value="${position}"/></td>
		<td><c:out value="${requestScope.user.userRole}"/></td>
		</tr>
		<tr>
		<td><c:out value="${faculty}"/></td>
		<td><c:out value="${requestScope.user.userFaculty}"/></td>
		</tr>
		<tr>
		<td><c:out value="${adress}"/></td>
		<td><c:out value="${requestScope.user.userAdress}"/></td>
		</tr>
		<tr>
		<td><c:out value="${phone}"/></td>
		<td><c:out value="${requestScope.user.userPhone}"/></td>
		</tr>
	
	</table>
	
	<c:if test="${requestScope.user.userRoleId == 1 }">
		
		<form action="Controller" method="post">
				<input type="hidden" name="command"
					value="view_student_rating_page" /> 
					<input type="hidden" name="userLogin" value="${requestScope.user.userLogin}"/>
					<input type="hidden" name="userId" value="${requestScope.user.userId}"/>
					
					<input type="submit" value="${see_student_rating}" />
		</form>
		
		<c:if test="${sessionScope.bean.userRoleId > 2 && 
		(sessionScope.bean.userFacultyId == requestScope.user.userFacultyId || sessionScope.bean.userRoleId == 4)}">
		<form action="Controller" method="post">
				<input type="hidden" name="command"
					value="view_another_user_run_courses" /> 
					<input type="hidden" name="userLogin" value="${requestScope.user.userLogin}"/>
					<input type="hidden" name="userId" value="${requestScope.user.userId}"/>
							<input type="hidden" name="userRole" value="${requestScope.user.userRoleId}"/>
						<input type="submit" value="${see_student_run_courses}" />
		</form>
		
		</c:if>
			
	
	</c:if>
	
	<c:if test="${requestScope.user.userRoleId > 1 && sessionScope.bean.userRoleId > 2 && (
	sessionScope.bean.userFacultyId == requestScope.user.userFacultyId ||  sessionScope.bean.userRoleId ==4) }">
		<c:if test="${requestScope.user.userRoleId != 3}">
		<form action="Controller" method="post">
				<input type="hidden" name="command"
					value="view_another_user_run_courses" /> 
				<input type="hidden" name="userId" value="${requestScope.user.userId}"/>
									<input type="hidden" name="userLogin" value="${requestScope.user.userLogin}"/>
				
					<input type="hidden" name="userRole" value="${requestScope.user.userRoleId}"/>
					<input type="submit" value="${see_lecturer_run_courses}" />
		</form>
		</c:if>
		<form action="Controller" method="post">
				<input type="hidden" name="command"
					value="fire_employee" /> 
										<input type="hidden" name="userId" value="${requestScope.user.userId}"/>
					
					<input type="submit" value="${fire_employee}" onclick="return confirm('Are you sure you want to fire?')" />
		</form>
	</c:if>
	
	
	<jsp:include page="footer.jsp" />

</body>
</html>