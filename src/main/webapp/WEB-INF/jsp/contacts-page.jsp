<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value="${contacts}"/></title>
<style>
<%@ include file="/css/style.css"%>
</style>

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.contacts.contacts" var="loc"
	scope="session" />

<fmt:message bundle="${loc}" key="contacts" var="contacts" />
<fmt:message bundle="${loc}" key="our_contacts" var="our_contacts" />
<fmt:message bundle="${loc}" key="adress" var="adress" />
<fmt:message bundle="${loc}" key="E-mail" var="E-mail" />
<fmt:message bundle="${loc}" key="rector_office" var="rector_office" />
<fmt:message bundle="${loc}" key="hr_dep" var="hr_dep" />
<fmt:message bundle="${loc}" key="chancellery" var="chancellery" />
<fmt:message bundle="${loc}" key="plan_and_fin" var="plan_and_fin" />
<fmt:message bundle="${loc}" key="past_grad" var="past_grad" />
<fmt:message bundle="${loc}" key="legal_dep" var="legal_dep" />
<fmt:message bundle="${loc}" key="adress_message" var="adress_message" />


<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_contacts_page" />
	
</head>
<body>
<jsp:include page="header.jsp" />

<div class="contacts">
<c:out value="${our_contacts}"/>

<table>

	<tr>
		<td>
			<c:out value="${adress_message}"/>
		</td>
		<td>
			<c:out value="${adress}"/>
		</td>
	</tr>
	<tr>
		<td>
			E-mail:
		</td>
		<td>
			belmaguniver@gmail.com
		</td>
	</tr>
	<tr>
		<td>
			<c:out value="${rector_office}"/>
		</td>
		<td>
			8 (017) 314 15 92
		</td>
		
	</tr>
	<tr>
		<td>
			<c:out value="${hr_dep}"/>
		</td>
		<td>
			8 (017) 217 28 48
		</td>
	</tr>
	<tr>
		<td>
			<c:out value="${chancellery}"/>
		</td>
		<td>
		8 (017) 666 13 66
		</td>
	</tr>
	<tr>
		<td>
			<c:out value="${past_grad}"/>
		</td>
		<td>
			8 (017) 744 11 22
		</td>
	</tr>
		<tr>
		<td>
			<c:out value="${legal_dep}"/>
		</td>
		<td>
			8 (017) 742 77 22
		</td>
	</tr>
		<tr>
		<td>
			<c:out value="${plan_and_fin}"/>
		</td>
		<td>
			8 (017) 323 11 22
		</td>
	</tr>

</table>

</div>

	<jsp:include page="footer.jsp" />

</body>
</html>