<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style><%@include file="/css/style.css"%></style>


<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="resources.localization" var="loc"
	scope="session" />
<fmt:message bundle="${loc}" key="registration.message.enter_data"
	var="message" />
<fmt:message bundle="${loc}" key="registration.parameter.userName"
	var="userName" />
<fmt:message bundle="${loc}" key="registration.parameter.password"
	var="password" />
<fmt:message bundle="${loc}" key="registration.parameter.first_name"
	var="firstName" />
<fmt:message bundle="${loc}" key="registration.parameter.second_name"
	var="secondName" />
<fmt:message bundle="${loc}" key="registration.parameter.email"
	var="email" />
<fmt:message bundle="${loc}" key="registration.parameter.patronymic"
	var="patronymic" />
<fmt:message bundle="${loc}" key="registration.parameter.position"
	var="position" />
<fmt:message bundle="${loc}" key="registration.button.send" var="send" />
<fmt:message bundle="${loc}" key="change_language.button.en"
	var="en_button" />
<fmt:message bundle="${loc}" key="change_language.button.ru"
	var="ru_button" />
<fmt:message bundle="${loc}" key="change_language.button.be"
	var="by_button" />
<fmt:message bundle="${loc}"
	key="registration.parameter.postiton_variant.student" var="student" />
<fmt:message bundle="${loc}"
	key="registration.parameter.postiton_variant.lecturer" var="lecturer" />
<fmt:message bundle="${loc}"
	key="registration.parameter.postiton_variant.dean" var="dean" />


<fmt:message bundle="${loc}" key="registration.parameter.faculty"
	var="faculty" />
<fmt:message bundle="${loc}"
	key="registration.parameter.faculty_variant.herbology_and_potions"
	var="herbology_and_potions" />
<fmt:message bundle="${loc}"
	key="registration.parameter.faculty_variant.applied_magic"
	var="applied_magic" />
<fmt:message bundle="${loc}"
	key="registration.parameter.faculty_variant.healing_witchcraft"
	var="healing_witchcraft" />
<fmt:message bundle="${loc}"
	key="registration.parameter.faculty_variant.astrology" var="astrology" />
<fmt:message bundle="${loc}"
	key="registration.parameter.faculty_variant.inhumanity"
	var="inhumanity" />
<fmt:message bundle="${loc}"
	key="registration.parameter.faculty_variant.shamanism" var="shamanism" />
<fmt:message bundle="${loc}"
	key="registration.parameter.faculty_variant.warlock" var="warlock" />
<fmt:message bundle="${loc}"
	key="registration.parameter.faculty_variant.jurisprudence"
	var="jurisprudence" />


<c:set var="adress" scope="session"
	value="WEB-INF/jsp/registration-page.jsp" />
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


	<form action="Controller" method="post">
		<input type="hidden" name="command" value="registration_command" />
		<c:out value="${message}" />
		<br />
		<c:out value="${userName}" />
		<input type="text" name="userlogin" required value="" /><br /> <br />
		<c:out value="${password}" />
		<input type="password" name="password" required value="" /><br />

		<c:out value="${email}" />
		<input type="email" name="user_email" required value="" /><br /> <br />
		<c:out value="${firstName}" />
		<input type="text" name="firstName" required value="" /><br /> <br />
		<c:out value="${secondName}" />
		<input type="text" name="secondName" required value="" /><br /> <br />
		<c:out value="${patronymic}" />
		<input type="text" name="patronymic" value="" /><br /> <br />

		<c:out value="${position}" />

		<input type="radio" name=position value="1">
		<c:out value="${student}" />
		<input type="radio" name="position" value="2">
		<c:out value="${lecturer}" />
		<input type="radio" name="position" value="3">
		<c:out value="${dean}" />
		<br>


		<c:out value="${faculty}" />
		<br> <input type="radio" name=faculty value="1">
		<c:out value="${herbology_and_potions}" />
		<br> <input type="radio" name=faculty value="2">
		<c:out value="${applied_magic}" />
		<br> <input type="radio" name=faculty value="3">
		<c:out value="${healing_witchcraft}" />
		<br> <input type="radio" name=faculty value="4">
		<c:out value="${astrology}" />
		<br> <input type="radio" name=faculty value="5">
		<c:out value="${inhumanity}" />
		<br> <input type="radio" name=faculty value="6">
		<c:out value="${shamanism}" />
		<br> <input type="radio" name=faculty value="7">
		<c:out value="${warlock}" />
		<br> <input type="radio" name=faculty value="8">
		<c:out value="${jurisprudence}" />
		<br> <input type="submit" value="${send}" /><br />
	</form>


</body>
</html>