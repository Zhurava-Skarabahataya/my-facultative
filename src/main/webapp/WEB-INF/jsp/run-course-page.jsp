<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Карточка курса</title>
</head>
<body>
<jsp:include page="header.jsp" />
	КУРССС
	
	<c:out value="${requestScope.run_course.courseName}"/>
		<jsp:include page="footer.jsp" />
	
</body>
</html>