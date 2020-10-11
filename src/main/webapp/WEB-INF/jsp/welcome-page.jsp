<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>БелМагУн</title>
<style><%@include file="/css/style.css"%></style>
<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.welcomepage" var="loc"
	scope="session" />

<fmt:message bundle="${loc}" key="message.welcome" var="message" />
<fmt:message bundle="${loc}" key="button.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="button.name.en" var="en_button" />
<fmt:message bundle="${loc}" key="button.name.by" var="by_button" />

<fmt:message bundle="${loc}" key="message.choose_form" var="enter" />
<fmt:message bundle="${loc}" key="button.name.registration"
	var="reg_button" />
<fmt:message bundle="${loc}" key="button.name.authorization"
	var="auth_button" />

<c:set var="adress" scope="session" value="WEB-INF/jsp/welcome-page.jsp" />
</head>
<body>


	<form action="Controller" method="post">
		<input type="hidden" name="command" value="change_language" /> <input
			type="hidden" name="local" value="ru" /> <input type="hidden"
			name="uri" value="${adress}" /> <input type="submit"
			value="${ru_button}" />
	</form>
	<form action="Controller" method="post">
		<input type="hidden" name="command" value="change_language" /> <input
			type="hidden" name="local" value="en" /> <input type="hidden"
			name="uri" value="${adress}" /><input type="submit"
			value="${en_button}" />
	</form>
	<form action="Controller" method="post">
		<input type="hidden" name="command" value="change_language" /> <input
			type="hidden" name="local" value="be" /> <input type="hidden"
			name="uri" value="${adress}" /><input type="submit"
			value="${by_button}" />
	</form>


	<h1 align="center">
		<c:out value="${message}" />
	</h1>

	<img src="image/welcome-cat.png" alt="Здесь должен был быть кот."
		height=200 align="right">

	<h3 align="center">
		<c:out value="${enter}" />
	</h3>

	<h2 align="center">
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="go_to_registration_page" />
			<input type="submit" value="${reg_button}" /><br />
		</form>
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="go_to_authorization_page" />
			<input type="submit" value="${auth_button}" /><br />
		</form>
	</h2>

<c:out value="${popularCourses}"/>

</body>
</html>