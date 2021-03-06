<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value="${authorization}"/></title>
<style>
<%@ include file="/css/style.css"%>
</style>
<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.authorization_page.authorization" var="loc"
	scope="session" />

<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_authorization_page" />

<fmt:message bundle="${loc}" key="authorization.message.enter_data"
	var="message" />
<fmt:message bundle="${loc}" key="authorization.field.password"
	var="password" />
<fmt:message bundle="${loc}" key="authorization.field.login" var="login" />
<fmt:message bundle="${loc}" key="authorization.button.send" var="send" />
<fmt:message bundle="${loc}" key="authorization.button.forgot_password"
	var="forgot_password" />
<fmt:message bundle="${loc}" key="no_user_found"	var="no_user_found" />
<fmt:message bundle="${loc}" key="authorization"	var="authorization" />


	<jsp:include page="header.jsp" />

</head>

<body>

	<body>
	<c:if test="${requestScope.message_from_authorization != null}">

		<div class="inscription">
			<c:choose>
				<c:when
					test="${requestScope.message_from_authorization == 'no_user_found'}">
					<c:out value="${no_user_found}" />
				</c:when>
				
			</c:choose>

		</div>
	</c:if>

	
	<div class="div_with_grey_background">
	<div align="center">
		

		<form action="Controller" method="post">
			<input type="hidden" name="command" value="authorization_command" />
			<c:out value="${message}" />
			<br>
			<table>
				<tr>
					<td><c:out value="${login}" /></td>
					<td><input type="text" required name="login" value="" /></td>
				</tr>
				<tr>
					<td><c:out value="${password}" /></td>
					<td><input type="password" required name="password" value="" /></td>
				</tr>

			</table>
			 <input type="submit" value="${send}" /> 
		<br>
		</form>
		
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="go_to_error_page" />
			<input type="hidden" name="errorMessage" value="forgot_password" />
			
			<input type="submit" value="${forgot_password}" />
		</form>
	</div>
	</div>
	
		<jsp:include page="footer.jsp" />
	
</body>
</html>