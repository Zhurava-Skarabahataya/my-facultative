<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style><%@include file="/css/style.css"%></style>

</head>
<body>
You are successfully registered!<br>
<form action="Controller" method="get">
		<input type="hidden" name="command" value="go_to_student_page" />
<input type="submit" value="Go to user page" />
</form>
</body>
</html>