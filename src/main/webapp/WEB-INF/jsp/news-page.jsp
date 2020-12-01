<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value = "${news}"/></title>

<style>
<%@ include file="/css/style.css"%>
</style>

<fmt:setLocale value="${sessionScope.local}" />

<fmt:setBundle basename="localization.welcome_page.welcome_page"
	var="loc" scope="session" />

<fmt:message bundle="${loc}" key="news" var="news" />

<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_news_page" />
</head>
<body>
	<jsp:include page="header.jsp" />

		<div class="inscription">
		<c:out value = "${news}"/> </div>
		<br>
		
		<c:forEach var="pieceOfNews" items = "${requestScope.news}">
		<hr>
		<div style="background-color: #f1f1f1; 	opacity:0.9;
	border-radius: 10px;padding:15px; margin:15px;font-weight: bold;">
			<h3><c:out value="${pieceOfNews.title}"/></h3>
			<hr>
			<div style = "display:inline-block;font-weight: normal;margin:10px; padding:10px;">
				<div style="width:30%; float:left;margin:5px; padding:5px;"><img width = 100% src="${pieceOfNews.imagePath}"/></div>
				<div style="width:60%; float:left;margin:5px; padding:5px;"><c:out value = "${pieceOfNews.text}"/></div>
			</div>
		</div>
		
		
		</c:forEach>
		

	<jsp:include page="footer.jsp" />

</body>
</html>