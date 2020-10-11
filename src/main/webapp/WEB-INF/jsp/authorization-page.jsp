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

</head>
<body>
	AUTHHHHH

	<hr>
	<form action="Controller" method="post">
		<input type="hidden" name="command" value="authorization_command" />
		Введите логин и пароль: <br> Логин <input type="text"
			name="login" value="" /> <br> Пароль <input type="password"
			name="password" value="" /> <br> <input type="submit"
			value="Не помню пароль" /><br> <input type="submit" value="send" />
	</form>

</body>
</html>