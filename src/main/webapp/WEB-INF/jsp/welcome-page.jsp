<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>БелМагУн</title>
<style>
<%@ include file="/css/style.css"%>
</style>
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

<c:set var="commandToLanguageChanger" scope="session" value="go_to_welcome_page" />
</head>
<body>
	<jsp:include page="header.jsp" />

	<div
		style="background-image: url(image/main-page-photo.jpg); background-size: contain; background-repeat: no-repeat; height: 600px;">


		<div>
		<h1 align="left" style="color: white;">
			<c:out value="${message}" />
		</h1>
		</div>

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

	</div>



	<img src="image/welcome-cat.png" alt="Здесь должен был быть кот."
		height=200 align="center">



	<c:out value="${popularCourses}" />
	<jsp:include page="footer.jsp" />

</body>
</html>