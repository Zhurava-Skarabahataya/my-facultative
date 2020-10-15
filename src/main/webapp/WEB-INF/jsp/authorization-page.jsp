<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<jsp:include page="/css/style.css" />

<c:set var="commandToLanguageChanger" scope="session"
	value="go_to_authorization_page" />
</head>

<body>
	<jsp:include page="header.jsp" />

	<hr>
	<div align="center">
		<c:out value="${messageFromServlet}" />
		
		<form action="Controller" method="post">
			<input type="hidden" name="command" value="authorization_command" />
			Введите логин и пароль: <br>
			<table>
				<tr>
					<td>Логин</td>
					<td><input type="text" name="login" value="" /></td>
				</tr>
				<tr>
					<td>Пароль</td>
					<td><input type="password" name="password" value="" /></td>
				</tr>

			</table>
			<br> <input type="submit" value="Отправить" /> <br> <br><input
				type="submit" value="Не помню пароль" /><br>
		</form>
	</div>
</body>
</html>