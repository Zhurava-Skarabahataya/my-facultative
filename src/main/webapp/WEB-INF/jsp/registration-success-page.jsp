<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style><%@include file="/css/style.css"%></style>
<c:set var="commandToLanguageChanger" scope="session" value="go_to_registration_success_page" />

</head>
<body>
<jsp:include page="header.jsp" />

You are successfully registered!<br>
<form action="Controller" method="get">
		<input type="hidden" name="command" value="go_to_user_page" />
<input type="submit" value="Go to user page" />
</form>
<form action="Controller" method="get">
		<input type="hidden" name="command" value="go_to_welcome_page" />
<input type="submit" value="Go to welcome page" />
</form>
</body>
</html>