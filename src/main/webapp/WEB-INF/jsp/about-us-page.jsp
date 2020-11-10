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
</head>

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.about_us_page.about_us_page" var="loc"
	scope="session" />

<fmt:message bundle="${loc}" key="title" var="title" />
<fmt:message bundle="${loc}" key="history_message" var="history_message" />
<fmt:message bundle="${loc}" key="history_content_1" var="history_content_1" />
<fmt:message bundle="${loc}" key="history_content_2" var="history_content_2" />
<fmt:message bundle="${loc}" key="history_content_3" var="history_content_3" />
<fmt:message bundle="${loc}" key="history_content_4" var="history_content_4" />
<fmt:message bundle="${loc}" key="history_content_5" var="history_content_5" />
<fmt:message bundle="${loc}" key="history_content_6" var="history_content_6" />
<fmt:message bundle="${loc}" key="history_content_7" var="history_content_7" />
<fmt:message bundle="${loc}" key="rector_message" var="rector_message" />
<fmt:message bundle="${loc}" key="faculties_message" var="faculties_message" />

<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_about_university_page" />

<body>
<jsp:include page="header.jsp" />

	<div class="about_university">
		<h3><c:out value="${history_message}"/>
		</h3>
	<p> <c:out value="${history_content_1}"/><br></p>
	<p> <c:out value="${history_content_2}"/><br></p>
	<p> <c:out value="${history_content_3}"/><br></p>
	<p> <c:out value="${history_content_4}"/><br></p>
	<p> <c:out value="${history_content_5}"/><br></p>
	<p> <c:out value="${history_content_6}"/><br></p>
	<p> <c:out value="${history_content_7}"/><br></p>
	<p> <c:out value="${rector_message}"/><br></p>
	<img alt="" src="image/rector.jpg">
	<p> <c:out value="${faculties_message}"/><br></p>
	
	</div>

	<jsp:include page="footer.jsp" />

</body>
</html>