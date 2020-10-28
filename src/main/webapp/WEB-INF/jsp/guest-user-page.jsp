<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User page</title>
<style>
<%@ include file="/css/style.css"%>
</style>


<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_user_page" />

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.user_page.user_page" var="loc"
	scope="session" />
<fmt:message bundle="${loc}" key="user_profile"	var="user_profile" />
	
	</head>
<body>
	<jsp:include page="header.jsp" />
	
	<div class="prescription"><c:out value="${user_profile}"/></div>
	
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
					<input type="submit" value="${see_student_rating}" />
		</form>
		<c:if test="${sessionScope.bean.userRoleId > 2 && 
		(sessionScope.bean.userFacultyId == requestScope.user.userFacultyId || sessionScope.bean.userRoleId == 4)}">
		<form action="Controller" method="post">
				<input type="hidden" name="command"
					value="view_another_user_run_courses" /> 
					<input type="submit" value="${see_student_run_courses}" />
		</form>
		<form action="Controller" method="post">
				<input type="hidden" name="command"
					value="expell_student" /> 
					<input type="submit" value="${expell_student}" onclick="return confirm('Are you sure you want to expell?')" />
		</form>
		</c:if>
			
	
	</c:if>
	
	<c:if test="${requestScope.user.userRoleId > 1 && sessionScope.bean.userRoleId > 2 && (
	sessionScope.bean.userFacultyId == requestScope.user.userFacultyId ||  sessionScope.bean.userRoleId ==4) }">
		<form action="Controller" method="post">
				<input type="hidden" name="command"
					value="view_another_user_run_courses" /> 
					<input type="submit" value="${see_lecturer_run_courses}" />
		</form>
		<form action="Controller" method="post">
				<input type="hidden" name="command"
					value="fire_user_command" /> 
					<input type="submit" value="${fire_employee}" onclick="return confirm('Are you sure you want to fire?')" />
		</form>
	</c:if>
	
	
	
	
	
	<jsp:include page="footer.jsp" />

</body>
</html>