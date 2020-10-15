<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Authorization</title>

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.localization" var="loc"
	scope="session" />

<jsp:include page="/css/style.css" />

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

</head>

<body>
	<jsp:include page="header.jsp" />

	<hr>
	<div align="center">
		<c:out value="${messageFromServlet}" />

		<form action="Controller" method="post">
			<input type="hidden" name="command" value="authorization_command" />
			<c:out value="${message}" />
			<br>
			<table>
				<tr>
					<td><c:out value="${login}" /></td>
					<td><input type="text" name="login" value="" /></td>
				</tr>
				<tr>
					<td><c:out value="${password}" /></td>
					<td><input type="password" name="password" value="" /></td>
				</tr>

			</table>
			<br> <input type="submit" value="${send}" /> <br> <br>
			<input type="submit" value="${forgot_password}" /><br>
		</form>
	</div>
</body>
</html>