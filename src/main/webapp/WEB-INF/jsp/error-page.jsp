<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value="${title}"/></title>
</head>

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.error_page.error_page"	var="loc" scope="session" />

<fmt:message bundle="${loc}" key="title" var="title" />
<fmt:message bundle="${loc}" key="user_not_approved" var="user_not_approved" />
<fmt:message bundle="${loc}" key="user_not_authorized" var="user_not_authorized" />
<fmt:message bundle="${loc}" key="promblems_with_registration" var="promblems_with_registration" />
<fmt:message bundle="${loc}" key="promblems_with_authorization" var="promblems_with_authorization" />
<fmt:message bundle="${loc}" key="to_main_page" var="to_main_page" />
<fmt:message bundle="${loc}" key="to_user_page" var="to_user_page" />
<fmt:message bundle="${loc}" key="user_already_authorized" var="user_already_authorized" />
<fmt:message bundle="${loc}" key="server_error" var="server_error" />

<c:set var="commandToLanguageChanger" scope="session" value="go_to_error_page" />
<body>
	<jsp:include page="header.jsp" />

<div class="inscription">
<c:choose>
			<c:when test="${requestScope.errorMessage=='user_not_approved'}">
					<c:out value="${user_not_approved}" />
					<form action="Controller" method="post">
				<input type="hidden" name="command" value="go_to_user_page" />
				<input type="submit" value="${to_user_page}" /></form><br />
			</c:when>
			
			<c:when test="${requestScope.errorMessage=='user_not_authorized'}">
					<c:out value="${user_not_authorized}" />
			</c:when>
			<c:when test="${requestScope.errorMessage=='user_already_authorized'}">
					<c:out value="${user_already_authorized}" />
					<form action="Controller" method="post">
				<input type="hidden" name="command" value="go_to_user_page" />
				<input type="submit" value="${to_user_page}" /><br />
			</form>
			</c:when>
			<c:when test="${requestScope.errorMessage=='promblems_with_registration'}">
					<c:out value="${promblems_with_registration}" />
			</c:when>
			<c:when test="${requestScope.errorMessage=='promblems_with_authorization'}">
					<c:out value="${promblems_with_authorization}" />
			</c:when>
			<c:when test="${requestScope.errorMessage=='server_error'}">
					<c:out value="${server_error}" />
			</c:when>
			
			
			
			
			<c:otherwise>Что-то не так</c:otherwise>
		</c:choose>
</div>

			<form action="Controller" method="post">
				<input type="hidden" name="command" value="go_to_welcome_page" />
				<input type="submit" value="${to_main_page}"/><br />
			</form>
			
			
				<jsp:include page="footer.jsp" />
			
</body>
</html>