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

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.contacts.contacts" var="loc"
	scope="session" />

<fmt:message bundle="${loc}" key="contacts" var="contacts" />


</head>
<body>
	<jsp:include page="header.jsp" />	

ПРЕДМЕТ




	<jsp:include page="footer.jsp" />

</body>
</html>