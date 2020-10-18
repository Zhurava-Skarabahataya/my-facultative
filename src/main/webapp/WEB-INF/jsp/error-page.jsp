<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error:(</title>
</head>
<body>
	<jsp:include page="header.jsp" />
<c:set var="commandToLanguageChanger" scope="session" value="go_to_error_page" />

Sorry:(
<h1>
<c:out value="${errorMessage}"/>
</h1>

<form action="Controller" method="post">
				<input type="hidden" name="command" value="go_to_welcome_page" />
				<input type="submit" value="Перейти на главную страницу" /><br />
			</form>
			
				<jsp:include page="footer.jsp" />
			
</body>
</html>