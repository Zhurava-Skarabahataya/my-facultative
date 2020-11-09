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

<jsp:include page="header.jsp" />

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.registration_page.registration"
	var="loc" scope="session" />
	
<fmt:message bundle="${loc}" key="registration.message.enter_data"
	var="message" />
<fmt:message bundle="${loc}" key="title"
	var="title" />
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
	
<fmt:message bundle="${loc}" key="Email_is_empty" var="Email_is_empty" />
<fmt:message bundle="${loc}"
	key="Email_does_not_correspond_to_the_norms"
	var="Email_does_not_correspond_to_the_norms" />
<fmt:message bundle="${loc}" key="Email_is_too_long"
	var="Email_is_too_long" />
<fmt:message bundle="${loc}" key="password_is_too_short"
	var="password_is_too_short" />
<fmt:message bundle="${loc}"
	key="password_contains_only_letters_and_numbers"
	var="password_contains_only_letters_and_numbers" />
<fmt:message bundle="${loc}" key="password_is_too_long"
	var="password_is_too_long" />
<fmt:message bundle="${loc}"
	key="first_and_second_name_must_not_be_empty"
	var="first_and_second_name_must_not_be_empty" />
<fmt:message bundle="${loc}" key="first_name_must_contain_only_letters"
	var="first_name_must_contain_only_letters" />
<fmt:message bundle="${loc}" key="second_name_must_contain_only_letters"
	var="second_name_must_contain_only_letters" />
<fmt:message bundle="${loc}" key="patronymic_must_contain_only_letters"
	var="patronymic_must_contain_only_letters" />
<fmt:message bundle="${loc}"
	key="first_and_second_name_must_be_shorter_45_symbols"
	var="first_and_second_name_must_be_shorter_45_symbols" />
<fmt:message bundle="${loc}" key="login_is_empty" var="login_is_empty" />
<fmt:message bundle="${loc}"
	key="login_must_contain_letters_numbers_and_underscore"
	var="login_must_contain_letters_numbers_and_underscore" />
<fmt:message bundle="${loc}" key="login_is_too_long"
	var="login_is_too_long" />
<fmt:message bundle="${loc}" key="data_is_empty" var="data_is_empty" />
<fmt:message bundle="${loc}" key="login_is_busy" var="login_is_busy" />
<fmt:message bundle="${loc}" key="email_is_busy" var="email_is_busy" />


<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_registration_page" />

</head>
<body>
	<c:if test="${requestScope.message_from_registration != null}">

		<div class="inscription">
			<c:choose>
				<c:when
					test="${requestScope.message_from_registration == 'Email_is_empty'}">
					<c:out value="${Email_is_empty}" />
				</c:when>
				<c:when
					test="${requestScope.message_from_registration == 'Email_does_not_correspond_to_the_norms'}">
					<c:out value="${Email_does_not_correspond_to_the_norms}" />
				</c:when>
				<c:when
					test="${requestScope.message_from_registration == 'Email_is_too_long'}">
					<c:out value="${Email_is_too_long}" />
				</c:when>
				<c:when
					test="${requestScope.message_from_registration == 'password_is_too_short'}">
					<c:out value="${password_is_too_short}" />
				</c:when>
				<c:when
					test="${requestScope.message_from_registration == 'password_contains_only_letters_and_numbers'}">
					<c:out value="${password_contains_only_letters_and_numbers}" />
				</c:when>
				<c:when
					test="${requestScope.message_from_registration == 'password_is_too_long'}">
					<c:out value="${password_is_too_long}" />
				</c:when>
				<c:when
					test="${requestScope.message_from_registration == 'first_and_second_name_must_not_be_empty'}">
					<c:out value="${first_and_second_name_must_not_be_empty}" />
				</c:when>
				<c:when
					test="${requestScope.message_from_registration == 'first_name_must_contain_only_letters'}">
					<c:out value="${first_name_must_contain_only_letters}" />
				</c:when>
				<c:when
					test="${requestScope.message_from_registration == 'second_name_must_contain_only_letters'}">
					<c:out value="${second_name_must_contain_only_letters}" />
				</c:when>
				<c:when
					test="${requestScope.message_from_registration == 'patronymic_must_contain_only_letters'}">
					<c:out value="${patronymic_must_contain_only_letters}" />
				</c:when>
				<c:when
					test="${requestScope.message_from_registration == 'first_and_second_name_must_be_shorter_45_symbols'}">
					<c:out value="${first_and_second_name_must_be_shorter_45_symbols}" />
				</c:when>
				<c:when
					test="${requestScope.message_from_registration == 'login_is_empty'}">
					<c:out value="${login_is_empty}" />
				</c:when>
				<c:when
					test="${requestScope.message_from_registration == 'login_must_contain_letters_numbers_and_underscore'}">
					<c:out value="${login_must_contain_letters_numbers_and_underscore}" />
				</c:when>
				<c:when
					test="${requestScope.message_from_registration == 'login_is_too_long'}">
					<c:out value="${login_is_too_long}" />
				</c:when>
				<c:when
					test="${requestScope.message_from_registration == 'data_is_empty'}">
					<c:out value="${data_is_empty}" />
				</c:when>
				<c:when
					test="${requestScope.message_from_registration == 'login_is_busy'}">
					<c:out value="${login_is_busy}" />
				</c:when>
				<c:when	test="${requestScope.message_from_registration == 'email_is_busy'}">
					<c:out value="${email_is_busy}" />
				</c:when>

			</c:choose>

		</div>
	</c:if>
	<form action="Controller" method="post" autocomplete="on">
		<input type="hidden" name="command" value="registration_command" />

		<div class="inscription">
			<c:out value="${message}" />
		</div>

		<br />
		<table>
			<tr>
				<td><c:out value="${userName}" /></td>
				<td><input type="text" name="userlogin" required value="" /><br />
					<br /></td>
			</tr>
			<tr>
				<td><c:out value="${password}" /></td>
				<td><input type="password" name="password" required value="" /><br />
				</td>
			</tr>
			<tr>
				<td><c:out value="${email}" /></td>
				<td><input type="email" name="user_email" required value="" /><br />
					<br /></td>
			</tr>
			<tr>
				<td><c:out value="${firstName}" /></td>
				<td><input type="text" name="firstName" required value="" /><br />
					<br /></td>
			</tr>
			<tr>
				<td><c:out value="${secondName}" /></td>
				<td><input type="text" name="secondName" required value="" /><br />
					<br /></td>
			</tr>
			<tr>
				<td><c:out value="${patronymic}" /></td>
				<td><input type="text" name="patronymic" value="" /><br /> <br />
				</td>
			</tr>
			<tr>
				<td><c:out value="${position}" /></td>
				<td><input type="radio" name=position required value="1"> <c:out
						value="${student}" /> <input type="radio" name="position"
					value="2"> <c:out value="${lecturer}" /> <input
					type="radio" name="position" value="3"> <c:out
						value="${dean}" /><br></td>

			</tr>
			<tr>
				<td><c:out value="${faculty}" /></td>
				<td><br> <input type="radio" name=faculty required value="1">
					<c:out value="${herbology_and_potions}" /> <br> <input
					type="radio" name=faculty value="2"> <c:out
						value="${applied_magic}" /> <br> <input type="radio"
					name=faculty value="3"> <c:out
						value="${healing_witchcraft}" /> <br> <input type="radio"
					name=faculty value="4"> <c:out value="${astrology}" /> <br>
					<input type="radio" name=faculty value="5"> <c:out
						value="${inhumanity}" /> <br> <input type="radio"
					name=faculty value="6"> <c:out value="${shamanism}" /> <br>
					<input type="radio" name=faculty value="7"> <c:out
						value="${warlock}" /> <br> <input type="radio" name=faculty
					value="8"> <c:out value="${jurisprudence}" /></td>

			</tr>

		</table>

		<br> <input type="submit" value="${send}" /><br />
	</form>
	<jsp:include page="footer.jsp" />

</body>
</html>